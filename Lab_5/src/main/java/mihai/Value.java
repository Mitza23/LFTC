package mihai;

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
}
