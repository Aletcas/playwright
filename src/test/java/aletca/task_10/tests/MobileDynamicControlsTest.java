package aletca.task_11.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MobileDynamicControlsTest {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();

        // Настройка параметров iPad Pro 11
        Browser.NewContextOptions deviceOptions = new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (iPad; CPU OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko)")
                .setViewportSize(834, 1194)
                .setDeviceScaleFactor(2)
                .setIsMobile(true)
                .setHasTouch(true);

        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)); // Установите headless на false для визуализации
        context = browser.newContext(deviceOptions);
        page = context.newPage();
    }

    @Test
    void testInputEnabling() {
        page.navigate("https://the-internet.herokuapp.com/dynamic_controls");
        Locator enableButton = page.locator("button:has-text('Enable')");
        enableButton.click();

        Locator message = page.locator("#message");
        message.waitFor(new Locator.WaitForOptions().setTimeout(10000));

        Locator inputField = page.locator("input[type='text']");
        boolean isEnabled = inputField.isEnabled();

        assertTrue(isEnabled, "Поле ввода должно быть активным после клика на кнопку Enable");
    }


    @AfterEach
    void tearDown() {
        context.close();
        browser.close();
        playwright.close();
    }
}
