package mihai;

import java.util.Scanner;

public class Main {

    public static FiniteAutomaton automaton = new FiniteAutomaton();

    /**
     * Prints menu of options
     */
    public static void printMenu() {
        System.out.println("1. Read automaton");
        System.out.println("2. Display states");
        System.out.println("3. Display alphabet");
        System.out.println("4. Display transitions");
        System.out.println("5. Check sequence");
        System.out.println("0. exit");
    }

    /**
     * Triggers the generation of the automaton
     */
    public static void readAutomaton() {
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        automaton.readAutomaton(fileName);
    }

    /**
     * Shows initial state + final states + all intermediary states
     */
    public static void displayStates() {
        var states = automaton.getInitialStates();
        System.out.println("Initial states");
        for (var state : states) {
            System.out.println(state);
        }
        System.out.println("\n\n");
        states = automaton.getFinalStates();
        System.out.println("Final states");
        for (var state : states) {
            System.out.println(state);
        }
        System.out.println("\n\n");
        states = automaton.getStates();
        System.out.println("All states");
        for (var state : states) {
            System.out.println(state);
        }
        System.out.println("\n\n");
    }

    /**
     * Displays the alphabet
     */
    public static void displayAlphabet() {
        var alphabet = automaton.getAlphabet();
        System.out.println("Alphabet:");
        StringBuilder builder = new StringBuilder();
        for (var letter : alphabet) {
            builder.append(letter);
        }
        System.out.println(builder.toString());
        System.out.println("\n\n");
    }

    /**
     * Show all transitions
     */
    public static void displayTransitions() {
        var transitions = automaton.getTransformations();
        System.out.println("Transitions:");
        for (var transition : transitions) {
            System.out.println(transition.initialState + ", " + transition.value + " -> " + transition.finalState);
        }
    }

    /**
     * Checks to see if a sequence belongs to the automaton
     */
    public static void checkSequence() {
        System.out.print("sequence: ");
        Scanner scanner = new Scanner(System.in);
        var word = scanner.next();
        System.out.println(automaton.checkSequence(word, automaton.getInitialStates()));
    }

    /**
     * Main event loop
     *
     * @param args
     */
    public static void main(String[] args) {
        boolean done = false;
        while (!done) {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    readAutomaton();
                    break;
                case 2:
                    displayStates();
                    break;
                case 3:
                    displayAlphabet();
                    break;
                case 4:
                    displayTransitions();
                    break;
                case 5:
                    checkSequence();
                    break;
                case 0:
                    done = true;
                    break;
                default:
                    System.out.println("wrong input");
            }
        }
    }
}