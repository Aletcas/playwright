package aletca.task_11.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;

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
        page.waitForCondition(() ->
                        message.isVisible() || !checkbox.isVisible(),
                new Page.WaitForConditionOptions().setTimeout(10000));
    }

    public boolean isCheckboxVisible() {
        return checkbox.isVisible();
    }
}



