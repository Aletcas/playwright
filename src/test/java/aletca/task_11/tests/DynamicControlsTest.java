package aletca.task_11.tests;

import aletca.task_11.context.TestContext;
import aletca.task_11.pages.DynamicControlsPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DynamicControlsTest {

    @Test
    public void testCheckboxRemoval() {
        TestContext context = new TestContext();

        try {
            DynamicControlsPage page = new DynamicControlsPage(context.getPage());

            assertTrue(page.isCheckboxVisible());

            page.clickRemoveButton();

            assertFalse(page.isCheckboxVisible(),
                    "Чекбокс должен исчезнуть после нажатия кнопки Remove");

        } finally {
            context.close();
        }
    }
}