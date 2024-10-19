package com.anschaucorp.spring_java.screenmatch.model;

public enum Category {
    ACAO("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    SCIFI("Sci-Fi"),
    DRAMA("Drama"),
    CRIME("Crime"),
    FANTASIA("Fantasy");

    private String categoryOmdb;

    Category(String categoryOmdb){
        this.categoryOmdb = categoryOmdb;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.categoryOmdb.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
