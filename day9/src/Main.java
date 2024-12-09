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
        printResult();
        processFile();
        //printBlock();
        part2();
        //printBlock();
        printResult();
    }


    public static void part2(){

        // need to reset these
        boolean foundToMoveEndingNumber = false;
        boolean foundToMoveStartingNumber = false;
        int numberToMove = -2;
        int toMoveEndingIndex = -2;
        int toMoveStartingIndex = -2;
        int gapToFindStartIndex = 0;
        int gapToFindEndIndex = 0;

        // don't reset these:
        int investigateToMoveFromHere = blocks.length - 1;

        outerloop:
        for (int i = investigateToMoveFromHere;  i >= 0; i--) {

            // find window of the current array to move
            // first find ending number of the window
            if(blocks[i] != -1){
                foundToMoveEndingNumber = true;
                toMoveEndingIndex = i;
                numberToMove = blocks[i];
            }

            //find starting number of the window
            if(foundToMoveEndingNumber){
                for (int j = toMoveEndingIndex;  j >= 0; j--) {
                    if(blocks[j] != numberToMove){
                        foundToMoveStartingNumber = true;
                        toMoveStartingIndex = j + 1;
                        break;
                    }
                }
            }

            //find a suitable window where to place the array window
            if(foundToMoveStartingNumber){
                int gapToFind = toMoveEndingIndex - toMoveStartingIndex + 1;
                int counterTheGap = 0;
                int gapIndex = -1;

                //start searching for a suitable gap
                for (int j = 0; j < toMoveEndingIndex; j++) {

                    //one break condition
                    if (j > toMoveEndingIndex - gapToFind) {
                        foundToMoveEndingNumber = false;
                        foundToMoveStartingNumber = false;
                        numberToMove = -2;
                        toMoveEndingIndex = -2;
                        toMoveStartingIndex = -2;
                        i = i - gapToFind + 1;
                        break;
                    }

                    //break condition end
                    if(blocks[j] == -1){
                        counterTheGap ++;
                        gapIndex = j;
                    } else {
                        counterTheGap = 0;
                    }

                    //switch the numbers if suitable gap found:
                    if (counterTheGap == gapToFind) {
                        gapToFindEndIndex = gapIndex;
                        gapToFindStartIndex = gapIndex - counterTheGap + 1;
                        for (int k = gapToFindStartIndex; k <= gapToFindEndIndex; k++) {
                            blocks[k] = numberToMove;
                        }

                        for (int k = toMoveStartingIndex; k <= toMoveEndingIndex; k++) {
                            blocks[k] = -1;
                        }

                        //reset values for the next loop:
                        foundToMoveEndingNumber = false;
                        foundToMoveStartingNumber = false;
                        numberToMove = -2;
                        toMoveEndingIndex = -2;
                        toMoveStartingIndex = -2;
                        i = i - counterTheGap + 1;
                        break;
                    }
                }
            }
        }
    }

    public static void part1(){
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
        }
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

    public static void printResult(){
        Long result = 0L;
        for (int i = 0; i < blocks.length; i++) {
            if(blocks[i] != -1){
                result = result + (long) i * blocks[i];
            }
        }
        System.out.println(result);
    }
}