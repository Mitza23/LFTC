package mihai;

import java.util.*;
import java.util.stream.Collectors;

public class Parser {
    GrammarReader grammarReader;

    Map<Value, Map<Value, String>> parsingTable = new HashMap<>();

    HashMap<Value, Set<Value>> firstTable = new HashMap<>();
    HashMap<Value, Set<Value>> followTable = new HashMap<>();

    Value epsilon = new Value("$", true);

//    String epsilon = "$";

    public Parser(GrammarReader grammarReader) {
        this.grammarReader = grammarReader;
    }

    public Set<Value> concatenateLengthOne(List<Set<Value>> list) {
        var result = new HashSet<Value>();
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

    public boolean areStatesEqual(HashMap<Value, Set<Value>> state1, HashMap<Value, Set<Value>> state2) {
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

    public Set<Value> computeFirstForValue(Value value) {
        var auxSet = new HashSet<Value>();
        if (value.isTerminal()) {
            auxSet.add(value);
        } else {
            auxSet.addAll(firstTable.get(value));
        }
        return auxSet;
    }

    public void computeFirst() {
        HashMap<Value, Set<Value>> previous = new HashMap<>();
        for (var v : grammarReader.getNonTerminals()) {
            previous.put(new Value(v, false), new HashSet<>());
        }

        HashMap<Value, Set<Value>> current = new HashMap<>();

        boolean done = false;
        while (!done) {
            for (var nonTerminal : grammarReader.getNonTerminals()) {
                current.put(new Value(nonTerminal, false), new HashSet<>());
            }
            for (var nonTerminal : grammarReader.getNonTerminals()) {
                var productions = grammarReader.getProductionsForNonTerminal(nonTerminal);
                for (var production : productions) {
                    var value = production.right.get(0);
                    if (value.isTerminal()) {
                        current.get(new Value(nonTerminal, false)).add(production.right.get(0));
                    } else {
                        List<Set<Value>> setList = new ArrayList<>();
                        for (var rightValue : production.right) {
                            if (rightValue.isTerminal()) {
                                Set<Value> toAdd = new HashSet<>();
                                toAdd.add(rightValue);
                                setList.add(toAdd);
                            } else {
                                var previousList = previous.get(rightValue);
                                if (previousList != null)
                                    setList.add(previousList);
                            }
                        }
                        current.get(new Value(nonTerminal, false)).addAll(concatenateLengthOne(setList));
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

    public Set<Value> computeFollowForValue(Value v) {
        var auxSet = new HashSet<Value>();
        if (grammarReader.getStartingSymbol().equals(v.getValue())) {
            auxSet.add(epsilon);
        }
        var productions = grammarReader.getProductions().stream().filter(v1 -> v1.right.contains(v)).toList();
        for (var production : productions) {
            Value value = new Value("");
            int index = 0;
            while (!value.getValue().equals(v.getValue()) && index < production.right.size()) {
                value = production.right.get(index);
                index++;
            }
            if (index < production.right.size()) {
                value = production.right.get(index);
            }
            if (value.equals(v)) {
                //epsilon
                if (!production.left.equals(value)) {
                    auxSet.addAll(computeFollowForValue(production.left));
                }
            } else if (value.isTerminal()) {
                //terminal
                auxSet.addAll(computeFirstForValue(value));
            } else {
                if (!computeFirstForValue(value).contains(epsilon)) {
                    auxSet.addAll(computeFirstForValue(value));
                } else {
                    auxSet.addAll(computeFirstForValue(value).stream().filter(v1 -> !v1.equals(epsilon)).collect(Collectors.toSet()));
                    auxSet.addAll(computeFollowForValue(production.left));
                }
            }
        }
        return auxSet;
    }

    public void computeFollow() {
        for (var nonTerminal : grammarReader.getNonTerminals()) {
            followTable.put(new Value(nonTerminal, false), computeFollowForValue(new Value(nonTerminal, false)));
        }
    }
    void computeParsingTable() {
        for (var nonTerminal : grammarReader.getNonTerminals()) {
            parsingTable.put(new Value(nonTerminal, false), new HashMap<>());
        }
        for (var production : grammarReader.getProductions()) {
            boolean containsEpsilon = false;
            for (var first : computeFirstForValue(production.right.get(0))) {
                if (epsilon.equals(first)) {
                    containsEpsilon = true;
                } else {
                    var a = parsingTable.get(production.left).get(first);
                    if (a == null || a.isEmpty()) {
                        parsingTable.get(production.left).put(first, production.name);
                    } else {
                        throw new RuntimeException("Conflict " + production.left + " " + first);
                    }
                }
            }
            if (containsEpsilon) {
                for (var follow : computeFollowForValue(production.left)) {
                    var a = parsingTable.get(production.left).get(follow);
                    if (a == null || a.isEmpty()) {
                        parsingTable.get(production.left).put(follow, production.name);
                    } else {
                        throw new RuntimeException("Conflict " + production.left + " " + follow);
                    }
                }
            }
        }
    }
}
