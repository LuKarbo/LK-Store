package ar.edu.davinci.carbone_lucas.lk_store.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.Support;

public class SupportAdapter extends RecyclerView.Adapter<SupportAdapter.ViewHolder> {
    private List<Support> supportList;
    private boolean isAnswered;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public SupportAdapter(List<Support> supportList, boolean isAnswered) {
        this.supportList = supportList;
        this.isAnswered = isAnswered;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_support, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Support support = supportList.get(position);

        holder.tvId.setText("ID: " + support.getId());
        holder.tvFecha.setText("Fecha: " + dateFormat.format(support.getFecha()));
        holder.tvConsulta.setText("Consulta: " + support.getConsulta());

        if (isAnswered) {
            holder.tvRespuesta.setVisibility(View.VISIBLE);
            holder.tvFechaRespuesta.setVisibility(View.VISIBLE);
            holder.tvRespuesta.setText("Respuesta: " + support.getRespuesta());
            holder.tvFechaRespuesta.setText("Fecha respuesta: " +
                    dateFormat.format(support.getFechaRespuesta()));
        } else {
            holder.tvRespuesta.setVisibility(View.GONE);
            holder.tvFechaRespuesta.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return supportList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvFecha, tvConsulta, tvRespuesta, tvFechaRespuesta;

        ViewHolder(View view) {
            super(view);
            tvId = view.findViewById(R.id.tvId);
            tvFecha = view.findViewById(R.id.tvFecha);
            tvConsulta = view.findViewById(R.id.tvConsulta);
            tvRespuesta = view.findViewById(R.id.tvRespuesta);
            tvFechaRespuesta = view.findViewById(R.id.tvFechaRespuesta);
        }
    }
}
