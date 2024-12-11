import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static ArrayList<Long> numbers1 = new ArrayList<>();
    static Map<Long, Long> numbers = new HashMap<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        part1();
        processFile2(input);
        part2();
    }

    public static void part1() {
        for (int k = 0; k < 25; k++) {
            ArrayList<Long> result = new ArrayList<>();
            for (Long number : numbers1) {
                result.addAll(processOneNumber(number));
            }
            numbers1 = result;
            System.out.println(numbers1.size());
        }
    }

    public static void part2() {
        for (int k = 0; k < 75; k++) {
            Map<Long, Long> newNumbers = new HashMap<>();
            for (Map.Entry<Long, Long> entry : numbers.entrySet()) {
                Long number = entry.getKey();
                Long count = entry.getValue();
                for (Long result : processOneNumber(number)) {
                    newNumbers.put(result, newNumbers.getOrDefault(result, 0L) + count);
                }
            }
            numbers = newNumbers;
            long totalCount = numbers.values().stream().mapToLong(Long::longValue).sum();
            System.out.println(totalCount);
        }

    }

    public static void processFile(List<String> input) {
        String[] array1 = input.get(0).split(" ");
        for (String s : array1) {
            numbers1.add(Long.valueOf(s));
        }
    }

    public static void processFile2(List<String> input) {
        String[] array1 = input.get(0).split(" ");
        for (String s : array1) {
            Long num = Long.valueOf(s);
            numbers.put(num, numbers.getOrDefault(num, 0L) + 1);
        }
    }

    public static ArrayList<Long> processOneNumber(Long number) {
        ArrayList<Long> result = new ArrayList<>();
        if (number == 0) {
            result.add(1L);
        } else if (String.valueOf(number).length() % 2 == 0) {
            String myString = String.valueOf(number);
            String s1 = myString.substring(0, myString.length() / 2);
            String s2 = myString.substring(myString.length() / 2);
            result.add(Long.valueOf(s1));
            result.add(Long.valueOf(s2));
        } else {
            result.add(number * 2024);
        }
        return result;
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