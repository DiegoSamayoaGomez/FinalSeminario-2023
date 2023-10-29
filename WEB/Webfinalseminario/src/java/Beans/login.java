/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Pit
 */
@ManagedBean
@ViewScoped
public class login {
    private String nombre;
    private String contra;
    private List usuarios;

    //@PostConstruct

    public void validar() {
        boolean flag = false;
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            for(int i=0;i<CRUDs.CRUDUsuario.universo().size();i++){
                if(nombre.equals(CRUDs.CRUDUsuario.universo().get(i).getNombre())){
                    if(contra.equals(CRUDs.CRUDUsuario.universo().get(i).getContrasenia())){
                        flag = true;
                    }
                }                
            }
            if (flag) {
                System.out.println("Ready");
                FacesContext.getCurrentInstance().getExternalContext().redirect("Paginas/menu.xhtml");
                context.addMessage(null, new FacesMessage("Exito", "Redigir"));
            } else {
                context.addMessage(null, new FacesMessage("Error", "Credenciales invalidas"));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error" + e));
            System.out.println("error =" + e);
        }
    }

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
}
