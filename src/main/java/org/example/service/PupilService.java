package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Disciplines;
import org.example.model.Pupil;
import org.example.repository.PupilRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PupilService {
    private final PupilRepository pupilRepository;
    private final ObjectMapper objectMapper;
    private static int LAST_PUPIL_NUMBER;
    private static final String FILE_PATH = "C:\\Users\\22294779\\Documents\\Курс Java Junior to  Middle\\HW 2a\\data.txt";

    public PupilService(PupilRepository pupilRepository, ObjectMapper objectMapper) {
        this.pupilRepository = pupilRepository;
        this.objectMapper = objectMapper;
    }

    public static int generatePupilNumber() {
        return ++LAST_PUPIL_NUMBER;
    }

    public void addPupil(Scanner scanner) {
        System.out.println("Введите имя нового ученика.");
        pupilRepository.addPupil(scanner.next());
        System.out.println("Новый ученик успешно добавлен.");
    }

    public void deletePupil(Scanner scanner) {
        System.out.println("Введите номер удаляемого ученика.");
        Pupil deletedPupil = pupilRepository.deletePupil(scanner.nextInt());
        System.out.println(deletedPupil != null ? "Ученик успешно удалён." : "Ученик не найден.");
    }

    public void updateMark(Scanner scanner) {
        System.out.println("Введите номер ученика.");
        int number = scanner.nextInt();
        if (!pupilRepository.pupilExists(number)) {
            System.out.println("Ученик не найден (number = " + number + ").");
            return;
        }

        System.out.println("Введите имя дисциплины.");
        String disciplineName = scanner.next();
        Disciplines discipline = Disciplines.getDisciplineByName(disciplineName);
        if (discipline == null) {
            System.out.println("Дисциплина " + disciplineName + " не найдена.");
            return;
        }

        System.out.println("Введите новое значение оценки.");
        int value = scanner.nextInt();
        if (value < 1 || value > 5) {
            System.out.println("Оценка должна быть в диапазоне [1,...,5].");
            return;
        }

        pupilRepository.updateMark(number, discipline, value);
    }

    public void showMarksForAllPupils() {
        if (pupilRepository.dataIsEmpty()) {
            System.out.println("Нет заведённых данных.");
            return;
        }

        pupilRepository.showMarksForAllPupils();
    }

    public void showMarksForPupil(Scanner scanner) {
        System.out.println("Введите номер ученика.");
        int number = scanner.nextInt();
        if (!pupilRepository.pupilExists(number)) {
            System.out.println("Ученик не найден (number = " + number + ").");
            return;
        }

        pupilRepository.showMarksForPupil(number);
    }

    public void writeInFile() {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bw = new BufferedWriter(osw)) {

            var pupils = pupilRepository.getPupils();
            bw.write(objectMapper.writeValueAsString(pupils));
            System.out.println("Данные успешно записаны в файл.");
        } catch (Exception e) {
            System.out.println("Произошла ошибка! (\"" + e.getMessage() + "\")");
        }
    }

    public void readFromFile() {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                var pupilsFromFile = objectMapper.readValue(line, HashMap.class);
                var pupils = parsePupilsFromFile(pupilsFromFile);
                pupilRepository.setPupils(pupils);
            }
            System.out.println("Данные успешно загружены из файла.");
        } catch (Exception e) {
            System.out.println("Произошла ошибка! (\"" + e.getMessage() + "\")");
        }
    }

    private Map<Integer, Pupil> parsePupilsFromFile(Map<String, Object> pupilsFromFile) {
        Map<Integer, Pupil> pupils = new HashMap<>();
        Pupil pupil;
        Map<String, Object> entryValue;
        Map<Disciplines, Integer> marks;

        for (var entry : pupilsFromFile.entrySet()) {
            entryValue = (Map<String, Object>) entry.getValue();
            var marksF = (Map<String, Integer>) entryValue.get("marks");

            marks = new HashMap<>();
            for (var entryM : marksF.entrySet()) {
                marks.put(Disciplines.valueOf(entryM.getKey()), entryM.getValue());
            }

            pupil = new Pupil((Integer) entryValue.get("number"), (String) entryValue.get("name"), marks);

            pupils.put(pupil.getNumber(), pupil);
        }

        return pupils;
    }
}