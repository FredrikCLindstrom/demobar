package com.example.demobar.Service;

import com.example.demobar.Model.Item;
import com.example.demobar.Model.Receipt;
import com.example.demobar.Repo.ReceiptRepository;
import com.example.demobar.SortTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


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

        receipt.setDate(getTodaysDate());
        receipt.setTime(getTheTime());

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
    public List<Receipt> getTodaysReceipts(){
        List<Receipt> allReceipts= new ArrayList<>();
        List<Receipt> onlyTodaysReceipts= new ArrayList<>();
        allReceipts=receiptRepository.findAll();
        onlyTodaysReceipts=allReceipts.stream().filter(
                receipt -> receipt.getDate().equals(getTodaysDate()))
                .collect(Collectors.toList());
        Collections.sort(onlyTodaysReceipts, new SortTime());

        return onlyTodaysReceipts;
    }

    public List<Receipt> getYesterdaysReceipts(){
        List<Receipt> allReceipts= new ArrayList<>();
        List<Receipt> onlyYesterdaysReceipts= new ArrayList<>();
        allReceipts=receiptRepository.findAll();
        onlyYesterdaysReceipts=allReceipts.stream().filter(
                        receipt -> receipt.getDate().equals(getYesterdaysDate()))
                .collect(Collectors.toList());
        Collections.sort(onlyYesterdaysReceipts, new SortTime());
        System.out.println(onlyYesterdaysReceipts);
        return onlyYesterdaysReceipts;
    }

    private String getTodaysDate(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");//HH:mm:ss
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dateFormatter.format(now));

        String todayDate = dateFormatter.format(now);
        return todayDate;
    }

    private String getYesterdaysDate(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy/MM/dd");
        String yesterdaysDate=now.minus(1, ChronoUnit.DAYS).withHour(0).withMinute(0)
                .withSecond(0).format(formatter);
        System.out.println(yesterdaysDate);
        return yesterdaysDate;
    }

    private String getTheTime(){

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");//HH:mm:ss
        LocalDateTime now = LocalDateTime.now();
        System.out.println(timeFormatter.format(now));
        String timeNow = timeFormatter.format(now);
        return timeNow;
    }
}
