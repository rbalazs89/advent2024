import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static ArrayList<Long> numbers = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input3.txt");
        processFile(input);
        part1();
    }

    public static void part1(){
        for (int k = 0; k < 75; k++) {
            ArrayList<Long> result = new ArrayList<>();
            for (int i = 0; i < numbers.size(); i++) {
                ArrayList<Long> tempList = new ArrayList<>();
                tempList = processOneNumber(numbers.get(i));
                for (int j = 0; j < tempList.size(); j++) {
                    result.add(tempList.get(j));
                }
            }
            numbers.clear();
            for (int i = 0; i < result.size(); i++) {
                numbers.add(result.get(i));
            }
            //System.out.println(k);
            System.out.println(numbers.size());
        }
    }

    public static void processFile(List<String> input){
        String string1 = input.get(0);
        String[] array1 = string1.split(" ");
        for (int i = 0; i < array1.length; i++) {
            numbers.add(Long.valueOf(array1[i]));
        }
    }

    public static ArrayList<Long> processOneNumber(Long number){
        ArrayList<Long> result = new ArrayList<>();
        if(number == 0){
            result.add(1L);
            return result;
        }
        if(String.valueOf(number).length() % 2 == 0){
            String myString = String.valueOf(number);
            String s1 = myString.substring(0, myString.length()/2);
            String s2 = myString.substring(myString.length()/2);
            result.add(Long.valueOf(s1));
            result.add(Long.valueOf(s2));
            /*
            if(s1.charAt(s1.length() - 1) == '0'){
                s1 = s1.replaceAll("0+$", "");
            }
            if(s2.charAt(s2.length() - 1) == '0') {
                s2 = s2.replaceAll("0+$", "");
            }
            if(s1.length() == 0){
                result.add(0L);
            } else {
                result.add(Long.valueOf(s1));
            }
            if(s2.length() == 0){
                result.add(0L);
            } else {
                result.add(Long.valueOf(s2));
            }*/
            return result;
        }
        result.add(number * 2024);
        return result;
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