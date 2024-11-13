package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.GetAllUsers;
import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.Login;
import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.Register;
import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.UpdateUser;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;
import ar.edu.davinci.carbone_lucas.lk_store.models.UserDTO;

public class UserController {

    public void login(String userId) {
        Login loginTask = new Login();
        loginTask.execute(userId);
    }

    public void editUser(String name, String email, String phoneNumber, String address) {
        User user = User.getInstance();
        UpdateUser updateTask = new UpdateUser(name, email, phoneNumber, address);
        updateTask.execute(user.getUserId());
    }

    public List<UserDTO> getAllUsers() {
        GetAllUsers getAllUsersTask = new GetAllUsers();
        try {
            return getAllUsersTask.execute().get();
        } catch (Exception e) {
            Log.e("User_Data", "Error getting all users: " + e.getMessage());
            return null;
        }
    }

    public void register(String name, String email, String pass){
        Register register = new Register();
        register.execute(name, email, pass);
    }
}