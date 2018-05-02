package ru.cdek.task.status;

public enum ResponseStatus {

    SUCCESS(0, "Запрос обработан успешно"),
    USER_ALREADY_EXISTS(1, "Запрос корректный, но пользователь с таким именем уже существует"),
    INVALID_REQUEST(1, "Запрос не соответствует схеме, либо пользователь не прошел валидацию"),
    INTERNAL_ERROR(2, "Внутренняя ошибка сервера");


    private int code;
    private String description;

    ResponseStatus(int code) {
        this(code, null);
    }

    ResponseStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }


}
