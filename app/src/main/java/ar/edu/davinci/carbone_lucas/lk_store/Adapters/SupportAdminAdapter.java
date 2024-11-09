package ar.edu.davinci.carbone_lucas.lk_store.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.Support;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.SupportController;

public class SupportAdminAdapter extends RecyclerView.Adapter<SupportAdminAdapter.ViewHolder> {

    private List<Support> supports;
    private OnResponseListener responseListener;
    private SupportController supportController;

    public interface OnResponseListener {
        void onResponse(Support support, String response);
    }

    public SupportAdminAdapter(List<Support> supports, OnResponseListener listener, SupportController controller) {
        this.supports = supports;
        this.responseListener = listener;
        this.supportController = controller;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_support_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Support support = supports.get(position);

        holder.textEmail.setText("Email: " + support.getEmail());
        holder.textConsulta.setText("Consulta: " + support.getConsulta());
        holder.textFecha.setText("Fecha: " + support.getFecha().toString());

        if (support.getRespuesta().isEmpty()) {
            holder.layoutResponder.setVisibility(View.VISIBLE);
            holder.textRespuesta.setVisibility(View.GONE);
        } else {
            holder.layoutResponder.setVisibility(View.GONE);
            holder.textRespuesta.setText("Respuesta: " + support.getRespuesta());
            holder.textRespuesta.setVisibility(View.VISIBLE);
        }

        holder.btnResponder.setOnClickListener(v -> {
            String response = holder.editRespuesta.getText().toString();
            if (!response.isEmpty()) {
                supportController.responderConsulta(support.getId(), response);

                if (responseListener != null) {
                    responseListener.onResponse(support, response);
                }

                supports.remove(position);
                notifyItemRemoved(position);

                notifyItemRangeChanged(position, supports.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return supports.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textEmail, textConsulta, textFecha, textRespuesta;
        EditText editRespuesta;
        Button btnResponder;
        View layoutResponder;

        ViewHolder(View view) {
            super(view);
            textEmail = view.findViewById(R.id.textEmail);
            textConsulta = view.findViewById(R.id.textConsulta);
            textFecha = view.findViewById(R.id.textFecha);
            textRespuesta = view.findViewById(R.id.textRespuesta);
            editRespuesta = view.findViewById(R.id.editRespuesta);
            btnResponder = view.findViewById(R.id.btnResponder);
            layoutResponder = view.findViewById(R.id.layoutResponder);
        }
    }
}