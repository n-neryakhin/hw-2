package org.example.repository;

import org.example.model.Disciplines;
import org.example.model.Pupil;

import java.util.HashMap;
import java.util.Map;

public class PupilRepository {
    private Map<Integer, Pupil> pupils;

    public PupilRepository() {
        this.pupils = new HashMap<>();
    }

    public Map<Integer, Pupil> getPupils() {
        return pupils;
    }

    public void setPupils(Map<Integer, Pupil> pupils) {
        this.pupils = pupils;
    }

    public void addPupil(String name) {
        Pupil newPupil = new Pupil(name);
        pupils.put(newPupil.getNumber(), newPupil);
    }

    public Pupil deletePupil(int number) {
        return pupils.remove(number);
    }

    public void updateMark(int number, Disciplines discipline, int value) {
        Pupil pupil = pupils.get(number);
        pupil.setMark(discipline, value);
    }

    public boolean pupilExists(int number) {
        return pupils.get(number) != null;
    }

    public boolean dataIsEmpty() {
        return pupils.isEmpty();
    }

    public void showMarksForAllPupils() {
        for (var entry : pupils.entrySet()) {
            showInformationForPupil(entry.getValue());
        }
    }

    public void showMarksForPupil(int number) {
        Pupil pupil = pupils.get(number);
        showInformationForPupil(pupil);
    }

    private void showInformationForPupil(Pupil pupil) {
        System.out.println("---- " + pupil);

        Map<Disciplines, Integer> marks = pupil.getMarks();
        for (var entryM : marks.entrySet()) {
            System.out.println(entryM.getKey() + ": " + entryM.getValue());
        }
        System.out.println();
    }
}
