/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.math.BigDecimal;

/**
 *
 * @author Samayoa
 */
public class listaVentaDetalle {

    private Integer idDetalleVenta;
    private String nombre; //Producto
    private Integer cantidad;
    private BigDecimal precio;
    private BigDecimal total;

    public listaVentaDetalle(Integer idDetalleVenta, String nombre, Integer cantidad, BigDecimal precio, BigDecimal total) {
        this.idDetalleVenta = idDetalleVenta;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
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
     * @return the precio
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    

}
