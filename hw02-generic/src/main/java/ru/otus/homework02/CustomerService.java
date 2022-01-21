package ru.otus.homework02;


import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CustomerService {

    private final Map<Customer, String> customerMap = new TreeMap<>();

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return customerMap.entrySet()
                          .stream()
                          .findFirst()
                          .map(entry -> Map.entry(cloneCustomer(entry.getKey()), entry.getValue()))
                          .get();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return customerMap.entrySet()
                          .stream()
                          .filter(entry -> entry.getKey().getScores() > customer.getScores())
                          .findFirst()
                          .map(entry -> Map.entry(cloneCustomer(entry.getKey()), entry.getValue()))
                          .orElse(null);
    }

    public void add(Customer customer, String data) {
        customerMap.put(customer, data);
    }

    private static Customer cloneCustomer(Customer original) {
        return new Customer(original.getId(), original.getName(), original.getScores());
    }
}
