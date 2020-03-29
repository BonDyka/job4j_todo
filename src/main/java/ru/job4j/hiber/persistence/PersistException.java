package ru.job4j.hiber.persistence;

public class PersistException extends Exception {

    public PersistException(String msg) {
        super(msg);
    }

    public PersistException(String msg, Exception e) {
        super(msg);
    }
}
