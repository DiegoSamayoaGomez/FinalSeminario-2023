/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.Categoria;
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
 * @author leade
 */
public class CRUDCategoria {
    
    public static boolean insert(String nombre, String descripcion, String telefono, int usuario) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Categoria.class);

        criteria.add(Restrictions.eq("nombre", nombre));
        criteria.add(Restrictions.eq("descripcion", descripcion));

        criteria.add(Restrictions.eq("estado", true));
        Categoria insert = (Categoria) criteria.uniqueResult();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Categoria();
                insert.setEstado(true);

                insert.setNombre(nombre);
                insert.setDescripcion(descripcion);

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
   
    public static boolean update(Integer idCategoria, String nombre, String descripcion, int usuario) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Categoria.class);
        criteria.add(Restrictions.eq("idCategoria", idCategoria));
        Categoria update = (Categoria) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                
                update.setNombre(nombre);
                update.setDescripcion(descripcion);              

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
    
    public static List<Categoria> universo() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Categoria> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Categoria.class);
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.desc("idCategoria"));
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
    public static Categoria select(Integer idCategoria) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Categoria.class);
        criteria.add(Restrictions.eq("idCategoria", idCategoria));
        Categoria select = (Categoria) criteria.uniqueResult();
        if (select == null) {
            select = new Categoria();
            select.setIdCategoria(0);
        }
        session.close();
        return select;
    }
    
    public static boolean anular(Integer idCategoria, Integer idUsuario) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Categoria.class);
        criteria.add(Restrictions.eq("idCategoria", idCategoria));
        Categoria update = (Categoria) criteria.uniqueResult();
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
