package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import java.util.ArrayList;
import java.util.List;

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

    public void editUser(String name, String email, String phoneNumber, String address) {
        User user = User.getInstance();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
    }


    public static class UserDTO {
        private String userId;
        private String name;
        private String email;
        private String phoneNumber;
        private String address;
        private boolean isAdmin;

        public UserDTO(String userId, String name, String email, String phoneNumber, String address, boolean isAdmin) {
            this.userId = userId;
            this.name = name;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.isAdmin = isAdmin;
        }

        public String getUserId() { return userId; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPhoneNumber() { return phoneNumber; }
        public String getAddress() { return address; }
        public boolean isAdmin() { return isAdmin; }
    }

    public List<UserDTO> getAllUsers() {

        List<UserDTO> users = new ArrayList<>();

        users.add(new UserDTO(
                "1",
                "John Doe",
                "john.doe@example.com",
                "1234567890",
                "123 Main St",
                false
        ));

        users.add(new UserDTO(
                "2",
                "Jane Smith",
                "jane.smith@example.com",
                "0987654321",
                "456 Oak Ave",
                true
        ));

        return users;
    }


}