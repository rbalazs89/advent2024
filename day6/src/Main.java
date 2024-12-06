import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static int[][] table;
    public static int[][] table2;
    public static int[] currentPos = new int[3];
    public static int[] currentPos2 = new int[3];
    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        firstPart();
        secondPart();
        //printoutTable();
    }

    public static void firstPart(){
        int maxY = table.length;
        int maxX = table[0].length;
        // currentPos[0] -> y
        // currentPos[1] -> x
        // currentPos[2] -> direction

        int[] step = new int[2];
        while(currentPos[0] >= 0 && currentPos[0] < maxY && currentPos[1] >= 0 && currentPos[1] < maxX ){
            //change table to visited:
            if(!(table[currentPos[0]][currentPos[1]] == 1)){
                table[currentPos[0]][currentPos[1]] = -1;
            }

            //step:
            currentPos[0] = currentPos[0] + step[0];
            currentPos[1] = currentPos[1] + step[1];

            if(!(currentPos[0] >= 0 && currentPos[0] < maxY && currentPos[1] >= 0 && currentPos[1] < maxX)){
                break;
            }

            //decide next step:
            int tempInt = table[currentPos[0]][currentPos[1]];
            if(tempInt == 1){
                if(currentPos[2] == 2){
                    currentPos[2] = 3; // continue right
                    step[0] = 1;
                    step[1] = 1; //
                } else if (currentPos[2] == 3){
                    currentPos[2] = 4; // continue down
                    step[0] = 1; //
                    step[1] = -1;
                } else if (currentPos[2] == 4){
                    currentPos[2] = 5; // continue left
                    step[0] = -1;
                    step[1] = -1; //
                } else if (currentPos[2] == 5){
                    currentPos[2] = 2; // continue up
                    step[0] = -1; //
                    step[1] = 1;
                }
            } else {
                if(currentPos[2] == 3){
                    step[0] = 0;
                    step[1] = 1;
                } else if(currentPos[2] == 4){
                    step[0] = 1;
                    step[1] = 0;
                } else if(currentPos[2] == 5){
                    step[0] = 0;
                    step[1] = -1;
                } else if(currentPos[2] == 2){
                    step[0] = -1;
                    step[1] = 0;
                }
            }
        }

        int counter = 0;
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                if(table[i][j] == -1){
                    counter ++;
                }
            }
        }
        System.out.println(counter);
    }

    public static void secondPart(){
        int maxY = table.length;
        int maxX = table[0].length;
        // currentPos[0] -> y
        // currentPos[1] -> x
        // currentPos[2] -> direction

        int[] step = new int[2];
        int counter = 0;
        int result = 0;

        for (int i = 0; i < table.length; i++) {
           for (int j = 0; j < table[1].length; j++) {
                if (!(table[i][j] == 0)) {
                    continue;
                } else {
                    table[i][j] = 1;
                    whileloop:
                    while (currentPos[0] >= 0 && currentPos[0] < maxY && currentPos[1] >= 0 && currentPos[1] < maxX) {
                        counter++;
                        if (counter > 10000000) {
                            result++;
                            counter = 0;
                            resetTableAndPos(-1,-1);
                            step[0] = -1;
                            step[1] = 0;
                            break whileloop;
                        }
                        //change table to visited:
                        if (!(table[currentPos[0]][currentPos[1]] == 1)) {
                            table[currentPos[0]][currentPos[1]] = -1;
                        }

                        //step:
                        currentPos[0] = currentPos[0] + step[0];
                        currentPos[1] = currentPos[1] + step[1];

                        if (!(currentPos[0] >= 0 && currentPos[0] < maxY && currentPos[1] >= 0 && currentPos[1] < maxX)) {
                            resetTableAndPos(-1,-1);
                            step[0] = -1;
                            step[1] = 0;
                            break whileloop;
                        }

                        //decide next step:
                        int tempInt = table[currentPos[0]][currentPos[1]];
                        if (tempInt == 1) {
                            if (currentPos[2] == 2) {
                                currentPos[2] = 3; // step back and continue right
                                step[0] = 1;
                                step[1] = 1; //
                            } else if (currentPos[2] == 3) {
                                currentPos[2] = 4; // step back and continue down
                                step[0] = 1; //
                                step[1] = -1;
                            } else if (currentPos[2] == 4) {
                                currentPos[2] = 5; // step back and continue left
                                step[0] = -1;
                                step[1] = -1; //
                            } else if (currentPos[2] == 5) {
                                currentPos[2] = 2; // step back and continue up
                                step[0] = -1; //
                                step[1] = 1;
                            }
                        } else {
                            if (currentPos[2] == 3) {
                                step[0] = 0;
                                step[1] = 1;
                            } else if (currentPos[2] == 4) {
                                step[0] = 1;
                                step[1] = 0;
                            } else if (currentPos[2] == 5) {
                                step[0] = 0;
                                step[1] = -1;
                            } else if (currentPos[2] == 2) {
                                step[0] = -1;
                                step[1] = 0;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }

    public static void processFile(List<String> input){
        table = new int[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '#') {
                    table[i][j] = 1;
                } else if (input.get(i).charAt(j) == '.') {
                    table[i][j] = 0;
                } else if (input.get(i).charAt(j) == '^') {
                    currentPos[2] = 2;
                    currentPos[0] = i;
                    currentPos[1] = j;
                    table[i][j] = 0;
                } else if (input.get(i).charAt(j) == '>') {
                    currentPos[2] = 3;
                    currentPos[0] = i;
                    currentPos[1] = j;
                    table[i][j] = 0;
                } else if (input.get(i).charAt(j) == 'v') {
                    currentPos[2] = 4;
                    currentPos[0] = i;
                    currentPos[1] = j;
                    table[i][j] = 0;
                } else if (input.get(i).charAt(j) == '<') {
                    currentPos[2] = 5;
                    currentPos[0] = i;
                    currentPos[1] = j;
                    table[i][j] = 0;
                }
            }
        }
        table2 = deepCopy(table);
        currentPos2 = Arrays.copyOf(currentPos, currentPos.length);
    }
    public static void resetTableAndPos(int wallY, int wallX) {
        // Deep copy the original table
        for (int i = 0; i < table2.length; i++) {
            for (int j = 0; j < table2[i].length; j++) {
                // Restore original values except for the temporary wall
                table[i][j] = (i == wallY && j == wallX) ? 1 : table2[i][j];
            }
        }

        // Reset position
        currentPos = Arrays.copyOf(currentPos2, currentPos2.length);
    }

    private static int[][] deepCopy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }


    public static void printoutTable(){
        for (int i = 0; i < table[0].length; i++) {
            for (int j = 0; j < table.length; j++) {
                System.out.print(table[i][j] + "  ");
                if(!(table[i][j] == -1)){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }


    public static List<String> readFile(String file){
        Path filePath = Paths.get(file);
        try{
            return Files.readAllLines(filePath);
        }
        catch (IOException e){
            System.err.println("beep beep error");
            return new ArrayList<>();
        }
    }
}