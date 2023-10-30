/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.Producto;
import POJOs.Usuario;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author leade
 */
public class CRUDProducto {
        
    public static boolean insert(String nombre, Integer cantidad, BigDecimal precio, int usuario) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Producto.class);
        criteria.add(Restrictions.eq("nombre", nombre));
        criteria.add(Restrictions.eq("estado", true));
        Producto insert = (Producto) criteria.uniqueResult();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Producto();
                insert.setEstado(true);
                insert.setNombre(nombre);
                insert.setCantidad(cantidad);
                insert.setPrecio(precio);

                Usuario usuario2 = new Usuario();
                usuario2.setIdUsuario(usuario);
                insert.setUsuarioByUsuarioIngresa(usuario2);
                
                insert.setFechaIngresa(fecha);
                session.save(insert);
                flag = true;

            }
            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Error = " + e);

        } finally {
            session.close();

        }
        return flag;

    }

    
    public static boolean update(Integer idProducto, String nombre, BigDecimal precio, Integer idUsuario){
        boolean flag=false;
        Date fecha = new Date();
        Session session=HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria=session.createCriteria(Producto.class);
        criteria.add(Restrictions.eq("idProducto",idProducto));
        Producto update=(Producto)criteria.uniqueResult();//ejecuta el codigo
        Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            //para ver si ya existe, y si sí, que lo modifique
            if(update!=null){
                //para conocer los nuevos valores[Estados,]
                update.setNombre(nombre);
                update.setPrecio(precio);
                update.setFechaModifica(fecha);
                Usuario usuario=new Usuario();
                usuario.setIdUsuario(idUsuario);
                update.setUsuarioByUsuarioModifica(usuario);
                session.update(update);//Los del parantesis son los valores nuevos, y el update fuera es la accion que se esta haciendo
                flag=true;
            }
            transaction.commit();
        }catch(Exception e){
            transaction.rollback();//en caso que la transaccion no se complete por alguna razon, se hace un rollback
            System.out.println("Error: "+e);
        }finally{
            session.close();
        }
        return flag;
    }
    
        
        //Eliminar no se usa en el sistema final, pero dejarlo por si acaso (Diego)
    public static boolean eliminar(Integer idProducto,Integer idUsuario){
        boolean flag=false;
        Date fecha = new Date();
        Session session=HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria=session.createCriteria(Producto.class);
        criteria.add(Restrictions.eq("idProducto",idProducto));
        Producto update=(Producto)criteria.uniqueResult();
        Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            if(update!=null){
                session.delete(update);
                flag=true;
            }
            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
            System.out.println("Error: "+e);
        }finally{
            session.close();
        }
        return flag;
    }
    
    public static List<Producto> universo() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Producto> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Producto.class);
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.desc("idProducto"));
            criteria.setMaxResults(500); // se limita la cantidad de datos a mostrar
            lista = criteria.list();

        } catch (HibernateException e) {
            System.out.println("Error" + e);
        } finally {
            session.getTransaction().commit(); //La sesion se cierra de forma distinta al update e insert
        }
        return lista;
    }
    
        //SELECT ESPECIFICO
    public static Producto select(Integer idProducto) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Producto.class);
        criteria.add(Restrictions.eq("idProducto", idProducto));
        Producto select = (Producto) criteria.uniqueResult();
        if (select == null) {
            select = new Producto();
            select.setIdProducto(0);
        }
        session.close();
        return select;
    }
    
    public static boolean anular(Integer idProducto, Integer idUsuario) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Producto.class);
        criteria.add(Restrictions.eq("idProducto", idProducto));
        Producto update = (Producto) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                update.setEstado(false);
                update.setFechaModifica(fecha);
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(idUsuario);
                update.setUsuarioByUsuarioModifica(usuario);
                session.update(update);
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
    
    
    public static List<Producto> reporteProducto() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Producto> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Producto.class);

            //Cargar los datos de otra clase
            //criteria.createAlias("compra", "v");
            criteria.createAlias("producto", "p");
            //criteria.createAlias("p.proveedor", "c");
            //criteria.createAlias("v.tipoPago", "f");
            criteria.createAlias("p.usuarioByUsuarioIngresa", "u");

            criteria.setProjection(Projections.projectionList()
                    
                    //.add(Projections.property("p.idProducto"))
                    .add(Projections.property("p.fechaIngresa"))                    
                    .add(Projections.property("p.nombre"))
                    .add(Projections.property("p.cantidad"))
                    //.add(Projections.property("p.precio"))
                    .add(Projections.property("u.nombre"))
            );

            //criteria.add(Restrictions.eq("p.idProducto", idProducto));
            //criteria.addOrder(Order.desc("idProducto"));
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
