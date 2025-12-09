package aletca.task_11.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MobileDragAndDropTest {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @BeforeEach
    void setup() {
        playwright = Playwright.create();

        Browser.NewContextOptions deviceOptions = new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (Linux; Android 12; SM-S908B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.0.0 Mobile Safari/537.36")
                .setViewportSize(384, 873)  // Разрешение экрана
                .setDeviceScaleFactor(3.5)
                .setIsMobile(true)
                .setHasTouch(true);

        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext(deviceOptions);
        page = context.newPage();
    }

    @AfterEach
    void tearDown() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

//    @Test
//    void testDragAndDropMobile() {
//        page.navigate("https://the-internet.herokuapp.com/drag_and_drop");
//
//        Locator columnA = page.locator("#column-a");
//        Locator columnB = page.locator("#column-b");
//
//        // 1. Проверка начального состояния
//        assertEquals("A", columnA.locator("header").textContent().trim(),
//                "Изначально в колонке A должен быть заголовок 'A'");
//        assertEquals("B", columnB.locator("header").textContent().trim(),
//                "Изначально в колонке B должен быть заголовок 'B'");
//
//        page.evaluate("() => {\n" +
//                "  const dataTransfer = new DataTransfer();\n" +
//                "  const event = new DragEvent('drop', { dataTransfer });\n" +
//                "  dataTransfer.setData('text/plain', 'A');\n" +
//                "  const columnA = document.querySelector('#column-a');\n" +
//                "  const columnB = document.querySelector('#column-b');\n" +
//                "  columnA.dispatchEvent(new DragEvent('dragstart', { \n" +
//                "    dataTransfer: dataTransfer,\n" +
//                "    bubbles: true\n" +
//                "  }));\n" +
//                "  columnB.dispatchEvent(new DragEvent('dragover', {\n" +
//                "    dataTransfer: dataTransfer,\n" +
//                "    bubbles: true\n" +
//                "  }));\n" +
//                "  columnB.dispatchEvent(event);\n" +
//                "  columnA.dispatchEvent(new DragEvent('dragend', {\n" +
//                "    dataTransfer: dataTransfer,\n" +
//                "    bubbles: true\n" +
//                "  }));\n" +
//                "}");
//
//         page.waitForFunction("document.querySelector('#column-a header').textContent.trim() === 'B'");
//        assertAll(
//                () -> assertEquals("A", columnB.locator("header").textContent().trim(),
//                        "Элемент A должен перейти в колонку B"),
//                () -> assertEquals("B", columnA.locator("header").textContent().trim(),
//                        "Элемент B должен перейти в колонку A")
//        );
//    }

    @Test
    void testDragAndDropMobile() {
        page.navigate("https://the-internet.herokuapp.com/drag_and_drop");

        Locator columnA = page.locator("#column-a");
        Locator columnB = page.locator("#column-b");

        assertThat(columnA).hasText("A");
        assertThat(columnB).hasText("B");

        columnA.dragTo(columnB);

        page.waitForTimeout(1000);
        assertThat(columnA).hasText("B");
        assertThat(columnB).hasText("A");
    }
}
