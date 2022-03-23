package com.example.demobar.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SnacksItem extends Item{

    public SnacksItem(String nameOfItem, int price) {
        super(nameOfItem, price);
    }


}
