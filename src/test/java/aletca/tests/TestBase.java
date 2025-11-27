package aletca.tests;

import aletca.utils.Config;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;

public class TestBase {
    protected static final String BASE_URL = "https://the-internet.herokuapp.com";

    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    // Для ExtentReports
    protected static ExtentReports extent;
    protected ExtentTest test;
    private boolean testFailed = false;

    protected void startTracing() {
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true)); // тоже полезно
    }

    protected void stopAndSaveTracing(String fileName) {
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("target/traces/" + fileName)));
    }


    @BeforeAll
    public static void setUpBeforeAll() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("target/extent-report.html");
        reporter.config().setDocumentTitle("Playwright Test Report");
        extent = new ExtentReports();
        extent.attachReporter(reporter);

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(true)); // временно true
    }

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        // 1. Инициализация Playwright
        testFailed = false;
        context = browser.newContext();
        page = context.newPage();

        // 2. Инициализация ExtentReports
        test = extent.createTest(testInfo.getDisplayName(),
                "Тест JavaScript алертов");
    }


    @AfterEach
    public void tearDown() {
        if (testFailed) {
            takeScreenshot("FAILED_TEST_" + System.currentTimeMillis());
        }

        if (context != null) {
            context.close();
        }
    }

    @AfterAll
    public static void tearDownAfterAll() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
        if (extent != null) {
            extent.flush();
        }
    }

    protected void openPage(String path) {
        page.navigate(BASE_URL + path);
    }

    @io.qameta.allure.Attachment(type = "image/png", value = "Скриншот при падении: {screenshotName}")
    public byte[] takeScreenshot(String screenshotName) {
        try {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            Allure.addAttachment(screenshotName, "image/png",
                    new ByteArrayInputStream(screenshot), ".png");
            return screenshot;
        } catch (Exception e) {
            System.out.println("Не удалось сделать скриншот: " + e.getMessage());
            return new byte[0];
        }
    }

    // Метод для успешных скриншотов
    protected void takeSuccessScreenshot(String screenshotName) {
        try {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));

            // Для Allure
            Allure.addAttachment(screenshotName, "image/png",
                    new ByteArrayInputStream(screenshot), ".png");

            // Для ExtentReports
            if (test != null) {
                String base64Screenshot = java.util.Base64.getEncoder().encodeToString(screenshot);
                test.addScreenCaptureFromBase64String(base64Screenshot, screenshotName);
            }
        } catch (Exception e) {
            System.out.println("Не удалось сделать скриншот: " + e.getMessage());
        }
    }

    // Логирование в ExtentReports - ОДИН метод!
    protected void logToExtent(Status status, String message) {
        if (test != null) {
            test.log(status, message);
        }
    }
}