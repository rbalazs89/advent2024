import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static int[] numbers;
    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        part1();
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
}