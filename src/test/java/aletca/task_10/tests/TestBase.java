package aletca.task_11.tests;

import aletca.task_11.utils.PlusUrl;
import aletca.task_6_10.utils.Config;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;


public class TestBase {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    public static void setUpBeforeAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(Config.isHeadless())
                .setSlowMo(50)); // optional - для наглядности
    }

    @BeforeEach
    public void setUp() {
        context = browser.newContext();

        // Настройка контекста при необходимости
        context.setDefaultTimeout(Config.getTimeout());

        page = context.newPage();
    }

    @AfterEach
    public void tearDown() {
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

    // Упрощенные методы для навигации
    protected void openPage(PlusUrl page) {
        navigate(page.getPath());
    }

    protected void navigate(String path) {
        page.navigate(Config.getBaseUrl() + path);
    }

    protected void fill(String selector, String value) {
        page.fill(selector, value);
    }

    protected void click(String selector) {
        page.click(selector);
    }

    protected boolean isVisible(String selector) {
        return page.isVisible(selector);
    }
}