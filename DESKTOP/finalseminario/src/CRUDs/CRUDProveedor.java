/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.Proveedor;
import POJOs.Usuario;
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
public class CRUDProveedor {

    public static boolean insert(String nombre, String direccion, String telefono, int usuario) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Proveedor.class);

        criteria.add(Restrictions.eq("nombre", nombre));
        criteria.add(Restrictions.eq("direccion", direccion));

        criteria.add(Restrictions.eq("estado", true));
        Proveedor insert = (Proveedor) criteria.uniqueResult();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Proveedor();
                insert.setEstado(true);

                insert.setNombre(nombre);
                insert.setDireccion(direccion);
                insert.setTelefono(telefono);

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

    
        public static boolean update(Integer idProveedor, String nombre, String direccion, String telefono, int usuario) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Proveedor.class);
        criteria.add(Restrictions.eq("idProveedor", idProveedor));
        Proveedor update = (Proveedor) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                
                update.setNombre(nombre);
                update.setDireccion(direccion);
                update.setTelefono(telefono);              

                update.setFechaModifica(fecha);

                Usuario usuario2 = new Usuario();
                usuario2.setIdUsuario(usuario);
                update.setUsuarioByUsuarioModifica(usuario2);

                session.update(update);
                flag = true;

            }
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Error = " + e);
        } finally {
            session.close();

        }
        return flag;
    }
        
        
    public static List<Proveedor> universo() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Proveedor> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Proveedor.class);
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.desc("idProveedor"));
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
    public static Proveedor select(Integer idProveedor) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Proveedor.class);
        criteria.add(Restrictions.eq("idProveedor", idProveedor));
        Proveedor select = (Proveedor) criteria.uniqueResult();
        if (select == null) {
            select = new Proveedor();
            select.setIdProveedor(0);
        }
        session.close();
        return select;
    }
    
    
    
        //CAMBIAR ESTADO
    public static boolean anular(Integer idProveedor, Integer idUsuario) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Proveedor.class);
        criteria.add(Restrictions.eq("idProveedor", idProveedor));
        Proveedor update = (Proveedor) criteria.uniqueResult();
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
}
