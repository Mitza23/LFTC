package mihai;

public class Main {
    public static void main(String[] args) {
//        String line = "int n; n = 2; if(n == 2) { }";
//        StringTokenizer tokenizer = new StringTokenizer(line, " ;(){}[]", true);
//        while (tokenizer.hasMoreTokens()) {
//            var token = tokenizer.nextToken();
//            System.out.println("[" + token + "]");
//        }

        ProgramScanner programScanner = new ProgramScanner("src/main/resources/token.txt");
        programScanner.readTokens();
        var pif = programScanner.readFile("src/main/resources/p3err.txt");
        System.out.println("\tPIF");
        for (var p : pif) {
            System.out.println(p);
        }
        var ST = programScanner.getSymbolTable();
        System.out.println("\n\n\tSYMBOL TABLE");
        for (var p : ST) {
            System.out.println(p);
        }
        var CT = programScanner.getConstantsTable();
        System.out.println("\n\n\tCONSTANTS TABLE");
        for (var p : CT) {
            System.out.println(p);
        }
    }
}