package reghzy.utils.memory.multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A ArrayListMultiMap is a collection of keys, which key to an arraylist, but with functions to make managing that easier
 *
 * @param <K> The Key type
 * @param <V> The Value type (this doesn't have to be a collection, but it will be an ArrayList in the background)
 */
public class ArrayListMultiMap<K, V> implements MultiMap<K,V> {
    private final HashMap<K, ArrayList<V>> map;

    public ArrayListMultiMap() {
        this.map = new HashMap<K, ArrayList<V>>();
    }

    public boolean put(K key, V value) {
        return getOrCreateValues(key).add(value);
    }

    public boolean putAll(K key, Collection<V> values) {
        return getOrCreateValues(key).addAll(values);
    }

    public ArrayList<V> remove(K key) {
        return map.remove(key);
    }

    public boolean remove(K key, V value) {
        return getOrCreateValues(key).remove(value);
    }

    public V removeAt(K key, int index) {
        return getOrCreateValues(key).remove(index);
    }

    public Collection<K> getKeys() {
        return map.keySet();
    }

    public ArrayList<V> getValues(K key) {
        return getOrCreateValues(key);
    }

    @Override
    public Collection<V> getValuesNoCreate(K key) {
        return map.get(key);
    }

    public boolean contains(K key, V value) {
        return getOrCreateValues(key).contains(value);
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public boolean containsValue(V value) {
        for(K key : getKeys()) {
            if (getValues(key).contains(value))
                return true;
        }
        return false;
    }

    public int keysSize() {
        return map.size();
    }

    public int valuesSize(K key) {
        ArrayList<V> values = getValues(key);
        if (values == null)
            return 0;

        return values.size();
    }

    @Override
    public int clear() {
        int cleared = this.map.size();
        this.map.clear();
        return cleared;
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        HashMap<K, Collection<V>> map = new HashMap<K, Collection<V>>(keysSize());
        for(K key : getKeys()) {
            map.put(key, getValues(key));
        }
        return map;
    }

    public ArrayList<Collection<V>> getAllValues() {
        ArrayList<Collection<V>> valuesTotal = new ArrayList<Collection<V>>(keysSize() * 4);
        for (K key : getKeys()) {
            valuesTotal.add(getValues(key));
        }
        return valuesTotal;
    }

    public Collection<MultiMapEntry<K, V>> getEntrySet() {
        Collection<MultiMapEntry<K, V>> entrySets = new ArrayList<MultiMapEntry<K, V>>(keysSize());
        for (K key : this.getKeys()) {
            entrySets.add(new MultiMapEntry<K, V>(key, getOrCreateValues(key)));
        }
        return entrySets;
    }

    private ArrayList<V> getOrCreateValues(K key) {
        ArrayList<V> list = map.get(key);
        if (list == null) {
            list = new ArrayList<V>();
            map.put(key, list);
        }

        return list;
    }
}
