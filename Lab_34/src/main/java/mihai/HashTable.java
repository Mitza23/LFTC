package mihai;


import java.util.ArrayList;
import java.util.List;

/**
 * HashTable is the basic ADT that will work as a hashtable is expected to work
 */
public class HashTable<K, V> {
    private List<List<Pair<K, V>>> list; // thow the table is represented

    public List<List<Pair<K, V>>> getList() {
        return list;
    }

    private int n; // the number of different buckets in the table

    public HashTable() {
        n = 101;
        list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(new ArrayList<>());
        }
    }


    /**
    The hash function calculates the position of the list in which the new pair to be inserted
    The calculation is done:
        Strings: sum of ASCII code of the caracters, modulo n
        Char: ASCII code of the caracter modulo n
        Int: the value modulo n
    */
    public int hash(K key) {
        if(key instanceof String) {
            int s = 0;
            for(char c : ((String) key).toCharArray()) {
               s += (int)c;
            }
            return s % n;
        }
        else if(key instanceof Character) {
            int k = (int)key;
            return k % n;
        }
        else if(key instanceof Integer) {
            int k = (int)key;
            return k % n;
        }
        else {
            return -1;
        }
    }


    /** Finds the value associated to a key, null if can't be found
     *
     * @param key
     * @return
     */
    public V findValue(K key) {
        int pos = hash(key);
        if(pos < 0) {
            return null;
        }
        var bucket = list.get(pos);
        for(var p : bucket) {
            if(p.key.equals(key)) {
                return p.value;
            }
        }
        return null;
    }


    /**Adds a new pair to the table and returns the position of the list in which it was added.
     In case the key already existed, it just updates the value associated to it
     In case the key and the values ar the same as the new one, nothing happens
    */
    public int add(Pair<K, V> p) {
        boolean found = false;
        int pos = hash(p.key);
        var bucket = list.get(pos);
        for(var pair : bucket) {
            if(pair.equals(p)) {
                found = true;
                break;
            }
            else if(pair.key == p.key) {
                found = true;
                pair.value = p.value;
                break;
            }
        }
        if(!found) {
            bucket.add(p);
        }
        return pos;
    }
}
