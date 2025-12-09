package aletca.task_6_9.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
public class ParallelNavigationTest {

    private static final ThreadLocal<Playwright> playwrightThreadLocal =
            ThreadLocal.withInitial(Playwright::create);

    @ParameterizedTest
    @CsvFileSource(resources = "/test-page.csv")
    void testPageLoad(String path, String expectedText, String browserType) {
        Playwright playwright = playwrightThreadLocal.get();

        try (Browser browser = createBrowser(playwright, browserType);
             BrowserContext context = browser.newContext();
             Page page = context.newPage()) {

            page.navigate("https://the-internet.herokuapp.com" + path);
            assertThat(page.locator("body")).containsText(expectedText);

        }
    }

    private Browser createBrowser(Playwright playwright, String browserType) {
        switch (browserType.toLowerCase()) {
            case "firefox":
                return playwright.firefox().launch();
            case "chromium":
            default:
                return playwright.chromium().launch();
        }
    }

    @AfterAll
    static void tearDown() {
        playwrightThreadLocal.get().close();
        playwrightThreadLocal.remove();
    }
}