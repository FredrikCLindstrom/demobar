package com.example.demobar.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class FoodItem extends Item{
    public FoodItem( String nameOfItem, int price) {
        super(nameOfItem, price);
    }


}
