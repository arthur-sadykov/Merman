package merman.util.dao;

/**
 * @author Arthur Sadykov
 */
public enum DatabaseName {
    MySQL("mysql"), MongoDB("mongodb"), Oracle("oracle");
    private final String name;

    DatabaseName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
