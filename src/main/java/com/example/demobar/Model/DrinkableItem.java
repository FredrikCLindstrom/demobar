package com.example.demobar.Model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DrinkableItem extends Item{

    private String type;
    private Boolean alcoholic;
    private Long volume;

    public DrinkableItem(String nameOfItem, int price, String type,Boolean alcoholic, Long volume) {
        super(nameOfItem, price);
        this.type = type;
        this.alcoholic = alcoholic;
        this.volume = volume;
    }


    @Override
    public String toString() {
        return "DrinkableItem{" +
                ", Name="+ getNameOfItem()+
                ", price="+getPrice()+
                ", type='" + type + '\'' +
                ", alcoholic=" + alcoholic +
                ", volume=" + volume +
                ", inStock=" + getInStock() +
                '}';
    }
}
