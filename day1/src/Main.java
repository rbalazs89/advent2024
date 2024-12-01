import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        //Processing the file
        List<String> input = readFile("src/input1.txt");
        System.out.println(firstPart(input));
        System.out.println(secondPart(input));

    }

    public static int firstPart(List<String> input){

        //processing input:
        ArrayList<Integer> firstColumn = new ArrayList<>();
        ArrayList<Integer> secondColumn = new ArrayList<>();

        for(int i = 0; i < input.size(); i ++){
            String[] parts = input.get(i).split("\\s+");
            firstColumn.add(Integer.valueOf(parts[0]));
            secondColumn.add(Integer.valueOf(parts[1]));
        }

        // solution:
        Collections.sort(firstColumn);
        Collections.sort(secondColumn);

        int result = 0;
        for(int i = 0; i < firstColumn.size(); i ++){
            result += Math.abs(firstColumn.get(i) - secondColumn.get(i));
        }

        return result;
    }

    public static int secondPart(List<String> input) {
        //processing input:
        ArrayList<Integer> firstColumn = new ArrayList<>();
        ArrayList<Integer> secondColumn = new ArrayList<>();

        for(int i = 0; i < input.size(); i ++){
            String[] parts = input.get(i).split("\\s+");
            firstColumn.add(Integer.valueOf(parts[0]));
            secondColumn.add(Integer.valueOf(parts[1]));
        }

        //solution:

        int result = 0;

        for(int i = 0; i < input.size(); i ++){
            int tempInt = firstColumn.get(i);
            int counter = 0;
            for(int j = 0; j < input.size(); j ++){
                if(tempInt == secondColumn.get(j)){
                    counter ++;
                }
            }
            result = result + tempInt * counter;
        }

        return result;

    }

    public static List<String> readFile(String file){
        Path filePath = Paths.get(file);
        try{
            List<String> fileLines = Files.readAllLines(filePath);
            return fileLines;
        }
        catch (IOException e){
            System.err.println("beep beep error");
            return new ArrayList<>();
        }
    }
}