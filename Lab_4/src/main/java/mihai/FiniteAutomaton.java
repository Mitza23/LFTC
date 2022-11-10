package mihai;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FiniteAutomaton {
    private List<Transformation> transformations;
    private Set<String> states;
    private Set<String> alphabet;
    private Set<String> initialStates;
    private Set<String> finalStates;

    public List<Transformation> getTransformations() {
        return transformations;
    }

    public Set<String> getStates() {
        return states;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public Set<String> getInitialStates() {
        return initialStates;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public FiniteAutomaton() {
        transformations = new ArrayList<>();
        states = new HashSet<>();
        alphabet = new HashSet<>();
        initialStates = new HashSet<>();
        finalStates = new HashSet<>();
    }

    public void readAutomaton(String fileName) {
        transformations = new ArrayList<>();
        states = new HashSet<>();
        alphabet = new HashSet<>();
        initialStates = new HashSet<>();
        finalStates = new HashSet<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            /// Initial states
            String line = scanner.nextLine();
            var tokens = line.split(" ");
            initialStates.addAll(List.of(tokens));
            /// Final states
            line = scanner.nextLine();
            tokens = line.split(" ");
            finalStates.addAll(List.of(tokens));
            /// Transformation
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                tokens = line.split(" ");
                transformations.add(new Transformation(tokens[0], tokens[1], tokens[2]));
                states.add(tokens[0]);
                states.add(tokens[2]);
                alphabet.add(tokens[1]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean checkSequence(String word, Set<String> initialStates) {
        boolean result = false;
        for (var state : initialStates) {
            if (finalStates.contains(state)) {
                return true;
            }
            Set<String> followingStates = new HashSet<>();
            String value = word.substring(0, 1);
            transformations.stream()
                    .filter(transformation -> (transformation.value.equals(value) && transformation.initialState.equals(state)))
                    .forEach(transformation -> {
                        followingStates.add(transformation.finalState);
                    });
            result = result | checkSequence(word.substring(1), followingStates);
        }
        return result;
    }
}
