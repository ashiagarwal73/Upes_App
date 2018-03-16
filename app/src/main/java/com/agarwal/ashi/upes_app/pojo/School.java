package com.agarwal.ashi.upes_app.pojo;

/**
 * Created by 500060150 on 16-03-2018.
 */

public class School {
    String name;
    School() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        School s=(School)o;
        return s.getName().equalsIgnoreCase(this.getName());
    }
}
