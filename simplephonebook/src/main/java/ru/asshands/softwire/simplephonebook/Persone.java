package ru.asshands.softwire.simplephonebook;

public final class Persone {
    private final String name;
    private final String surName;
    private final String telNum;

    public Persone(String name, String surName, String telNum) {
        this.name = name;
        this.surName = surName;
        this.telNum = telNum;
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public String getTelNum() {
        return telNum;
    }
}