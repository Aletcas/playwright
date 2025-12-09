package aletca.task_11.pages;

import com.microsoft.playwright.Page;

public class DynamicallyLoadedPage {
    private final Page page;

    private final String startButton = "#start button";
    private final String finishText = "#finish";

    public DynamicallyLoadedPage(Page page) {
        this.page = page;
    }

    public void clickStart() {
        page.click(startButton, new Page.ClickOptions()
                .setTimeout(5000));
    }

    public void waitForTextToAppear() {
        page.waitForSelector(finishText, new Page.WaitForSelectorOptions()
                .setTimeout(10000));
    }

    public String getFinishText() {
        return page.textContent(finishText);
    }
}