import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static ArrayList<String> instructions = new ArrayList<>();
    public static ArrayList<Integer> numbers = new ArrayList<>();
    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        part2();
    }

    public static void part2(){
        int chainedKeyPads = 12;
        for (int i = 0; i < 1; i++) {
            String firstString = "^";
            for (int j = 0; j < chainedKeyPads; j++) {
                firstString = keyPad(firstString);
                System.out.println(firstString.length());
            }
        }
    }

    public static void part1() {
        int result = 0;
        for (int i = 0; i < instructions.size(); i++) {
            String firstString = numPad(instructions.get(i));
            String secondString = keyPad(firstString);
            String thirdString = keyPad(secondString);
            result = result + thirdString.length() * numbers.get(i);
        }
        System.out.println(result);
    }

    public static void processFile(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            instructions.add(input.get(i));

            String numberString = "";
            for (int j = 0; j < input.get(i).length(); j++) {
                if(input.get(i).charAt(j) != 'A'){
                    if(!(input.get(i).charAt(j) == 'O' && j == 0)){
                        numberString = numberString + input.get(i).charAt(j);
                    }
                }
            }
            numbers.add(Integer.valueOf(numberString));
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
    /**
    numpad:
     +---+---+---+
     | 7 | 8 | 9 |
     +---+---+---+
     | 4 | 5 | 6 |
     +---+---+---+
     | 1 | 2 | 3 |
     +---+---+---+
         | 0 | A |
         +---+---+

     keypad:
         +---+---+
         | ^ | A |
     +---+---+---+
     | < | v | > |
     +---+---+---+
     */

    public static String numPad(String s) {
        int currentPosition = 0;
        String toKeyPad = "";
        for (int i = 0; i < s.length(); i++) {
            int endPosition = getNumPadPos(s.charAt(i));
            toKeyPad = toKeyPad + getInstructionsNumPad2(currentPosition, endPosition) + "A";
            currentPosition = endPosition;
        }
        return toKeyPad;
    }

    public static String keyPad(String s){
        char currentChar = 'A';
        String fromKeyPadToKeyPad = "";
        for (int i = 0; i < s.length(); i++) {
            char endChar = s.charAt(i);
            fromKeyPadToKeyPad = fromKeyPadToKeyPad + getInstructionsKeyPad2(currentChar, endChar) + "A";
            currentChar = endChar;
        }
        return fromKeyPadToKeyPad;
    }

    public static int getNumPadPos(Character c){
        if(c == 'A'){
            return 0;
        } else if( c == '0'){
            return -1;
        } else return Integer.parseInt(String.valueOf(c));
    }

    public static String getInstructionsKeyPad(char current, char end){
        StringBuilder code = new StringBuilder();
        int currentRow = getKeyPadRow(current);
        int currentCol = getKeyPadCol(current);

        int endRow = getKeyPadRow(end);
        int endCol = getKeyPadCol(end);

        while (currentRow < endRow) {
            code.append("v"); // move down
            currentRow++;
        }

        while (currentCol > endCol) {
            code.append("<"); // move left
            currentCol--;
        }

        while (currentCol < endCol) {
            code.append(">"); // move right
            currentCol++;
        }

        while (currentRow > endRow) {
            code.append("^"); // move up
            currentRow --;
        }

        return code.toString();

    }

    public static String getInstructionsNumPad(int current, int end) {

        int currentRow = (int)(Math.ceil(current / 3.0));
        int currentCol = (current + 2) % 3;

        int endRow = (int)(Math.ceil(end / 3.0));
        int endCol = (end + 2) % 3;

        StringBuilder code = new StringBuilder();


        while (currentRow < endRow) {
            code.append("^"); // move up
            currentRow ++;
        }

        while (currentCol > endCol) {
            code.append("<"); // move left
            currentCol--;
        }

        while (currentCol < endCol) {
            code.append(">"); // move right
            currentCol++;
        }
        while (currentRow > endRow) {
            code.append("v"); // move down
            currentRow--;
        }

        return code.toString();
    }

    public static int getKeyPadRow(char c){
        if(c == '^' || c == 'A'){
            return 0;
        } else return 1;
    }

    public static int getKeyPadCol(char c){
        if(c == '<'){
            return 0;
        } else if (c == '^' || c == 'v'){
            return 1;
        } else return 2;
    }

    public static String getInstructionsKeyPad2(char current, char end){
        String s = getInstructionsKeyPad(current, end);
        if(s.equals("v>")){
            return "<v";
        }
        if(s.equals("^<")){
            return "<^";
        }
        if(s.equals(">v")){
            return "v>";
        }
        return s;
    }

    public static String getInstructionsNumPad2(int current, int end){
        int currentRow = (int)(Math.ceil(current / 3.0));
        int currentCol = (current + 2) % 3;

        int endRow = (int)(Math.ceil(end / 3.0));
        int endCol = (end + 2) % 3;

        StringBuilder code = new StringBuilder();
        if((currentRow == 0 && endCol == 0) || (currentCol == 0 && endRow == 0)){
            return getInstructionsNumPad(current, end);
        } else {
            while (currentCol > endCol) {
                code.append("<"); // move left
                currentCol--;
            }

            while (currentRow > endRow) {
                code.append("v"); // move down
                currentRow--;
            }

            while (currentCol < endCol) {
                code.append(">"); // move right
                currentCol++;
            }

            while (currentRow < endRow) {
                code.append("^"); // move up
                currentRow ++;
            }
        }
        return code.toString();
    }

    public static String applyKeyPadTransformations(String input, int chains) {
        char currentChar = 'A'; // Starting position
        for (int i = 0; i < chains; i++) {
            for (char instruction : input.toCharArray()) {
                currentChar = getNextKeyPadPosition(currentChar, instruction);
            }
        }
        return Character.toString(currentChar); // Return the final key
    }

    public static char getNextKeyPadPosition(char current, char instruction) {
        // Define keypad layout with the null element at 0,0
        char[][] keypad = {
                {'-', 'A', '^'},  // Row 0
                {'<', 'v', '>'}   // Row 1
        };

        // Locate current position
        int currentRow = getKeyPadRow(current);
        int currentCol = getKeyPadCol(current);

        // Apply movement based on instruction
        switch (instruction) {
            case '^':
                if (currentRow > 0 && currentCol < keypad[currentRow - 1].length) {
                    currentRow -= 1;
                }
                break;
            case 'v':
                if (currentRow < keypad.length - 1 && currentCol < keypad[currentRow + 1].length) {
                    currentRow += 1;
                }
                break;
            case '<':
                if (currentCol > 0 && keypad[currentRow][currentCol - 1] != '-') {
                    currentCol -= 1;
                }
                break;
            case '>':
                if (currentCol < keypad[currentRow].length - 1 && keypad[currentRow][currentCol + 1] != '-') {
                    currentCol += 1;
                }
                break;
        }

        return keypad[currentRow][currentCol];
    }
}