import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        System.out.println(firstPart(input));
        System.out.println(secondPart(input));
    }

    public static int firstPart(List<String> input){
        int result = 0;

        for (int i = 0; i < input.size(); i++) {
            String currentString = input.get(i);

            while(currentString.length() > 8){
                String investigate = "";
                int number1 = 0;
                int number2 = 0;

                if(!currentString.contains("mul(")){
                    break;
                } else {
                    investigate = currentString.substring(currentString.indexOf("mul(") + 4);
                }
                int temp = investigate.indexOf(",");
                if(temp <= 3 && temp >= 1){
                    if(isNumeric(investigate.substring(0, temp))){
                        number1 = Integer.valueOf(investigate.substring(0, temp));
                    } else {
                        currentString = investigate;
                        continue;
                    }
                } else {
                    currentString = currentString.substring(1);
                    continue;
                }

                investigate = investigate.substring(temp + 1);
                temp = investigate.indexOf(")");
                if(temp <= 3 && temp >= 1){
                    if(isNumeric(investigate.substring(0, temp))){
                        number2 = Integer.valueOf(investigate.substring(0, temp));
                    } else {
                        currentString = investigate;
                        continue;
                    }
                } else {
                    currentString = currentString.substring(1);
                    continue;
                }
                investigate = investigate.substring(temp);
                currentString = investigate;
                result = result + number1 * number2;
            }
        }

        return result;
    }

    public static int secondPart(List<String> input){
        int result = 0;
        boolean valid = true;

        for (int i = 0; i < input.size(); i++) {

            String currentString = input.get(i);
            while(currentString.length() > 8){
                String investigate = "";
                int number1 = 0;
                int number2 = 0;

                int doIndex = currentString.indexOf("do()");
                int dontIndex = currentString.indexOf("don't()");
                int mulIndex = currentString.indexOf("mul(");

                if(mulIndex == -1){
                    mulIndex = Integer.MAX_VALUE;
                }

                if(doIndex == -1){
                    doIndex = Integer.MAX_VALUE;
                }

                if(dontIndex == -1){
                    dontIndex = Integer.MAX_VALUE;
                }

                if(doIndex < mulIndex && doIndex < dontIndex){
                    valid = true;
                    currentString = currentString.substring(doIndex + 4);
                    continue;
                }

                if(dontIndex < mulIndex && dontIndex < mulIndex){
                    valid = false;
                    currentString = currentString.substring(dontIndex + 7);
                    continue;
                }

                if(mulIndex < doIndex && mulIndex < dontIndex){
                    investigate = currentString.substring(currentString.indexOf("mul(") + 4);
                }
                if(mulIndex == Integer.MAX_VALUE){
                    break;
                }

                int temp = investigate.indexOf(",");
                if(temp <= 3 && temp >= 1){
                    if(isNumeric(investigate.substring(0, temp))){
                        number1 = Integer.valueOf(investigate.substring(0, temp));
                    } else {
                        currentString = investigate;
                        continue;
                    }
                } else {
                    currentString = currentString.substring(1);
                    continue;
                }

                investigate = investigate.substring(temp + 1);
                temp = investigate.indexOf(")");
                if(temp <= 3 && temp >= 1){
                    if(isNumeric(investigate.substring(0, temp))){
                        number2 = Integer.valueOf(investigate.substring(0, temp));
                    } else {
                        currentString = investigate;
                        continue;
                    }
                } else {
                    currentString = currentString.substring(1);
                    continue;
                }
                investigate = investigate.substring(temp);
                currentString = investigate;
                if(!valid){
                    number1 = 0;
                    number2 = 0;
                }
                result = result + number1 * number2;
            }
        }

        return result;
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
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