package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.GetAllUsers;
import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.Login;
import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.Register;
import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.UpdateUser;
import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.UpdateUserRole;
import ar.edu.davinci.carbone_lucas.lk_store.Interface.LoginCallback;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;
import ar.edu.davinci.carbone_lucas.lk_store.models.UserDTO;

public class UserController {

    public void login(String userId, LoginCallback callback) {
        Login loginTask = new Login(callback);
        loginTask.execute(userId);
    }

    public void editUser(String name, String phoneNumber, String address) {
        User user = User.getInstance();
        UpdateUser updateTask = new UpdateUser(name, phoneNumber, address);
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

    public void register(String id, String name, String email, String pass){
        Register register = new Register();
        register.execute(id, name, email, pass);
    }

    public static void changeRol(String userId, Boolean isAdmin){
        new UpdateUserRole(isAdmin).execute(userId);
    }
}