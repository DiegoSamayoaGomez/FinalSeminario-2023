/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scope;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Pit
 */
@ManagedBean
@ViewScoped
public class SessionBean {
    private POJOs.Usuario usuario=null;
    private String usuarioSession="";
    private String mensaje=null;

    /**
     * @return the usuario
     */
    public POJOs.Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(POJOs.Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the usuarioSession
     */
    public String getUsuarioSession() {
        return usuarioSession;
    }

    /**
     * @param usuarioSession the usuarioSession to set
     */
    public void setUsuarioSession(String usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
