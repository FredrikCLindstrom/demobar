package com.example.demobar;

import com.example.demobar.Model.Receipt;

import java.util.Comparator;

public class SortTime implements Comparator<Receipt> {
    @Override
    public int compare(Receipt o1, Receipt o2) {
        return (int)(o2.getTime().compareTo(o1.getTime()));
    }
}
