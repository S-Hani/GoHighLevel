package navigation;

public enum NavigationPaths {
    DASHBOARD("dashboard"),
    CONVERSATIONS("conversations/conversations"),
    CONVERSATION_TEMPLATES("conversations/templates"),
    CONTACTS_SMART_LIST("contacts/smart_list/All");

    private String path;

    NavigationPaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
