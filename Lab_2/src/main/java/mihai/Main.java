package mihai;

public class Main {
    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.addVariable("v1", 1);
        symbolTable.addVariable("v2", 2);
        System.out.println(symbolTable);
        symbolTable.addVariable("v2", 3);
        symbolTable.addVariable("v1'", 1);
        symbolTable.addVariable("va", "a");
        symbolTable.addVariable("vabc", "abc");
        System.out.println(symbolTable);
    }
}