/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Samayoa
 */
public class ReporteProducto {

    private Date fecha;
    private String producto;
    private Integer cantidad;
   // private BigDecimal precio;
    private String usuarioProducto;

    public ReporteProducto(Date fecha, String producto, Integer cantidad, String usuarioProducto) {
        this.fecha = fecha;
        this.producto = producto;
        this.cantidad = cantidad;
        this.usuarioProducto = usuarioProducto;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(String producto) {
        this.producto = producto;
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
     * @return the usuarioProducto
     */
    public String getUsuarioProducto() {
        return usuarioProducto;
    }

    /**
     * @param usuarioProducto the usuarioProducto to set
     */
    public void setUsuarioProducto(String usuarioProducto) {
        this.usuarioProducto = usuarioProducto;
    }

    
}
