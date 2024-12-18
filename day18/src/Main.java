import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    //7 for example input, 71 for real input
    static int maxY = 71;
    static int maxX = 71;
    static int maxWall = 0;

    static int[][] wall;
    static String[][] field = new String[maxY][maxX];


    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        printoutField();
        part1();
        part2();
    }

    public static void part2(){
        outerLoop:
        for (int j = 1024; j < maxWall; j++) {
            int wallX = wall[1][j];
            int wallY = wall[0][j];
            field[wallY][wallX] = "#";

            Set<String> visited = new HashSet<>();
            int startX = 0;
            int startY = 0;
            visited.add(startY + "," + startX);
            Queue<int[]> queue = new LinkedList<>();
            queue.add(new int[]{startY, startX});
            int counter = 0;
            while(!queue.isEmpty()){
                for (int i = 0; i < queue.size(); i++) {
                    int[] current = queue.poll();
                    int x = current[1];
                    int y = current[0];
                    if(x == maxX - 1 && y == maxY - 1){
                        break;
                    }

                    x = current[1] + 1;
                    y = current[0];
                    if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                        if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                            queue.add(new int[]{y,x});
                            field[y][x] = "O";
                            visited.add(y + "," + x);
                        }
                    }

                    x = current[1] - 1;
                    y = current[0];
                    if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                        if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                            queue.add(new int[]{y,x});
                            field[y][x] = "O";
                            visited.add(y + "," + x);
                        }
                    }

                    x = current[1];
                    y = current[0] + 1;
                    if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                        if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                            queue.add(new int[]{y,x});
                            field[y][x] = "O";
                            visited.add(y + "," + x);
                        }
                    }

                    x = current[1];
                    y = current[0] - 1;
                    if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                        if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                            queue.add(new int[]{y,x});
                            field[y][x] = "O";
                            visited.add(y + "," + x);
                        }
                    }

                    if(queue.size() == 0 && !visited.contains("70,70")){
                        System.out.println(wallX + "," + wallY);
                        printoutField();
                        break outerLoop;
                    }
                }
            }
            //reprint field:
            for (int k = 0; k < maxY; k++) {
                for (int l = 0; l < maxX; l++) {
                    field[k][l] = ".";
                }
            }
            for (int k = 0; k <= j; k++) {
                field[wall[0][k]][wall[1][k]] = "#";
            }
        }
    }

    public static void part1(){
        Set<String> visited = new HashSet<>();
        int startX = 0;
        int startY = 0;
        visited.add(startY + "," + startX);
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startY, startX});
        int counter = 0;
        while(!queue.isEmpty()){
            counter ++;
            for (int i = 0; i < queue.size(); i++) {
                int[] current = queue.poll();
                int x = current[1];
                int y = current[0];
                if(x == maxX - 1 && y == maxY - 1){
                    System.out.println(counter);
                    break;
                }

                x = current[1] + 1;
                y = current[0];
                if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                    if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                        queue.add(new int[]{y,x});
                        visited.add(y + "," + x);
                    }
                }

                x = current[1] - 1;
                y = current[0];
                if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                    if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                        queue.add(new int[]{y,x});
                        visited.add(y + "," + x);
                    }
                }

                x = current[1];
                y = current[0] + 1;
                if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                    if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                        queue.add(new int[]{y,x});
                        visited.add(y + "," + x);
                    }
                }

                x = current[1];
                y = current[0] - 1;
                if(x >= 0 && x < maxX && y >= 0 && y < maxY) {
                    if(!visited.contains(y + "," + x) && !(field[y][x].equals("#"))){
                        queue.add(new int[]{y,x});
                        visited.add(y + "," + x);
                    }
                }
            }
        }
    }

    public static void processFile(List<String> input){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                String s = ".";
                field[i][j] = s;
            }
        }
        int size = 0;
        if(maxY == 7){
            size = 12;
        } else if (maxY == 71){
            size = 1024;
        }
        for (int i = 0; i < size; i++) {
            String[] coordinates = input.get(i).split(",");
            field[Integer.valueOf(coordinates[1])][Integer.valueOf(coordinates[0])] = "#";
        }

        wall = new int[2][input.size()];

        for (int i = 0; i < input.size(); i++) {
            String[] coordinates = input.get(i).split(",");
            wall[0][i] = Integer.valueOf(coordinates[1]);
            wall[1][i] = Integer.valueOf(coordinates[0]);
        }
        maxWall = input.size();
    }
    public static void printoutField(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
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