package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.models.Support;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;

public class SupportController {
    public void enviarConsulta(String id,String userId, String email, String consulta){
        Support support = new Support(id,userId,email,consulta);

        // Envio a la api

    }

    public List<Support> getConsultas() {
        // consultar la API Support

        List<Support> support_list = new ArrayList<>();

        // TEST
        support_list.add(new Support("1", User.getInstance().getUserId(),"test2@gmail.com", "Mi consuultaaaaaa"));
        support_list.add(new Support("2", "2","test2@gmail.com", "Mi consuultaaaaaa"));
        support_list.add(new Support("3", "12314","test2@gmail.com", "Mi consuultaaaaaa"));
        support_list.add(new Support("4", "4r653456","test2@gmail.com", "Mi consuultaaaaaa"));
        support_list.add(new Support("5", User.getInstance().getUserId(),"test2@gmail.com", "Mi consuultaaaaaa"));
        support_list.add(new Support("6", User.getInstance().getUserId(),"test2@gmail.com", "Mi consuultaaaaaa"));
        support_list.add(new Support("7", User.getInstance().getUserId(),"test2@gmail.com", "Mi consuultaaaaaa"));
        support_list.add(new Support("8", User.getInstance().getUserId(),"test2@gmail.com", "Mi consuultaaaaaa"));

        return support_list;
    }

    public List<Support> getMisConsultas() {
        // consultar la API Support

        List<Support> support_list = getConsultas();

        // TEST
        List<Support> mySupportList = new ArrayList<>();
        int i = 0;
        for (Support sup: support_list) {
            if(sup.getUserId().equals(User.getInstance().getUserId())){
                if(i < 2){
                    sup.setRespuesta("Respuestaaaaaa");
                    i++;
                }
                mySupportList.add(sup);
            }
        }

        return mySupportList;
    }

    public Support getConsulta(String id){
        return getConsultas().stream()
                .filter(support -> support.getId().equals(id))
                .findFirst()
                .orElse(null);

    }

    public void responderConsulta(String id, String respuesta){
        Support sup = getConsulta(id);
        sup.setRespuesta("Respuestaaaaaa");
    }

}
