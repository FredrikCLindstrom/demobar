package com.example.demobar.Service;

import com.example.demobar.GroceryItem;
import com.example.demobar.Model.*;
import com.example.demobar.Repo.ItemRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void createItems() {
        System.out.println("Data creation started...");
        //itemRepository.save(new Item( "beer", 59));
        itemRepository.save(new DrinkableItem("sprite", 25,"soda",false, 33L ));
        itemRepository.save(new DrinkableAlcoholicItem("spendrups", 39, "beer",true, 50L, 5));
        itemRepository.save(new FoodItem("Spaggethi", 99));
        itemRepository.save(new SnacksItem("peanuts", 30));

        itemRepository.save(new DrinkableItem("fanta", 25,"soda",false, 33L ));
        itemRepository.save(new DrinkableAlcoholicItem("pripps", 49, "beer",true, 50L, 5));
        itemRepository.save(new FoodItem("pizza", 99));
        itemRepository.save(new SnacksItem("chips", 30));

        System.out.println("Data creation complete...");
    }

    public List<Item> showAllItems() {

        List<Item> allItemsList=new ArrayList<>();
        itemRepository.findAll().forEach(item -> allItemsList.add(item));
        return allItemsList;
    }

    public List<Item> allAvailableMenuItems() {
        List<Item> allInstockItemsList=new ArrayList<>();
        allInstockItemsList=itemRepository.findAll().stream().filter(item -> item.getInStock().equals(true)).collect(Collectors.toList());
        return allInstockItemsList;

    }

    public List<Item> outOfStock() {
        List<Item> outOfStock=new ArrayList<>();
        outOfStock=itemRepository.findAll().stream().filter(item -> item.getInStock().equals(false)).collect(Collectors.toList());
        return outOfStock;

    }

    public void removeItemCategoryFromMenu(Item itemCategory) {

        List<Item> listOfItemCategoryToSetOutOfStock=new ArrayList<>();

        listOfItemCategoryToSetOutOfStock=showAllItems().stream().filter(item -> item.getClass().equals(itemCategory.getClass())).collect(Collectors.toList());

        listOfItemCategoryToSetOutOfStock.forEach(itemInList->{
            itemInList.setInStock(false);
        });
        itemRepository.saveAll(listOfItemCategoryToSetOutOfStock);


    }

    public void reInstateItemCategory(Item itemCategory) {
        List<Item> listOfItemCategoryToBeSetInStock=new ArrayList<>();

        listOfItemCategoryToBeSetInStock=showAllItems().stream().filter(item -> item.getClass().equals(itemCategory.getClass())).collect(Collectors.toList());

        listOfItemCategoryToBeSetInStock.forEach(itemInList->{
            itemInList.setInStock(true);
        });
        System.out.println(listOfItemCategoryToBeSetInStock+ " ååååå after ");
        itemRepository.saveAll(listOfItemCategoryToBeSetInStock);

    }

    public void findbyname(){
        String nameOfItem="pripps";
        Item found;
        found=itemRepository.findItemByName(nameOfItem);
        System.out.println(found+ "this is found");
    }

    public void createNewFoodOrSnacksItem(String nameOfItem, int price, String itemCategory){

        try {
            switch (itemCategory) {
                case "SnacksItem":
                    itemRepository.save(new SnacksItem(nameOfItem, price));
                    break;
                case "FoodItem":
                    itemRepository.save(new FoodItem(nameOfItem, price));
                    break;
                default:
                    itemRepository.save(new Item(nameOfItem, price));

            }

        }catch (Exception e){
            System.err.println(e);
        }

    }

    public void createNewDrinkableNonAlcItem(String nameOfItem, int price, String type, Long volume){

        try {
                    itemRepository.save(new DrinkableItem(nameOfItem,price,type,false,volume));

        }catch (Exception e){
            System.err.println(e);
        }

    }
    public void createNewDrinkableAlcoholicItem(String nameOfItem, int price, String type, Long volume, double percantage){

        try {
            itemRepository.save(new DrinkableAlcoholicItem(nameOfItem,price,type,true,volume,percantage ));

        }catch (Exception e){
            System.err.println(e);
        }

    }



}

//        itemRepository.findAll().forEach(
//                item -> allItemsList.add(item));

//    public void findCountOfItems() {
//        long count = itemRepository.count();
//        System.out.println("Number of documents in the collection: " + count);
//    }