/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import CRUDs.CRUDVentaDetalle;
import POJOs.Usuario;
import finalseminario.Finalseminario;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import reportefinalseminario.factory;

/**
 *
 * @author Samayoa
 */
@ManagedBean //usados para conectar con el backend
@ViewScoped //usados para conectar con el backend
public class BeanVenta {

    //Venta
    private Integer idCliente;
    private Integer idTipoPago;
    //Venta Detalle
    private Integer idVenta;
    private Integer idProducto;
    private Integer cantidad = 0;
    private BigDecimal monto = new BigDecimal(0);
    private List ListaCliente;
    private List listaFormaPago;
    private List listaProducto;
    private boolean componentes = false;
    private ArrayList<listaVentaDetalle> listaVentaDetalle = new ArrayList<listaVentaDetalle>();
    private BigDecimal montoTotal;
    private BigDecimal totalFactura;
    //Reportes
    private List listaReporteVenta;

/////////////////////CODIGO PROPIO//////////////
    //Funciones
    //Carga automática de funciones al abrir la vista
    @PostConstruct //carga los items 
    public void inicio() {
        llenarComboCliente();
        llenarComboFormaPago();
        llenarComboProducto();
        mostrar();
        mostrarDetalle();
    }

    //Venta (Encabezado)
    //Dropdown list para obtener todos los clientes registrados
    public List<SelectItem> llenarComboCliente() {
        setListaCliente(new ArrayList<SelectItem>());
        List<POJOs.Cliente> lstCliente = CRUDs.CRUDCliente.universo();
        for (POJOs.Cliente cliente : lstCliente) {

            SelectItem clienteItem = new SelectItem(cliente.getIdCliente(), cliente.getNombre() + " " + cliente.getApellido());
            getListaCliente().add(clienteItem);
        }
        return getListaCliente();
    }

    //Dropdown list para obtener todos los tipos de pago registrados
    public List<SelectItem> llenarComboFormaPago() {
        setListaFormaPago(new ArrayList<SelectItem>());
        List<POJOs.TipoPago> lstFormaPago = CRUDs.CRUDFormaPago.universo();
        for (POJOs.TipoPago formaPago : lstFormaPago) {

            SelectItem formaPagoItem = new SelectItem(formaPago.getIdTipoPago(), formaPago.getNombre());
            getListaFormaPago().add(formaPagoItem);
        }
        return getListaCliente();
    }

    //Insertar venta (bloquear encabezado con cliente y tipo de pago)
    public void insertar() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(1);
            boolean flag = CRUDs.CRUDVenta.insert(idCliente, idTipoPago, 1);
            if (flag) {

                componentes = true;
                context.addMessage(null, new FacesMessage("Exito", "Venta Ingresada"));
                mostrar();

            } else {
                context.addMessage(null, new FacesMessage("Error", "Venta fallida"));

            }

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error"));
            System.out.println("Error = " + e);

        }

    }

    //Fin venta (Encabezado)
    //Detalle venta
    //Dropdown list para obtener todos los productos registrados
    public List<SelectItem> llenarComboProducto() {
        setListaProducto(new ArrayList<SelectItem>());
        List<POJOs.Producto> lstProducto = CRUDs.CRUDProducto.universo();
        for (POJOs.Producto producto : lstProducto) {

            SelectItem productoItem = new SelectItem(producto.getIdProducto(), producto.getNombre());
            getListaProducto().add(productoItem);
        }
        return getListaProducto();
    }

    //Fin detalle venta
    //Tabla con productos seleccionados
    //Mostrar detalles de la tabla 
    public void mostrarDetalle() {
        totalVenta();
        tablaDetalle();
    }
//Obtener el ID de venta e información del encabezado

    public void mostrar() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        idVenta = CRUDs.CRUDVenta.select(usuario).getIdVenta();
        System.out.println("id venta " + idVenta);

        if (idVenta != 0) {
            idCliente = CRUDs.CRUDVenta.select(usuario).getCliente().getIdCliente();
            idTipoPago = CRUDs.CRUDVenta.select(usuario).getTipoPago().getIdTipoPago();
            componentes = true;
            mostrarDetalle();
        } else {
            componentes = false;
        }
    }

    public void precio() {
        //System.out.println("Precio" + CRUDs.CRUDProducto.select(idProducto).getPrecio());
        setMonto(CRUDs.CRUDProducto.select(idProducto).getPrecio());
        BigDecimal cant = new BigDecimal(cantidad);
        setMontoTotal(monto.multiply(cant));
    }

    public void insertarDetalle() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            System.out.println("id producto+" + idProducto + " cantidad=" + cantidad + " monto=" + monto);
            boolean flag = CRUDs.CRUDVentaDetalle.insert(idProducto, idVenta, cantidad, monto);
            //POR SI FALLA: CAMBIAR EL ORDEN DESDE CRUDS AL DE ABAJO
            //boolean flag = CRUDs.CRUDVentaDetalle.insert(idVenta, idProducto, cantidad, monto);

            if (flag) {
                limpiarDetalle();
                mostrarDetalle();
                componentes = true;
                context.addMessage(null, new FacesMessage("Exito", "Venta Ingresada"));

            } else {
                context.addMessage(null, new FacesMessage("Error", "Venta fallida"));

            }

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error"));
            System.out.println("Error = " + e);

        }

    }

    public void seleccionarDatos(listaVentaDetalle ventaDetalle) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            //System.out.println("venta detalle = " + ventaDetalle);
            //boolean flag = CRUDs.CRUDVentaDetalle.eliminar(ventaDetalle.getIdVentaDetalle());
            boolean flag = CRUDs.CRUDVentaDetalle.eliminar(ventaDetalle.getIdDetalleVenta());

            if (flag) {
                mostrarDetalle();
                limpiarDetalle();
                context.addMessage(null, new FacesMessage("Exito", "Detalle eliminado"));

            } else {
                context.addMessage(null, new FacesMessage("Error", "Detalle eliminado fallida"));

            }

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error ", "Error" + e));

        }

    }

    public void totalVenta() {
        try {
            for (Iterator it = CRUDVentaDetalle.selectMontoTotalVenta(idVenta).iterator(); it.hasNext();) {
                BigDecimal item = (BigDecimal) it.next();
                setTotalFactura(item);
                System.out.println("Monto: " + item);
            }

        } catch (ParseException ex) {
            //Logger.getLogger(Finalseminario.)
            Logger.getLogger(Finalseminario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void limpiarDetalle() {
        setIdProducto(null);
        setCantidad(0);
        BigDecimal cero = new BigDecimal(0);
        setMonto(cero);
    }

    public void montoTotal() {
        BigDecimal cant = new BigDecimal(cantidad);
        setMontoTotal(monto.multiply(cant));
    }

    public void limpiarEncabezado() {
        setIdCliente(null);
        setIdTipoPago(null);
    }

    public void cerrarVenta() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            //System.out.println("id producto+" + idProducto + " cantidad=" + cantidad + " monto=" + monto);
            boolean flag = CRUDs.CRUDVenta.update(idVenta);

            //boolean flag = CRUDs.CRUDVenta.update(idVenta);
            if (flag) {
                reporteVenta();
                limpiarDetalle();
                mostrarDetalle();
                limpiarEncabezado();
                mostrar();
                tablaDetalle();
                setTotalFactura(null);
                BigDecimal totalFactura = new BigDecimal(0);
                setTotalFactura(totalFactura);
                context.addMessage(null, new FacesMessage("Exito", "Venta finalizada"));

            } else {
                context.addMessage(null, new FacesMessage("Error", "Venta no cerrada"));

            }

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error"));
            System.out.println("Error = " + e);

        }

    }

    public void tablaDetalle() {
        BigDecimal total, pr, cant2;
        Integer cant;
        listaVentaDetalle.clear();
        for (Iterator it = CRUDs.CRUDVentaDetalle.universo(idVenta).iterator(); it.hasNext();) {
            //for (Iterator it = CRUDs.CRUDVentaDetalle.universo(idVenta).iterator(); it.hasNext();) {
            Object[] item = (Object[]) it.next();
            pr = (BigDecimal) item[3];
            cant = (Integer) item[2];
            cant2 = new BigDecimal(cant);
            total = cant2.multiply(pr);
            getListaVentaDetalle().add(new listaVentaDetalle((Integer) item[0], (String) item[1], (Integer) item[2], (BigDecimal) item[3], total));

        }

    }

    //Reportes (Falta)
    //Rerpote venta
    //Rerpote venta
    public void reporteVenta() throws IOException, JRException, ParseException {
        try {
            Integer estado = CRUDs.CRUDVentaDetalle.reporteVenta(idVenta).size();
            //Integer estado = CRUDs.CRUDVentaDetalle.reporteVenta(idVenta).size();
            if (estado != 0) {
                reportefinalseminario.ReporteFinalSeminario.reporteVenta(idVenta);
                setListaReporteVenta(factory.reporteVenta());

                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(getListaReporteVenta());
                File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("Reportes/reporteVenta.jasper"));
                byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), null, ds);
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.setContentType("application/pdf"); //Exporte de archivo en PDF
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                FacesContext.getCurrentInstance().responseComplete();

            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Error", "No existe información "));
            }
        } catch (JRException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "Error al cargar el reporte " + e));
            System.out.println("error=" + e);
        }
    }

    //Fin tabla con productos seleccionados
    //Encapsulación
    /**
     * @return the idCliente
     */
    public Integer getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * @return the idTipoPago
     */
    public Integer getIdTipoPago() {
        return idTipoPago;
    }

    /**
     * @param idTipoPago the idTipoPago to set
     */
    public void setIdTipoPago(Integer idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    /**
     * @return the idVenta
     */
    public Integer getIdVenta() {
        return idVenta;
    }

    /**
     * @param idVenta the idVenta to set
     */
    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
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
     * @return the ListaCliente
     */
    public List getListaCliente() {
        return ListaCliente;
    }

    /**
     * @param ListaCliente the ListaCliente to set
     */
    public void setListaCliente(List ListaCliente) {
        this.ListaCliente = ListaCliente;
    }

    /**
     * @return the listaFormaPago
     */
    public List getListaFormaPago() {
        return listaFormaPago;
    }

    /**
     * @param listaFormaPago the listaFormaPago to set
     */
    public void setListaFormaPago(List listaFormaPago) {
        this.listaFormaPago = listaFormaPago;
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
     * @return the componentes
     */
    public boolean isComponentes() {
        return componentes;
    }

    /**
     * @param componentes the componentes to set
     */
    public void setComponentes(boolean componentes) {
        this.componentes = componentes;
    }

    /**
     * @return the listaVentaDetalle
     */
    public ArrayList<listaVentaDetalle> getListaVentaDetalle() {
        return listaVentaDetalle;
    }

    /**
     * @param listaVentaDetalle the listaVentaDetalle to set
     */
    public void setListaVentaDetalle(ArrayList<listaVentaDetalle> listaVentaDetalle) {
        this.listaVentaDetalle = listaVentaDetalle;
    }

    /**
     * @return the montoTotal
     */
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    /**
     * @param montoTotal the montoTotal to set
     */
    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    /**
     * @return the totalFactura
     */
    public BigDecimal getTotalFactura() {
        return totalFactura;
    }

    /**
     * @param totalFactura the totalFactura to set
     */
    public void setTotalFactura(BigDecimal totalFactura) {
        this.totalFactura = totalFactura;
    }

    /**
     * @return the listaReporteVenta
     */
    public List getListaReporteVenta() {
        return listaReporteVenta;
    }

    /**
     * @param listaReporteVenta the listaReporteVenta to set
     */
    public void setListaReporteVenta(List listaReporteVenta) {
        this.listaReporteVenta = listaReporteVenta;
    }

}
