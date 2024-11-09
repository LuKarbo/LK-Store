package ar.edu.davinci.carbone_lucas.lk_store.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.UserController.UserDTO;
import ar.edu.davinci.carbone_lucas.lk_store.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<UserDTO> users;

    public UserAdapter(List<UserDTO> users) {
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserDTO user = users.get(position);

        holder.textName.setText("Nombre: " + user.getName());
        holder.textEmail.setText("Email: " + user.getEmail());
        holder.textPhone.setText("Teléfono: " + user.getPhoneNumber());
        holder.textAddress.setText("Dirección: " + user.getAddress());
        holder.textRole.setText(user.isAdmin() ? "Rol: Administrador" : "Rol: Usuario");
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textEmail, textPhone, textAddress, textRole;

        ViewHolder(View view) {
            super(view);
            textName = view.findViewById(R.id.textName);
            textEmail = view.findViewById(R.id.textEmail);
            textPhone = view.findViewById(R.id.textPhone);
            textAddress = view.findViewById(R.id.textAddress);
            textRole = view.findViewById(R.id.textRole);
        }
    }
}