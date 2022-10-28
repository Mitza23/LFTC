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
        System.out.println(programScanner.readFile("src/main/resources/p1.txt"));
    }
}