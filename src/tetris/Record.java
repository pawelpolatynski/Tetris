package tetris;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Record {

    private String name;
    private int score;

    public Record (String name, int score) {
        this.name = name;
        this.score = score;
    }

    public static void saveTypes(LinkedList<Record> savedRecords) {
        File file = new File("./records.txt");
        try {
            FileWriter writer = new FileWriter(file); // overwrites the file
            for (Record r: savedRecords) {
                writer.write(r.name + ";" + r.score);
                writer.write("\n");
            }

            writer.close();
        }
        catch (IOException e) {
            System.out.println("IOException occured while saving records!");
        }
    }

    public static void loadTypes(LinkedList<Record> loadedRecords) {
        File file = new File("./records.txt");
        if (file.exists()) {
            try {
                Scanner reader = new Scanner(file); // overwrites the file
                while (reader.hasNext()) {
                    String text = reader.nextLine();
                    String[] line = text.split(";");
                    loadedRecords.add(new Record(line[0], Integer.parseInt(line[1])));
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("IOException occured while loading records!");
            }
        }
    }
}
