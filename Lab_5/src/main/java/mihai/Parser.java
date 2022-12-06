package mihai;

import java.util.*;

public class Parser {
    GrammarReader grammarReader;

    HashMap<String, Set<String>> firstTable = new HashMap<>();

//    Value epsilon = new Value("$", true);

    String epsilon = "$";

    public Parser(GrammarReader grammarReader) {
        this.grammarReader = grammarReader;
    }

    public Set<String> concatenateLengthOne(List<Set<String>> list) {
        var result = new HashSet<String>();
        int index = 0;
        boolean done = false;
        while (!done && index < list.size()) {
            for(var v: list.get(index)) {
                if(!epsilon.equals(v)){
                    result.add(v);
                }
            }
            if (list.get(index).contains(epsilon)) {
                index += 1;
            } else {
                done = true;
            }
        }
        return result;
    }

    public void computeFirstIteration(HashMap<String, Set<String>> previous) {
        HashMap<String, Set<String>> current = new HashMap<>();
        for (var nonTerminal : grammarReader.getNonTerminals()) {
            current.put(nonTerminal, new HashSet<>());
        }
        for (var nonTerminal : grammarReader.getNonTerminals()) {
            var terminalList = current.get(nonTerminal);
            var productions = grammarReader.getProductionsForNonTerminal(nonTerminal);
            for (var production : productions) {
                var value = production.right.get(0);
                if (value.isTerminal()) {
                    terminalList.add(production.right.get(0).getValue());
                } else {
                    // is NON TERMINAL
                    List<Set<String>> setList = new ArrayList<>();
                    for (var rightValue : production.right) {
                        if (rightValue.isTerminal()) {
                            Set<String> toAdd = new HashSet<>();
                            toAdd.add(rightValue.getValue());
                            setList.add(toAdd);
                        }
                        else {
                            var previousList = previous.get(rightValue.getValue());
                            setList.add(previousList);
                        }
                    }
                    current.put(value.getValue(), concatenateLengthOne(setList));
                }
            }
        }
        // compare CURRENT with PREVIOUS
        boolean match = true;
        for (var key: previous.keySet()) {
            if (!current.containsKey(key)) {
                match = false;
                break;
            }
                for(var value: previous.get(key)) {
                    if(!current.get(key).contains(value)) {
                        match = false;
                        break;
                    }
                }
        }
        if(!match) {
            computeFirstIteration(current);
        }
        else {
            firstTable = current;
        }
    }
}
