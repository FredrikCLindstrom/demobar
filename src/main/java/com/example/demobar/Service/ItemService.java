package com.example.demobar.Service;
import com.example.demobar.Model.*;
import com.example.demobar.Repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Item> getAllItemsInCategoryAsList(Item itemType) {
        List<Item> allItemsInACategory=new ArrayList<>();
        allItemsInACategory=itemRepository.findAll().stream().filter(item -> item.getClass().equals(itemType.getClass())).collect(Collectors.toList());
        return allItemsInACategory;
    }

    public List<Item> getStockedStatusItemsInCategory(List<Item> itemList, Boolean instock) {
        System.out.println(instock);
        List<Item> newItemList=itemList.stream().filter(item->item.getInStock().equals(instock.booleanValue())).collect(Collectors.toList());
        return newItemList;
    }

    public List<Item> allCategoryItemsInstock(Item itemType) {
        List<Item> allItemsInACategory= getAllItemsInCategoryAsList(itemType);
        System.out.println(allItemsInACategory);
        List<Item> allItemsInACategoryInstock= getStockedStatusItemsInCategory(allItemsInACategory, true);

        return allItemsInACategoryInstock;

    }

    public List<Item> allCategoryItemsNotInstock(Item itemType) {

        List<Item> allItemsInACategory= getAllItemsInCategoryAsList(itemType);
        System.out.println(allItemsInACategory);
        List<Item> allItemsNotInACategoryInstock= getStockedStatusItemsInCategory(allItemsInACategory, false);

        return allItemsNotInACategoryInstock;

    }

    public List<Item> outOfStock() {
        List<Item> outOfStock;
        outOfStock=itemRepository.findAll().stream().filter(item -> item.getInStock().equals(false)).collect(Collectors.toList());
        return outOfStock;

    }

    public void removeAllItemCategorysFromMenu() {

        List<Item> itemList;

        itemList=showAllItems().stream().collect(Collectors.toList());

        itemList.forEach(itemInList->{
            itemInList.setInStock(false);
        });
        itemRepository.saveAll(itemList);

    }

    public void reInstateAllItemCategorysFromMenu() {

        List<Item> itemList;

        itemList=showAllItems().stream().collect(Collectors.toList());

        itemList.forEach(itemInList->{
            itemInList.setInStock(true);
        });
        itemRepository.saveAll(itemList);

    }


    public void removeItemCategoryFromMenu(Item itemCategory) {

        List<Item> listOfItemCategoryToSetOutOfStock;

        listOfItemCategoryToSetOutOfStock=showAllItems().stream().filter(item -> item.getClass().equals(itemCategory.getClass())).collect(Collectors.toList());

        listOfItemCategoryToSetOutOfStock.forEach(itemInList->{
            itemInList.setInStock(false);
        });
        itemRepository.saveAll(listOfItemCategoryToSetOutOfStock);

    }

    public void reInstateItemCategory(Item itemCategory) {
        List<Item> listOfItemCategoryToBeSetInStock;

        listOfItemCategoryToBeSetInStock=showAllItems().stream().filter(item -> item.getClass().equals(itemCategory.getClass())).collect(Collectors.toList());

        listOfItemCategoryToBeSetInStock.forEach(itemInList->{
            itemInList.setInStock(true);
        });
        itemRepository.saveAll(listOfItemCategoryToBeSetInStock);

    }

    public Item findItemById(String itemId){
        Item found;
        found=itemRepository.findItemById(itemId);
        return found;
    }

    public Item setOutOrInStock(String itemId, boolean instock){
        Item found;
        found=itemRepository.findItemById(itemId);
        found.setInStock(instock);
        itemRepository.save(found);
        return found;
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

    public void createNewDrinkableNonAlcItem(String nameOfItem, int price, Long volume){

        try {
            itemRepository.save(new DrinkableItem(nameOfItem,price,false,volume));
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

    public void changeNameOfItem(String id, String newName) {
        System.out.println("id: "+ id+" newName: "+newName);
        Item foundItem=itemRepository.findItemById(id);
        foundItem.setNameOfItem(newName);
        itemRepository.save(foundItem);
    }

    public void changeTheItemType(String id, String type) {
        System.out.println("id: "+ id+" type: "+type);
        Item foundItem=itemRepository.findItemById(id);
        DrinkableAlcoholicItem drinkableAlcoholicItem= (DrinkableAlcoholicItem) foundItem;
        drinkableAlcoholicItem.setType(type);
        itemRepository.save(drinkableAlcoholicItem);
    }

    public void changePriceOfItem(String id, int price) {
        System.out.println("id: "+ id+" price: "+price);
        Item foundItem=itemRepository.findItemById(id);
        foundItem.setPrice(price);
        itemRepository.save(foundItem);

    }

    public void changeVolumeOfItem(String id, int volume) {
        System.out.println("id: "+ id+" volume: "+volume);
        Item foundItem=itemRepository.findItemById(id);
        DrinkableItem drinkableItem= (DrinkableItem) foundItem;
        drinkableItem.setVolume((long) volume);
        itemRepository.save(drinkableItem);

    }

    public void changePercentageOfItem(String id, int percentage) {
        System.out.println("<<<><<>>>>>>><<<<><<><>><<<<");
        System.out.println("id: "+ id+" percentage: "+percentage);

        Item foundItem=itemRepository.findItemById(id);
        System.out.println("foundItem: "+foundItem);
        DrinkableAlcoholicItem drinkableAlcoholicItem= (DrinkableAlcoholicItem) foundItem;
        System.out.println("drinkableAlcoholicItem: "+drinkableAlcoholicItem);
        drinkableAlcoholicItem.setPercentageAlcohol((long) percentage);
        itemRepository.save(drinkableAlcoholicItem);

    }

    public void deleteFromDatabase(String id){
        Item itemFound= itemRepository.findItemById(id);
        itemRepository.delete(itemFound);
    }

//    public void createItems() {
//
//        itemRepository.save(new DrinkableAlcoholicItem("Mythos", 79, "beer",true, 50L, 5));
//        itemRepository.save(new FoodItem("Biffteki", 99));
//        itemRepository.save(new DrinkableAlcoholicItem("pripps", 49, "beer",true, 50L, 5));
//        itemRepository.save(new FoodItem("pizza Veg", 99));
//        itemRepository.save(new SnacksItem("chips n dip", 30));
//    }
}
