/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;
import POJOs.Producto;
import POJOs.Venta;
import java.math.BigDecimal;
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
public class BeanDetalleVenta {
    private Integer idDetalleVenta;
    private Integer producto;
    private Integer venta;
    private Integer cantidad;
    private BigDecimal monto;
    
    private List listaDetalleVenta;
    
    @PostConstruct
    public void mostrar(){
        setListaDetalleVenta(CRUDs.CRUDDetalle_venta.universo());
    }
    
    public void limpiar(){
        setIdDetalleVenta(null);
        setProducto(null);
        setVenta(null);
        setCantidad(null);
        setMonto(null);
    }
    
    public void insertar(){
        FacesContext context=FacesContext.getCurrentInstance();
        try{
            boolean flag=CRUDs.CRUDDetalle_venta.insert(producto, venta, cantidad, monto);
            if(flag){
                mostrar();
                limpiar();
                context.addMessage(null, new FacesMessage("Exito","Cliente ingresado"));
            }else{
                context.addMessage(null, new FacesMessage("Error","Revise que no haya sido ingresado antes"));
            }
        }catch(Exception e){
            context.addMessage(null, new FacesMessage("Error","Error"+e));
            System.out.println("error ="+e);
        }
    }
   
    public void eliminar(){
        FacesContext context=FacesContext.getCurrentInstance();
        try{
            boolean flag=CRUDs.CRUDDetalle_venta.eliminar(getIdDetalleVenta());
            if(flag){
                mostrar();
                limpiar(); 
                context.addMessage(null, new FacesMessage("Exito","Reguistro eliminado"));
            }else{
                context.addMessage(null, new FacesMessage("Error","Revise que no los datos ingresados sean correctos"));
            }
        }catch(Exception e){
            context.addMessage(null, new FacesMessage("Error","Error"+e));
            System.out.println("error ="+e);
        }
    }
    
//    Se extrae lo de la tabla y llena la informacion de cada insert en la tabla
    public void selecionarDatos(POJOs.DetalleVenta dtventa){
        setIdDetalleVenta(dtventa.getIdDetalleVenta());
        Producto produ = dtventa.getProducto();
        Venta venta = dtventa.getVenta();
        setProducto(produ.getIdProducto());
        setVenta(venta.getIdVenta());
        setCantidad(dtventa.getCantidad());
        setMonto(dtventa.getMonto());
    }
    
    /**
     * @return the idDetalleVenta
     */
    public Integer getIdDetalleVenta() {
        return idDetalleVenta;
    }

    /**
     * @param idDetalleVenta the idDetalleVenta to set
     */
    public void setIdDetalleVenta(Integer idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    /**
     * @return the producto
     */
    public Integer getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Integer producto) {
        this.producto = producto;
    }

    /**
     * @return the venta
     */
    public Integer getVenta() {
        return venta;
    }

    /**
     * @param venta the venta to set
     */
    public void setVenta(Integer venta) {
        this.venta = venta;
    }

    /**
     * @return the cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * @return the listaDetalleVenta
     */
    public List getListaDetalleVenta() {
        return listaDetalleVenta;
    }

    /**
     * @param listaDetalleVenta the listaDetalleVenta to set
     */
    public void setListaDetalleVenta(List listaDetalleVenta) {
        this.listaDetalleVenta = listaDetalleVenta;
    }
     
}
