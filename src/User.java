public abstract class User {
    protected Table[] tables;

    public User(Table[] tables) {
        this.tables = tables;
    }

    public abstract void executeMenu();
}
