import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    static String part1Result;
    public static ArrayList<Wire> wires = new ArrayList<>();
    public static ArrayList<Instruction> instructions = new ArrayList<>();
    public static HashMap<String, Wire> storage = new HashMap<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        //check();
        //part1();
        part2();
    }

    public static void part2() {
        processFile();

        /**
         * switched nodes manually found from the graph png file created with graphviz
         *
        switch 1:
        5 kwb
        163 z12

        switch 2:
        50 z21
        198 tgr

        switch 3:
        46: jqn
        87: cph

        switch 4:
        112: qkf
        195: z16
        */
        switchWire(5,163);
        switchWire(50,198);
        switchWire(46,87);
        switchWire(112,195);
        getGraphValues();
        check();
        determineDifference();

    }

    public static void switchWire(int instructionNumberA, int instructionNumberB){
        Wire endWireA = storage.get(instructions.get(instructionNumberA).endWire.name);
        Wire endWireB = storage.get(instructions.get(instructionNumberB).endWire.name);
        instructions.get(instructionNumberA).endWire = endWireB;
        instructions.get(instructionNumberB).endWire = endWireA;
    }

    public static void determineDifference(){
        StringBuilder xResult = new StringBuilder();
        wires.stream()
                .filter(wire -> wire.name.startsWith("x"))
                .sorted((w1, w2) -> Integer.compare(
                        Integer.parseInt(w2.name.substring(1)),
                        Integer.parseInt(w1.name.substring(1))
                ))
                .forEach(wire -> xResult.append(wire.value));

        StringBuilder yResult = new StringBuilder();
        wires.stream()
                .filter(wire -> wire.name.startsWith("y"))
                .sorted((w1, w2) -> Integer.compare(
                        Integer.parseInt(w2.name.substring(1)),
                        Integer.parseInt(w1.name.substring(1))
                ))
                .forEach(wire -> yResult.append(wire.value));


        String yBinaryString = yResult.toString();
        long yBinaryAsLong = Long.parseLong(yBinaryString, 2);

        String xBinaryString = xResult.toString();
        long xBinaryAsLong = Long.parseLong(xBinaryString, 2);

        long XYResult = yBinaryAsLong + xBinaryAsLong;
        String stringResult = Long.toString(XYResult, 2);

        StringBuilder result = new StringBuilder();
        wires.stream()
                .filter(wire -> wire.name.startsWith("z"))
                .sorted((w1, w2) -> Integer.compare(
                        Integer.parseInt(w2.name.substring(1)),
                        Integer.parseInt(w1.name.substring(1))
                ))
                .forEach(wire -> result.append(wire.value));

        String binaryString = result.toString();

        part1Result = binaryString;

        System.out.println(stringResult + " <- goal");
        System.out.println(part1Result + " <- current");
        int differentDigits = 0;
        for (int i = 0; i < part1Result.length(); i++) {
            if(part1Result.charAt(i) != stringResult.charAt(i)){
                System.out.print(45 - i + " ");
                differentDigits ++;
            }
        }
        System.out.println();
        System.out.println(differentDigits);

    }
    public static void getGraphValues(){
        Queue<Instruction> queue = new LinkedList();
        for (int i = 0; i < instructions.size(); i++) {
            queue.add(instructions.get(i));
        }
        int counter = 0;

        while(!queue.isEmpty()){
            counter ++;
            Instruction current = queue.poll();

            if(current.type.equals("AND")){
                if(current.wire1.value == -1 && current.wire2.value == -1){
                    queue.add(current);
                } else if(current.wire1.value == 1 && current.wire2.value == 1){
                    current.endWire.value = 1;
                } else if(current.wire1.value == 0 && current.wire2.value == 0){
                    current.endWire.value = 0;
                } else if(current.wire1.value == 1 && current.wire2.value == 0){
                    current.endWire.value = 0;
                } else if(current.wire1.value == 0 && current.wire2.value == 1){
                    current.endWire.value = 0;
                } else if(current.wire1.value == -1 && current.wire2.value == 1){
                    queue.add(current);
                } else if(current.wire1.value == 1 && current.wire2.value == -1){
                    queue.add(current);
                } else if(current.wire1.value == -1 && current.wire2.value == 0){
                    current.endWire.value = 0;
                    //queue.add(current);
                } else if(current.wire1.value == 0 && current.wire2.value == -1){
                    current.endWire.value = 0;
                    //queue.add(current);
                }
            } else if (current.type.equals("OR")){
                if(current.wire1.value == -1 && current.wire2.value == -1){
                    queue.add(current);
                } else if(current.wire1.value == 1 && current.wire2.value == 1){
                    current.endWire.value = 1;
                } else if(current.wire1.value == 0 && current.wire2.value == 0){
                    current.endWire.value = 0;
                } else if(current.wire1.value == 1 && current.wire2.value == 0){
                    current.endWire.value = 1;
                } else if(current.wire1.value == 0 && current.wire2.value == 1){
                    current.endWire.value = 1;
                } else if(current.wire1.value == -1 && current.wire2.value == 1){
                    current.endWire.value = 1;
                    //queue.add(current);
                } else if(current.wire1.value == 1 && current.wire2.value == -1){
                    current.endWire.value = 1;
                    //queue.add(current);
                } else if(current.wire1.value == -1 && current.wire2.value == 0){
                    queue.add(current);
                } else if(current.wire1.value == 0 && current.wire2.value == -1){
                    queue.add(current);
                }
            } else if (current.type.equals("XOR")) {
                if(current.wire1.value == -1 && current.wire2.value == -1){
                    queue.add(current);
                } else if(current.wire1.value == 1 && current.wire2.value == 1){
                    current.endWire.value = 0;
                } else if(current.wire1.value == 0 && current.wire2.value == 0){
                    current.endWire.value = 0;
                } else if(current.wire1.value == 1 && current.wire2.value == 0){
                    current.endWire.value = 1;
                } else if(current.wire1.value == 0 && current.wire2.value == 1){
                    current.endWire.value = 1;
                } else if(current.wire1.value == -1 && current.wire2.value == 1){
                    queue.add(current);
                } else if(current.wire1.value == 1 && current.wire2.value == -1){
                    queue.add(current);
                } else if(current.wire1.value == -1 && current.wire2.value == 0){
                    queue.add(current);
                } else if(current.wire1.value == 0 && current.wire2.value == -1){
                    queue.add(current);
                }
            }
        }
        //System.out.println(counter);
    }

    public static void part1(){
        getGraphValues();

        StringBuilder result = new StringBuilder();
        wires.stream()
                .filter(wire -> wire.name.startsWith("z"))
                .sorted((w1, w2) -> Integer.compare(
                        Integer.parseInt(w2.name.substring(1)),
                        Integer.parseInt(w1.name.substring(1))
                ))
                .forEach(wire -> result.append(wire.value));

        String binaryString = result.toString();
        //System.out.println(result);
        part1Result = binaryString;
        long binaryAsLong = Long.parseLong(binaryString, 2);

        //System.out.println("resulting binary string: " + binaryString);
        //System.out.println("binary as long: " + binaryAsLong);
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

    public static void processFile(){
        List<String> input = readFile("src/input1.txt");
        // reset global storages:
        part1Result = "";
        wires.clear();
        instructions.clear();
        storage.clear();

        // process the file input:
        boolean instructionsInput = false;
        Set<String> wiresSet = new HashSet<>();
        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).equals("")){
                instructionsInput = true;
                continue;
            }
            if(!instructionsInput){
                String s = input.get(i);
                String[] s2 = s.split(" ");
                Wire wire = new Wire();
                wire.name = s2[0].substring(0, s2[0].length() - 1);
                wire.value = Integer.parseInt(s2[1]);
                storage.put(wire.name, wire);
                wiresSet.add(wire.name);
                wires.add(storage.get(wire.name));
            } else {
                Instruction instruction = new Instruction();

                String s = input.get(i);
                String[] s2 = s.split(" ");

                Wire wire1 = new Wire();
                wire1.name = s2[0];

                if(wiresSet.add(wire1.name)){
                    storage.put(wire1.name, wire1);
                    wires.add(storage.get(wire1.name));
                } else {
                    wire1 = storage.get(wire1.name);
                }

                Wire wire2 = new Wire();
                wire2.name = s2[2];
                if(wiresSet.add(wire2.name)){
                    storage.put(wire2.name, wire2);
                    wires.add(storage.get(wire2.name));
                } else {
                    wire2 = storage.get(wire2.name);
                }

                Wire endWire = new Wire();
                endWire.name = s2[4];
                if(wiresSet.add(endWire.name)){
                    storage.put(endWire.name, endWire);
                    wires.add(storage.get(endWire.name));
                } else {
                    endWire = storage.get(endWire.name);
                }

                instruction.wire1 = wire1;
                instruction.wire2 = wire2;
                instruction.endWire = endWire;
                instruction.type = s2[1];
                instructions.add(instruction);
            }
        }
    }

    public static void check(){
        for (int i = 0; i < instructions.size(); i++) {
            System.out.println(i + ": " + instructions.get(i).wire1.name + " " + instructions.get(i).wire1.value + " " +
                    instructions.get(i).type + " " + instructions.get(i).wire2.name + " " +instructions.get(i).wire2.value +
                    " -> " + instructions.get(i).endWire.name + " " + instructions.get(i).endWire.value);
        }

        /*
        for (int i = 0; i < wires.size(); i++) {
            System.out.println(wires.get(i).name + " " + wires.get(i).value);
        }*/
    }

    public static void processFile(List<String> input){
        // reset global storages:
        part1Result = "";
        wires.clear();
        instructions.clear();
        storage.clear();

        // process the file input:
        boolean instructionsInput = false;
        Set<String> wiresSet = new HashSet<>();
        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).equals("")){
                instructionsInput = true;
                continue;
            }
            if(!instructionsInput){
                String s = input.get(i);
                String[] s2 = s.split(" ");
                Wire wire = new Wire();
                wire.name = s2[0].substring(0, s2[0].length() - 1);
                wire.value = Integer.parseInt(s2[1]);
                storage.put(wire.name, wire);
                wiresSet.add(wire.name);
                wires.add(storage.get(wire.name));
            } else {
                Instruction instruction = new Instruction();

                String s = input.get(i);
                String[] s2 = s.split(" ");

                Wire wire1 = new Wire();
                wire1.name = s2[0];

                if(wiresSet.add(wire1.name)){
                    storage.put(wire1.name, wire1);
                    wires.add(storage.get(wire1.name));
                } else {
                    wire1 = storage.get(wire1.name);
                }

                Wire wire2 = new Wire();
                wire2.name = s2[2];
                if(wiresSet.add(wire2.name)){
                    storage.put(wire2.name, wire2);
                    wires.add(storage.get(wire2.name));
                } else {
                    wire2 = storage.get(wire2.name);
                }

                Wire endWire = new Wire();
                endWire.name = s2[4];
                if(wiresSet.add(endWire.name)){
                    storage.put(endWire.name, endWire);
                    wires.add(storage.get(endWire.name));
                } else {
                    endWire = storage.get(endWire.name);
                }

                instruction.wire1 = wire1;
                instruction.wire2 = wire2;
                instruction.endWire = endWire;
                instruction.type = s2[1];
                instructions.add(instruction);
            }
        }
    }
}