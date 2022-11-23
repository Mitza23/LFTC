package mihai;

public class Production {
    String left;
    String right;
    String name;

    public Production(String left, String right, String name) {
        this.left = left;
        this.right = right;
        this.name = name;
    }

    @Override
    public String toString() {
        return left + " -> " + right + " (" + name + ")";
    }
}
