package mihai;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GrammarReader {

    private List<Production> productions;
    private Set<String> nonTerminals;
    private Set<String> terminals;
    private String startingSymbol;

    public List<Production> getProductions() {
        return productions;
    }

    public Set<String> getNonTerminals() {
        return nonTerminals;
    }

    public Set<String> getTerminals() {
        return terminals;
    }

    public String getStartingSymbol() {
        return startingSymbol;
    }

    public void readGrammar(String fileName) {
        productions = new ArrayList<>();
        nonTerminals = new HashSet<>();
        terminals = new HashSet<>();
        String line;
        try (Scanner scanner = new Scanner(new File(fileName))) {
            /// Non Terminals
            line = scanner.nextLine();
            var tokens = line.split(" ");
            nonTerminals.addAll(List.of(tokens));
            /// Terminals
            line = scanner.nextLine();
            tokens = line.split(" ");
            terminals.addAll(List.of(tokens));
            /// Starting symbol
            line = scanner.nextLine();
            startingSymbol = line;
            /// Transformation
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                tokens = line.split(" ");
                productions.add(new Production(tokens[0], tokens[1], generateName(productions, tokens[0])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private String generateName(List<Production> productions, String token) {
        long count = productions.stream().filter(v -> Objects.equals(v.left, token)).count();
        return token + (count + 1);
    }


}
