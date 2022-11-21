package mihai;


/**
 * SymbolTable aggregates the concept of Constants Table with the Identifier table
 */
public class SymbolTable {
    private HashTable<Object, Object> constantsTable;
    private HashTable<String, Integer> identifiersTable;

    public SymbolTable() {
        constantsTable = new HashTable<>();
        identifiersTable = new HashTable<>();
    }

    /**
     * Adds a new constant to the Constants table in case it doesn't already exist
     * @param value the value to be added
     * @return
     */
    public int addConstant(Object value) {
       return constantsTable.add(new Pair<>(value, value));

    }

    /**
     * Adds a new variable to the Identifiers table and a new constant in the CT in case it's needed
     *
     * @param identifier
     * @param value
     */
    public int addVariable(String identifier, Object value) {
        int pos = addConstant(value);
        return identifiersTable.add(new Pair<>(identifier, pos));
    }

    /**
     * Gets a value associated to an identifier
     * @param identifier
     * @return
     */
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
