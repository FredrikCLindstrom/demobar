package com.example.demobar.Model;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Menu {

    Item one = new Item("beer", 78);
    Item two = new Item("cider", 68);
    Item three = new Item("wine", 95);

    private List<Item> MenuItemsList= new ArrayList<>(List.of(one, two, three));
}
