package aletca.task_11.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class HoverTest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false) // Для визуального наблюдения установите false
                .setSlowMo(500)); // Замедление для наглядности
    }

    @BeforeEach
    void setup() {
        context = browser.newContext();
        page = context.newPage();
    }

    @Test
    void testHoverProfiles() {
        page.navigate("https://the-internet.herokuapp.com/hovers",
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));

        Locator figures = page.locator(".figure");
        int count = figures.count();

        for (int i = 0; i < count; i++) {
            Locator figure = figures.nth(i);
            figure.hover();

            // Ждем появления ссылки "View profile"
            Locator profile = figure.locator("text=View profile");
            profile.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE));

            // Проверяем, что ссылка видима
            assertTrue(profile.isVisible(),
                    "Ссылка 'View profile' должна быть видимой после наведения");

            // Кликаем
            profile.click();

            // Ждем загрузки страницы и проверяем URL
            page.waitForURL("**/users/*");
            String currentUrl = page.url();
            assertTrue(currentUrl.matches(".*/users/\\d+"),
                    "URL должен содержать /users/{id}. Текущий URL: " + currentUrl);

            // Возвращаемся назад
            page.goBack();

            // Ждем возврата на исходную страницу
            page.waitForURL("https://the-internet.herokuapp.com/hovers");
        }
    }

    @AfterEach
    void teardown() {
        if (context != null) {
            context.close();
        }
    }

    @AfterAll
    static void teardownClass() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
