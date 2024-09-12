package org.example.model;

import org.example.service.PupilService;

import java.util.HashMap;
import java.util.Map;

public class Pupil {
    private final int number;
    private final String name;
    private final Map<Disciplines, Integer> marks;

    public Pupil(String name) {
        this.number = PupilService.generatePupilNumber();
        this.name = name;
        marks = new HashMap<>();
    }

    public Pupil(int number, String name, Map<Disciplines, Integer> marks) {
        this.number = number;
        this.name = name;
        this.marks = marks;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Map<Disciplines, Integer> getMarks() {
        return marks;
    }

    public void setMark(Disciplines discipline, int value) {
        marks.put(discipline, value);
    }

    @Override
    public String toString() {
        return "Pupil{" +
               "number=" + number +
               ", name='" + name + '\'' +
               '}';
    }
}