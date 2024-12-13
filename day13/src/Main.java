import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static ArrayList<ClawSetup> setups = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        processFile(input);
        //part1();
        part2();
    }

    public static void part2(){
        for (int a = 0; a < setups.size(); a++) {
            ClawSetup current = setups.get(a);
            current.goalX = current.goalX + 10000000000000L;
            current.goalY = current.goalY + 10000000000000L;
            long maxMultA = Math.max(current.goalX / current.AX + 1,  current.goalY / current.AY + 1);
            long maxMultB = Math.max(current.goalX / current.BX + 1,  current.goalY / current.BY + 1);
            ArrayList<long[]> possibleCombinations = new ArrayList<>();
            for (int i = 0; i < maxMultA; i++) {
                for (int j = 0; j < maxMultB; j++) {
                    if( i * current.AX + j * current.BX == current.goalX &&
                            i * current.AY + j * current.BY == current.goalY){
                        possibleCombinations.add(new long[]{i,j});
                    }
                }
            }

            if(possibleCombinations.size() == 0){
                current.value = 0;
            }

            long minValue = Integer.MAX_VALUE;
            for (int i = 0; i < possibleCombinations.size(); i++) {
                if(possibleCombinations.get(i)[0] * 3 +  possibleCombinations.get(i)[1] < minValue){
                    minValue = possibleCombinations.get(i)[0] * 3L +  possibleCombinations.get(i)[1];
                }
            }
            current.value = minValue;
        }

        int result = 0;
        for (int i = 0; i < setups.size(); i++) {
            result += setups.get(i).value;
        }
        System.out.println(result);
    }

    public static void part1(){

        for (int a = 0; a < setups.size(); a++) {

            //get all possible setups to arrive to the prize:
            ClawSetup current = setups.get(a);
            long maxMultA = Math.max(current.goalX / current.AX + 1,  current.goalY / current.AY + 1);
            long maxMultB = Math.max(current.goalX / current.BX + 1,  current.goalY / current.BY + 1);
            ArrayList<long[]> possibleCombinations = new ArrayList<>();
            for (int i = 0; i < maxMultA; i++) {
                for (int j = 0; j < maxMultB; j++) {
                    if( i * current.AX + j * current.BX == current.goalX &&
                    i * current.AY + j * current.BY == current.goalY){
                        possibleCombinations.add(new long[]{i,j});
                    }
                }
            }
            if(possibleCombinations.size() == 0){
                current.value = 0;
                continue outerloop;
            }

            // get lowest value: A cost 3 tokens, B cost 1 tokens
            long minValue = Integer.MAX_VALUE;
            for (int i = 0; i < possibleCombinations.size(); i++) {
                if(possibleCombinations.get(i)[0] * 3 +  possibleCombinations.get(i)[1] < minValue){
                    minValue = possibleCombinations.get(i)[0] * 3L +  possibleCombinations.get(i)[1];
                }
            }
            current.value = minValue;
        }

        int result = 0;
        for (int i = 0; i < setups.size(); i++) {
            result += setups.get(i).value;
        }
        System.out.println(result);
    }

    public static void processFile(List<String> input) {
        for (int i = 0; i < input.size(); i += 4) {
            if (i + 2 >= input.size()) {
                System.err.println("Incomplete setup at index " + i);
                break;
            }
            try {
                ClawSetup setup = new ClawSetup();
                setup.AX = parseCoordinate(input.get(i), "X");
                setup.AY = parseCoordinate(input.get(i), "Y");
                setup.BX = parseCoordinate(input.get(i + 1), "X");
                setup.BY = parseCoordinate(input.get(i + 1), "Y");
                setup.goalX = parseCoordinate(input.get(i + 2), "X");
                setup.goalY = parseCoordinate(input.get(i + 2), "Y");

                setups.add(setup);
            } catch (Exception e) {
                System.err.println("Error");
            }
        }
    }

    public static int parseCoordinate(String line, String coordinate) throws Exception {
        //String pattern = coordinate + "([+=])(-?\\d+)";
        String[] parts = line.split(",");
        for (String part : parts) {
            if (part.contains(coordinate + "+") || part.contains(coordinate + "=")) {
                return Integer.parseInt(part.replaceAll("[^\\d-]", "").trim());
            }
        }
        throw new Exception("Missing coordinate ");
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