import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    static int maxX;
    static int maxY;
    static int startX = -1;
    static int startY = -1;
    static int endX = -1;
    static int endY = -1;
    static int shortestPathCost = Integer.MAX_VALUE;
    static Node[][] field;

    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        part1();
        part2();
    }

    public static void part2(){
        // 20 max distance allowed instead of 2, create steps array:
        ArrayList<int[]> stepsList = new ArrayList<>();
        for (int k = -20; k <= 20; k++) {
            for (int l = -20; l <= 20; l++) {
                if (Math.abs(k) + Math.abs(l) <= 20) {
                    stepsList.add(new int[]{k, l});
                }
            }
        }
        int[][] steps = new int[stepsList.size()][3];

        for (int k = 0; k < stepsList.size(); k++) {
            steps[k][0] = stepsList.get(k)[0];
            steps[k][1] = stepsList.get(k)[1];
            steps[k][2] = Math.abs(steps[k][0]) + Math.abs(steps[k][1]);
        }

        int result = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                Node current = field[i][j];
                for (int k = 0; k < steps.length; k++) {
                    int nextY = current.Y + steps[k][0];
                    int nextX = current.X + steps[k][1];
                    if (nextY >= 0 && nextY < maxY && nextX >= 0 && nextX < maxX && !field[nextY][nextX].wall && !current.wall) {
                        Node nextNode = field[nextY][nextX];
                        if(nextNode.costFromEnd + current.costFromStart + steps[k][2] <= shortestPathCost - 100){
                            result ++;
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }
    public static void part1(){
        // get costs of all nodes from the starting point:
        Queue<Node> q = new PriorityQueue<>(Comparator.comparingInt(node -> node.costFromStart));
        q.add(field[startY][startX]);
        Set<Node> visited = new HashSet<>();
        visited.add(field[startY][startX]);
        while(!q.isEmpty()){
            Node current = q.poll();
            visited.add(current);
            int[][] steps = {{0,1},{0,-1},{1,0},{-1,0}};
            for (int i = 0; i < steps.length; i++) {
                int nextY = current.Y + steps[i][0];
                int nextX = current.X + steps[i][1];
                if(nextY >= 0 && nextY < maxY && nextX >= 0 && nextX < maxX){
                    Node nextNode = field[nextY][nextX];
                    if(!nextNode.wall && !visited.contains(nextNode)){
                        if(nextNode.costFromStart > current.costFromStart + 1){
                            nextNode.costFromStart = current.costFromStart + 1;
                            q.add(nextNode);
                        }
                    }
                }
            }
        }
        shortestPathCost = field[endY][endX].costFromStart;

        //get cost of all nodes from the ending point:
        Queue<Node> q2 = new PriorityQueue<>(Comparator.comparingInt(node -> node.costFromStart));
        field[endY][endX].costFromEnd = 0;
        q2.add(field[endY][endX]);
        Set<Node> visited2 = new HashSet<>();
        visited2.add(field[endY][endX]);
        while(!q2.isEmpty()){
            Node current = q2.poll();
            visited2.add(current);
            int[][] steps = {{0,1},{0,-1},{1,0},{-1,0}};
            for (int i = 0; i < steps.length; i++) {
                int nextY = current.Y + steps[i][0];
                int nextX = current.X + steps[i][1];
                if(nextY >= 0 && nextY < maxY && nextX >= 0 && nextX < maxX){
                    Node nextNode = field[nextY][nextX];
                    if(!nextNode.wall && !visited2.contains(nextNode)){
                        if(nextNode.costFromEnd > current.costFromEnd + 1){
                            nextNode.costFromEnd = current.costFromEnd + 1;
                            q2.add(nextNode);
                        }
                    }
                }
            }
        }

        //get how many seconds (cost) one jump would save:
        int result = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                int[][] steps = {{0, 2}, {0, -2}, {2, 0}, {-2, 0}};
                Node current = field[i][j];
                for (int k = 0; k < steps.length; k++) {
                    int nextY = current.Y + steps[k][0];
                    int nextX = current.X + steps[k][1];
                    if (nextY >= 0 && nextY < maxY && nextX >= 0 && nextX < maxX && !field[nextY][nextX].wall && !current.wall) {
                        Node nextNode = field[nextY][nextX];
                        if(nextNode.costFromEnd + current.costFromStart + 2 <= shortestPathCost - 100){
                            result ++;
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }
    public static void processFile(List<String> input){
        maxX = input.get(0).length();
        maxY = input.size();
        field = new Node[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                Node node = new Node();
                node.X = j;
                node.Y = i;
                if(input.get(i).charAt(j) == '#'){
                    node.wall = true;
                } else{
                    node.wall = false;
                }
                if(input.get(i).charAt(j) == 'S'){
                    startX = j;
                    startY = i;
                    node.costFromStart = 0;
                }
                if(input.get(i).charAt(j) == 'E'){
                    endX = j;
                    endY = i;
                }
                field[i][j] = node;
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
    public static void printoutField(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(!field[i][j].wall){
                    if(field[i][j].costFromStart < 10){
                        System.out.print( "." + field[i][j].costFromStart);
                    } else {
                        System.out.print(field[i][j].costFromStart);
                    }

                } else {
                    System.out.print("##");
                }

            }
            System.out.println();
        }
        System.out.println();
    }
    public static void printoutField2(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(!field[i][j].wall){
                    if(field[i][j].costFromEnd < 10){
                        System.out.print( "." + field[i][j].costFromEnd);
                    } else {
                        System.out.print(field[i][j].costFromEnd);
                    }

                } else {
                    System.out.print("##");
                }

            }
            System.out.println();
        }
        System.out.println();
    }
}