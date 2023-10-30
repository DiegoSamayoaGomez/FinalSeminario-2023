/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Scope.SessionBean;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Pit
 */
@ManagedBean
@ViewScoped
public class login {
    @ManagedProperty(value="#{sessionBean}")
    private SessionBean sessionBean = null;
    private String contrasena = null;

    private String nombre;
    private String contra;
    private List usuarios;

    @PostConstruct
    public void init(){
        getSessionBean().setUsuario(null);
        getSessionBean().setUsuarioSession("");
        getSessionBean().setMensaje("");
    }
    
    public String irPrincipal() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        if(contrasena.equals("") || getSessionBean().getUsuarioSession().equals("")){
            getSessionBean().setMensaje("");
            return null;
        }else{
            POJOs.Usuario usuarioSelect=CRUDs.CRUDUsuario.select(getSessionBean().getUsuarioSession());
            getSessionBean().setUsuario(usuarioSelect);
            if(usuarioSelect==null){
                getSessionBean().setUsuarioSession("");
                setContrasena("");
                context.addMessage(null, new FacesMessage("Error", "El usuario no existe"));
                return "index";
            }else if(usuarioSelect.getContrasenia().equals(contrasena)){
                getSessionBean().setUsuarioSession("");
                context.getExternalContext().redirect("Paginas/menu.xhtml");
                context.addMessage(null, new FacesMessage("Exito", "Bienvenido"));
                return null;
            }else{
                setContrasena("");
                getSessionBean().setMensaje("");
                context.addMessage(null, new FacesMessage("Error", "Usuario / Contraseña incorrecta"));
                return "index";
            }
        }
    }
    public void cancelarLogin() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().redirect("../");
        setSessionBean(null);
    }
    /*
    public void validar() {
        boolean flag = false;
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            for (int i = 0; i < CRUDs.CRUDUsuario.universo().size(); i++) {
                if (nombre.equals(CRUDs.CRUDUsuario.universo().get(i).getNombre())) {
                    if (contra.equals(CRUDs.CRUDUsuario.universo().get(i).getContrasenia())) {
                        flag = true;
                        System.out.println("ID DE USUARIO:" + CRUDs.CRUDUsuario.universo().get(i).getIdUsuario());
                    }
                }
            }
            if (flag) {
                context.addMessage(null, new FacesMessage("Exito", "Redigir"));
                System.out.println("Ready");
                FacesContext.getCurrentInstance().getExternalContext().redirect("Paginas/menu.xhtml");

            } else {
                context.addMessage(null, new FacesMessage("Error", "Credenciales invalidas"));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error" + e));
            System.out.println("error =" + e);
        }
    }
    *

    /**
     * @return the usuarios
     */
    public List getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the contra
     */
    public String getContra() {
        return contra;
    }

    /**
     * @param contra the contra to set
     */
    public void setContra(String contra) {
        this.contra = contra;
    }

    /**
     * @return the sessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
