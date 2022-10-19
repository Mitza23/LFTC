package mihai;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HashTable<K, V> {
    private List<List<Pair<K, V>>> list;
    private int n;

    public HashTable() {
        n = 101;
        list = new ArrayList<>(n);
        for(int i = 0 ; i < n ; i++) {
            list.add(new ArrayList<>());
        }
    }


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

    public V findValue(K key) {
        int pos = hash(key);
        var bucket = list.get(pos);
        for(var p : bucket) {
            if(p.key.equals(key)) {
                return p.value;
            }
        }
        return null;
    }

    public int add(Pair<K, V> p) {
        boolean found = false;
        int pos = hash(p.key);
        var bucket = list.get(pos);
        for(var c : bucket) {
            if(c.equals(p)) {
                found = true;
                break;
            }
            else if(c.key == p.key) {
                bucket.remove(c);
                break;
            }
        }
        if(!found) {
            bucket.add(p);
        }
        return pos;
    }
}
