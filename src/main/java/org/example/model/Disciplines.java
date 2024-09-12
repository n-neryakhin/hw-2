package org.example.model;

public enum Disciplines {
    LITERATURE("Литература"), MATHEMATICS("Математика");
    private final String name;

    Disciplines(String name) {
        this.name = name;
    }

    public static Disciplines getDisciplineByName(String name) {
        return switch (name) {
            case "Литература" -> LITERATURE;
            case "Математика" -> MATHEMATICS;
            default -> null;
        };
    }
}
