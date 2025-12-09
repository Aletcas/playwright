package aletca.task_11.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicControlsTest {
    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
    }

    @Test
    void testDynamicCheckbox() {
        // 1. Открываем страницу
        page.navigate("https://the-internet.herokuapp.com/dynamic_controls");

        // 2. Находим чекбокс с атрибутом type="checkbox"
        Locator checkbox = page.locator("input[type='checkbox']");
        assertTrue(checkbox.isVisible(), "Чекбокс должен быть видим при загрузке страницы");

        // 3. Кликаем на кнопку "Remove"
        Locator removeButton = page.locator("button:has-text('Remove')");
        removeButton.click();

        // 4. Ожидаем исчезновения чекбокса
        checkbox.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));

        // 5. Проверяем, что появляется текст "It's gone!"
        Locator message = page.locator("p#message");
        message.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertEquals("It's gone!", message.textContent());

        // 6. Кликаем на кнопку "Add"
        Locator addButton = page.locator("button:has-text('Add')");
        addButton.click();

        // 7. Проверяем, что чекбокс снова отображается
        checkbox.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertTrue(checkbox.isVisible(), "Чекбокс должен снова появиться после нажатия Add");
    }

    @AfterEach
    void tearDown() {
        page.close();
        browser.close();
        playwright.close();
    }
}

