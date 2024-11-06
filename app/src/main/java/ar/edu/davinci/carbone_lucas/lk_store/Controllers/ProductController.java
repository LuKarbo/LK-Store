package ar.edu.davinci.carbone_lucas.lk_store.Controllers;
import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.models.Hamburger;
import ar.edu.davinci.carbone_lucas.lk_store.models.Fries;
import ar.edu.davinci.carbone_lucas.lk_store.models.Drink;

public class ProductController {
    private static final String API_URL = "https://tu-api.com/";

    public List<Hamburger> getHamburgers() {
        // consultar la API Hamburger

        List<Hamburger> hamburger_list = new ArrayList<>();
        // Agrego las hamburguesas a la lista

        // TEST
        hamburger_list.add(new Hamburger("1","/discount/abc123", true, "Cheeseburger", 5.99, 50, "https://example.com/cheeseburger.jpg"));
        hamburger_list.add(new Hamburger("2","/discount/def456", true, "Big Mac", 7.99, 30, "https://example.com/bigmac.jpg"));
        hamburger_list.add(new Hamburger("3",null, false, "Hamburguesa Sencilla", 4.99, 80, "https://example.com/hamburguesa-sencilla.jpg"));
        hamburger_list.add(new Hamburger("4","/discount/ghi789", true, "Doble Queso", 6.49, 25, "https://example.com/doble-queso.jpg"));

        return hamburger_list;
    }

    public List<Fries> getFries() {
        // consultar la API  Fries
        List<Fries> fries_list = new ArrayList<>();
        // Agrego las papas fritas a la lista

        // TEST
        fries_list.add(new Fries("1","/discount/mno345", true, "Small Fries", 2.49, 100, "https://example.com/small-fries.jpg"));
        fries_list.add(new Fries("2","/discount/pqr678", true, "Medium Fries", 3.29, 80, "https://example.com/medium-fries.jpg"));
        fries_list.add(new Fries("3",null, false, "Papas Fritas", 2.99, 120, "https://example.com/papas-fritas.jpg"));
        fries_list.add(new Fries("4","/discount/stu901", true, "Papas Deluxe", 3.99, 60, "https://example.com/papas-deluxe.jpg"));

        return fries_list;
    }

    public List<Drink> getDrinks() {
        // consultar la API Drink
        List<Drink> bebidas = new ArrayList<>();
        // Agrega las bebidas a la lista

        // TEST
        bebidas.add(new Drink("1","Cola", 2.29, 150, "https://example.com/cola.jpg"));
        bebidas.add(new Drink( "21","Sprite", 2.29, 130, "https://example.com/sprite.jpg"));
        bebidas.add(new Drink("31","Jugo de Naranja", 2.79, 100, "https://example.com/jugo-naranja.jpg"));
        bebidas.add(new Drink("4","Té Helado", 2.49, 90, "https://example.com/te-helado.jpg"));

        return bebidas;
    }

    public Hamburger getHamburger(String id) {
        // consultar la API Hamburger
        Hamburger hamburger = new Hamburger("1","/discount/abc123", true, "Cheeseburger", 5.99, 50, "https://example.com/cheeseburger.jpg");
        // guardar la respuesta

        return hamburger;
    }

    public Fries getFries(String id) {
        // consultar la API Fries
        Fries fries = new Fries("1","/discount/mno345", true, "Small Fries", 2.49, 100, "https://example.com/small-fries.jpg");
        // guardar la respuesta

        return fries;
    }

    public Drink getDrink(String id) {
        // consultar la API Drink
        Drink drink = new Drink("1","Cola", 2.29, 150, "https://example.com/cola.jpg");
        // guardar la respuesta

        return drink;
    }

    public List<String> getHamburgersIds(){
        List<String> hamburgerIDs_list = new ArrayList<>();
        // Agrego las hamburguesas a la lista

        // TEST
        hamburgerIDs_list.add(new Hamburger("1","/discount/abc123", true, "Cheeseburger", 5.99, 50, "https://example.com/cheeseburger.jpg").getId());
        hamburgerIDs_list.add(new Hamburger("2","/discount/def456", true, "Big Mac", 7.99, 30, "https://example.com/bigmac.jpg").getId());
        hamburgerIDs_list.add(new Hamburger("3",null, false, "Hamburguesa Sencilla", 4.99, 80, "https://example.com/hamburguesa-sencilla.jpg").getId());
        hamburgerIDs_list.add(new Hamburger("4","/discount/ghi789", true, "Doble Queso", 6.49, 25, "https://example.com/doble-queso.jpg").getId());

        return hamburgerIDs_list;
    }

    public List<String> getFriesIds(){
        List<String> friesIDs_list = new ArrayList<>();
        // Agrego las hamburguesas a la lista

        // TEST
        friesIDs_list.add(new Fries("1","/discount/mno345", true, "Small Fries", 2.49, 100, "https://example.com/small-fries.jpg").getId());
        friesIDs_list.add(new Fries("2","/discount/pqr678", true, "Medium Fries", 3.29, 80, "https://example.com/medium-fries.jpg").getId());
        friesIDs_list.add(new Fries("3",null, false, "Papas Fritas", 2.99, 120, "https://example.com/papas-fritas.jpg").getId());
        friesIDs_list.add(new Fries("4","/discount/stu901", true, "Papas Deluxe", 3.99, 60, "https://example.com/papas-deluxe.jpg").getId());

        return friesIDs_list;
    }

    public List<String> getDrinksIds(){
        List<String> drinksIDs_list = new ArrayList<>();
        // Agrego las hamburguesas a la lista

        // TEST
        drinksIDs_list.add(new Drink("1","Cola", 2.29, 150, "https://example.com/cola.jpg").getId());
        drinksIDs_list.add(new Drink( "21","Sprite", 2.29, 130, "https://example.com/sprite.jpg").getId());
        drinksIDs_list.add(new Drink("31","Jugo de Naranja", 2.79, 100, "https://example.com/jugo-naranja.jpg").getId());
        drinksIDs_list.add(new Drink("4","Té Helado", 2.49, 90, "https://example.com/te-helado.jpg").getId());

        return drinksIDs_list;
    }


}
