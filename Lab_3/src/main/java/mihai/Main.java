package mihai;

public class Main {
    public static void main(String[] args) {
        ProgramScanner programScanner = new ProgramScanner("tokens.txt");
        programScanner.readTokens();
    }
}