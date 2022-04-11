package com.example.demobar.Repo;


import com.example.demobar.Model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

    @Query("{id:'?0'}")
    Item findItemById(String id);

}
