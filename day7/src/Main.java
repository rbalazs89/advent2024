import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    static ArrayList<ArrayList<String>> input = new ArrayList<>();

    public static void main(String[] args) {
        List<String> inputRaw = readFile("src/input1.txt");
        processFile(inputRaw);
        firstPart();
    }

    public static void firstPart(){
        Long result = 0L;
        for (int i = 0; i < input.size(); i++) {
            result = result + processOneElement(input.get(i));
        }
        System.out.println(result);
    }

    public static Long processOneElement(ArrayList<String> oneLineInput){
        int numCombinations = (int) Math.pow(2, oneLineInput.size() - 2);
        String[] combinations = new String[numCombinations];
        String item1 = "+";
        String item2 = "*";
        // Generate combinations

        for (int i = 0; i < numCombinations; i++) {
            StringBuilder combination = new StringBuilder();
            for (int j = 0; j < oneLineInput.size() - 2; j++) {
                if ((i & (1 << j)) == 0) {
                    combination.append(item1);
                } else {
                    combination.append(item2);
                }
            }
            combinations[i] = combination.toString();
        }


        // generate number from combinations:
        for(int i = 0; i < combinations.length; i ++){
            Long tempResult = Long.parseLong(oneLineInput.get(1));
            for (int j = 0; j < combinations[i].length(); j++) {
                if(combinations[i].charAt(j) == '+'){
                    tempResult = tempResult + Long.parseLong(oneLineInput.get(j + 2));
                } else {
                    tempResult = tempResult * Long.parseLong(oneLineInput.get(j + 2));
                }
            }
            if(tempResult == Long.parseLong(oneLineInput.get(0))){
                return tempResult;
            }
        }
        return 0L;
    }


    public static void processFile(List<String> inputRaw) {
        for (String line : inputRaw) {
            String[] parts = line.split(":");
            ArrayList<String> row = new ArrayList<>();
            row.add(parts[0].trim());
            String[] values = parts[1].trim().split("\\s+");
            for (String value : values) {
                row.add(value);
            }
            input.add(row);
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