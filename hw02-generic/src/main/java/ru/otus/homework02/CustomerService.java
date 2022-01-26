package ru.otus.homework02;


import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> customerMap = new TreeMap<>(customerComparator());

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return getEntryClone(customerMap.descendingMap().lastEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return getEntryClone(customerMap.tailMap(customer, true).pollFirstEntry());
    }

    public void add(Customer customer, String data) {
        customerMap.put(customer, data);
    }


    private static Comparator<Customer> customerComparator() {
        return Comparator.comparingLong(Customer::getScores);
    }

    private static Map.Entry<Customer, String> getEntryClone(Map.Entry<Customer, String> entry) {
        NavigableMap<Customer, String> clone = new TreeMap<>(customerComparator());
        return Optional.ofNullable(entry)
                       .map(entry1 -> {
                           clone.put(getCustomerClone(entry1.getKey()), entry1.getValue());
                           return clone.firstEntry();
                       })
                       .orElse(null);
    }

    private static Customer getCustomerClone(Customer origin) {
        return new Customer(origin.getId(), origin.getName(), origin.getScores());
    }
}
