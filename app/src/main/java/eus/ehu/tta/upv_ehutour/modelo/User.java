package eus.ehu.tta.upv_ehutour.modelo;

/**
 * Created by josu on 16/01/18.
 */

public class User {
    private int id;
    private String login;
    private String password;
    private String nombre;
    private String apellido;
    private String centroPrevio;
    private String telefono;

    public User(String login, String password, String nombre, String apellido, String centroPrevio, String telefono){
        this.login=login;
        this.password=password;
        this.nombre=nombre;
        this.apellido=apellido;
        this.centroPrevio=centroPrevio;
        this.telefono=telefono;

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCentroPrevio() {
        return centroPrevio;
    }

    public void setCentroPrevio(String centroPrevio) {
        this.centroPrevio = centroPrevio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
