package ar.edu.davinci.carbone_lucas.lk_store.generalFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import ar.edu.davinci.carbone_lucas.lk_store.MainActivity;
import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;

public class MyAccountFragment extends Fragment {

    private TextView userNameText;
    private TextView userEmailText;
    private TextView userPhoneText;
    private TextView userAddressText;
    private Button viewHistoryButton;
    private Button editProfileButton;
    private LinearLayout linearButtons;
    private ImageButton backButton;

    public MyAccountFragment() {}

    public static MyAccountFragment newInstance() {
        return new MyAccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearButtons = view.findViewById(R.id.linearButtons);
        userNameText = view.findViewById(R.id.userName);
        userEmailText = view.findViewById(R.id.userEmail);
        userPhoneText = view.findViewById(R.id.userPhone);
        userAddressText = view.findViewById(R.id.userAddress);

        loadUserInfo();

        createButtons();
        createButtonFunctionality();

        ImageView profileImage = view.findViewById(R.id.profileImage);
        Glide.with(this)
                .load(R.drawable.user_profile)
                .circleCrop()
                .into(profileImage);


        backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });
    }

    private void loadUserInfo() {
        User currentUser = User.getInstance();
        if (currentUser != null) {
            userNameText.setText(currentUser.getName());
            userEmailText.setText(currentUser.getEmail());
            userPhoneText.setText(currentUser.getPhoneNumber());
            userAddressText.setText(currentUser.getAddress());
        }
    }

    private void createButtons() {

        viewHistoryButton = createButton(getString(R.string.ver_historial));
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        );
        buttonParams.setMarginEnd(dpToPx(8));
        viewHistoryButton.setLayoutParams(buttonParams);


        editProfileButton = createButton(getString(R.string.edit_profile));
        LinearLayout.LayoutParams button2Params = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        );
        button2Params.setMarginStart(dpToPx(8));
        editProfileButton.setLayoutParams(button2Params);

        linearButtons.addView(viewHistoryButton);
        linearButtons.addView(editProfileButton);
    }

    private Button createButton(String text) {
        Button button = new Button(getContext());
        button.setText(text);
        button.setTextColor(getResources().getColor(android.R.color.white));
        button.setTextSize(15);
        button.setBackgroundResource(R.drawable.rounded_button);
        return button;
    }

    private void createButtonFunctionality() {
        viewHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ver historial", Toast.LENGTH_SHORT).show();
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEditFragment userEditFragment = UserEditFragment.newInstance();
                ((MainActivity) requireActivity()).replaceFragment(userEditFragment);
            }
        });
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}