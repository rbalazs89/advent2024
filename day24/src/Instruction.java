public class Instruction {
    public String type;
    // XOR
    // AND
    // OR

    public Wire wire1;
    public Wire wire2;
    public Wire endWire;
    public Instruction(){

    }

    public Instruction(String type, Wire wire1, Wire wire2, Wire endWire) {
        this.type = type;
        this.wire1 = wire1;
        this.wire2 = wire2;
        this.endWire = endWire;
    }
}
