package ru.otus.homework01.service.impl;

import com.google.common.collect.Lists;
import ru.otus.homework01.service.PrintService;

import java.util.List;

public class GuavaPrintServiceImpl implements PrintService {
    @Override
    public List<String> getList(){
        return Lists.newArrayList("John", "Adam", "Jane");
    }
}
