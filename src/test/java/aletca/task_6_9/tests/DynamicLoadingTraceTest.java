package aletca.task_6_9.tests;

import aletca.task_6_9.pages.DynamicallyLoadedPage;
import aletca.task_6_9.utils.Config;
import com.aventstack.extentreports.Status;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Тесты для the-internet.herokuapp.com")
@Feature("Запись и анализ трассировки")
@Story("Динамическая загрузка элемента")
@Severity(SeverityLevel.CRITICAL)
public class DynamicLoadingTraceTest extends TestBase {

    @Test
    void testDynamicLoadingTrace() {
        try {
            Files.createDirectories(Paths.get("target/traces"));
            openPage(Config.getDynamicPage());

            DynamicallyLoadedPage dynamicPage = new DynamicallyLoadedPage(page);
            startTracing();

            dynamicPage.clickStart();
            dynamicPage.waitForTextToAppear();

            String resultText = dynamicPage.getFinishText();
            assertEquals("Hello World!", resultText);

            stopAndSaveTracing("trace-dynamic-loading.zip");
            logToExtent(Status.PASS, "Трассировка сохранена, текст: " + resultText);

        } catch (Exception e) {
            logToExtent(Status.FAIL, "Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
