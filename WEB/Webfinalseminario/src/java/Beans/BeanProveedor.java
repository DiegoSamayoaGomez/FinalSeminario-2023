/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Samayoa
 */
@ManagedBean
@ViewScoped
public class BeanProveedor {
    private Integer idProveedor;
    private String nombre;
    private String direccion;
    private String telefono;
    private List listaProovedores;
    
    @PostConstruct
    public void mostrar(){
        setListaProovedores(CRUDs.CRUDProveedor.universo());
    }
    
    public void prueba() {
        System.out.println("Prueba usuario: " + getNombre()+ " dirección:" + getDireccion() +" telefono: "+ getTelefono());
    }

    public void limpiar(){
        setNombre("");
        setDireccion("");
        setTelefono("");        
    }
    
    public void insertar2(){
        FacesContext context=FacesContext.getCurrentInstance();
        try{
            boolean flag=CRUDs.CRUDProveedor.insert(nombre, direccion, telefono, 1);
            if (flag) {
                context.addMessage(null, new FacesMessage("Exito", "Registro Ingresado"));
                mostrar();
                limpiar();
            } else {
                context.addMessage(null, new FacesMessage("Error", "Registro fallido"));

            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error"));
            System.out.println("Error = " + e);

        }
    }
    
        public void modificar() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {

            boolean flag = CRUDs.CRUDProveedor.update(idProveedor, nombre, direccion, telefono, 1);
            if (flag) {
                mostrar();
                context.addMessage(null, new FacesMessage("Exito", "Registro modificado"));
                limpiar();
            } else {
                context.addMessage(null, new FacesMessage("Error", "Registro fallido"));

            }

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error"));
            System.out.println("Error = " + e);

        }

    }
    
    public void anular() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {

            boolean flag = CRUDs.CRUDProveedor.anular(idProveedor, 1);
            if (flag) {
                mostrar();
                context.addMessage(null, new FacesMessage("Exito", "Registro anulado"));
                limpiar();
            } else {
                context.addMessage(null, new FacesMessage("Error", "Anulación fallida"));

            }

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error"));
            System.out.println("Error = " + e);

        }

    }
    
//    Se extrae lo de la tabla y llena la informacion de cada insert en la tabla
    public void selecionarDatos(POJOs.Proveedor proveedor){
        setIdProveedor(proveedor.getIdProveedor());
        setNombre(proveedor.getNombre());
        setDireccion(proveedor.getDireccion());
        setTelefono(proveedor.getTelefono());
    }
 

    /**
     * @return the idProveedor
     */
    public Integer getIdProveedor() {
        return idProveedor;
    }

    /**
     * @param idProveedor the idProveedor to set
     */
    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
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
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the listaProovedores
     */
    public List getListaProovedores() {
        return listaProovedores;
    }

    /**
     * @param listaProovedores the listaProovedores to set
     */
    public void setListaProovedores(List listaProovedores) {
        this.listaProovedores = listaProovedores;
    }
}
