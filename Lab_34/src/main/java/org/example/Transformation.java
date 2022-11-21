package mihai;

/**
 * Transformation class is used to represent a transformation from a state to another
 */
public class Transformation {
    String initialState;
    String value;
    String finalState;

    public Transformation(String initialState, String value, String finalState) {
        this.initialState = initialState;
        this.value = value;
        this.finalState = finalState;
    }
}
