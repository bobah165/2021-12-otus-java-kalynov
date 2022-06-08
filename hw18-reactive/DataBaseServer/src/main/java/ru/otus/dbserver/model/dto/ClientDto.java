package ru.otus.dbserver.model.dto;


public class ClientDto {
    private String name;
    private String street;
    private String phonesNumbers;

    public ClientDto() {}

    public ClientDto(String name, String street, String phonesNumbers) {
        this.name = name;
        this.street = street;
        this.phonesNumbers = phonesNumbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhonesNumbers() {
        return phonesNumbers;
    }

    public void setPhonesNumbers(String phonesNumbers) {
        this.phonesNumbers = phonesNumbers;
    }
}
