package aletca.task_11.context;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TestContext {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    public TestContext() {
        this.playwright = Playwright.create();
        this.browser = playwright.chromium().launch();
        this.context = browser.newContext();
        this.page = context.newPage();

        page.navigate("https://the-internet.herokuapp.com/dynamic_controls");
    }

    public Page getPage() {
        return page;
    }

    public void close() {
        // Закрываем в правильном порядке (обратном созданию)
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}