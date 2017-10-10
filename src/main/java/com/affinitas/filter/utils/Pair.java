package com.affinitas.filter.utils;

import java.util.Objects;

public class Pair<T, TT> {
    private T first;
    private TT second;

    public Pair() {
    }

    public Pair(T first, TT second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public TT getSecond() {
        return second;
    }

    public void setSecond(TT second) {
        this.second = second;
    }

    public static <T,TT> Pair <T, TT> of(T first, TT second) {
        return new Pair<>(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
