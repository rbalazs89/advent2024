import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    static String[] towels;
    static ArrayList<String> patterns = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        part1();
        part2();
    }

    public static void part2() {
        long totalArrangements = 0;

        for (int j = 0; j < patterns.size(); j++) {
            String pattern = patterns.get(j);
            long[] dp = new long[pattern.length() + 1];
            dp[0] = 1;

            for (int i = 0; i < pattern.length(); i++) {
                if (dp[i] == 0) continue;

                for (int k = 0; k < towels.length; k++) {
                    String towel = towels[k];
                    int endPosition = i + towel.length();

                    if (endPosition <= pattern.length() &&
                            pattern.substring(i, endPosition).equals(towel)) {
                        dp[endPosition] += dp[i];
                    }
                }
            }

            long arrangements = dp[pattern.length()];
            totalArrangements += arrangements;
        }

        System.out.println("total: " + totalArrangements);
    }

    public static void part1() {
        int result = 0;

        for (int j = 0; j < patterns.size(); j++) {
            String pattern = patterns.get(j);
            Queue<Integer> queue = new LinkedList<>();
            Set<Integer> visited = new HashSet<>();
            queue.add(0);

            boolean isPossible = false;

            while (!queue.isEmpty()) {
                int currentPosition = queue.poll();
                if (visited.contains(currentPosition)) {
                    continue;
                }
                visited.add(currentPosition);

                for (int i = 0; i < towels.length; i++) {
                    String towel = towels[i];
                    int nextPosition = currentPosition + towel.length();

                    if (nextPosition <= pattern.length() &&
                            pattern.substring(currentPosition, nextPosition).equals(towel)) {

                        if (nextPosition == pattern.length()) {
                            isPossible = true;
                            result++;
                            break;
                        }
                        queue.add(nextPosition);
                    }
                }

                if (isPossible){
                    break;
                }
            }
        }
        System.out.println(result);
    }

    public static void processFile(List<String> input){
        String s = input.get(0).replaceAll(" ", "");
        towels = s.split(",");

        for (int i = 2; i < input.size(); i++) {
            patterns.add(input.get(i));
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
}