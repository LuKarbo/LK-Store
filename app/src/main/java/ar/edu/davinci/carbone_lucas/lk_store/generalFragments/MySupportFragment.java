package ar.edu.davinci.carbone_lucas.lk_store.generalFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.Adapters.SupportAdapter;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.SupportController;
import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.Support;


public class MySupportFragment extends Fragment {
    private RecyclerView rvAnswered, rvPending;
    private TextView tvNoAnswered, tvNoPending;
    private List<Support> answeredList, pendingList;

    public static MySupportFragment newInstance() {
        return new MySupportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_support, container, false);

        rvAnswered = view.findViewById(R.id.rvAnswered);
        rvPending = view.findViewById(R.id.rvPending);
        tvNoAnswered = view.findViewById(R.id.tvNoAnswered);
        tvNoPending = view.findViewById(R.id.tvNoPending);

        rvAnswered.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPending.setLayoutManager(new LinearLayoutManager(getContext()));

        loadSupports();

        return view;
    }

    private void loadSupports() {
        // Obtener consultas y separarlas
        SupportController supportController = new SupportController();
        List<Support> allSupports = supportController.getMisConsultas();
        answeredList = new ArrayList<>();
        pendingList = new ArrayList<>();

        for (Support support : allSupports) {
            if (support.getRespuesta() != null && !support.getRespuesta().isEmpty()) {
                answeredList.add(support);
            } else {
                pendingList.add(support);
            }
        }

        updateUI();
    }

    private void updateUI() {
        // Mostrar/ocultar mensajes de "no hay consultas"
        tvNoAnswered.setVisibility(answeredList.isEmpty() ? View.VISIBLE : View.GONE);
        tvNoPending.setVisibility(pendingList.isEmpty() ? View.VISIBLE : View.GONE);

        rvAnswered.setAdapter(new SupportAdapter(answeredList, true));
        rvPending.setAdapter(new SupportAdapter(pendingList, false));
    }
}