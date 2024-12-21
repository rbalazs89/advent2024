import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    static Node[][] field;
    static int maxY;
    static int maxX;
    static int startX;
    static int startY;
    static int endX;
    static int endY;
    static ArrayList<Node> bestNodes = new ArrayList<>();
    public static void main(String[] args) {
        List<String> input = readFile("src/input3.txt");
        //processFile(input);
        //part1();
        //processFile(input); // reset field values for part 2
        //part2();
        //printMaze();
        countPart2(input);
    }

    public static void part1(){
        Node startingNode = field[startY][startX];

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.cost - b.cost);
        pq.add(startingNode);
        Set<Node> visited = new HashSet<>();
        int result = 0;
        while(!pq.isEmpty()){

            Node currentNode = pq.poll();
            visited.add(currentNode);

            if(currentNode.x == endX && currentNode.y == endY){
                result = currentNode.cost;
                break;
            }

            int steps[][] = {{0,1},{0,-1},{1,0},{-1,0}};
            for (int i = 0; i < steps.length; i++) {
                int newX = currentNode.x + steps[i][1];
                int newY = currentNode.y + steps[i][0];
                if(newX >= 0 && newX < maxX && newY >= 0 && newY < maxX) {
                    Node nextNode = field[currentNode.y + steps[i][0]][currentNode.x + steps[i][1]];
                    if(nextNode.nodeType.equals("#") || visited.contains(nextNode)){
                        continue;
                    }
                    if(nextNode.cost > currentNode.cost + currentNode.getAddCost(nextNode)){
                        nextNode.cost = currentNode.cost + currentNode.getAddCost(nextNode);
                        currentNode.changeDirection(nextNode);
                    }
                    pq.add(nextNode);
                }

            }
        }

        System.out.println(result);
    }

    public static void processFile(List<String> input){
        maxY = input.size();
        maxX = input.get(0).length();
        field = new Node[maxY][maxX];

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                Node node = new Node();
                node.nodeType = input.get(i).substring(j, j + 1);
                node.x = j;
                node.y = i;
                if(input.get(i).substring(j, j + 1).equals("S")){
                    startX = j;
                    startY = i;
                    node.currentDirection = ">";
                    node.cost = 0;
                }if(input.get(i).substring(j, j + 1).equals("E")){
                    endX = j;
                    endY = i;
                }
                field[i][j] = node;
            }
        }
    }
    public static void part2() {
        //get values:
        Node startingNode = field[startY][startX];

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.cost - b.cost);
        pq.add(startingNode);
        Set<Node> visited = new HashSet<>();
        while(!pq.isEmpty()){

            Node currentNode = pq.poll();
            visited.add(currentNode);

            int steps[][] = {{0,1},{0,-1},{1,0},{-1,0}};
            for (int i = 0; i < steps.length; i++) {
                int newX = currentNode.x + steps[i][1];
                int newY = currentNode.y + steps[i][0];
                if(newX >= 0 && newX < maxX && newY >= 0 && newY < maxX) {
                    Node nextNode = field[currentNode.y + steps[i][0]][currentNode.x + steps[i][1]];
                    if(nextNode.nodeType.equals("#") || visited.contains(nextNode)){
                        continue;
                    }
                    if(nextNode.cost > currentNode.cost + currentNode.getAddCost(nextNode)){
                        nextNode.cost = currentNode.cost + currentNode.getAddCost(nextNode);
                        currentNode.changeDirection(nextNode);
                        nextNode.lastParent = currentNode;
                    }
                    pq.add(nextNode);
                }
            }
        }

        //from the path
        int result = 0;
        Node currentNode = field[endY][endX];
        while(currentNode != null){
            result ++;
            currentNode.nodeType = "O";
            if(currentNode.lastParent == field[startY][startX]){
                break;
            }
            currentNode = currentNode.lastParent;
        }
        System.out.println(result);
    }

    public static void printMaze(){
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                System.out.print(field[i][j].nodeType);
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
    public static void countPart2(List<String> input){
        int result = 0;
        maxX = input.get(0).length();
        maxY = input.size();

        System.out.println(input.get(1));
        System.out.println(maxX);
        System.out.println(maxY);

        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                System.out.println(i + " " + j);
                if(input.get(i).charAt(j) == 'O'){
                    result ++;
                }
            }
        }
        System.out.println(result + 1);
    }
}