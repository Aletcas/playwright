package aletca.task_11.pages;

import com.microsoft.playwright.Page;

public class PageFactory {
    private final Page page;

    public PageFactory(Page page) {
        this.page = page;
    }

    public DragDropPage createDragDropPage() {
        return new DragDropPage(page);
    }
}
