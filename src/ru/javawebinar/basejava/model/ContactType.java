package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE_NUMBER("Телефон"),
    MOBILE("Мобильный телефон"),
    HOME_NUMBER("Домашний телефон"),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    EMAIL("Почта") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn") {
        @Override
        protected String toHtml0(String value) {
            return getLink(value);
        }
    },
    GITHUB("Профиль Github") {
        @Override
        protected String toHtml0(String value) {
            return getLink(value);
        }
    },
    STACKOVERFLOW("Профиль Stackoverflow") {
        @Override
        protected String toHtml0(String value) {
            return getLink(value);
        }
    },
    HOME_PAGE("Домашняя страница") {
        @Override
        protected String toHtml0(String value) {
            return getLink(value);
        }
    };

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return value == null ? "" : toHtml0(value);
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    protected String getLink(String value) {
        return getTitle() + ": <a href=http://" + value + ">" + value + "</a>";
    }
}