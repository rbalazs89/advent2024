import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
    }
    public static void part1(){
        printOutField();
        for (int i = 0; i < commands.size(); i++) {
            oneMoveBoxes(robotX, robotY, commands.get(i));
        }
        printOutField();

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

    public static List<String> readFile(String file) {
        Path filePath = Paths.get(file);
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println("Error reading file");
            return new ArrayList<>();
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
}