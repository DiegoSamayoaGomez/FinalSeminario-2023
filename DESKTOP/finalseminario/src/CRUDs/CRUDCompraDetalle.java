/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.Compra;
import POJOs.DetalleCompra;

import POJOs.Producto;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Samayoa
 */
public class CRUDCompraDetalle {

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

    //Universo
        public static List<DetalleCompra> universo(Integer idCompra) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<DetalleCompra> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(DetalleCompra.class);

            //Cargar los datos de otra clase
            criteria.createAlias("compra", "c");
            criteria.createAlias("producto", "p");

            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("idDetalleCompra"))
                    .add(Projections.property("p.nombre"))
                    .add(Projections.property("cantidad"))
                    .add(Projections.property("monto"))
            );

            criteria.add(Restrictions.eq("c.idCompra", idCompra));
            criteria.addOrder(Order.desc("idDetalleCompra"));
            criteria.setMaxResults(500); // se limita la cantidad de datos a mostrar
            lista = criteria.list();

        } catch (HibernateException e) {
            System.out.println("Error" + e);
        } finally {
            session.getTransaction().commit(); //La sesion se cierra de forma distinta al update e insert
        }
        return lista;
    }
    
    public static List<DetalleCompra> universo() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<DetalleCompra> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(DetalleCompra.class);
            criteria.addOrder(Order.desc("idDetalleCompra"));
            criteria.setMaxResults(500);
            lista = criteria.list();
        } catch (HibernateException e) {
            System.out.println("Error = " + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;
    }

    //Eliminar
    public static boolean eliminar(Integer idDetalleCompra) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DetalleCompra.class);
        //Las comillas son el nombre de lavariable de SQL
        criteria.add(Restrictions.eq("idDetalleCompra", idDetalleCompra));
        DetalleCompra update = (DetalleCompra) criteria.uniqueResult();
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

    //Select monto total
    public static List<DetalleCompra> selectMontoTotalCompra(Integer idCompra) throws ParseException {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Query query = session.createSQLQuery("call procedimientoTotalCompra(" + idCompra + ");");
        List<DetalleCompra> listDatos = query.list();
        session.getTransaction().commit();
        return listDatos;
    }

    public static List<DetalleCompra> reporteCompra(Integer idCompra) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<DetalleCompra> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(DetalleCompra.class);

            //Cargar los datos de otra clase
            criteria.createAlias("compra", "v");
            criteria.createAlias("producto", "p");
            criteria.createAlias("v.proveedor", "c");
            criteria.createAlias("v.tipoPago", "f");
            criteria.createAlias("v.usuarioByUsuarioIngresa", "u");

            criteria.setProjection(Projections.projectionList()
                    .add(Projections.property("v.idCompra"))
                    .add(Projections.property("c.nombre"))
                    .add(Projections.property("v.fechaCompra"))
                    .add(Projections.property("f.nombre"))
                    .add(Projections.property("p.nombre"))
                    .add(Projections.property("cantidad"))
                    .add(Projections.property("monto"))
                    .add(Projections.property("u.nombre"))
            );

            criteria.add(Restrictions.eq("v.idCompra", idCompra));
            criteria.addOrder(Order.desc("idDetalleCompra"));
            criteria.setMaxResults(500); // se limita la cantidad de datos a mostrar
            lista = criteria.list();

        } catch (HibernateException e) {
            System.out.println("Error" + e);
        } finally {
            session.getTransaction().commit(); //La sesion se cierra de forma distinta al update e insert
        }
        return lista;
    }

}
