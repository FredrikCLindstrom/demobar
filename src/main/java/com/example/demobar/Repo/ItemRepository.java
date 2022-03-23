package com.example.demobar.Repo;


import com.example.demobar.GroceryItem;
import com.example.demobar.Model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

    @Query("{id:'?0'}")
    Item findItemById(String id);
//
//    @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
     // List<Item> findAll(String category);
      public long count();


}
