import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    static int maxY;
    static int maxX;
    static String[][] field;
    static int robotX;
    static int robotY;
    static ArrayList<String> commands = new ArrayList<String>();
    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        part1();
        processFile2(input);
        part2();
        //printOutField();
    }
    public static void part2(){
        for (int i = 0; i < commands.size(); i++) {
            oneMoveBoxes2(robotX, robotY, commands.get(i));
        }

        int result = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(field[i][j].equals("[")){
                    result = result + i * 100 + j;
                }
            }
        }
        System.out.println(result);
    }
    public static void oneMoveBoxes2(int x, int y, String direction){
        ArrayList<int[]> boxesCoordinates = new ArrayList<>();
        ArrayList<int[]> boxesCoordinatesSaved = new ArrayList<>();
        HashSet<String> visited = new HashSet<>();
        boxesCoordinatesSaved.add(new int[]{y, x});
        visited.add(y + "," + x);
        boxesCoordinates.add(new int[]{y, x});

        if(direction.equals("^")){
            while (!boxesCoordinates.isEmpty()) {
                int[] current = boxesCoordinates.remove(0); //get and remove the first box

                int currY = current[0];
                int currX = current[1];

                if (field[currY - 1][currX].equals("[")) {
                    if (!visited.contains((currY - 1) + "," + currX)) {
                        boxesCoordinates.add(new int[]{currY - 1, currX});
                        boxesCoordinatesSaved.add(new int[]{currY - 1, currX});
                        visited.add((currY - 1) + "," + currX);
                    }
                    if (!visited.contains((currY - 1) + "," + (currX + 1))) {
                        boxesCoordinates.add(new int[]{currY - 1, currX + 1});
                        boxesCoordinatesSaved.add(new int[]{currY - 1, currX + 1});
                        visited.add((currY - 1) + "," + (currX + 1));
                    }
                } else if (field[currY - 1][currX].equals("]")) {
                    // Right part of a box
                    if (!visited.contains((currY - 1) + "," + currX)) {
                        boxesCoordinates.add(new int[]{currY - 1, currX});
                        boxesCoordinatesSaved.add(new int[]{currY - 1, currX});
                        visited.add((currY - 1) + "," + currX);
                    }
                    if (!visited.contains((currY - 1) + "," + (currX - 1))) {
                        boxesCoordinates.add(new int[]{currY - 1, currX - 1});
                        boxesCoordinatesSaved.add(new int[]{currY - 1, currX - 1});
                        visited.add((currY - 1) + "," + (currX - 1));
                    }
                }
            }
            boolean canMove = true;
            for (int i = 0; i < boxesCoordinatesSaved.size(); i++) {
                int currX = boxesCoordinatesSaved.get(i)[1];
                int currY = boxesCoordinatesSaved.get(i)[0];
                if(!(field[currY - 1][currX].equals(".") || visited.contains((currY - 1) + "," + (currX)))){
                    canMove = false;
                    break;
                }
            }
            if(canMove) {
                Collections.sort(boxesCoordinatesSaved, new Comparator<int[]>() {
                    @Override
                    public int compare(int[] a, int[] b) {
                        if (a[0] < b[0]) {
                            return -1;
                        } else if (a[0] > b[0]) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

                robotY --;
                for (int i = 0; i < boxesCoordinatesSaved.size(); i++) {
                    int[] current = boxesCoordinatesSaved.get(i);
                    field[current[0] - 1][current[1]] = field[current[0]][current[1]];
                    field[current[0]][current[1]] = ".";
                }
            }
        }
        else if (direction.equals("v")){
            while (!boxesCoordinates.isEmpty()) {
                int[] current = boxesCoordinates.remove(0); //get and remove the first box

                int currY = current[0];
                int currX = current[1];

                if (field[currY + 1][currX].equals("[")) {
                    if (!visited.contains((currY + 1) + "," + currX)) {
                        boxesCoordinates.add(new int[]{currY + 1, currX});
                        boxesCoordinatesSaved.add(new int[]{currY + 1, currX});
                        visited.add((currY + 1) + "," + currX);
                    }
                    if (!visited.contains((currY + 1) + "," + (currX + 1))) {
                        boxesCoordinates.add(new int[]{currY + 1, currX + 1});
                        boxesCoordinatesSaved.add(new int[]{currY + 1, currX + 1});
                        visited.add((currY + 1) + "," + (currX + 1));
                    }
                } else if (field[currY + 1][currX].equals("]")) {
                    // right part of a box
                    if (!visited.contains((currY + 1) + "," + currX)) {
                        boxesCoordinates.add(new int[]{currY + 1, currX});
                        boxesCoordinatesSaved.add(new int[]{currY + 1, currX});
                        visited.add((currY + 1) + "," + currX);
                    }
                    if (!visited.contains((currY + 1) + "," + (currX - 1))) {
                        boxesCoordinates.add(new int[]{currY + 1, currX - 1});
                        boxesCoordinatesSaved.add(new int[]{currY + 1, currX - 1});
                        visited.add((currY + 1) + "," + (currX - 1));
                    }
                }
            }
            boolean canMove = true;
            for (int i = 0; i < boxesCoordinatesSaved.size(); i++) {
                int currX = boxesCoordinatesSaved.get(i)[1];
                int currY = boxesCoordinatesSaved.get(i)[0];
                if(!(field[currY + 1][currX].equals(".") || visited.contains((currY + 1) + "," + (currX)))){
                    canMove = false;
                    break;
                }
            }
            if(canMove) {
                Collections.sort(boxesCoordinatesSaved, new Comparator<int[]>() {
                    @Override
                    public int compare(int[] a, int[] b) {
                        if (a[0] > b[0]) {
                            return -1;
                        } else if (a[0] < b[0]) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

                robotY ++;
                for (int i = 0; i < boxesCoordinatesSaved.size(); i++) {
                    int[] current = boxesCoordinatesSaved.get(i);
                    field[current[0] + 1][current[1]] = field[current[0]][current[1]];
                    field[current[0]][current[1]] = ".";
                }
            }
        }
        else if (direction.equals("<")){

            int areaToMove = 0;
            for(int i = x - 1; i >= 0; i --){
                areaToMove ++;
                if(field[y][i].equals("#") || field[y][i].equals(".")){
                    break;
                }
            }

            for (int i = x - areaToMove; i < x - 1; i ++) {

                if(field[y][i].equals(".")){
                    field[y][i] = "[";
                    field[y][i + 1] = "]";
                    field[y][i + 2] = ".";
                }
                i = i + 1;
            }

            if(field[robotY][robotX - 1].equals(".")){
                field[robotY][robotX] = ".";
                robotX --;
                field[robotY][robotX] = "@";
            }
        }
        else if (direction.equals(">")){
            int areaToMove = 0;
            for(int i = x + 1; i < maxX; i ++){
                areaToMove ++;
                if(field[y][i].equals("#") || field[y][i].equals(".")){
                    break;
                }
            }

            for (int i = x + areaToMove; i > x + 1; i --) {
                if(field[y][i].equals(".")){
                    field[y][i] = "]";
                    field[y][i - 1] = "[";
                    field[y][i - 2] = ".";
                }
                i = i - 1;
            }

            if(field[robotY][robotX + 1].equals(".")){
                field[robotY][robotX] = ".";
                robotX ++;
                field[robotY][robotX] = "@";
            }
        }
    }
    public static void processFile2(List<String> input){
        maxX = input.get(0).length();
        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).equals("")){
                maxY = i;
            }
        }
        for (int i = maxY + 1; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                commands.add(input.get(i).substring(j, j + 1));
            }
        }

        field = new String[maxY][maxX * 2];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(input.get(i).substring(j, j +1).equals(".")){
                    field[i][2 * j] = ".";
                    field[i][2 * j + 1] = ".";
                }
                else if(input.get(i).substring(j, j +1).equals("#")){
                    field[i][2 * j] = "#";
                    field[i][2 * j + 1] = "#";
                }
                else if(input.get(i).substring(j, j +1).equals("O")){
                    field[i][2 * j] = "[";
                    field[i][2 * j + 1] = "]";
                }
                else if(input.get(i).substring(j,j + 1).equals("@")){
                    robotX = 2 * j;
                    robotY = i;
                    field[i][2 * j] = "@";
                    field[i][2 * j + 1] = ".";
                }
            }
        }
        maxX = maxX * 2;
    }
    public static void part1(){
        for (int i = 0; i < commands.size(); i++) {
            oneMoveBoxes(robotX, robotY, commands.get(i));
        }

        // calculate result:
        int result = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(field[i][j].equals("O")){
                    result = result + i * 100 + j;
                }
            }
        }
        System.out.println(result);
    }

    public static void processFile(List<String> input){
        maxX = input.get(0).length();
        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).equals("")){
                maxY = i;
            }
        }

        field = new String[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                field[i][j] = input.get(i).substring(j,j + 1);
                if(input.get(i).substring(j,j + 1).equals("@")){
                    robotX = j;
                    robotY = i;
                }
            }
        }

        for (int i = maxY + 1; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                commands.add(input.get(i).substring(j, j + 1));
            }
        }
    }

    public static void printOutField(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void oneMoveBoxes(int x, int y, String direction) {
        int toMove = 0;
        //decide move direction:
        if(direction.equals("^")){
            //see how many stepts until find wall or empty space
            for (int i = y - 1; i >= 0; i --) {
                toMove ++;
                if(field[i][x].equals("#") || field[i][x].equals(".")){
                    break;
                }
            }

            //move boxes if there is empty space:
            for (int i = y - toMove; i < y; i ++) {
                if(field[i][x].equals("O") && field[i - 1][x].equals(".")){
                    field[i][x] = ".";
                    field[i - 1][x] = "O";
                }
            }
            if(field[robotY - 1][robotX].equals(".")){
                field[robotY][robotX] = ".";
                robotY --;
                field[robotY][robotX] = "@";
            }
            return;
        } else if(direction.equals(">")){
            for (int i = x + 1; i < maxX; i++) {
                toMove ++;
                if(field[y][i].equals("#") || field[y][i].equals(".")){
                    break;
                }
            }
            for (int i = x + toMove; i >= x ; i--) {
                if(field[y][i].equals("O") && field[y][i + 1].equals(".")){
                    field[y][i] = ".";
                    field[y][i + 1] = "O";
                }
            }
            if(field[robotY][robotX + 1].equals(".")){
                field[robotY][robotX] = ".";
                robotX ++;
                field[robotY][robotX] = "@";
            }
        } else if (direction.equals("v")){
            for (int i = y + 1; i < maxY ; i ++) {
                toMove ++;
                if(field[i][x].equals("#") || field[i][x].equals(".")){
                    break;
                }
            }
            for (int i = y + toMove; i > y; i --) {
                if(field[i][x].equals("O") && field[i + 1][x].equals(".")){
                    field[i][x] = ".";
                    field[i + 1][x] = "O";
                }
            }

            if(field[robotY + 1][robotX].equals(".")){
                field[robotY][robotX] = ".";
                robotY ++;
                field[robotY][robotX] = "@";
            }

        } else if (direction.equals("<")){
            for(int i = x - 1; i >= 0; i --){
                toMove ++;
                if(field[y][i].equals("#") || field[y][i].equals(".")){
                    break;
                }
            }
            for (int i = x - toMove; i < x; i ++) {
                if(field[y][i + 1].equals("O") && field[y][i].equals(".")){
                    field[y][i + 1] = ".";
                    field[y][i] = "O";
                }
            }
            if(field[robotY][robotX - 1].equals(".")){
                field[robotY][robotX] = ".";
                robotX --;
                field[robotY][robotX] = "@";
            }
        }
    }
    public static List<String> readFile(String file) {
        Path filePath = Paths.get(file);
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println("Error reading file");
            return new ArrayList<>();
        }
    }
}