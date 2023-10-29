package Beans;

import CRUDs.CRUDCompraDetalle;
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
public class BeanCompra {
    //Compra

    private Integer idProveedor;
    private Integer idTipoPago;
    //Compra Detalle
    private Integer idCompra;
    private Integer idProducto;
    private Integer cantidad = 0;
    private BigDecimal monto = new BigDecimal(0);
    private List ListaProveedor;
    private List listaFormaPago;
    private List listaProducto;
    private boolean componentes = false;
    private ArrayList<listaCompraDetalle> listaCompraDetalle = new ArrayList<listaCompraDetalle>();
    private BigDecimal montoTotal;
    private BigDecimal totalFactura;
    //Reportes
    private List listaReporteCompra;

    //Funciones
    //Carga automática de funciones al abrir la vista
    @PostConstruct //carga los items 
    public void inicio() {
        llenarComboProveedor();
        llenarComboFormaPago();
        llenarComboProducto();
        mostrar();
        mostrarDetalle();
    }

    //Compra (Encabezado)
    //Dropdown list para obtener todos los proveedores registrados
    public List<SelectItem> llenarComboProveedor() {
        setListaProveedor(new ArrayList<SelectItem>());
        List<POJOs.Proveedor> lstProveedor = CRUDs.CRUDProveedor.universo();
        for (POJOs.Proveedor proveedor : lstProveedor) {

            SelectItem clienteItem = new SelectItem(proveedor.getIdProveedor(), proveedor.getNombre());
            getListaProveedor().add(clienteItem);
        }
        return getListaProveedor();
    }

    //Dropdown list para obtener todos los tipos de pago registrados
    public List<SelectItem> llenarComboFormaPago() {
        setListaFormaPago(new ArrayList<SelectItem>());
        List<POJOs.TipoPago> lstFormaPago = CRUDs.CRUDFormaPago.universo();
        for (POJOs.TipoPago formaPago : lstFormaPago) {

            SelectItem formaPagoItem = new SelectItem(formaPago.getIdTipoPago(), formaPago.getNombre());
            getListaFormaPago().add(formaPagoItem);
        }
        return getListaProveedor();
    }

    //Insertar venta (bloquear encabezado con cliente y tipo de pago)
    public void insertar() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(1);
            //boolean flag = CRUDs.CRUDCompra.insert(idProveedor, idTipoPago, 1);
            boolean flag = CRUDs.CRUDCompra.insert(idProveedor, idTipoPago, 1);
            if (flag) {

                componentes = true;
                context.addMessage(null, new FacesMessage("Exito", "Compra Ingresada"));
                mostrar();

            } else {
                context.addMessage(null, new FacesMessage("Error", "Compra fallida"));

            }

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error"));
            System.out.println("Error = " + e);

        }

    }

    public List<SelectItem> llenarComboProducto() {
        setListaProducto(new ArrayList<SelectItem>());
        List<POJOs.Producto> lstProducto = CRUDs.CRUDProducto.universo();
        for (POJOs.Producto producto : lstProducto) {

            SelectItem productoItem = new SelectItem(producto.getIdProducto(), producto.getNombre());
            getListaProducto().add(productoItem);
        }
        return getListaProducto();
    }

    public void mostrarDetalle() {
        totalCompra();
        tablaDetalle();
    }

    public void mostrar() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        //idVenta = CRUDs.CRUDVenta.select(usuario).getIdVenta();
        idCompra = CRUDs.CRUDCompra.select(usuario).getIdCompra();

        System.out.println("id compra " + idCompra);

        if (idCompra != 0) {
            idProveedor = CRUDs.CRUDCompra.select(usuario).getProveedor().getIdProveedor();
            //idCliente = CRUDs.CRUDVenta.select(usuario).getCliente().getIdCliente();
            idTipoPago = CRUDs.CRUDCompra.select(usuario).getTipoPago().getIdTipoPago();
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
            //System.out.println("id producto+" + idProducto + " cantidad=" + cantidad + " monto=" + monto);
            boolean flag = CRUDs.CRUDCompraDetalle.insert(idProducto, idCompra, cantidad, monto);
//boolean flag = CRUDs.CRUDVentaDetalle.insert(idProducto, idVenta, cantidad, monto);
            //POR SI FALLA: CAMBIAR EL ORDEN DESDE CRUDS AL DE ABAJO
            //boolean flag = CRUDs.CRUDVentaDetalle.insert(idVenta, idProducto, cantidad, monto);
            if (flag) {
                limpiarDetalle();
                mostrarDetalle();
                componentes = true;
                context.addMessage(null, new FacesMessage("Exito", "Compra Ingresada"));

            } else {
                context.addMessage(null, new FacesMessage("Error", "Compra fallida"));

            }

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error"));
            System.out.println("Error = " + e);

        }

    }

    public void seleccionarDatos(listaCompraDetalle compraDetalle) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            //System.out.println("venta detalle = " + ventaDetalle);
            //boolean flag = CRUDs.CRUDVentaDetalle.eliminar(ventaDetalle.getIdVentaDetalle());
            //boolean flag = CRUDs.CRUDVentaDetalle.eliminar(ventaDetalle.getIdDetalleVenta());
            boolean flag = CRUDs.CRUDCompraDetalle.eliminar(compraDetalle.getIdDetalleCompra());
            if (flag) {
                mostrarDetalle();
                limpiarDetalle();
                context.addMessage(null, new FacesMessage("Exito", "Detalle eliminado"));

            } else {
                context.addMessage(null, new FacesMessage("Error", "Detalle eliminado fallida"));

            }

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error" + e));

        }

    }

    public void totalCompra() {
        try {
            //for (Iterator it = CRUDVentaDetalle.selectMontoTotalVenta(idVenta).iterator(); it.hasNext();) {
            for (Iterator it = CRUDCompraDetalle.selectMontoTotalCompra(idCompra).iterator(); it.hasNext();) {
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
        setIdProveedor(null);
        setIdTipoPago(null);
    }

    public void cerrarCompra() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {

            boolean flag = CRUDs.CRUDCompra.update(idCompra);

            if (flag) {
                reporteCompra();
                limpiarDetalle();
                mostrarDetalle();
                limpiarEncabezado();
                mostrar();
                tablaDetalle();
                setTotalFactura(null);
                BigDecimal totalFactura = new BigDecimal(0);
                setTotalFactura(totalFactura);
                context.addMessage(null, new FacesMessage("Exito", "Compra finalizada"));

            } else {
                context.addMessage(null, new FacesMessage("Error", "Compra no cerrada"));

            }

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error", "Error"));
            System.out.println("Error = " + e);

        }

    }

    public void tablaDetalle() {
        BigDecimal total, pr, cant2;
        Integer cant;
        listaCompraDetalle.clear();
        for (Iterator it = CRUDs.CRUDCompraDetalle.universo(idCompra).iterator(); it.hasNext();) {
            //for (Iterator it = CRUDs.CRUDVentaDetalle.universo(idVenta).iterator(); it.hasNext();) {
            Object[] item = (Object[]) it.next();
            pr = (BigDecimal) item[3];
            cant = (Integer) item[2];
            cant2 = new BigDecimal(cant);
            total = cant2.multiply(pr);
            getListaCompraDetalle().add(new listaCompraDetalle((Integer) item[0], (String) item[1], (Integer) item[2], (BigDecimal) item[3], total));

        }

    }

//Reportes
    public void reporteCompra() throws IOException, JRException, ParseException {
        try {
            //Integer estado = CRUDs.CRUDVentaDetalle.reporteVenta(idVenta).size();
            Integer estado = CRUDs.CRUDCompraDetalle.reporteCompra(idCompra).size();
            if (estado != 0) {
                reportefinalseminario.ReporteFinalSeminario.reporteCompra(idCompra);

//    reportefinalseminario.ReporteFinalSeminario.reporteVenta(idVenta);
//                setListaReporteVenta(factory.reporteVenta());
                setListaReporteCompra(factory.reporteCompra());
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(getListaReporteCompra());
                File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("Reportes/reporteCompra.jasper"));
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

//Setter y Getter
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
     * @return the idCompra
     */
    public Integer getIdCompra() {
        return idCompra;
    }

    /**
     * @param idCompra the idCompra to set
     */
    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
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
     * @return the ListaProveedor
     */
    public List getListaProveedor() {
        return ListaProveedor;
    }

    /**
     * @param ListaProveedor the ListaProveedor to set
     */
    public void setListaProveedor(List ListaProveedor) {
        this.ListaProveedor = ListaProveedor;
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
     * @return the listaCompraDetalle
     */
    public ArrayList<listaCompraDetalle> getListaCompraDetalle() {
        return listaCompraDetalle;
    }

    /**
     * @param listaCompraDetalle the listaCompraDetalle to set
     */
    public void setListaCompraDetalle(ArrayList<listaCompraDetalle> listaCompraDetalle) {
        this.listaCompraDetalle = listaCompraDetalle;
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
     * @return the listaReporteCompra
     */
    public List getListaReporteCompra() {
        return listaReporteCompra;
    }

    /**
     * @param listaReporteCompra the listaReporteCompra to set
     */
    public void setListaReporteCompra(List listaReporteCompra) {
        this.listaReporteCompra = listaReporteCompra;
    }

}
