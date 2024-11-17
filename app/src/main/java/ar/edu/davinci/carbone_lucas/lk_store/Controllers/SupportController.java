package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.GetSupportsApi;
import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.PostSupportAPI;
import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.PutSupportReplyApi;
import ar.edu.davinci.carbone_lucas.lk_store.models.Support;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;

public class SupportController {
    private static SupportController instance;
    private List<Support> supportList;
    private long lastUpdated;
    private static final long UPDATE_INTERVAL = 30000;

    private SupportController() {
        loadData();
    }

    public static SupportController getInstance() {
        if (instance == null || System.currentTimeMillis() - instance.lastUpdated > UPDATE_INTERVAL) {
            instance = new SupportController();
        }
        return instance;
    }

    private void loadData() {
        GetSupportsApi supportsTask = new GetSupportsApi();

        try {
            supportList = supportsTask.execute().get();
            lastUpdated = System.currentTimeMillis();
        } catch (Exception e) {
            Log.e("SupportController", "Error loading support data: " + e.getMessage());
        }
    }

    public List<Support> getConsultas() {
        return supportList;
    }

    public List<Support> getMisConsultas() {
        Log.i("Consultas: ", supportList.toString());
        String userId = User.getInstance().getUserId();
        return supportList.stream()
                .filter(support -> support.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public void responderConsulta(String id, String respuesta) {
        PutSupportReplyApi PutSupportReplyApi = new PutSupportReplyApi();
        PutSupportReplyApi.execute(id, respuesta);
    }

    public void enviarConsulta(String userId, String email, String consulta) {
        Support support = new Support(null, userId, email, consulta);
        PostSupportAPI postSupportAPI = new PostSupportAPI();
        try {
            postSupportAPI.execute(support).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}