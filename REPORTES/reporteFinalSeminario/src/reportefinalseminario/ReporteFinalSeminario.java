/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportefinalseminario;

import Reportes.ReporteCompra;
import Reportes.ReporteVenta;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Samayoa
 */
public class ReporteFinalSeminario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

    public static List reporteVenta(Integer idVenta) throws ParseException {
        Integer noFactura, cantidad;
        String nombreCliente, formaPago, producto, usuario;
        Date fechaVenta;
        BigDecimal precio;
        BigDecimal total, totalFinal, cant;

        List<Reportes.ReporteVenta> list = new ArrayList<Reportes.ReporteVenta>();
        for (Iterator it = CRUDs.CRUDVentaDetalle.reporteVenta(idVenta).iterator(); it.hasNext();) {
            //for (Iterator it = CRUDs.CRUDVentaDetalle.reporteVenta(idVenta).iterator();   it.hasNext();) {
            Object[] item = (Object[]) it.next();
            noFactura = (Integer) item[0];
            nombreCliente = (String) item[1];
            fechaVenta = (Date) item[2];
            formaPago = (String) item[3];
            producto = (String) item[4];
            cantidad = (Integer) item[5];
            cant = new BigDecimal(cantidad);

            total = (BigDecimal) item[6];
            totalFinal = cant.multiply(total);

            usuario = (String) item[7];

            list.add(new ReporteVenta(noFactura, nombreCliente, fechaVenta, formaPago, producto, cantidad, total, totalFinal, usuario));

            factory comp = new factory();
            comp.setReporteVenta((ArrayList<ReporteVenta>) list);

        }
        return list;
    }

    //Reporte Compra
    public static List reporteCompra(Integer idCompra) throws ParseException {
        Integer noFactura, cantidad;
        String nombreProveedor, formaPago, producto, usuario;
        Date fechaVenta;
        BigDecimal precio;
        BigDecimal total, totalFinal, cant;

        List<Reportes.ReporteCompra> list = new ArrayList<Reportes.ReporteCompra>();
        for (Iterator it = CRUDs.CRUDCompraDetalle.reporteCompra(idCompra).iterator(); it.hasNext();) {
            //for (Iterator it = CRUDs.CRUDVentaDetalle.reporteVenta(idVenta).iterator();   it.hasNext();) {
            Object[] item = (Object[]) it.next();
            noFactura = (Integer) item[0];
            nombreProveedor = (String) item[1];
            fechaVenta = (Date) item[2];
            formaPago = (String) item[3];
            producto = (String) item[4];
            cantidad = (Integer) item[5];
            cant = new BigDecimal(cantidad);

            total = (BigDecimal) item[6];
            totalFinal = cant.multiply(total);

            usuario = (String) item[7];

            list.add(new ReporteCompra(noFactura, nombreProveedor, fechaVenta, formaPago, producto, cantidad, total, totalFinal, usuario));

            factory comp = new factory();
            comp.setReporteCompra((ArrayList<ReporteCompra>) list);

        }
        return list;
    }

}
