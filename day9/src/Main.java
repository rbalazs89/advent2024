import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String input = "";
    public static int blocks[];

    public static void main(String[] args) {
        input = readFile("src/input1.txt").get(0);
        processFile();
        part1();
    }

    public static void part1(){
        int progress = 0;

        outerloop:
        for (int i = 0; i < blocks.length; i++) {
            if(blocks[i] != -1){
                continue;
            }

            for (int j = blocks.length - 1; j >= 0 ; j--) {
                if(i >= j){
                    break outerloop;
                }
                if(blocks[j] != -1){
                    int tempInt = blocks[i];
                    blocks[i] = blocks[j];
                    blocks[j] = tempInt;
                    break;
                }

            }

            progress ++;
        }

        Long result = 0L;
        for (int i = 0; i < blocks.length; i++) {
            if(blocks[i] != -1){
                result = result + i * blocks[i];
            }
        }
        System.out.println(result);

    }

    public static void processFile(){
        int length = 0;
        for (int i = 0; i < input.length(); i++) {
            length = length + Integer.valueOf(String.valueOf(input.charAt(i)));
        }
        blocks = new int[length];

        int blockValue = 0;
        int stepCounter = 0;
        int currentValue = 0;

        for (int i = 0; i < blocks.length; i++) {
            int currentLength = Integer.valueOf(String.valueOf(input.charAt(stepCounter)));
            stepCounter++;
            if (stepCounter % 2 == 0) {
                currentValue = -1;
                blockValue--;
            } else {
                currentValue = blockValue;
            }
            for (int j = 0; j < currentLength; j++) {
                blocks[i] = currentValue;
                i++;
            }
            blockValue++;
            i--;
        }
    }

    public static void printBlock(){

        for (int i = 0; i < blocks.length; i++) {
            if(blocks[i] == -1){
                System.out.print(".");
            }
            else System.out.print(blocks[i]);
        }
        System.out.println();
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