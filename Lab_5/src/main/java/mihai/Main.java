package mihai;

import java.util.HashMap;
import java.util.Scanner;

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
                        parser.computeFirstIteration(new HashMap<>());
                        for(var key: parser.firstTable.keySet()) {
                            System.out.println(key + ": ");
                            for(var value: parser.firstTable.get(key)) {
                                System.out.println("\t" + value);
                            }
                        }
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
        System.out.println("0. Exit");
    }
}
