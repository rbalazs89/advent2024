import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static int maxX = 101;
    //11 for example; 101
    static int maxY = 103;
    // 7 for example input, 103 for real input
    static ArrayList<Robot> robots = new ArrayList<>();
    static int[][] field = new int[maxY][maxX];

    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        part1();
        processFile(input);
        part2();
    }
    public static void part2(){
        int maxCloseRobots = 0;
        int maxTime = -1;
        for (int j = 0; j < 10000; j++) {
            for (int i = 0; i < robots.size(); i++) {
                Robot current = robots.get(i);

                current.X = current.X + current.velX;
                if(current.X >= maxX){
                    current.X = current.X % maxX;
                } else if (current.X < 0){
                    current.X = current.X + maxX;
                }

                current.Y = current.Y + current.velY;
                if(current.Y >= maxY){
                    current.Y = current.Y % maxY;
                } else if (current.Y < 0){
                    current.Y  = current.Y + maxY;
                }
            }
            int closeRobots = 0;
            for (int i = 0; i < robots.size(); i++) {
                if(checkIfCloseToOtherRobot(robots.get(i))){
                    closeRobots ++;
                }
            }
            if(closeRobots > maxCloseRobots){
                maxTime = j;
                maxCloseRobots = closeRobots;
            }
        }
        System.out.println(maxCloseRobots);
        System.out.println(maxTime + 1);

    }

    public static void part1(){
        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < robots.size(); i++) {
                Robot current = robots.get(i);

                current.X = current.X + current.velX;
                if(current.X >= maxX){
                    current.X = current.X % maxX;
                } else if (current.X < 0){
                    current.X = current.X + maxX;
                }

                current.Y = current.Y + current.velY;
                if(current.Y >= maxY){
                    current.Y = current.Y % maxY;
                } else if (current.Y < 0){
                    current.Y  = current.Y + maxY;
                }
            }
        }

        int[] quadrants = new int[5];
        for (int i = 0; i < robots.size(); i++) {
            quadrants[getQuadrant(robots.get(i))] ++;
        }

        int result = 1;
        for (int i = 1; i < quadrants.length; i++) {
            System.out.println("quadrant i : " + quadrants[i]);
            if(quadrants[i] != 0){
                result = result * quadrants[i];
            }
        }
    }
    public static int getQuadrant(Robot robot){
        if(robot.X == maxX/2 || robot.Y == maxY/2){
            return 0;
        }
        if(robot.X < maxX/2 && robot.Y < maxY/2){
            return 1;
        }
        if(robot.X > maxX/2 && robot.Y < maxY/2){
            return 2;
        }
        if(robot.X < maxX/2 && robot.Y > maxY/2){
            return 3;
        }
        if(robot.X > maxX/2 && robot.Y > maxY/2){
            return 4;
        }
        return 0;
    }

    public static void printOutField(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                field[i][j] = 0;
            }
        }
        for (int i = 0; i < robots.size(); i++) {
            field[robots.get(i).Y][robots.get(i).X] ++;
        }
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    public static boolean checkIfCloseToOtherRobot(Robot robot) {
        int x = robot.X;
        int y = robot.Y;
        int neighbours = 0;
        for (int i = 0; i < robots.size(); i++) {
            Robot current = robots.get(i);
            if(current.X + 1 == x && current.Y == y){
                neighbours++;
            }
            if(current.X - 1 == x && current.Y == y){
                neighbours++;
            }
            if(current.X == x && current.Y - 1 == y){
                neighbours++;
            }
            if(current.X + 1 == x && current.Y + 1 == y){
                neighbours++;
            }
        }
        if(neighbours >= 3){
            return true;
        } else return false;
    }

    public static void processFile(List<String> input) {
        robots.clear();
        // Parse the input and populate the field
        for (String line : input) {
            // Example line: p=0,4 v=3,-3
            String[] parts = line.split(" ");
            String[] position = parts[0].substring(2).split(",");
            String[] velocity = parts[1].substring(2).split(",");

            int x = Integer.parseInt(position[0]);
            int y = Integer.parseInt(position[1]);
            int velX = Integer.parseInt(velocity[0]);
            int velY = Integer.parseInt(velocity[1]);

            // Ensure the position is within bounds
            if (x >= 0 && x < maxX && y >= 0 && y < maxY) {
                Robot robot = new Robot();
                robot.X = x;
                robot.Y = y;
                robot.velX = velX;
                robot.velY = velY;
                robots.add(robot);
            } else {
                System.err.println("Robot position out of bounds: " + line);
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