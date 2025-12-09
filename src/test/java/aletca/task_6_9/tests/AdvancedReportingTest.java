package aletca.task_6_9.tests;

import aletca.task_6_9.pages.JavaScriptAlertsPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import com.aventstack.extentreports.Status;

@Epic("Тесты для the-internet.herokuapp.com")
@Feature("Работа с JavaScript-алертами")
public class AdvancedReportingTest extends TestBase {

    @Test
    @Story("Проверка JS Alert")
    @Description("Тест взаимодействия с JS Alert и проверка результата")
    @Severity(SeverityLevel.NORMAL)
    void testJavaScriptAlert() {
        try {
            JavaScriptAlertsPage alertsPage = new JavaScriptAlertsPage(page);
            navigateToAlertsPage(alertsPage);
            handleJsAlert(alertsPage);
            verifyAlertResult(alertsPage);

            takeSuccessScreenshot("Успешное выполнение JS Alert теста");
            logToExtent(Status.PASS, "Тест успешно завершен");

        } catch (Exception e) {
            logToExtent(Status.FAIL, "Тест упал с ошибкой: " + e.getMessage());
            throw e;
        }
    }

    @Step("Открыть и проверить страницу с алертами")
    private void navigateToAlertsPage(JavaScriptAlertsPage alertsPage) {
        openPage("/javascript_alerts");

        String heading = alertsPage.getHeadingText();
        if (!"JavaScript Alerts".equals(heading)) {
            throw new AssertionError("Заголовок страницы не соответствует ожидаемому");
        }
        logToExtent(Status.INFO, "Страница с алертами загружена");
    }

    @Step("Обработать JS Alert")
    private void handleJsAlert(JavaScriptAlertsPage alertsPage) {
        // Устанавливаем обработчик для алерта
        page.onceDialog(dialog -> {
            String message = dialog.message();
            logToExtent(Status.INFO, "Получен алерт с сообщением: " + message);
            dialog.accept();
        });

        alertsPage.clickJsAlert();
        logToExtent(Status.INFO, "Клик по кнопке JS Alert выполнен");
    }

    @Step("Проверить результат после алерта")
    private void verifyAlertResult(JavaScriptAlertsPage alertsPage) {

        page.waitForTimeout(1000);

        String resultText = alertsPage.getResultText();
        logToExtent(Status.INFO, "Получен результат: " + resultText);

        if (!resultText.contains("successfully")) {
            throw new AssertionError("Текст результата не содержит ожидаемую фразу");
        }

        if (!"You successfully clicked an alert".equals(resultText)) {
            throw new AssertionError("Ожидался текст 'You successfully clicked an alert', но получен: " + resultText);
        }
    }
}
