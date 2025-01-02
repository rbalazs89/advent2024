import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    static HashMap<String, Long> myMap = new HashMap<>();
    static HashMap<String, Long> nextMap = new HashMap<>();
    public static ArrayList<String> instructions = new ArrayList<>();
    public static ArrayList<Integer> numbers = new ArrayList<>();
    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        part2();
        //part1();
    }

    public static void testConfigurations(){
        long result = 0;
        int chainedKeyPads = 5;

        // numpad:
        String currentString = "<<vA";
        myMap.put(currentString, 1L);

        // keypad:
        int counter = 0;
        while(counter < chainedKeyPads){
            counter++;

            // put new values into "nextMap"
            nextMap.clear();
            for(HashMap.Entry<String, Long> entry : myMap.entrySet()){
                sliceUpString(entry.getKey(), entry.getValue());
            }

            myMap.clear();
            // process next values into myMap
            for(HashMap.Entry<String, Long> entry : nextMap.entrySet()){
                processStringIntoHashMap(entry.getKey(), entry.getValue());
                //System.out.println(entry.getKey() + " values into map " + entry.getValue());
            }
        }

        // get length:;
        for(HashMap.Entry<String, Long> entry : myMap.entrySet()){
            result = result + entry.getKey().length() * entry.getValue();
        }
        System.out.println(result);

        myMap.clear();
    }



    public static void part2(){
        long result = 0;
        int chainedKeyPads = 25;
        long part1result = 0;
        for (int j = 0; j < instructions.size(); j++) {
            String instruction = instructions.get(j);

            // numpad:
            String currentString = numPad(instruction);
            String[] instructionArray = makeArray(currentString);

            for (int i = 0; i < instructionArray.length; i++) {
                currentString = instructionArray[i];
                if(myMap.get(currentString) == null){
                    myMap.put(currentString, 1L);
                } else {
                    myMap.put(currentString, myMap.get(currentString) + 1L);
                }
            }
            // part 1 version for reference comparison:
            /*
            String s = numPad(instructions.get(j));
            for (int i = 0; i < chainedKeyPads; i++) {
                s = keyPad(s);
            }
            part1result = part1result + s.length() * numbers.get(j);
            */

            // keypad:
            int counter = 0;
            while(counter < chainedKeyPads){
                counter++;

                // put new values into "nextMap"
                nextMap.clear();
                for(HashMap.Entry<String, Long> entry : myMap.entrySet()){
                    sliceUpString(entry.getKey(), entry.getValue());
                }

                myMap.clear();
                // process next values into myMap
                for(HashMap.Entry<String, Long> entry : nextMap.entrySet()){
                    processStringIntoHashMap(entry.getKey(), entry.getValue());
                    //System.out.println(entry.getKey() + " values into map " + entry.getValue());
                }
            }

            // get length:;
            for(HashMap.Entry<String, Long> entry : myMap.entrySet()){
                result = result + entry.getKey().length() * entry.getValue() * numbers.get(j);
            }
            myMap.clear();
        }
        System.out.println("part 2 result with hashmap: " + result);
        System.out.println("part 1 result for reference: " + part1result);

    }
    public static void sliceUpString(String s, long value){
        String[] myArray = makeArray(s);
        for (int i = 0; i < myArray.length; i++) {
            String currentString = myArray[i];
            if(nextMap.get(currentString) == null){
                nextMap.put(currentString, value);
            } else {
                nextMap.put(currentString, nextMap.get(currentString) + value);
            }
        }
    }
    public static String[] makeArray(String s){
        ArrayList<String> helper = new ArrayList<>();
        while(s.length() > 0){
            for (int i = 0; i < s.length(); i++) {
                if(s.charAt(i) == 'A'){
                    helper.add(s.substring(0,i + 1));
                    s = s.substring(i + 1, s.length());
                    break;
                }
            }
        }
        String[] result = new String[helper.size()];
        for (int i = 0; i < helper.size(); i++) {
            result[i] = helper.get(i);
        }

        return result;
    }
    public static void processStringIntoHashMap(String s, long value){
        if(myMap.get(s) == null){
            myMap.put(keyPad(s),value);
        } else {
            myMap.put(keyPad(s), myMap.get(s) + value);
        }
    }

    public static void part1() {
        int result = 0;
        for (int i = 0; i < instructions.size(); i++) {
            String firstString = numPad(instructions.get(i));
            String secondString = keyPad(firstString);
            String thirdString = keyPad(secondString);
            //String fourthString = keyPad(thirdString);
            //String fifthString = keyPad(fourthString);
            result = result + thirdString.length() * numbers.get(i);
            //result = result + fifthString.length() * numbers.get(i);
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
        if(current != '<' && end != '<'){
            if(s.equals(">^")){
                return "^>";
            }
            if(s.equals("v<")){
                return "<v";
            }
            if(s.equals("^<")){
                return "<^";
            }
            if(s.equals(">v")){
                return "v>";
            }
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

            while (currentRow < endRow) {
                code.append("^"); // move up
                currentRow ++;
            }

            while (currentCol < endCol) {
                code.append(">"); // move right
                currentCol++;
            }
        }
        return code.toString();
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