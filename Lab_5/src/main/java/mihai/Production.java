package mihai;

import java.util.List;

public class Production {
    Value left;
    List<Value> right;
    String name;

    public Production(Value left, List<Value> right, String name) {
        this.left = left;
        this.right = right;
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(var e : right){
            sb.append(e);
        }
        return left + " -> " + sb.toString() + " (" + name + ")";
    }
}
