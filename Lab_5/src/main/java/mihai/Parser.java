package mihai;

import java.util.*;

public class Parser {
    GrammarReader grammarReader;

    HashMap<String, Set<String>> firstTable = new HashMap<>();
    HashMap<String, Set<String>> followTable = new HashMap<>();

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
            if (list.get(index) != null) {
                for (var v : list.get(index)) {
                    if (!epsilon.equals(v)) {
                        result.add(v);
                    }
                }
                if (list.get(index).contains(epsilon)) {
                    index += 1;
                } else {
                    done = true;
                }
            }

        }
        return result;
    }

    public boolean areStatesEqual(HashMap<String, Set<String>> state1, HashMap<String, Set<String>> state2) {
        boolean match = true;
        for (var key : state1.keySet()) {
            if (!state2.containsKey(key)) {
                match = false;
                break;
            }
            for (var value : state1.get(key)) {
                if (!state2.get(key).contains(value)) {
                    match = false;
                    break;
                }
            }
        }
        for (var key : state2.keySet()) {
            if (!state1.containsKey(key)) {
                match = false;
                break;
            }
            for (var value : state2.get(key)) {
                if (!state1.get(key).contains(value)) {
                    match = false;
                    break;
                }
            }
        }
        return match;
    }

    public void computeFirst() {
        HashMap<String, Set<String>> previous = new HashMap<>();
        for (var v : grammarReader.getNonTerminals()) {
            previous.put(v, new HashSet<>());
        }

        HashMap<String, Set<String>> current = new HashMap<>();

        boolean done = false;
        while (!done) {
            for (var nonTerminal : grammarReader.getNonTerminals()) {
                current.put(nonTerminal, new HashSet<>());
            }
            for (var nonTerminal : grammarReader.getNonTerminals()) {
                var productions = grammarReader.getProductionsForNonTerminal(nonTerminal);
                for (var production : productions) {
                    var value = production.right.get(0);
                    if (value.isTerminal()) {
                        current.get(nonTerminal).add(production.right.get(0).getValue());
                    } else {
                        List<Set<String>> setList = new ArrayList<>();
                        for (var rightValue : production.right) {
                            if (rightValue.isTerminal()) {
                                Set<String> toAdd = new HashSet<>();
                                toAdd.add(rightValue.getValue());
                                setList.add(toAdd);
                            } else {
                                var previousList = previous.get(rightValue.getValue());
                                if (previousList != null)
                                    setList.add(previousList);
                            }
                        }
                        current.get(nonTerminal).addAll(concatenateLengthOne(setList));
                    }
                }
            }
            firstTable = current;
            if (areStatesEqual(previous, current)) {
                done = true;
            } else {
                previous = current;
                current = new HashMap<>();
            }
        }
    }

    public void computeFollow() {
        HashMap<String, Set<String>> previous = new HashMap<>();
        for (var v : grammarReader.getNonTerminals()) {
            if (v.equals(grammarReader.getStartingSymbol())) {
                Set<String> auxSet = new HashSet<>();
                auxSet.add("$");
                previous.put(v, auxSet);
            } else {
                previous.put(v, new HashSet<>());
            }
        }

        HashMap<String, Set<String>> current = new HashMap<>();

        boolean done = false;
        while (!done) {
            for (var previousValue : previous.keySet()) {
                HashSet<String> auxSet = new HashSet<>();
                for(var prevValue : previous.get(previousValue)) {
                    auxSet.add(prevValue);
                }
                current.put(previousValue, auxSet);
            }
            for (var nonTerminal : grammarReader.getNonTerminals()) {
                var valueToMatch = new Value(nonTerminal, false);
                var productions = grammarReader.getProductions().stream().filter(v -> v.right.contains(valueToMatch)).toList();
                for (var production : productions) {
                    Value value = new Value("");
                    int index = 0;
                    while (!value.getValue().equals(valueToMatch.getValue()) && index < production.right.size()) {
                        value = production.right.get(index);
                        index++;
                    }
                    if (index < production.right.size()) {
                        value = production.right.get(index);
                    }
                    if (value.equals(valueToMatch)) {
                        //epsilon
                        current.get(nonTerminal).add("$");
                    } else if (value.isTerminal()) {
                        //terminal
                        current.get(nonTerminal).add(value.getValue());
                    } else {
                        current.get(nonTerminal).addAll(previous.get(production.left.getValue()));
                        current.get(nonTerminal).addAll(firstTable.get(value.getValue()));
                    }

                }
            }
            followTable = current;
            if (areStatesEqual(previous, current)) {
                done = true;
            } else {
                previous = current;
                current = new HashMap<>();
            }
        }
    }
}
