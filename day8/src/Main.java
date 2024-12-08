import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static Node[][] nodes;
    static int maxX;
    static int maxY;

    public static void main(String[] args) {
        List<String> inputRaw = readFile("src/input1.txt");
        processFile(inputRaw);
        firstPart();
        //secondPart();
    }
    public static void firstPart() {
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                Node currentNode = nodes[i][j];
                for (int k = 0; k < nodes.length; k++) {
                    for (int l = 0; l < nodes[0].length; l++) {
                        Node compare = nodes[k][l];
                        if (currentNode != compare && currentNode.frequency.equals(compare.frequency) && !currentNode.frequency.equals(".") &&
                                !currentNode.frequency.equals("#")) {
                            int xDif = currentNode.x - compare.x;
                            int yDif = currentNode.y - compare.y;
                            int newX = currentNode.x + xDif;
                            int newY = currentNode.y + yDif;
                            if (isInside(newX, newY)) {
                                nodes[newY][newX].containsAntiNode = true;
                            }
                        }
                    }
                }
            }
        }
        int result = 0;
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                if (nodes[i][j].containsAntiNode) {
                    result++;
                }
            }
        }
        System.out.println(result);
    }

    public static void processFile(List<String> inputRaw){
        maxX = inputRaw.get(0).length();
        maxY  = inputRaw.size();
        nodes = new Node[maxY][maxX];
        for (int i = 0; i < inputRaw.size(); i++) {
            for (int j = 0; j < inputRaw.get(i).length(); j++) {
                Node node = new Node();
                node.x = j;
                node.y = i;
                node.frequency = String.valueOf(inputRaw.get(i).charAt(j));
                nodes[i][j] = node;
            }
        }
    }
    public static boolean isInside(int x, int y){
        if(x >= 0 && x < maxX && y >= 0 && y < maxY){
            return true;
        } else return false;
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