/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.Compra;
import POJOs.DetalleCompra;
import POJOs.DetalleVenta;
import POJOs.Producto;
import POJOs.Venta;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Samayoa
 */
public class CRUDVentaDetalle {

    public static boolean insert(Integer idProducto, Integer idCompra, Integer cantidad, BigDecimal monto) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DetalleCompra.class);
        criteria.createAlias("compra", "c");
        criteria.createAlias("producto", "p");
        criteria.add(Restrictions.eq("p.idProducto", idProducto));
        criteria.add(Restrictions.eq("c.idCompra", idCompra));
        DetalleCompra insert = (DetalleCompra) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new DetalleCompra();
                Producto producto = new Producto();
                producto.setIdProducto(idProducto);
                insert.setProducto(producto);

                Compra compra = new Compra();
                compra.setIdCompra(idCompra);
                insert.setCompra(compra);

                insert.setCantidad(cantidad);
                insert.setMonto(monto);
                session.save(insert);
                flag = true;
            }
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Error= " + e);
        } finally {
            session.close();
        }

        return flag;
    }

    
    
    public static boolean eliminar(Integer idDetalleVenta){
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DetalleVenta.class);
        //Las comillas son el nombre de lavariable de SQL
        criteria.add(Restrictions.eq("idDetalleVenta", idDetalleVenta));
        DetalleVenta update = (DetalleVenta) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                session.delete(update);
                flag = true;

            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error" + e);
        } finally {
            session.close();
        }
        return flag;
    }
    
    public static List<DetalleVenta> universo() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<DetalleVenta> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(DetalleVenta.class);
            criteria.addOrder(Order.desc("idDetalleVenta"));
            criteria.setMaxResults(500);
            lista = criteria.list();
        } catch (HibernateException e) {
            System.out.println("Error = " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }
    
    
    //Esto no sé que hace, pero no es montoTotal por si está la duda (Diego)
    public static DetalleVenta select(Integer idDetalleVenta) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DetalleVenta.class);
        criteria.add(Restrictions.eq("idDetalleVenta", idDetalleVenta));
        DetalleVenta select = (DetalleVenta) criteria.uniqueResult();
        if (select == null) {
            select = new DetalleVenta();
            select.setIdDetalleVenta(0);
        }
        session.close();
        return select;
    }
    
    
}
