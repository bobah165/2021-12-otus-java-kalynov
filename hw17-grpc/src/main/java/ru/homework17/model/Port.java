package ru.homework17.model;

public enum Port {
    NUMBER_8090("8090");

    private String port;

    Port(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }
}
