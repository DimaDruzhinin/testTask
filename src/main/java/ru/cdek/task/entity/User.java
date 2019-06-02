package ru.cdek.task.entity;

public class User {

    /**
     * Идентификатор пользователя в хранилище.
     */
    private Long id;

    private String name;

    public User(String name) {
        this(name, null);
    }

    public User(String name, Long id) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
