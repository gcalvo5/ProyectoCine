package com.example.testdrawerlayout;

import java.io.Serializable;

public class UsuariosDataModel implements Serializable {
    private String id;
    private String nombre;
    private String correo;
    private String contrasena;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getConstrasena() {
        return contrasena;
    }

    public void setContrasena(String constrasena) {
        this.contrasena = constrasena;
    }

    public UsuariosDataModel(){

    }

}
