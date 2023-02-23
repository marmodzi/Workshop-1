package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.MissingFormatArgumentException;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {

        String[][] temporary = listToDo("/Users/mac/programowanie/Workshop-1/Workshop/src/tasks.csv");
        boolean ifQuit = false;

        try (Scanner scanner = new Scanner(System.in)) {
            while (!ifQuit) {
                options();
                String action = getTxtInfoFromUser();
                int number;
                switch (action) {
                    case "add":
                        temporary = addTask(temporary);
                        break;
                    case "list":
                        getList(temporary);
                        break;
                    case "remove":
                        System.out.println("Please select number to remove");
                        number = getIntInfoFromUser();
                        temporary = removeTask(temporary, number);
                        System.out.println("Value was successfuly deleted");
                        break;
                    case "quit":
                        System.out.println("Bye,bye");
                        saveList(temporary);
                        ifQuit=true;
                        break;
                    default:
                        System.out.println("Please select a correct option.");
                }
            }
        }catch(MissingFormatArgumentException e){
            System.err.println("Error");
        }
    }
    public static void options() {
        System.out.println(ConsoleColors.BLUE + "Please select an option" + ConsoleColors.RESET);
        String[] options = {"add", "remove", "list", "quit"};
        for (String option : options) {
            System.out.println(option);
        }
    }
    public static String getTxtInfoFromUser(){
        return new Scanner(System.in).nextLine();
    }
    public static int getIntInfoFromUser(){
        return new Scanner(System.in).nextInt();
    }
    public static String[][] listToDo (String fileName) {
        String line;
        int linenumber = 0;
        String[][] task;
        String[] task1;
        StringBuilder sb = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                sb.append(line);
                linenumber++;
            }
            task1 = sb.toString().split(",");

            task = new String[linenumber][3];
            int indextask1 = -1;

            for (int i = 0; i < linenumber; i++) {
                for (int j = 0; j < 3; j++) {
                    indextask1++;
                    task[i][j] = task1[indextask1];
                }
            } return task;
        } catch (FileNotFoundException e) {
            System.err.println("File not exist");
            return null;
        }
    }
    public static String[][] addTask(String[][] table){

        StringBuilder sb = new StringBuilder();

        System.out.println("Please add task description");
        sb.append(getTxtInfoFromUser()).append(",");
        System.out.println("Please add task due date");
        sb.append(getTxtInfoFromUser()).append(",");
        System.out.println("Is your task is important: true/false");
        sb.append(getTxtInfoFromUser()).append(",");

        String[] tableAdd2 = sb.toString().split(",");

        table = Arrays.copyOf(table, table.length+1);

        for (int i = 0; i < 3; i++) {
            table[table.length-1] = tableAdd2;
        }
        return table;
    }
    public static void getList(String[][] table){
        for (int i = 0; i < table.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < table[i].length ; j++) {
                System.out.print(table[i][j]+ " ");
            }
            System.out.println();
        }
    }
    public static String[][] removeTask(String[][] tab, int number){
        return ArrayUtils.remove(tab, number);
    }

    public static void saveList (String [][] table){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                sb.append(table[i][j]).append(",");
            }
            sb.append("\n");
        }
        try(FileWriter writer = (new FileWriter("/Users/mac/programowanie/Workshop-1/Workshop/src/tasks.csv",false))) {
            writer.write(sb.toString());
        } catch (IOException e) {
            System.err.println("Bład zapisu");
        }
    }
}