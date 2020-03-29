package ru.job4j.hiber.models;

import java.sql.Timestamp;

public class Item {
    private long id;
    private String description;
    private Timestamp created;
    private boolean done;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        return  id == item.getId() && description.equals(item.description)
                && created.equals(item.created) && done == item.done;
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + created.hashCode();
        result = 31 * result + (done ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "Item{description='%s', created='%s', done='%s'}",
                description, created, done
        );
    }
}
