package aletca.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class CheckboxesPage {
    private final Page page;
    private final String path = "/checkboxes";

    // Локаторы
    private final Locator firstCheckbox;
    private final Locator secondCheckbox;
    private final Locator allCheckboxes;

    public CheckboxesPage(Page page) {
        this.page = page;
        this.firstCheckbox = page.locator("input[type='checkbox']").first();
        this.secondCheckbox = page.locator("input[type='checkbox']").nth(1);
        this.allCheckboxes = page.locator("input[type='checkbox']");
    }

    @Step("Открыть страницу с чекбоксами")
    public void open() {
        page.navigate(getFullUrl());
    }

    @Step("Получить полный URL страницы")
    public String getFullUrl() {
        return "https://the-internet.herokuapp.com" + path;
    }

    @Step("Установить первый чекбокс в состояние: {checked}")
    public void setFirstCheckbox(boolean checked) {
        boolean currentState = isFirstCheckboxChecked();
        if (currentState != checked) {
            firstCheckbox.click();
        }
    }

    @Step("Установить второй чекбокс в состояние: {checked}")
    public void setSecondCheckbox(boolean checked) {
        boolean currentState = isSecondCheckboxChecked();
        if (currentState != checked) {
            secondCheckbox.click();
        }
    }

    @Step("Кликнуть на первый чекбокс")
    public void clickFirstCheckbox() {
        firstCheckbox.click();
    }

    @Step("Кликнуть на второй чекбокс")
    public void clickSecondCheckbox() {
        secondCheckbox.click();
    }

    @Step("Получить состояние первого чекбокса")
    public boolean isFirstCheckboxChecked() {
        return firstCheckbox.isChecked();
    }

    @Step("Получить состояние второго чекбокса")
    public boolean isSecondCheckboxChecked() {
        return secondCheckbox.isChecked();
    }

    @Step("Установить все чекбоксы в состояние: {checked}")
    public void setAllCheckboxes(boolean checked) {
        allCheckboxes.all().forEach(checkbox -> {
            if (checkbox.isChecked() != checked) {
                checkbox.click();
            }
        });
    }
}

