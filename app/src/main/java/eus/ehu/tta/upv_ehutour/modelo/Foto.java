package eus.ehu.tta.upv_ehutour.modelo;

/**
 * Created by josu on 16/01/18.
 */

public class Foto {
    private int id;
    private String nombre;
    private String timestamp;
    private String usuario;

    public Foto(String nombre,String timestamp, String usuario)
    {
        this.nombre=nombre;
        this.timestamp=timestamp;
        this.usuario=usuario;

    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
