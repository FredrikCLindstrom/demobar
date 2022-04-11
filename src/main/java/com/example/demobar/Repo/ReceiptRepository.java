package com.example.demobar.Repo;

import com.example.demobar.Model.Receipt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends MongoRepository<Receipt, String> {


}