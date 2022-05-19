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


    private Boolean alcoholic;
    private Long volume;

    public DrinkableItem(String nameOfItem, int price,Boolean alcoholic, Long volume) {
        super(nameOfItem, price);
        this.alcoholic = alcoholic;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "DrinkableItem{" +
                ", Name="+ getNameOfItem()+
                ", price="+getPrice()+
                ", alcoholic=" + alcoholic +
                ", volume=" + volume +
                ", inStock=" + getInStock() +
                '}';
    }
}
