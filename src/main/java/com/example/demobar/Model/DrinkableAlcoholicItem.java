package com.example.demobar.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
@NoArgsConstructor
@Getter
@Setter
@Entity
public class DrinkableAlcoholicItem extends DrinkableItem{

    private double percentageAlcohol;

    public DrinkableAlcoholicItem(String nameOfItem, int price, String type, Boolean alcoholic, Long volume, double percentageAlcohol) {
        super(nameOfItem, price, type, alcoholic, volume);
        this.percentageAlcohol = percentageAlcohol;
    }


    @Override
    public String toString() {
        return "DrinkableAlcoholicItem{" +
                ", Name="+ getNameOfItem()+
                ", price="+getPrice()+
                ", type='" + getType() + '\'' +
                ", alcoholic=" + getAlcoholic() +
                ", volume=" + getVolume() +
                "percentageAlcohol=" + percentageAlcohol +
                ", inStock=" + getInStock() +
                '}';
    }
}
