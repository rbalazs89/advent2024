import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static Step[][] field;
    static int maxX = 0;
    static int maxY = 0;
    static int part2Result = 0;

    public static void main(String[] args) {

        List<String> input = readFile("src/input1.txt");
        processFile(input);
        part1();
        part2();
    }

    public static void part1(){
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if(field[i][j].value == 0){
                    stepNext(field[i][j], j, i, 0);
                }
            }
        }

        int result = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                result = result + field[i][j].results.size();
            }
        }
        System.out.println(result);
    }


    public static void part2(){
        System.out.println(part2Result);
    }

    public static void stepNext(Step step, int currentX, int currentY, int fieldValue){
        if(field[currentY][currentX].value == 9){
            part2Result ++;
            if(!step.results.contains(field[currentY][currentX])){
                step.results.add(field[currentY][currentX]);
            }
            return;
        }
        fieldValue = fieldValue + 1;

        int[][] pathSteps = {{-1,0},{1,0},{0,1},{0,-1}};
        for (int i = 0; i < pathSteps.length; i++) {
            int nextX = currentX + pathSteps[i][0];
            int nextY = currentY + pathSteps[i][1];
            if(nextX >= 0 && nextX < maxX && nextY >= 0 && nextY < maxY && field[nextY][nextX].value == fieldValue){
                stepNext(step, nextX, nextY, fieldValue);
            }
        }
    }



    public static void processFile(List<String> input){
        maxY = input.size();
        maxX = input.get(0).length();
        field = new Step[maxY][maxX];


        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                Step step = new Step();
                field[i][j] = step;
                step.posX = j;
                step.posY = i;
                step.value = Integer.parseInt(input.get(i).substring(j, j + 1));
            }
        }
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