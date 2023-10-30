/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportefinalseminario;

import Reportes.ReporteCompra;
import Reportes.ReporteProducto;
import Reportes.ReporteVenta;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samayoa
 */
public class factory {

    private static ArrayList<ReporteVenta> reporteVenta = new ArrayList<ReporteVenta>();
    private static ArrayList<ReporteCompra> reporteCompra = new ArrayList<ReporteCompra>();
    private static ArrayList<ReporteProducto> reporteProducto = new ArrayList<ReporteProducto>();

    public static List reporteVenta() {
        return getReporteVenta();
    }

    public static List reporteCompra() {
        return getReporteCompra();
    }

    public static List reporteProducto() {
        return getReporteProducto();
    }

    /**
     * @return the reporteVenta
     */
    public static ArrayList<ReporteVenta> getReporteVenta() {
        return reporteVenta;
    }

    /**
     * @param aReporteVenta the reporteVenta to set
     */
    public static void setReporteVenta(ArrayList<ReporteVenta> aReporteVenta) {
        reporteVenta = aReporteVenta;
    }

    /**
     * @return the reporteCompra
     */
    public static ArrayList<ReporteCompra> getReporteCompra() {
        return reporteCompra;
    }

    /**
     * @param aReporteCompra the reporteCompra to set
     */
    public static void setReporteCompra(ArrayList<ReporteCompra> aReporteCompra) {
        reporteCompra = aReporteCompra;
    }

    /**
     * @return the reporteProducto
     */
    public static ArrayList<ReporteProducto> getReporteProducto() {
        return reporteProducto;
    }

    /**
     * @param aReporteProducto the reporteProducto to set
     */
    public static void setReporteProducto(ArrayList<ReporteProducto> aReporteProducto) {
        reporteProducto = aReporteProducto;
    }

}
