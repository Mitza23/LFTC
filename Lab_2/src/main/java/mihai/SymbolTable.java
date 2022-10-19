package mihai;


public class SymbolTable {
    private HashTable<Object, Object> constantsTable;
    private HashTable<String, Integer> identifiersTable;

    public SymbolTable() {
        constantsTable = new HashTable<>();
        identifiersTable = new HashTable<>();
    }

    public int addConstant(Object value) {
       return constantsTable.add(new Pair<>(value, value));

    }

    public void addVariable(String identifier, Object value) {
        int pos = addConstant(value);
        identifiersTable.add(new Pair<>(identifier, pos));
    }

    public Object getValue(String identifier) {
        int pos = identifiersTable.findValue(identifier);
        return constantsTable.findValue(pos);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CONSTANTS:\n");
        for(var bucket : constantsTable.getList()){
            for(var pair : bucket) {
                stringBuilder.append("\t")
                        .append(pair.toString())
                        .append("\n");
            }
        }
        stringBuilder.append("IDENTIFIERS:\n");
        for(var bucket : identifiersTable.getList()){
            for(var pair : bucket) {
                stringBuilder.append("\t")
                        .append(pair.toString())
                        .append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
