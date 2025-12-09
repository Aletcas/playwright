package aletca.task_11.utils;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;

import java.nio.file.Files;
import java.nio.file.Paths;

public interface Traceable {

    default void withTracing(BrowserContext context, String testName, Runnable test) {
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        try {
            test.run();
        } catch (Exception e) {
            // Сохраняем трассировку ДАЖЕ при падении теста!
            System.out.println("Test failed! Saving trace for debugging...");
            saveTrace(context, testName);
            throw e; // перебрасываем исключение
        } finally {
            // Всегда сохраняем если не было ошибки
            saveTrace(context, testName);
        }
    }

    private void saveTrace(BrowserContext context, String testName) {
        try {
            Files.createDirectories(Paths.get("target/traces"));
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("target/traces/" + testName + ".zip")));
        } catch (Exception e) {
            System.out.println("Failed to save trace: " + e.getMessage());
        }
    }
}



