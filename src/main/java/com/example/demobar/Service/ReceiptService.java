package com.example.demobar.Service;

import com.example.demobar.Model.Receipt;
import com.example.demobar.Repo.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public void saveReceiptToDB(Receipt receipt){
        receiptRepository.save(receipt);
    }

    public void calculateTotalReceipt(Receipt receipt){

        ArrayList<String> listToPrint= new ArrayList<>();

            receipt.getItemsPurchased().forEach(item->{
                String nameOfItem=(item.getNameOfItem());
                int price=(item.getPrice());
                receipt.setTotalPrice(receipt.getTotalPrice()+item.getPrice());
                listToPrint.add("Item "+nameOfItem+", "+" price :"+price+" kr");

            });

        printReceipt(listToPrint, receipt.getTotalPrice(),receipt);
    }
    private void printReceipt(ArrayList<String> items, int totalPrice, Receipt receipt){
        String location="/Users/lindstrom/Desktop/rec/";
        Date today = new Date();

        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(location.concat(receipt.getIdOfReceipt().concat(".txt")), true));
            myWriter.newLine();
            myWriter.write(today.toString());
            myWriter.newLine();
            myWriter.newLine();
            myWriter.write("{Table 1} Receipt id: "+receipt.getIdOfReceipt());
            myWriter.newLine();

            items.forEach(item->{
                try {
                    myWriter.write(item);
                    myWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            myWriter.newLine();
            myWriter.newLine();
            myWriter.write("Total Cost: "+totalPrice);


            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
