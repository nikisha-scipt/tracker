package ru.job4j.tracker.jdbc;


@FunctionalInterface
public interface Observe<T> {

    void receive(T model);

}
