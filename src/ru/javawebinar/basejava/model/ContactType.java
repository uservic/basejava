package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE_NUMBER("Телефон"),
    MOBILE("Мобильный"),
    HOME_NUMBER("Домашний"),
    SKYPE("Skype"),
    EMAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль Github"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
