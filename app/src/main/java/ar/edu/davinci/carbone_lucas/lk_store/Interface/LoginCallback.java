package ar.edu.davinci.carbone_lucas.lk_store.Interface;

public abstract class LoginCallback {

    public abstract void onLoginSuccess(boolean success);

    public abstract void onLoginError(String error);
}
