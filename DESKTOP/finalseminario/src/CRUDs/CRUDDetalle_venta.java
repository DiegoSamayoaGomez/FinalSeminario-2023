/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

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
 * @author Pit
 */
public class CRUDDetalle_venta {
    public static boolean insert(Integer idProducto, Integer idVenta, Integer cantidad, BigDecimal monto) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DetalleVenta.class);
        criteria.createAlias("venta","v");
        criteria.createAlias("producto","p");
        criteria.add(Restrictions.eq("p.idProducto", idProducto));
        criteria.add(Restrictions.eq("v.idVenta", idVenta));
        DetalleVenta insert = (DetalleVenta) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new DetalleVenta();
                Producto producto=new Producto();
                producto.setIdProducto(idProducto);
                insert.setProducto(producto);

                Venta venta=new Venta();
                venta.setIdVenta(idVenta);
                insert.setVenta(venta);
                
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
