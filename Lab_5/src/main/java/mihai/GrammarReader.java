package mihai;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

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
            terminals.add(" ");
            /// Starting symbol
            line = scanner.nextLine();
            startingSymbol = line;
            /// Transformation
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                tokens = line.split("~");
                List<Value> value = findValues(tokens[0]);
                if(value.size() != 1 || value.get(0).isTerminal()){
                    throw new RuntimeException("Not CFG!");
                }
                productions.add(new Production(value.get(0), findValues(tokens[1]), generateName(productions, value.get(0).getValue())));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private List<Value> findValues(String string) {
        boolean isTerminal = true;
        List<Value> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder(string);
        StringBuilder aux = new StringBuilder();

        while (sb.length() > 0) {
            if ("[".equals(sb.substring(0, 1))) {
                isTerminal = false;
//                if(!aux.isEmpty()){
//                   result.add(new Value(aux.toString(), true));
//                }
                aux = new StringBuilder();
            } else if("]".equals(sb.substring(0,1))) {
                isTerminal = true;
                if(!aux.isEmpty()){
                    result.add(new Value(aux.toString(),false));
                    aux = new StringBuilder();
                }
            } else {
                if(isTerminal){
                    result.add(new Value(sb.substring(0, 1), true));
                }
                else {
                    aux.append(sb.substring(0, 1));
                }
            }
            sb = new StringBuilder(sb.substring(1));
        }
        return result;
    }

    private String generateName(List<Production> productions, String token) {
        long count = productions.stream().filter(v -> Objects.equals(v.left.getValue(), token)).count();
        return token + (count + 1);
    }

    public List<Production> getProductionsForNonTerminal(String nonTerminal) {
        return getProductions().stream().filter(v -> v.left.getValue().equals(nonTerminal)).collect(Collectors.toList());
    }

    public Production getProductionByName(String name){
        for(var p : productions){
            if(p.name.equals(name)){
                return p;
            }
        }
        return null;
    }
}
