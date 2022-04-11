package com.example.demobar.Controller;

import com.example.demobar.Model.*;
import com.example.demobar.Service.ItemService;
import com.example.demobar.Service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class ApiController {

    //TODO: add response to all endpoints

    ItemService itemService;
    ReceiptService receiptService;

    @Autowired
    public ApiController(ItemService itemService, ReceiptService receiptService) {
        this.itemService = itemService;
        this.receiptService = receiptService;
    }

//    @GetMapping("/addToDataBase") //adds testdata do DB, only runs once
//    public String getTestObject() {
//        itemService.createItems();
//        return "added";
//    }

    @GetMapping("/all") //show all menu items regardless of in stock or not
    public List<Item> getAllItemsInDB() {
        return itemService.showAllItems();
    }


    @GetMapping("/ShowFullMenu") //shows the menu items in stock that can be ordered
    public List<Item> allInStockItems() {
        return itemService.allAvailableMenuItems();

    }

    @GetMapping("/ShowNotOnMenu") //items that are out of stock
    public List<Item> showNotOnMenu() {
        return itemService.outOfStock();

    }

    @GetMapping("/RemoveAlcoholFromMenu") //sets all alcoholic bevergaes to --OUT-- of stock
    public void removeAlcohol() {
        Item item = new DrinkableAlcoholicItem();
        itemService.removeItemCategoryFromMenu(item);

    }

    @GetMapping("/ReinstateAlcoholToMenu") //sets all alcoholic bevergaes to out --IN-- stock
    public void reInstateAlcoholicBeveragesToMenu() {
        Item item = new DrinkableAlcoholicItem();
        itemService.reInstateItemCategory(item);

    }

    @GetMapping("/CloseTheKitchen") //sets all Fooditems  to --OUT-- of stock
    public void CloseKitchenByRemovingFoodItems() {
        Item item = new FoodItem();
        itemService.removeItemCategoryFromMenu(item);

    }

    @GetMapping("/OpenTheKitchen") //sets all Fooditems to out --IN-- stock
    public void reInstateFoodItemsToMenu() {
        Item item = new FoodItem();
        itemService.reInstateItemCategory(item);

    }

    @PostMapping("/addItemFoodOrSnacks/{itemCategory}/{nameOfItem}/{price}") //Add food or snack item
    public void addFoodOrSnackToMeny(@PathVariable String itemCategory, @PathVariable String nameOfItem, @PathVariable int price) {
        itemService.createNewFoodOrSnacksItem(nameOfItem,price, itemCategory);
    }

    @PostMapping("/addItemDrink/{nameOfItem}/{price}/{type}/{volume}") // add Drink item
    public void addDrinkItemToMenu( @PathVariable String nameOfItem, @PathVariable int price,@PathVariable String type,  @PathVariable Long volume) {
        itemService.createNewDrinkableNonAlcItem(nameOfItem,price, type, volume);
    }

    @PostMapping("/addItemDrinkAlcoholic/{nameOfItem}/{price}/{type}/{volume}/{percentage}") //add alcoholic drink item
    public void addAlcoholicDrinkItemToMenu( @PathVariable String nameOfItem,@PathVariable String type, @PathVariable int price, @PathVariable Long volume, @PathVariable double percentage) {
        itemService.createNewDrinkableAlcoholicItem(nameOfItem,price, type, volume, percentage);
    }
    @PostMapping("/DeleteItem/{id}")
    public void deleteForeverItem(@PathVariable String id) {
       //TODO: delete the one with that id
    }

    @PostMapping("/SetOutOfStockSingleItem/{id}")
    public void takeItemOffMenu(@PathVariable String id) {
        //TODO: set in stock of said item to false
    }

    @PostMapping("/printReceipt")
    public ResponseEntity<String>  printReceipt (@RequestBody List<String> items) throws IOException {

        Receipt receipt= new Receipt();
        ArrayList<Item> listOfItems = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {

            Item item = itemService.findItemById(items.get(i));
            listOfItems.add(item);

        }
        receipt.setItemsPurchased(listOfItems);
        receiptService.saveReceiptToDB(receipt);
        receiptService.calculateTotalReceipt(receipt);

        return new ResponseEntity<>("200", HttpStatus.OK);
    }
}