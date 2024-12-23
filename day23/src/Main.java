import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static Node[] graph;
    public static HashMap<String, Node> graphMap = new HashMap<>();
    public static List<String> myInput = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        part1();
        part2();
    }

    public static void part1(){
        int result = 0;
        for (int i = 0; i < graph.length; i++) {
            Node current = graph[i];
            for (int j = 0; j < current.connectedNodes.size(); j++) {
                Node secondNode = current.connectedNodes.get(j);
                for (int k = 0; k < current.connectedNodes.size(); k++) {
                    if(j != k){
                        Node thirdNode = current.connectedNodes.get(k);
                        if(secondNode.connectedNodes.contains(thirdNode)){
                            if(current.value.charAt(0) == 't' || secondNode.value.charAt(0) == 't' || thirdNode.value.charAt(0) == 't'){
                                result ++;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(result / 6);
    }

    public static void part2(){
        for (int i = 0; i < graph.length; i++) {

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
    public static void processFile(List<String> input){
        myInput = input;
        Set<String> names = new HashSet<>();
        for (int i = 0; i < input.size(); i++) {
            names.add(input.get(i).substring(0,2));
            names.add(input.get(i).substring(3,5));
        }

        graph = new Node[names.size()];
        names.clear();

        for (int i = 0; i < input.size(); i++) {
            String s1 = input.get(i).substring(0,2);
            String s2 = input.get(i).substring(3,5);

            if(names.add(s1)){
                Node node1 = new Node();

                node1.connectedNodes = new ArrayList<>();
                node1.value = s1;
                addNodeToArray(node1);
                graphMap.put(s1, node1);
            }

            if(names.add(s2)){
                Node node2 = new Node();

                node2.connectedNodes = new ArrayList<>();
                node2.value = s2;
                addNodeToArray(node2);
                graphMap.put(s2, node2);
            }

            graphMap.get(s1).connectNode(graphMap.get(s2));
        }
    }
    public static void addNodeToArray(Node node){
        for (int i = 0; i < graph.length; i++) {
            if(graph[i] == null){
                graph[i] = node;
                return;
            }
        }
    }
}