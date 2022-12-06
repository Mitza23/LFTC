package mihai;

import java.util.Objects;

public class Value{
    private String value;
    private boolean isTerminal;

    public Value(String v){
        this.value = v;
    }

    public Value(String v, boolean t){
        this.value = v;
        this.isTerminal = t;
    }

    public String getValue() {
       return this.value;
    }

    public boolean isTerminal(){
        return isTerminal;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Value value1)) return false;
        return isTerminal == value1.isTerminal && Objects.equals(value, value1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, isTerminal);
    }
}
