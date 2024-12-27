import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LogicGateGraph {
    public static ArrayList<Wire> wires = new ArrayList<>();
    public static ArrayList<Instruction> instructions = new ArrayList<>();
    public static HashMap<String, Wire> storage = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // Example setup
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        switchedSolution();
        generateDotFile("graph.dot");
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
    public static void generateDotFileCircular(String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);

        // Write DOT graph structure
        writer.write("digraph LogicGates {\n");

        // Use neato layout engine for circular representation
        writer.write("graph [layout=neato, overlap=false, splines=true];\n");

        for (Instruction inst : instructions) {
            String gateNode = String.format("\"%s_%s\" [label=\"%s\"];\n",
                    inst.type, inst.endWire.name, inst.type);
            writer.write(gateNode);

            String edge1 = String.format("\"%s\" -> \"%s_%s\";\n",
                    inst.wire1.name, inst.type, inst.endWire.name);
            writer.write(edge1);

            String edge2 = String.format("\"%s\" -> \"%s_%s\";\n",
                    inst.wire2.name, inst.type, inst.endWire.name);
            writer.write(edge2);

            String outputEdge = String.format("\"%s_%s\" -> \"%s\";\n",
                    inst.type, inst.endWire.name, inst.endWire.name);
            writer.write(outputEdge);
        }

        writer.write("}\n");
        writer.close();
        System.out.println("DOT file created: " + fileName);
    }

    public static void generateDotFile(String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write DOT graph structure
            writer.write("digraph LogicGates {\n");

            for (Instruction inst : instructions) {
                if (inst == null || inst.type == null || inst.endWire == null) {
                    System.err.println("Invalid instruction: " + inst);
                    continue; // Skip invalid instructions
                }

                // Create a node for the gate
                String gateNode = String.format("\"%s_%s\" [label=\"%s\"];\n",
                        inst.type, inst.endWire.name, inst.type);
                writer.write(gateNode);

                // Determine edge color based on gate type
                String edgeColor;
                switch (inst.type.toUpperCase()) {
                    case "AND":
                        edgeColor = "color=red";
                        break;
                    case "OR":
                        edgeColor = "color=green";
                        break;
                    case "XOR":
                        edgeColor = "color=blue";
                        break;
                    default:
                        edgeColor = "color=black"; // Default color for unknown types
                        break;
                }

                // Create edges for wire1 and wire2 if they exist
                if (inst.wire1 != null) {
                    String edge1 = String.format("\"%s\" -> \"%s_%s\" [%s];\n",
                            inst.wire1.name, inst.type, inst.endWire.name, edgeColor);
                    writer.write(edge1);
                }

                if (inst.wire2 != null) {
                    String edge2 = String.format("\"%s\" -> \"%s_%s\" [%s];\n",
                            inst.wire2.name, inst.type, inst.endWire.name, edgeColor);
                    writer.write(edge2);
                }

                // Create an output edge for the gate
                String outputEdge = String.format("\"%s_%s\" -> \"%s\" [%s];\n",
                        inst.type, inst.endWire.name, inst.endWire.name, edgeColor);
                writer.write(outputEdge);
            }

            writer.write("}\n");
            System.out.println("DOT file created: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing DOT file: " + e.getMessage());
            throw e; // Re-throw to notify caller of the issue
        }
    }

    public static void processFile(List<String> input){
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

    public static void switchWire(int instructionNumberA, int instructionNumberB){
        Wire endWireA = storage.get(instructions.get(instructionNumberA).endWire.name);
        Wire endWireB = storage.get(instructions.get(instructionNumberB).endWire.name);
        instructions.get(instructionNumberA).endWire = endWireB;
        instructions.get(instructionNumberB).endWire = endWireA;
    }

    public static void switchedSolution(){
        switchWire(5,163);
        switchWire(50,198);
        switchWire(46,87);
    }
}