package aletca.task_11.pages.base;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public abstract class BasePage {
    protected final Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    public void navigateTo(String url) {
        page.navigate(url);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }
}



