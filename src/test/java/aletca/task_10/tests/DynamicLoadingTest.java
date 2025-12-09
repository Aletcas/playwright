package aletca.task_11.tests;

import aletca.task_11.pages.DynamicallyLoadedPage;
import aletca.task_11.utils.PlusUrl;
import aletca.task_11.utils.Traceable;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicLoadingTest extends TestBase implements Traceable {
    @Test
    public void testDynamicLoadingWithNetworkMonitoring() {
        final boolean[] requestCompleted = {false};
        final int[] statusCode = {0};

        withTracing(context, "dynamic_loading_test", () -> {
            Allure.step("1. Переходим на страницу", () -> {
                openPage(PlusUrl.DYNAMIC);
            });

            DynamicallyLoadedPage loaded = new DynamicallyLoadedPage(page);

            Allure.step("2. Настраиваем перехват сетевых запросов", () -> {
                page.onResponse(response -> {
                    String url = response.url();
                    System.out.println("DEBUG: " + url + " - " + response.status());
                    if (url.contains("dynamic_loading")) {
                        requestCompleted[0] = true;
                        statusCode[0] = response.status();
                    }
                });
            });

            Allure.step("3. Запускаем процесс загрузки", () -> {
                loaded.clickStart();
            });

            Allure.step("4. Ожидаем завершения загрузки", () -> {
                // Ждем либо сетевой запрос, либо появление текста
                page.waitForCondition(() -> requestCompleted[0] ||
                                page.isVisible("#finish"),
                        new Page.WaitForConditionOptions().setTimeout(30000));
            });

            Allure.step("5. Проверяем статус", () -> {
                if (requestCompleted[0]) {
                    assertEquals(200, statusCode[0]);
                }
            });

            Allure.step("6. Проверяем текст", () -> {
                loaded.waitForTextToAppear();
                String actualText = loaded.getFinishText();
                assertEquals("Hello World!", actualText.trim());
            });
        });
    }
}