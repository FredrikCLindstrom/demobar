package com.example.demobar.Repo;

import com.example.demobar.Model.Item;
import com.example.demobar.Model.Receipt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ReceiptRepository extends MongoRepository<Receipt, String> {

//    @Query("{nameOfItem:'?0'}")
//    Item findItemByName(String nameOfItem);
    //
//    @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
    // List<Item> findAll(String category);
    public long count();


}