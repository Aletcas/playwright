package aletca.tests;

import com.microsoft.playwright.*;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {
    protected static final String BASE_URL = "https://the-internet.herokuapp.com";

    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    private boolean testFailed = false;

    @BeforeAll
    public static void setUpBeforeAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)); // true для CI
    }

    @BeforeEach
    public void setUp() {
        testFailed = false; // сбрасываем флаг перед каждым тестом
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    public void tearDown() {
        if (testFailed) {
            takeScreenshot("FAILED_TEST_" + System.currentTimeMillis());
        }

        if (context != null) {
            context.close();
        }
    }

    @AfterAll
    public static void tearDownAfterAll() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    // Метод для пометки теста как упавшего
    protected void markTestAsFailed() {
        testFailed = true;
    }

    @Attachment(type = "image/png", value = "Скриншот при падении: {screenshotName}")
    public byte[] takeScreenshot(String screenshotName) {
        try {
            return page.screenshot(new Page.ScreenshotOptions()
                    .setFullPage(true));
        } catch (Exception e) {
            System.out.println("Не удалось сделать скриншот: " + e.getMessage());
            return new byte[0];
        }
    }

    protected void openPage(String path) {
        page.navigate(BASE_URL + path);
    }
}
