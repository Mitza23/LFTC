package mihai;

import lombok.AllArgsConstructor;

import java.util.Objects;

/**
Represents a pair that is going to be stored in the HashTable
*/
@AllArgsConstructor
public class Pair<K, V> {
    public K key;
    public V value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair<?, ?> pair)) return false;
        return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);
    }

    @Override
    public String toString() {
        return key + " -> " + value;
    }
}
