package de.longuyen.core.utils;

import java.util.Objects;

public class MutableLong implements Comparable<MutableLong> {
    private long count;

    public MutableLong(long count) {
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void increment() {
        ++this.count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutableLong that = (MutableLong) o;
        return count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }

    @Override
    public int compareTo(MutableLong that) {
        return Long.compare(this.count, that.count);
    }
}
