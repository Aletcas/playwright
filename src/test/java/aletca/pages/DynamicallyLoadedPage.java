package aletca.pages;

import com.microsoft.playwright.Page;

public class DynamicallyLoadedPage {
    private final Page page;

    private final String startButton = "#start button";
    private final String finishText = "#finish h4";

    public DynamicallyLoadedPage(Page page) {
        this.page = page;
    }

    public void clickStart() {
        page.click(startButton);
    }

    public void waitForTextToAppear() {
        // Просто ждем появления элемента с текстом
        page.waitForSelector(finishText);
    }

    public String getFinishText() {
        return page.textContent(finishText);
    }
}