package aletca.task_11.utils;

public enum PlusUrl {
       DYNAMIC("/dynamic_loading/1"),
       CONTROLS("/dynamic_controls");

    private final String path;

    PlusUrl(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
