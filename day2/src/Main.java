import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    static Set<Integer> chatgpt = new HashSet<>();
    static Set<Integer> mysolution = new HashSet<>();

    public static void main(String[] args) {
            //Processing the file
            List<String> input = readFile("src/input1.txt");
            System.out.println(firstPart(input));
            System.out.println(secondPart(input));
        }


    public static int secondPart(List<String> input){
        //process file:
        ArrayList<List<Integer>> inputs = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            String[] oneLine = input.get(i).split(" ");
            ArrayList<Integer> input1line = new ArrayList<>();
            for(int j = 0; j < oneLine.length; j ++){
                input1line.add(Integer.valueOf(oneLine[j]));
            }
            inputs.add(input1line);
        }

        //get the numbers that are safe without modification:
        Set<Integer> safeNumbers = new HashSet<>();
        outerloop:
        for(int i = 0; i < inputs.size(); i ++){
            boolean isSafe = false;
            boolean isIncreasing = false;

            if (inputs.get(i).get(0) - inputs.get(i).get(1) == 0){
                continue outerloop;
            }

            if (inputs.get(i).get(0) - inputs.get(i).get(1) < 0){
                isIncreasing = true;
            }

            for (int j = 0; j < inputs.get(i).size() - 1; j++) {
                int temp = inputs.get(i).get(j) - inputs.get(i).get(j + 1);
                if (isIncreasing) {
                    if (temp == -1 || temp == -2 || temp == -3) {
                        isSafe = true;
                    } else {
                        continue outerloop;
                    }
                } else if (!isIncreasing) {
                    if (temp == 1 || temp == 2 || temp == 3) {
                        isSafe = true;
                    } else {
                        isSafe = false;
                        continue outerloop;
                    }
                }
            }
            if(isSafe){
                safeNumbers.add(i);
            }
        }

        //solution:
        int counter = 0;

        outerloop:
        for(int i = 0; i < inputs.size(); i ++){
            if(safeNumbers.contains(i)){
                counter ++;
                mysolution.add(i);
                continue ;
            }

            middleloop:
            for (int k = 0; k < inputs.get(i).size(); k++) {
                boolean isSafe = false;
                boolean isIncreasing = false;
                List<Integer> copiedList = new ArrayList<>(inputs.get(i));
                inputs.get(i).remove(k);

                if (inputs.get(i).get(0) - inputs.get(i).get(1) == 0){
                    inputs.remove(i);
                    inputs.add(i, copiedList);
                    continue middleloop;
                }

                if (inputs.get(i).get(0) - inputs.get(i).get(1) < 0){
                    isIncreasing = true;
                }

                for (int j = 0; j < inputs.get(i).size() - 1; j++) {
                    int temp = inputs.get(i).get(j) - inputs.get(i).get(j + 1);
                    if (isIncreasing) {
                        if (temp == -1 || temp == -2 || temp == -3) {
                            isSafe = true;
                        } else {
                            isSafe = false;
                            inputs.remove(i);
                            inputs.add(i, copiedList);
                            continue middleloop;
                        }
                    } else if (!isIncreasing) {
                        if (temp == 1 || temp == 2 || temp == 3) {
                            isSafe = true;
                        } else {
                            isSafe = false;
                            inputs.remove(i);
                            inputs.add(i, copiedList);
                            continue middleloop;
                        }
                    }
                }
                if(isSafe){
                    counter ++;
                    mysolution.add(i);
                    continue outerloop;
                }
            }
        }
        return counter;
    }

    public static int firstPart(List<String> input){
        //process file:
        ArrayList<List<Integer>> inputs = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            String[] oneLine = input.get(i).split(" ");
            ArrayList<Integer> input1line = new ArrayList<>();
            for(int j = 0; j < oneLine.length; j ++){
                input1line.add(Integer.valueOf(oneLine[j]));
            }
            inputs.add(input1line);
        }

        //solution:
        int counter = 0;

        outerloop:
        for(int i = 0; i < inputs.size(); i ++){
            boolean isSafe = false;
            boolean isIncreasing = false;

            if (inputs.get(i).get(0) - inputs.get(i).get(1) == 0){
                continue outerloop;
            }

            if (inputs.get(i).get(0) - inputs.get(i).get(1) < 0){
                isIncreasing = true;
            }

            for (int j = 0; j < inputs.get(i).size() - 1; j++) {
                int temp = inputs.get(i).get(j) - inputs.get(i).get(j + 1);
                if (isIncreasing) {
                    if (temp == -1 || temp == -2 || temp == -3) {
                        isSafe = true;
                    } else {
                        continue outerloop;
                    }
                } else if (!isIncreasing) {
                    if (temp == 1 || temp == 2 || temp == 3) {
                        isSafe = true;
                    } else {
                        isSafe = false;
                        continue outerloop;
                    }
                }
            }
            if(isSafe){
                counter ++;
                continue outerloop;
            }
        }
        return counter;
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