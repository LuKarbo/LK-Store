package ar.edu.davinci.carbone_lucas.lk_store.models;

import java.util.Date;
import java.util.UUID;

public class Support {
    private String id;
    private String userId;
    private String email;
    private String consulta;
    private Date fecha;
    private String respuesta;
    private Date fechaRespuesta;

    public Support() {
        this.fecha = new Date();
        this.respuesta = "";
    }

    public Support(String id, String userId, String email, String consulta) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.consulta = consulta;
        this.fecha = new Date();
        this.respuesta = "";
    }

    public String getId() { return id; }

    public String getUserId(){return userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getConsulta() { return consulta; }
    public void setConsulta(String consulta) { this.consulta = consulta; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
        this.fechaRespuesta = new Date();
    }

    public Date getFechaRespuesta() { return fechaRespuesta; }
}
