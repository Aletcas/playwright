package aletca.task_11.tests.base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import aletca.task_11.pages.PageFactory;

import java.nio.file.Paths;

public abstract class TestBase {
    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    protected Page page;
    protected PageFactory pageFactory;

    @BeforeAll
    public static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false) // для локальной разработки
                // .setHeadless(true) // для CI/CD
        );
    }

    @BeforeEach
    public void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
        pageFactory = new PageFactory(page);
    }

    @AfterEach
    public void closeContext() {
        if (context != null) {
            context.close();
        }
    }

    @AfterAll
    public static void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    protected boolean isHeadless = Boolean.parseBoolean(
            System.getProperty("headless", "true")
    );

    // Конфигурируемые браузеры:
    private static BrowserType getBrowserType(Playwright playwright) {
        String browserName = System.getProperty("browser", "chromium");
        switch (browserName.toLowerCase()) {
            case "firefox":
                return playwright.firefox();
            case "webkit":
                return playwright.webkit();
            default:
                return playwright.chromium();
        }
    }

    // Вспомогательные методы для всех тестов
    protected void takeScreenshot(String testName) {
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("screenshots/" + testName + ".png")));
    }
}
