package aletca.task_6_9.tests;

import aletca.task_6_9.pages.CheckboxesPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Web UI Testing")
@Feature("Checkboxes Functionality")
@DisplayName("Тесты для страницы Checkboxes")
public class CheckboxesTest extends TestBase {

    private CheckboxesPage checkboxesPage;

    @BeforeEach
    @Step("Инициализация Page Object")
    public void initPage() {
        checkboxesPage = new CheckboxesPage(page);
    }

    @Test
    @Step("Проверка переключения первого чекбокса")
    public void testFirstCheckboxToggle() {
        openCheckboxesPage();
        setInitialCheckboxState();
        toggleFirstCheckbox();
        verifyFirstCheckboxState();
    }

    @Test
    @Step("Проверка независимости чекбоксов")
    public void testCheckboxesIndependence() {
        openCheckboxesPage();
        setSpecificCheckboxState();
        toggleSecondCheckbox();
        verifyCheckboxesIndependence();
    }

    @Step("Открыть страницу Checkboxes")
    private void openCheckboxesPage() {
        checkboxesPage.open();
    }

    @Step("Установить начальное состояние чекбоксов")
    private void setInitialCheckboxState() {
        checkboxesPage.setFirstCheckbox(false);
        checkboxesPage.setSecondCheckbox(true);
    }

    @Step("Установить специфическое состояние чекбоксов")
    private void setSpecificCheckboxState() {
        checkboxesPage.setFirstCheckbox(true);
        checkboxesPage.setSecondCheckbox(false);
    }

    @Step("Переключить первый чекбокс")
    private void toggleFirstCheckbox() {
        checkboxesPage.clickFirstCheckbox();
    }

    @Step("Переключить второй чекбокс")
    private void toggleSecondCheckbox() {
        checkboxesPage.clickSecondCheckbox();
    }

    @Step("Проверить состояние первого чекбокса")
    private void verifyFirstCheckboxState() {
        try {
            assertThat(checkboxesPage.isFirstCheckboxChecked())
                    .as("Первый чекбокс должен быть отмечен после клика")
                    .isTrue();
        } catch (AssertionError e) {
            throw e;
        }
    }

    @Step("Проверить независимость чекбоксов")
    private void verifyCheckboxesIndependence() {
        try {
            assertThat(checkboxesPage.isFirstCheckboxChecked())
                    .as("Первый чекбокс не должен измениться при клике на второй")
                    .isTrue();
            assertThat(checkboxesPage.isSecondCheckboxChecked())
                    .as("Второй чекбокс должен быть отмечен после клика")
                    .isTrue();
        } catch (AssertionError e) {
            throw e;
        }
    }
}
