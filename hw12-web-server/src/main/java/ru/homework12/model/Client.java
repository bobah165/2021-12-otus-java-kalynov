package ru.homework12.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "client")
public class Client implements Cloneable, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Phone> phones;

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones == null ? new ArrayList<>() : getUpdatedPhoneList(phones);
    }

    public Client(Long id, String name, String password, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.address = address;
        this.phones = phones == null ? new ArrayList<>() : getUpdatedPhoneList(phones);
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.password, new Address(this.address), phoneCopy(this.phones));
    }

    private List<Phone> phoneCopy(List<Phone> phones) {
        return Optional.ofNullable(phones)
                       .map(phoneList -> phoneList.stream()
                                                  .map(Phone::new)
                                                  .collect(Collectors.toList()))
                       .orElse(new ArrayList<>());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\" :" + id +
                ", \"name\" : " +  "\"" + name + "\"" +
                '}';
    }

    private List<Phone> getUpdatedPhoneList(List<Phone> phones) {
        if (phones == null) return new ArrayList<>();
        phones.forEach(phone -> phone.setClient(this));
        return phones;
    }
}
