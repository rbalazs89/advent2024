import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static int regA;
    public static int regB;
    public static int regC;
    public static int[] instructions;
    public static int instructionPointer = 0;
    public static ArrayList<Integer> outputs = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        part1();
        //part2();
    }

    public static void part2(){
        for (int i = 0; i < instructions.length; i++) {
            System.out.print(instructions[i] + " ");
        }
        // Program: 2_,4,1_,5,7_,5,4_,3,1_,6,0_,3,5_,5,3_,0
        regA = 2;
        command(2,4);
        command(1,5);
        command(7,5);
        printoutValues();
        System.out.println(outputs);
    }

    public static void part1(){
        instructionPointer = 0;
        while(instructionPointer < instructions.length){
            command(instructions[instructionPointer], instructions[instructionPointer + 1]);
            instructionPointer += 2;
            System.out.println(instructionPointer);
        }

        printoutValues();
        String result = "";
        for (int i = 0; i < outputs.size(); i++) {
            result = result + String.valueOf(outputs.get(i)) + ",";
        }
        System.out.println(result = result.substring(0,result.length()-1));
    }

    public static void processFile(List<String> input){
        int[] tempInt = new int[3];
        for (int i = 0; i < input.size(); i++) {
            if(i == 0 || i == 1 || i == 2){
                String[] s = input.get(i).split(" ");
                tempInt[i] = Integer.valueOf(s[2]);
            }
            if(i == 4){
                String[] s2 = input.get(i).split(" ");
                String[] temp = s2[1].split(",");
                instructions = new int[temp.length];
                for (int j = 0; j < temp.length; j++) {
                    instructions[j] = Integer.valueOf(temp[j]);
                }
            }
        }
        regA = tempInt[0];
        regB = tempInt[1];
        regC = tempInt[2];
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

    public static void command(int i, int literalOperand) {
        int comboOperand = literalOperand;
        if(comboOperand == 4){
            comboOperand = regA;
        } else if (comboOperand == 5){
            comboOperand = regB;
        } else if (comboOperand == 6){
            comboOperand = regC;
        }

        if(i == 0){
            double denominator = Math.pow(2, (double) comboOperand);
            int newNumber = (int)(Math.floor(regA / denominator));
            regA = newNumber;
        } else if(i == 1){
            int bitwiseXOR = regB ^ literalOperand;
            regB = bitwiseXOR;
        } else if(i == 2){
            regB = comboOperand % 8;
        } else if (i == 3){
            if(regA != 0){
                instructionPointer = literalOperand - 2;
            }
        } else if (i == 4){
            int bitwiseXOR = regB ^ regC;
            regB = bitwiseXOR;
        } else if (i == 5){
            outputs.add(comboOperand % 8);
        } else if (i == 6){
            double denominator = Math.pow(2, (double) comboOperand);
            int newNumber = (int)(Math.floor(regA / denominator));
            regB = newNumber;
        } else if (i == 7){
            double denominator = Math.pow(2, (double) comboOperand);
            int newNumber = (int)(Math.floor(regA / denominator));
            regC = newNumber;
        }
    }
    public static void printoutValues(){
        System.out.println();
        System.out.println("A: " + regA);
        System.out.println("B: " + regB);
        System.out.println("C: " + regC);
        for (int i = 0; i < instructions.length; i++) {
            System.out.print(instructions[i]);
        }
        System.out.println();
        //System.out.println(outputs);
    }
}