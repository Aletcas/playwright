package aletca.task_10.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TodoApiTest {
    Playwright playwright;
    APIRequestContext requestContext;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        requestContext = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL("https://jsonplaceholder.typicode.com")
        );
    }

    @Test
    void testTodoApi() throws Exception {
        // 1. Выполнение GET-запроса напрямую через API
        APIResponse response = requestContext.get("/todos/1");

        // 2. Проверка статуса
        assertEquals(200, response.status());

        // 3. Парсинг JSON
        String responseBody = response.text();
        Map<String, Object> todo = objectMapper.readValue(responseBody, Map.class);

        // 4. Проверка структуры
        assertTrue(todo.containsKey("userId"), "Должно содержать userId");
        assertTrue(todo.containsKey("id"), "Должно содержать id");
        assertTrue(todo.containsKey("title"), "Должно содержать title");
        assertTrue(todo.containsKey("completed"), "Должно содержать completed");

        // Проверка типов данных
        assertInstanceOf(Integer.class, todo.get("userId"));
        assertInstanceOf(Integer.class, todo.get("id"));
        assertInstanceOf(String.class, todo.get("title"));
        assertInstanceOf(Boolean.class, todo.get("completed"));

        // Проверка конкретных значений
        assertEquals(1, todo.get("userId"));
        assertEquals(1, todo.get("id"));
        assertEquals("delectus aut autem", todo.get("title"));
        assertEquals(false, todo.get("completed"));
    }

    @AfterEach
    void tearDown() {
        if (requestContext != null) {
            requestContext.dispose();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}


