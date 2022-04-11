package com.example.demobar.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.*;
import java.util.ArrayList;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Document(collection = "Receipts")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private String idOfReceipt;

    @OneToMany
    private ArrayList<Item> itemsPurchased;

    private int totalPrice;

    public Receipt() {
        this.idOfReceipt = UUID.randomUUID().toString();
    }
}
