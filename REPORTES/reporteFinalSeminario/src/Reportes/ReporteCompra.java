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
public class ReporteCompra {

    private Integer noFactura;
    private String nombreProveedor;
    private Date fecha;
    private String formaPago;
    private String producto;
    private Integer cantidad;
    private BigDecimal precio;
    private BigDecimal total;
    private String usuarioCompra;

    public ReporteCompra(Integer noFactura, String nombreProveedor, Date fecha, String tipoPago, String producto, Integer cantidad, BigDecimal precio, BigDecimal total, String usuarioCompra) {
        this.noFactura = noFactura;
        this.nombreProveedor = nombreProveedor;
        this.fecha = fecha;
        this.formaPago = tipoPago;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
        this.usuarioCompra = usuarioCompra;
    }

    /**
     * @return the noFactura
     */
    public Integer getNoFactura() {
        return noFactura;
    }

    /**
     * @param noFactura the noFactura to set
     */
    public void setNoFactura(Integer noFactura) {
        this.noFactura = noFactura;
    }

    /**
     * @return the nombreProveedor
     */
    public String getNombreProveedor() {
        return nombreProveedor;
    }

    /**
     * @param nombreProveedor the nombreProveedor to set
     */
    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
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
     * @return the tipoPago
     */
    public String getFormaPago() {
        return formaPago;
    }

    /**
     * @param formaPago the tipoPago to set
     */
    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
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

    /**
     * @return the usuarioCompra
     */
    public String getUsuarioCompra() {
        return usuarioCompra;
    }

    /**
     * @param usuarioCompra the usuarioCompra to set
     */
    public void setUsuarioCompra(String usuarioCompra) {
        this.usuarioCompra = usuarioCompra;
    }
    
    

}
