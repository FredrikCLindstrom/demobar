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

    @GetMapping("/ShowFoodOnMenu") //shows the food available on menu
    public List<Item> showFoodOnMenu() {
        FoodItem foodItem= new FoodItem();
        return itemService.allCategoryItemsInstock(foodItem);
    }

    @GetMapping("/ShowFoodNotOnMenu") //shows the food NOT available on menu
    public List<Item> showFoodNotOnMenu() {
        FoodItem foodItem= new FoodItem();
        return itemService.allCategoryItemsNotInstock(foodItem);
    }

    @GetMapping("/ShowSnacksOnMenu") //shows the food available on menu
    public List<Item> showSnacksOnMenu() {
        SnacksItem snacksItem= new SnacksItem();
        return itemService.allCategoryItemsInstock(snacksItem);
    }

    @GetMapping("/ShowSnacksNotOnMenu") //shows the food NOT available on menu
    public List<Item> showSnacksNotOnMenu() {
        SnacksItem snacksItem= new SnacksItem();
        return itemService.allCategoryItemsNotInstock(snacksItem);
    }

    @GetMapping("/ShowDrinkableItemsOnMenu") //shows the food available on menu
    public List<Item> ShowDrinkableItemsOnMenu() {
        DrinkableItem drinkableItem= new DrinkableItem();
        return itemService.allCategoryItemsInstock(drinkableItem);
    }

    @GetMapping("/ShowDrinkableItemsNotOnMenu") //shows the food NOT available on menu
    public List<Item> ShowDrinkableItemsNotOnMenu() {
        DrinkableItem drinkableItem= new DrinkableItem();
        return itemService.allCategoryItemsNotInstock(drinkableItem);
    }

    @GetMapping("/ShowDrinkableAlcoholicItemsOnMenu") //shows the food available on menu
    public List<Item> ShowDrinkableAlcoholicItemsOnMenu() {
        DrinkableAlcoholicItem drinkableAlcoholicItem= new DrinkableAlcoholicItem();
        return itemService.allCategoryItemsInstock(drinkableAlcoholicItem);
    }

    @GetMapping("/ShowDrinkableAlcoholicItemsNotOnMenu") //shows the food NOT available on menu
    public List<Item> ShowDrinkableAlcoholicItemsNotOnMenu() {
        DrinkableAlcoholicItem drinkableAlcoholicItem= new DrinkableAlcoholicItem();
        return itemService.allCategoryItemsNotInstock(drinkableAlcoholicItem);
    }

    @GetMapping("/ShowNotOnMenu") //items that are out of stock
    public List<Item> showNotOnMenu() {
        return itemService.outOfStock();

    }

    @GetMapping("/getTodaysReceipts")
    public List<Receipt> getTodaysReceipts() {
        List<Receipt> todaysReceipts= new ArrayList<>();
        todaysReceipts=receiptService.getTodaysReceipts();
        return todaysReceipts;

    }

    @GetMapping("/getYesterdayReceipts")
    public List<Receipt> getYesterdaysReceipts() {
        List<Receipt> yesterdaysReceipts= new ArrayList<>();
        yesterdaysReceipts=receiptService.getYesterdaysReceipts();
        return yesterdaysReceipts;

    }

    @GetMapping("/RemoveAlcoholFromMenu") //sets all alcoholic bevergaes to --OUT-- of stock
    public ResponseEntity<String> removeAlcohol() {
        Item item = new DrinkableAlcoholicItem();
        itemService.removeItemCategoryFromMenu(item);
        return new ResponseEntity<>("200", HttpStatus.OK);

    }

    @GetMapping("/RemoveAllItemsFromMenu") //sets all items to --OUT-- of stock
    public ResponseEntity<String> removeAllItemsFromMenu() {
        itemService.removeAllItemCategorysFromMenu();
        return new ResponseEntity<>("200", HttpStatus.OK);

    }

    @GetMapping("/ReInstateAllItemsFromMenu") //sets all items to --OUT-- of stock
    public ResponseEntity<String> reInstateAllItemsFromMenu() {
        itemService.reInstateAllItemCategorysFromMenu();
        return new ResponseEntity<>("200", HttpStatus.OK);

    }

    @GetMapping("/ReinstateAlcoholToMenu") //sets all alcoholic bevergaes to out --IN-- stock
    public ResponseEntity<String> reInstateAlcoholicBeveragesToMenu() {
        Item item = new DrinkableAlcoholicItem();
        itemService.reInstateItemCategory(item);
        return new ResponseEntity<>("200", HttpStatus.OK);

    }

    @GetMapping("/CloseTheKitchen") //sets all Fooditems  to --OUT-- of stock
    public ResponseEntity<String> CloseKitchenByRemovingFoodItems() {
        Item item = new FoodItem();
        itemService.removeItemCategoryFromMenu(item);

        return new ResponseEntity<>("200", HttpStatus.OK);
    }

    @GetMapping("/OpenTheKitchen") //sets all Fooditems to out --IN-- stock
    public ResponseEntity<String> reInstateFoodItemsToMenu() {
        Item item = new FoodItem();
        itemService.reInstateItemCategory(item);
        return new ResponseEntity<>("200", HttpStatus.OK);

    }

    @PostMapping("/addItemFoodOrSnacks/{itemCategory}/{nameOfItem}/{price}") //Add food or snack item
    public ResponseEntity<String> addFoodOrSnackToMeny(@PathVariable String itemCategory, @PathVariable String nameOfItem, @PathVariable int price) {
        itemService.createNewFoodOrSnacksItem(nameOfItem,price, itemCategory);
        return new ResponseEntity<>("200", HttpStatus.OK);
    }

    @PostMapping("/addItemDrink/{nameOfItem}/{price}/{volume}") // add Drink item
    public void addDrinkItemToMenu( @PathVariable String nameOfItem, @PathVariable int price,  @PathVariable Long volume) {
        System.out.println("<<<< name of item: "+nameOfItem+" price: "+price+" volume: "+volume);
        itemService.createNewDrinkableNonAlcItem(nameOfItem,price,volume);
    }

    @PostMapping("/addItemDrinkAlcoholic/{nameOfItem}/{price}/{type}/{volume}/{percentage}") //add alcoholic drink item
    public void addAlcoholicDrinkItemToMenu( @PathVariable String nameOfItem,@PathVariable String type, @PathVariable int price, @PathVariable Long volume, @PathVariable double percentage) {
        itemService.createNewDrinkableAlcoholicItem(nameOfItem,price, type, volume, percentage);
    }

    @PutMapping("/DeleteItem/{id}")
    public ResponseEntity<String> deleteForeverItem(@PathVariable String id) {
        System.out.println("item to be deleted "+id);
        itemService.deleteFromDatabase(id);
        return new ResponseEntity<>("200", HttpStatus.OK);
       //TODO: change to deletemapping later
    }

    @PutMapping("/SetOutOfStockSingleItem/{id}")
    public ResponseEntity<String> takeItemOffMenu(@PathVariable String id) {
        itemService.setOutOrInStock(id, false);
        return new ResponseEntity<>("200", HttpStatus.OK);
    }
    @PutMapping("/SetInStockSingleItem/{id}")
    public ResponseEntity<String> putItemBackOnMenu(@PathVariable String id) {
        itemService.setOutOrInStock(id, true);
        return new ResponseEntity<>("200", HttpStatus.OK);
    }

    @PutMapping("/ChangeTheNameOfItem/{id}/{newName}")
    public ResponseEntity<String> changeNameOfItem(@PathVariable String id,@PathVariable String newName) {
        itemService.changeNameOfItem(id, newName);
        return new ResponseEntity<>("200", HttpStatus.OK);
    }

    @PutMapping("/ChangeTheItemType/{id}/{type}")
    public ResponseEntity<String> changeItemType(@PathVariable String id,@PathVariable String type) {
        itemService.changeTheItemType(id, type);
        return new ResponseEntity<>("200", HttpStatus.OK);
    }

    @PutMapping("/ChangeThePriceOfItem/{id}/{price}")
    public ResponseEntity<String> changePriceOfItem(@PathVariable String id,@PathVariable int price) {
        itemService.changePriceOfItem(id, price);
        return new ResponseEntity<>("200", HttpStatus.OK);
    }

    @PutMapping("/ChangeTheVolumeOfItem/{id}/{volume}")
    public ResponseEntity<String> changeVolumeOfItem(@PathVariable String id,@PathVariable int volume) {
        itemService.changeVolumeOfItem(id, volume);
        return new ResponseEntity<>("200", HttpStatus.OK);
    }

    @PutMapping("/ChangeThePercentageOfItem/{id}/{percentage}")
    public ResponseEntity<String> changePercentageOfItem(@PathVariable String id,@PathVariable int percentage) {
        itemService.changePercentageOfItem(id, percentage);
        return new ResponseEntity<>("200", HttpStatus.OK);
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

        //receipt.setLocalDateTime(LocalDateTime.of(ye).now());

        receiptService.calculateTotalReceipt(receipt);

        receiptService.saveReceiptToDB(receipt);

        return new ResponseEntity<>("200", HttpStatus.OK);
    }
}