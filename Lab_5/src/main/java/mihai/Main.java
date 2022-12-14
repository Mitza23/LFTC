package mihai;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        GrammarReader grammarReader = new GrammarReader();
        grammarReader.readGrammar("src/main/resources/g2.txt");
        Parser parser = new Parser(grammarReader);

        try (Scanner scanner = new Scanner(System.in)) {
            boolean done = false;

            while (!done) {
                menu();
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println(grammarReader.getNonTerminals());
                        break;
                    case 2:
                        System.out.println(grammarReader.getTerminals());
                        break;
                    case 3:
                        grammarReader.getProductions()
                                .stream().forEach(v -> System.out.println(v));;
                        break;
                    case 4:
                        Scanner scanner2 = new Scanner(System.in);
                        String nonTerminal = scanner2.next();
                        grammarReader.getProductionsForNonTerminal(nonTerminal).forEach(System.out::println);
                        break;
                    case 5:
                        parser.computeFirst();
                        for(var key: parser.firstTable.keySet()) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(key).append(": ");
                            for(var value: parser.firstTable.get(key)) {
                                sb.append("\t").append(value);
                            }
                            System.out.println(sb);
                        }
                        break;
                    case 6:
                        parser.computeFollow();
                        for(var key: parser.followTable.keySet()) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(key).append(": ");
                            for(var value: parser.followTable.get(key)) {
                                sb.append("\t").append(value);
                            }
                            System.out.println(sb);
                        }
                        break;
                    case 7:
                        parser.computeParsingTable();
                        StringBuilder sb = new StringBuilder();
                        for(var key: parser.parsingTable.keySet()) {
                            for(var key2: parser.parsingTable.get(key).keySet()) {
                                sb.append(key).append(", ").append(key2).append(" ").append(parser.parsingTable.get(key).get(key2)).append("\n");
                            }
                        }
                        System.out.println(sb);
                        System.out.println(parser.parse("a*(a+a)"));
                        break;
                    case 0:
                        done = true;
                        break;
                    default:
                        System.out.println("Invalid operation");
                }
            }
        }
    }

    static void menu() {
        System.out.println("1. Set of non-terminals");
        System.out.println("2. Set of terminals");
        System.out.println("3. Set of productions");
        System.out.println("4. Production for terminal");
        System.out.println("5. FIRST");
        System.out.println("6. FOLLOW");
        System.out.println("7. PARSE");
        System.out.println("0. Exit");
    }
}
