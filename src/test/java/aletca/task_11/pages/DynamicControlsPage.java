package aletca.task_11.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class DynamicControlsPage {
    private final Page page;
    private final Locator removeButton;
    private final Locator checkbox;
    private final Locator message;

    public DynamicControlsPage(Page page) {
        this.page = page;
        this.removeButton = page.locator("button:has-text('Remove')");
        this.checkbox = page.locator("#checkbox");
        this.message = page.locator("#message");
    }

    public void clickRemoveButton() {
        removeButton.click();
        message.waitFor();
    }

    public boolean isCheckboxVisible() {
        return checkbox.isVisible();
    }
}



