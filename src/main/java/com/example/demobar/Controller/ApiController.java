package com.example.demobar.Controller;

import com.example.demobar.Model.Item;
import com.example.demobar.Model.Menu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

    @GetMapping("/test")
    public String getTestData() {
        String testData="hello!!";


        return testData;
    }

    @GetMapping("/one")
    public Item getTestObject() {
        Item one=new Item("Beer", 89);

        return one;
    }

    @GetMapping("/menu")
    public List getTestMenu() {
        Menu menu=new Menu();
        return menu.getMenuItemsList();
    }
}
