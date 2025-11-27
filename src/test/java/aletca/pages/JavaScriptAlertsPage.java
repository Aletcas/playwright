package aletca.pages;

import aletca.utils.Config;
import com.microsoft.playwright.Page;


public class JavaScriptAlertsPage {
    private final Page page;

    private final String heading = "h3";
    private final String jsAlertButton = "button[onclick='jsAlert()']";
    private final String jsConfirmButton = "button[onclick='jsConfirm()']";
    private final String jsPromptButton = "button[onclick='jsPrompt()']";
    private final String resultText = "#result";

    public void navigate() {
        page.navigate(Config.getBaseUrl() + Config.getAlertsPagePath());
    }

    public JavaScriptAlertsPage(Page page) {
        this.page = page;
    }

     public void clickJsAlert() {
        page.click(jsAlertButton);
    }

    public void clickJsConfirm() {
        page.click(jsConfirmButton);
    }

    public void clickJsPrompt() {
        page.click(jsPromptButton);
    }

    public String getResultText() {
        return page.textContent(resultText);
    }

    public String getHeadingText() {
        return page.textContent(heading);
    }

    public byte[] takeScreenshot() {
        return page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
    }
}
