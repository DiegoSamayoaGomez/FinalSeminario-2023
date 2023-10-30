/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import reportefinalseminario.factory;

/**
 *
 * @author juanl
 */
@ManagedBean
@ViewScoped
public class BeanProducto {

    private Integer idProducto;
    private String nombre;
    private Integer cantidad;
    private BigDecimal precio;
    private List listaProducto;
    private List listaReporteProducto;

    @PostConstruct
    public void mostrar() {
        setListaProducto(CRUDs.CRUDProducto.universo());
    }

    public void prueba() {
        System.out.println("prueba" + getNombre() + " cantidad" + getCantidad() + "orecui=" + getPrecio());
    }

    public void limpiar() {
        setNombre("");
        setCantidad(null);
        setPrecio(null);
    }

    public void insertar2() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            boolean flag = CRUDs.CRUDProducto.insert(getNombre(), getCantidad(), getPrecio(), 1);
            if (flag) {
                mostrar();
                limpiar();
                context.addMessage(null, new FacesMessage("Exito", "Reguistro ingresado"));
            } else {
                context.addMessage(null, new FacesMessage("Exito", "Revise que no haya sido ingresado antes"));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error" + e));
            System.out.println("error =" + e);
        }
    }

    public void modificar() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
//            unica linea que cambia con el de guardar
            boolean flag = CRUDs.CRUDProducto.update(getIdProducto(), getNombre(), getPrecio(), 1);
            if (flag) {
                mostrar();
                limpiar();
                context.addMessage(null, new FacesMessage("Exito", "Reguistro modificado"));
            } else {
                context.addMessage(null, new FacesMessage("Error", "Revise que no los datos ingresados sean correctos"));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error" + e));
            System.out.println("error =" + e);
        }
    }

    public void anular() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
//            unica linea que cambia con el de guardar
            boolean flag = CRUDs.CRUDProducto.anular(getIdProducto(), 1);
            if (flag) {
                mostrar(); //Actualiza la tabla
                limpiar(); //Si no se ejecuta, las variables se quedan en la memoria, y vuelve a hacer la ejecucion, por eso se limpia de la memoria
//                anular();
                context.addMessage(null, new FacesMessage("Exito", "Registro anulado"));
            } else {
                context.addMessage(null, new FacesMessage("Error", "Revise que no los datos ingresados sean correctos"));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error" + e));
            System.out.println("error =" + e);
        }
    }

//    Se extrae lo de la tabla y llena la informacion de cada insert en la tabla
    public void selecionarDatos(POJOs.Producto producto) {
        setIdProducto(producto.getIdProducto());
        setNombre(producto.getNombre());
        setCantidad(producto.getCantidad());
        setPrecio(producto.getPrecio());

    }

    //Reportes
    public void reporteProducto() throws IOException, JRException {
        try {
    
            setListaReporteProducto(factory.reporteProducto());

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(getListaReporteProducto());
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("Reportes/reporteProducto.jasper"));
            byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), null, ds);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf"); //Exporte de archivo en PDF
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();
            FacesContext.getCurrentInstance().responseComplete();

        } catch (JRException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "Error al cargar el reporte " + e));
            System.out.println("error=" + e);
        }
    }

    

//    -----------------------------------------------------------------------------------
    /**
     * @return the nombre
     */
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
     * @return the listaProducto
     */
    public List getListaProducto() {
        return listaProducto;
    }

    /**
     * @param listaProducto the listaProducto to set
     */
    public void setListaProducto(List listaProducto) {
        this.listaProducto = listaProducto;
    }

    /**
     * @return the idProducto
     */
    public Integer getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
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
     * @return the listaReporteProducto
     */
    public List getListaReporteProducto() {
        return listaReporteProducto;
    }

    /**
     * @param listaReporteProducto the listaReporteProducto to set
     */
    public void setListaReporteProducto(List listaReporteProducto) {
        this.listaReporteProducto = listaReporteProducto;
    }
}
