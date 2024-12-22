import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    static int[] numbers;
    static Set<String> seq = new HashSet<>();
    static HashMap<String, Integer> savedData = new HashMap<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        part1();
        part2();
    }

    public static void part2(){
        for (int j = 0; j < numbers.length; j++) {
            long current = numbers[j];
            int[] window = new int[4];
            for (int i = 0; i < 2000; i++) {
                long changed = oneStep(current);
                int ch10 = (int)(changed % 10);
                int cu10 = (int)(current % 10);

                if(i < 4){
                    window[i] = ch10 - cu10;
                }

                if( i >= 4 ){

                    window[0] = window[1];
                    window[1] = window[2];
                    window[2] = window[3];
                    window[3] = ch10 - cu10;

                    String s = window[0] + "," + window[1] + "," +window[2] + "," + window[3];
                    if(seq.add(s)){
                        if(savedData.get(s) == null){
                            savedData.put(s, ch10);
                        } else {
                            savedData.put(s, savedData.get(s) + ch10);
                        }
                    }
                }
                current = changed;
            }
            seq.clear();
        }
        int max = 0;
        for( Map.Entry<String, Integer> entry : savedData.entrySet()){
            max = Math.max(entry.getValue(), max);
        }
        System.out.println(max);
    }




    public static void part1(){
        long result = 0L;
        for (int j = 0; j < numbers.length; j++) {
            long current = numbers[j];
            for (int i = 0; i < 2000; i++) {
                current = oneStep(current);
                if(i == 1999){
                    result += current;
                }
            }
        }
        System.out.println(result);
    }
    public static long oneStep(Long n){
        n = (n * 64) ^ n;
        n = prune(n);

        n = n ^ (n / 32);
        n = prune(n);

        n = (n * 2048) ^ n;
        n = prune(n);
        return n;
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
        numbers = new int[input.size()];
        for (int i = 0; i < input.size(); i++) {
            numbers[i] = Integer.parseInt(input.get(i));
        }
    }
    public static long prune(long n){
        return n % 16777216;
    }
}