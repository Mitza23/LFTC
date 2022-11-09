package mihai;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class FiniteAutomaton {
    List<Transformation<String, String>> transformations;
    Set<String> states;
    Set<String> alphabet;

    Set<String> initialStates;

    Set<String> finalStates;

    public void readAutomaton(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))){
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
                System.out.println(line);
                tokens = line.split(" ");
                transformations.add(new Transformation<>(tokens[0], tokens[1], tokens[2]));
                states.add(tokens[0]);
                states.add(tokens[2]);
                alphabet.add(tokens[1]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean check(String word) {

    }
}
