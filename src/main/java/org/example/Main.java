package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.repository.PupilRepository;
import org.example.service.PupilService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        showMenu();

        PupilService pupilService = new PupilService(new PupilRepository(), new ObjectMapper());
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String menuItem = scanner.next();
            switch (menuItem) {
                case "a" -> pupilService.addPupil(scanner);
                case "b" -> pupilService.deletePupil(scanner);
                case "c" -> pupilService.updateMark(scanner);
                case "d" -> pupilService.showMarksForAllPupils();
                case "e" -> pupilService.showMarksForPupil(scanner);
                case "f" -> pupilService.writeInFile();
                case "g" -> pupilService.readFromFile();
            }
        }
    }

    private static void showMenu() {
        System.out.println("a. Добавьте нового ученика");
        System.out.println("b. Удалите ученика");
        System.out.println("c. Обновите оценку ученика");
        System.out.println("d. Просмотр оценок всех учащихся");
        System.out.println("e. Просмотр оценок конкретного учащегося");
        System.out.println("f. Записать в файл");
        System.out.println("g. Прочитать из файла");
        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.println();
    }
}