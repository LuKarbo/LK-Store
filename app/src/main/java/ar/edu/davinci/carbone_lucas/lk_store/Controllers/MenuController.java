package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.models.Discount.Discount;
import ar.edu.davinci.carbone_lucas.lk_store.models.Drink;
import ar.edu.davinci.carbone_lucas.lk_store.models.Fries;
import ar.edu.davinci.carbone_lucas.lk_store.models.Hamburger;
import ar.edu.davinci.carbone_lucas.lk_store.models.Menu.Menu;
import ar.edu.davinci.carbone_lucas.lk_store.models.Menu.MenuData;

public class MenuController {
    public List<MenuData> getAllMenus() {
        ProductController pc = new ProductController();
        DiscountController dc = new DiscountController();

        List<Menu> allMenus = new ArrayList<>();

        // Recibo de la api
        allMenus.add(new Menu("1","1","2","3","",false,60));
        allMenus.add(new Menu("2","3","2","3","1",true,20));
        allMenus.add(new Menu("3","2","2","3","1",true,50));
        allMenus.add(new Menu("4","1","2","3","",false,120));

        // Genero el MenuData para tener toda la info del menu
        List<MenuData> menuList = new ArrayList<>();
        for (Menu menu: allMenus) {
            menuList.add(new MenuData(menu.getId(),pc.getHamburger(menu.getHamburgerId()),pc.getFries(menu.getFriesId()),pc.getDrink(menu.getDrinkId()),dc.getDiscount(menu.getDiscountId()),menu.isDiscounted(),menu.getPrice()));
        }

        return menuList;
    }

    public List<MenuData> getDiscountedMenus() {
        ProductController pc = new ProductController();
        DiscountController dc = new DiscountController();

        List<Menu> discountedMenus = new ArrayList<>();

        // Recibo de la api
        discountedMenus.add(new Menu("1","1","2","3","",false,60));
        discountedMenus.add(new Menu("2","3","2","3","1",true,20));
        discountedMenus.add(new Menu("4","1","2","3","",false,120));
        discountedMenus.add(new Menu("3","2","3","3","1",true,50));
        discountedMenus.add(new Menu("3","1","1","2","1",true,50));
        discountedMenus.add(new Menu("3","3","2","1","1",true,50));

        // Genero el MenuData para tener toda la info del menu
        List<MenuData> menuList = new ArrayList<>();
        for (Menu menu: discountedMenus) {
            if(menu.isDiscounted()){
                menuList.add(new MenuData(menu.getId(),pc.getHamburger(menu.getHamburgerId()),pc.getFries(menu.getFriesId()),pc.getDrink(menu.getDrinkId()),dc.getDiscount(menu.getDiscountId()),menu.isDiscounted(),menu.getPrice()));
            }
        }

        return menuList;
    }

    private List<String> getAllMenuIds() {
        return Arrays.asList("kr506q1A3QK01dtAgM1x", "b1DSsgpmUrjkJ0g7pN6y", "9NnA4iYLEf0QIGndrwMQ");
    }

    public MenuData getMenu(String id){
        ProductController pc = new ProductController();
        DiscountController dc = new DiscountController();

        // busco en la api
        Menu menu = new Menu("1","1","2","3","",false,60);

        // Genero el MenuData para tener toda la info del menu
        MenuData menuData = new MenuData(menu.getId(),pc.getHamburger(menu.getHamburgerId()),pc.getFries(menu.getFriesId()),pc.getDrink(menu.getDrinkId()),dc.getDiscount(menu.getDiscountId()),menu.isDiscounted(),menu.getPrice());

        return menuData;
    }

}
