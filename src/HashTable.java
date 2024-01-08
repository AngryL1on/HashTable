import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashTable<K, V> implements Iterable<KeyValue<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.80d;

    private LinkedList<KeyValue<K, V>>[] slots;
    private int count;
    private int capacity;

    public HashTable() {
        slots = new LinkedList[DEFAULT_CAPACITY];
        this.capacity = slots.length;
    }

    public HashTable(int capacity) {
        slots = new LinkedList[capacity];
        this.capacity = slots.length;
    }

    public void add(K key, V value) {
        this.growIfNeeded();
        int slotNumber = findSlotNumber(key);

        if (this.slots[slotNumber] == null) {
            this.slots[slotNumber] = new LinkedList<>();
        }

        // Checking if such a key already exists
        for (KeyValue<K, V> entry : this.slots[slotNumber]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value); // Update the value if the key is found
                return;
            }
        }

        this.slots[slotNumber].add(new KeyValue<>(key, value)); // Add a new key-value pair
        this.count++;
    }

    private int findSlotNumber(K key) {
        return Math.abs(key.hashCode()) % this.capacity;
    }

    private void growIfNeeded() {
        if ((double) (this.count + 1) / this.capacity > LOAD_FACTOR) {
            this.grow();
        }
    }

    private void grow() {
        HashTable<K, V> newTable = new HashTable<>(this.capacity * 2);

        for (LinkedList<KeyValue<K, V>> slot : this.slots) {
            if (slot != null) {
                for (KeyValue<K, V> entry : slot) {
                    newTable.add(entry.getKey(), entry.getValue());
                }
            }
        }

        this.slots = newTable.slots;
        this.capacity = newTable.capacity;
    }

    public int size() {
        return this.count;
    }

    public int capacity() {
        return this.capacity;
    }

    public boolean addOrReplace(K key, V value) {
        int slotNumber = findSlotNumber(key);
        growIfNeeded();

        if (this.slots[slotNumber] == null) {
            this.slots[slotNumber] = new LinkedList<>();
        }

        for (KeyValue<K, V> entry : this.slots[slotNumber]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return false; // Replacing an existing value
            }
        }

        this.slots[slotNumber].add(new KeyValue<>(key, value));
        this.count++;
        return true; // Adding a new value
    }

    public V get(K key) {
        int slotNumber = findSlotNumber(key);

        if (this.slots[slotNumber] != null) {
            for (KeyValue<K, V> entry : this.slots[slotNumber]) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }

        throw new NoSuchElementException("Key not found: " + key);
    }

    public KeyValue<K, V> find(K key) {
        int slotNumber = findSlotNumber(key);

        if (this.slots[slotNumber] != null) {
            for (KeyValue<K, V> entry : this.slots[slotNumber]) {
                if (entry.getKey().equals(key)) {
                    return entry;
                }
            }
        }

        throw new NoSuchElementException("Key not found: " + key);
    }

    public boolean containsKey(K key) {
        int slotNumber = findSlotNumber(key);

        if (this.slots[slotNumber] != null) {
            for (KeyValue<K, V> entry : this.slots[slotNumber]) {
                if (entry.getKey().equals(key)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean remove(K key) {
        int slotNumber = findSlotNumber(key);

        if (this.slots[slotNumber] != null) {
            Iterator<KeyValue<K, V>> iterator = this.slots[slotNumber].iterator();
            while (iterator.hasNext()) {
                KeyValue<K, V> entry = iterator.next();
                if (entry.getKey().equals(key)) {
                    iterator.remove();
                    this.count--;
                    return true;
                }
            }
        }

        return false;
    }

    public void clear() {
        this.slots = new LinkedList[this.capacity];
        this.count = 0;
    }

    public Iterable<K> keys() {
        return () -> new Iterator<K>(){
            private int index = 0;
            private Iterator<KeyValue<K, V>> slotIterator = slots[index] == null ? null : slots[index].iterator();

            @Override
            public boolean hasNext() {
                if (slotIterator != null && slotIterator.hasNext()) {
                    return true;
                }

                while (++index < capacity) {
                    if (slots[index] != null && !slots[index].isEmpty()) {
                        slotIterator = slots[index].iterator();
                        return slotIterator.hasNext();
                    }
                }

                return false;
            }

            @Override
            public K next() {
                if (slotIterator == null) {
                    throw new NoSuchElementException();
                }
                return slotIterator.next().getKey();
            }
        };
    }

    public Iterable<V> values() {
        return () -> new Iterator<V>(){
            private int index = 0;
            private Iterator<KeyValue<K, V>> slotIterator = slots[index] == null ? null : slots[index].iterator();

            @Override
            public boolean hasNext() {
                if (slotIterator != null && slotIterator.hasNext()) {
                    return true;
                }

                while (++index < capacity) {
                    if (slots[index] != null && !slots[index].isEmpty()) {
                        slotIterator = slots[index].iterator();
                        return slotIterator.hasNext();
                    }
                }

                return false;
            }

            @Override
            public V next() {
                if (slotIterator == null) {
                    throw new NoSuchElementException();
                }
                return slotIterator.next().getValue();
            }
        };
    }

    @Override
    public Iterator<KeyValue<K, V>> iterator() {
        return new Iterator<KeyValue<K, V>>() {
            private int index = 0;
            private Iterator<KeyValue<K, V>> slotIterator = slots[index] == null ? null : slots[index].iterator();

            @Override
            public boolean hasNext() {
                if (slotIterator != null && slotIterator.hasNext()) {
                    return true;
                }

                while (++index < capacity) {
                    if (slots[index] != null && !slots[index].isEmpty()) {
                        slotIterator = slots[index].iterator();
                        return slotIterator.hasNext();
                    }
                }

                return false;
            }

            @Override
            public KeyValue<K, V> next() {
                if (slotIterator == null) {
                    throw new NoSuchElementException();
                }
                return slotIterator.next();
            }
        };
    }

    @Override
    public String toString() {
        return "HashTable{" +
                "slots=" + Arrays.toString(slots) +
                ", capacity=" + capacity +
                ", size=" + count +
                '}';
    }
}
