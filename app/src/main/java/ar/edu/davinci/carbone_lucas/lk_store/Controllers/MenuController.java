package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.GetMenusApi;
import ar.edu.davinci.carbone_lucas.lk_store.models.Menu.Menu;
import ar.edu.davinci.carbone_lucas.lk_store.models.Menu.MenuData;

public class MenuController {
    private static MenuController instance;
    private List<MenuData> allMenus;

    private MenuController() {
        loadData();
    }

    public static MenuController getInstance() {
        if (instance == null) {
            instance = new MenuController();
        }
        return instance;
    }

    private void loadData() {
        GetMenusApi task = new GetMenusApi();
        try {
            List<Menu> menuList = task.execute().get();
            allMenus = convertToMenuData(menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<MenuData> convertToMenuData(List<Menu> menuList) {
        DiscountController dc = DiscountController.getInstance();
        ProductController pc = ProductController.getInstance();

        List<MenuData> menuDataList = new ArrayList<>();
        for (Menu menu : menuList) {
            MenuData menuData = new MenuData(
                    menu.getId(),
                    pc.getHamburger(menu.getHamburgerId()),
                    pc.getFries(menu.getFriesId()),
                    pc.getDrink(menu.getDrinkId()),
                    dc.getDiscount(menu.getDiscountId()),
                    menu.isDiscounted(),
                    menu.getPrice(),
                    menu.getImg_url()
            );
            menuDataList.add(menuData);
        }
        return menuDataList;
    }

    public List<MenuData> getAllMenus() {
        return allMenus;
    }

    public List<MenuData> getDiscountedMenus() {
        List<MenuData> discountedMenus = new ArrayList<>();
        for (MenuData menu : allMenus) {
            if (menu.isDiscounted()) {
                discountedMenus.add(menu);
            }
        }
        return discountedMenus;
    }

    public MenuData getMenu(String id) {
        for (MenuData menu : allMenus) {
            if (menu.getId().equals(id)) {
                return menu;
            }
        }
        return null;
    }
}