package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE_NUMBER("Телефон"),
    SKYPE("Skype"),
    EMAIL("Почта"),
    ORG_SITE("Сайты организаций");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
