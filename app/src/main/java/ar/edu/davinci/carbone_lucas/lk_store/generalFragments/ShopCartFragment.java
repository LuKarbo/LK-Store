package ar.edu.davinci.carbone_lucas.lk_store.generalFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ar.edu.davinci.carbone_lucas.lk_store.R;

public class ShopCartFragment extends Fragment {

    private TextView fragmentTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentTitle = view.findViewById(R.id.FragmentTitle);
        fragmentTitle.setText("Mi Carrito");

    }
}
