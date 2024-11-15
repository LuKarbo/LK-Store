package ar.edu.davinci.carbone_lucas.lk_store.generalFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ar.edu.davinci.carbone_lucas.lk_store.Controllers.UserController;
import ar.edu.davinci.carbone_lucas.lk_store.MainActivity;
import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;

public class UserEditFragment extends Fragment {

    private EditText etName, etEmail, etPhoneNumber, etAddress;
    private Button btnSave;
    private ImageButton backButton;
    private TextView tvTitle;

    private UserController userController;
    private User currentUser;

    public UserEditFragment() {}

    public static UserEditFragment newInstance() {
        return new UserEditFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_edit, container, false);

        tvTitle = view.findViewById(R.id.tv_title);
        etName = view.findViewById(R.id.et_name);
        etEmail = view.findViewById(R.id.et_email);
        etPhoneNumber = view.findViewById(R.id.et_phone_number);
        etAddress = view.findViewById(R.id.et_address);
        btnSave = view.findViewById(R.id.btn_save);
        backButton = view.findViewById(R.id.back_button);

        tvTitle.setText("Editar Cuenta");

        userController = new UserController();
        currentUser = User.getInstance();
        loadUserInfo();

        btnSave.setOnClickListener(v -> {
            saveUserInfo();
        });

        backButton.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        return view;
    }

    private void loadUserInfo() {
        etName.setText(currentUser.getName());
        etEmail.setText(currentUser.getEmail());
        etPhoneNumber.setText(currentUser.getPhoneNumber());
        etAddress.setText(currentUser.getAddress());
    }

    private void saveUserInfo() {
        String newName = etName.getText().toString();
        String newEmail = etEmail.getText().toString();
        String newPhoneNumber = etPhoneNumber.getText().toString();
        String newAddress = etAddress.getText().toString();

        boolean hasChanges = !newName.equals(currentUser.getName())
                || !newEmail.equals(currentUser.getEmail())
                || !newPhoneNumber.equals(currentUser.getPhoneNumber())
                || !newAddress.equals(currentUser.getAddress());

        if (hasChanges) {
            userController.editUser(newName, newEmail, newPhoneNumber, newAddress);
            Toast.makeText(requireContext(), "Cuenta Actualizada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "No hay cambios para guardar", Toast.LENGTH_SHORT).show();
        }
    }
}