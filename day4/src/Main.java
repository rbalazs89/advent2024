import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String input[][];
    static int maxX;
    static int maxY;
    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        firstPart();
        secondPart();
    }

    public static void firstPart(){
        int result = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(searchUp(j,i)){
                    result++;
                } if (searchUpRight(j,i)){
                    result++;
                } if (searchRight(j,i)){
                    result++;
                } if (searchDownRight(j,i)){
                    result++;
                } if (searchDown(j,i)){
                    result++;
                } if (searchDownLeft(j,i)){
                    result++;
                } if (searchLeft(j,i)){
                    result++;
                } if (searchUpLeft(j,i)){
                    result++;
                }
            }
        }
        System.out.println(result);
    }

    public static void secondPart(){
        int result = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(searchX(j, i)) {
                    result++;
                }
            }
        }
        System.out.println(result);
    }

    public static boolean searchX(int x, int y){
        if(!input[y][x].equals("A")){
            return false;
        }
        if(x - 1 < 0 || x + 1 >= maxX || y - 1 < 0 || y + 1 >= maxY){
            return false;
        }
        if(input[y - 1][x - 1].equals("M") && input[y - 1][x + 1].equals("M")){
            if(input[y + 1][x - 1].equals("S") && input[y + 1][x + 1].equals("S")){
                return true;
            }
        }
        if(input[y + 1][x + 1].equals("M") && input[y - 1][x + 1].equals("M")){
            if(input[y - 1][x - 1].equals("S") && input[y + 1][x - 1].equals("S")){
                return true;
            }
        }

        if(input[y - 1][x - 1].equals("S") && input[y - 1][x + 1].equals("S")){
            if(input[y + 1][x - 1].equals("M") && input[y + 1][x + 1].equals("M")){
                return true;
            }
        }
        if(input[y + 1][x + 1].equals("S") && input[y - 1][x + 1].equals("S")){
            if(input[y - 1][x - 1].equals("M") && input[y + 1][x - 1].equals("M")){
                return true;
            }
        }

        return false;
    }
    public static boolean searchUp(int x, int y){
        if (y - 3 < 0) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        y = y - 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        y = y - 1;
        if(!input[y][x].equals("A")){
                return false;
        }

        y = y - 1;
        if(!input[y ][x].equals("S")){
                return false;
        }
        return true;
    }

    public static boolean searchUpRight(int x, int y){
        if (y - 3 < 0 || x + 3 >= maxX) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        y = y - 1;
        x = x + 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        y = y - 1;
        x = x + 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        y = y - 1;
        x = x + 1;
        if(!input[y ][x].equals("S")){
            return false;
        }
        return true;
    }

    public static boolean searchRight(int x, int y){
        if (x + 3 >= maxX) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        x = x + 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        x = x + 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        x = x + 1;
        if(!input[y][x].equals("S")){
            return false;
        }
        return true;
    }

    public static boolean searchDownRight(int x, int y){
        if (x + 3 >= maxX || y + 3 >= maxY) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        x = x + 1;
        y = y + 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        x = x + 1;
        y = y + 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        x = x + 1;
        y = y + 1;
        if(!input[y][x].equals("S")){
            return false;
        }
        return true;
    }

    public static boolean searchDown(int x, int y){
        if (y + 3 >= maxY) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        y = y + 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        y = y + 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        y = y + 1;
        if(!input[y][x].equals("S")){
            return false;
        }
        return true;
    }

    public static boolean searchDownLeft(int x, int y){
        if (y + 3 >= maxY || x - 3 < 0) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        y = y + 1;
        x = x - 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        y = y + 1;
        x = x - 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        y = y + 1;
        x = x - 1;
        if(!input[y][x].equals("S")){
            return false;
        }
        return true;
    }

    public static boolean searchLeft(int x, int y){
        if ( x - 3 < 0) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        x = x - 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        x = x - 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        x = x - 1;
        if(!input[y][x].equals("S")){
            return false;
        }
        return true;
    }

    public static boolean searchUpLeft(int x, int y){
        if (x - 3 < 0 || y - 3 < 0) {
            return false;
        }

        if(!input[y][x].equals("X")){
            return false;
        }

        x = x - 1;
        y = y - 1;
        if(!input[y][x].equals("M")){
            return false;
        }

        x = x - 1;
        y = y - 1;
        if(!input[y][x].equals("A")){
            return false;
        }

        x = x - 1;
        y = y - 1;
        if(!input[y][x].equals("S")){
            return false;
        }
        return true;
    }

    public static void processFile(List<String> input2){
        int y = input2.size();
        int x = input2.get(0).length();
        maxY = y;
        maxX = x;
        input = new String[y][x];
        for(int i = 0; i < y; i ++){
            for (int j = 0; j < x; j++) {
                input[i][j] = input2.get(i).substring(j, j + 1);
            }
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