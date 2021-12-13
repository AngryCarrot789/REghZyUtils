package dragonjetz.utils.memory;

import java.util.Iterator;

/**
 * A concurrent array (with a fixed size) supporting an add method, which will wrap back around to the start if the internal index reaches the end
 *
 * @param <E>
 */
public class ConcurrentArray<E> {
    public final int size;
    private final Object[] elements;
    private volatile int index;

    public ConcurrentArray(int size) {
        this.elements = new Object[size];
        this.size = size;
        this.index = 0;
    }

    public synchronized void add(E value) {
        if (this.index >= this.size) {
            throw new UnsupportedOperationException("The array is full!");
        }

        this.elements[this.index++] = value;
    }

    public synchronized void addAll(boolean capIfTooLong, E... values) {
        if (values.length > this.size) {
            throw new IllegalStateException("Cannot add all elements because the source array is bigger than the internal array");
        }
        else if (values.length == this.size) {
            if (this.index != 0) {
                throw new UnsupportedOperationException("Cannot copy all elements because the index is not 0 but the source array is the size of the internal array");
            }

            System.arraycopy(this.elements, 0, values, 0, values.length);
        }
        else {
            int lastIndex = this.index + values.length;
            if (lastIndex < this.size) {
                Object[] elements = this.elements;
                for (int i = this.index, j = 0; i < lastIndex; i++) {
                    elements[i] = values[j++];
                }
            }
            else {
                if (capIfTooLong) {
                    Object[] elements = this.elements;
                    for (int i = this.index, j = 0, len = this.size; i < len; i++) {
                        elements[i] = values[j++];
                    }
                }
                else {
                    throw new UnsupportedOperationException("Cannot add all elements because it would exceed the size of this array");
                }
            }
        }
    }

    public synchronized E get(int index) {
        if (index < 0 || index > this.elements.length) {
            throw new IndexOutOfBoundsException("Cannot get an element because the index out of bounds! " + index + " < 0 || " + index + " > " + this.elements.length);
        }

        return (E) elements[index];
    }

    public synchronized E unsafeGet(int index) {
        return (E) elements[index];
    }

    public synchronized void set(int index, E value) {
        if (index < 0 || index > this.elements.length) {
            throw new IndexOutOfBoundsException("Cannot set an element because the index out of bounds! " + index + " < 0 || " + index + " > " + this.elements.length);
        }

        elements[index] = value;
    }

    public synchronized void unsafeSet(int index, E value) {
        elements[index] = value;
    }

    public synchronized void swap(int oldIndex, int newIndex) {
        if (oldIndex < 0 || newIndex < 0 || oldIndex > this.elements.length || newIndex > this.elements.length) {
            throw new IndexOutOfBoundsException("Cannot swap element because one of the indexes are out of bounds!");
        }

        Object oldObj = elements[oldIndex];
        Object newObj = elements[newIndex];
        elements[oldIndex] = newObj;
        elements[newIndex] = oldObj;
    }

    public int length() {
        return elements.length;
    }

    public void reset() {
        this.index = 0;
    }

    public boolean isFull() {
        return this.index >= this.size;
    }

    public synchronized void clear() {
        Object[] objects = this.elements;
        for (int i = 0, len = objects.length; i < len; i++) {
            objects[i] = null;
        }

        this.index = 0;
    }

    public Iterator<E> iterator() {
        return new ArrayIterator<E>();
    }

    private class ArrayIterator<I> implements Iterator<I> {
        private final int indexedSize;
        private int index;

        public ArrayIterator() {
            this.indexedSize = elements.length - 1;
        }

        public boolean hasNext() {
            return index < indexedSize;
        }

        public I next() {
            return (I) elements[index++];
        }

        public void remove() {
            elements[index] = null;
        }
    }
}
