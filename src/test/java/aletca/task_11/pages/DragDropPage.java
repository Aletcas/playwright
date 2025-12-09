package aletca.task_11.pages;

import com.microsoft.playwright.Page;
import aletca.task_11.components.DragDropArea;
import aletca.task_11.pages.base.BasePage;

public class DragDropPage extends BasePage {
    private DragDropArea dragDropArea;

    public DragDropPage(Page page) {
        super(page);
    }

    public DragDropArea dragDropArea() {
        if (dragDropArea == null) {
            dragDropArea = new DragDropArea(page);
        }
        return dragDropArea;
    }

    public DragDropPage open() {
        navigateTo("https://the-internet.herokuapp.com/drag_and_drop");
        return this;
    }
}

