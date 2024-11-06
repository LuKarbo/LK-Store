package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import ar.edu.davinci.carbone_lucas.lk_store.models.User;

public class UserController {
    public void login(String userId) {

        // consulta a la api con el userId
        // recupero la info del usuario
        // guardo los datos en la instancia
        User user = User.getInstance();
        user.setUserId(userId);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPhoneNumber("123123123");
        user.setPassword("123");
        user.setAddress("Av. Corrientes 2037, C1001 Cdad. Autónoma de Buenos Aires");
        user.setAdmin(true);
    }
}
