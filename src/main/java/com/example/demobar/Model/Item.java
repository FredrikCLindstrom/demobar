package com.example.demobar.Model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Document("Items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private String id;

    private String nameOfItem;

    private int price;

    private Boolean inStock;

    public Item(String name, int price) {
        this.id = UUID.randomUUID().toString();
        nameOfItem = name;
        this.price = price;
        this.inStock=true;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", nameOfItem='" + nameOfItem + '\'' +
                ", price=" + price +
                ", inStock=" + inStock +
                '}';
    }
}
