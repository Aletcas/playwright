package aletca.task_11.tests;

import com.github.javafaker.Faker;
import com.microsoft.playwright.*;

public class FakerGenerTest {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();

            Faker faker = new Faker();
            String generatedName = faker.name().username();

            page.route("**/dynamic_content", route -> {
                route.fulfill(new Route.FulfillOptions()
                        .setBody("[\"" + generatedName + "\"]")
                        .setContentType("application/json"));
            });

            page.navigate("https://the-internet.herokuapp.com/dynamic_content");

            // Ждем появления текста на странице
            page.waitForSelector("text=" + generatedName);

            // Проверяем, что имя отображается
            boolean isNameDisplayed = page.locator("text=" + generatedName).isVisible();
            System.out.println(isNameDisplayed);
        }
    }
}