package aletca.task_11.tests;

import org.junit.jupiter.api.Test;
import aletca.task_11.tests.base.TestBase;

import static org.junit.jupiter.api.Assertions.*;

public class DragDropTest extends TestBase {

    @Test
    public void testDragAndDrop() {
        var page = pageFactory.createDragDropPage();
        page.open();

        page.dragDropArea().dragAToB();

        assertEquals("A", page.dragDropArea().getTextB(),
                "После перетаскивания A → B, в колонке B должен быть текст 'A'");
    }
}

