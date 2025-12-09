package aletca.task_11.tests;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.FilePayload;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class FileUploadTest {
    private static Playwright playwright;
    private static APIRequestContext request;

    @BeforeAll
    static void setUp() {
        playwright = Playwright.create();
        request = playwright.request().newContext();
    }

    @AfterAll
    static void tearDown() {
        if (request != null) {
            request.dispose();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test
    void testFileUploadAndDownload() {
        // Генерация тестового PNG файла в памяти
        byte[] testFile = createTestPNG();

        APIResponse uploadResponse = request.post(
                "https://httpbin.org/post",
                RequestOptions.create().setMultipart(
                        FormData.create()
                                .set("file", new FilePayload("test.png", "image/png", testFile))
                )
        );

        // Проверка получения файла
        String responseBody = uploadResponse.text();
        assertTrue(responseBody.contains("data:image/png;base64"),
                "Ответ должен содержать base64 PNG данные");

        // Верификация содержимого
        String base64Data = responseBody.split("\"file\": \"")[1].split("\"")[0];
        byte[] receivedBytes = Base64.getDecoder().decode(base64Data.split(",")[1]);
        assertArrayEquals(testFile, receivedBytes,
                "Загруженный файл должен совпадать с оригинальным");

        // Скачивание и проверка эталона
        APIResponse downloadResponse = request.get("https://httpbin.org/image/png");

        // Проверка MIME-типа
        assertEquals("image/png", downloadResponse.headers().get("content-type"),
                "Content-Type должен быть image/png");

        // Проверка сигнатуры PNG
        byte[] content = downloadResponse.body();
        assertEquals(0x89, content[0] & 0xFF, "Первый байт PNG сигнатуры должен быть 0x89");
        assertEquals(0x50, content[1] & 0xFF, "Второй байт PNG сигнатуры должен быть 'P' (0x50)");
        assertEquals(0x4E, content[2] & 0xFF, "Третий байт PNG сигнатуры должен быть 'N' (0x4E)");
        assertEquals(0x47, content[3] & 0xFF, "Четвертый байт PNG сигнатуры должен быть 'G' (0x47)");
    }

    private byte[] createTestPNG() {
        // Создаем минимальный валидный PNG файл
        return new byte[]{
                (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A // PNG сигнатура
        };
    }
}