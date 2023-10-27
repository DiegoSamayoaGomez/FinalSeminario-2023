/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.Cliente;
import POJOs.TipoPago;
import POJOs.Usuario;
import POJOs.Venta;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Samayoa
 */
public class CRUDCompra {

    public static boolean insert(Integer idCliente, Integer idTipoPago, Integer idUsuario) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Venta.class);
        criteria.add(Restrictions.eq("estadoFinalizado", false));
        criteria.add(Restrictions.eq("usuarioByUsuarioIngresa.idUsuario", idUsuario));
        //No deja tener 2 nombres iguales mientras esta activo

        criteria.add(Restrictions.eq("estado", true));
        Venta insert = (Venta) criteria.uniqueResult(); //Hace una comparativa y permite hacer la exclusiion
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Venta(); //solo se usa cuando se inserta algo completamente nuevo

                insert.setEstado(true);
                insert.setEstadoFinalizado(false);
                Cliente cliente = new Cliente();
                cliente.setIdCliente(idCliente);
                insert.setCliente(cliente);

                insert.setFechaVenta(fecha);

                TipoPago tipoPago = new TipoPago();
                tipoPago.setIdTipoPago(idTipoPago);
                insert.setTipoPago(tipoPago);

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(idUsuario);
                insert.setUsuarioByUsuarioIngresa(usuario);
                insert.setFechaIngresa(fecha);

                session.save(insert);
                flag = true;

            }
            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Error=" + e);

        } finally {
            session.close();

            //permite ejecutar codigo de forma obligatoria
        }

        return flag;
    }
    
    
    
    
    public static Venta select(Usuario usuario) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Venta.class);
        criteria.add(Restrictions.eq("estadoFinalizado", false));
        criteria.add(Restrictions.eq("usuarioByUsuarioIngresa", usuario));
        
        Venta select = (Venta) criteria.uniqueResult();
        if (select == null) {
            select = new Venta();
            select.setIdVenta(0);
        }
        session.close();
        return select;
    }
    
    
         public static boolean update(Integer idVenta) {
        boolean flag = false;
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Venta.class);
        //Las comillas son el nombre de lavariable de SQL
        criteria.add(Restrictions.eq("idVenta", idVenta));
        Venta update = (Venta) criteria.uniqueResult();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (update != null) {
                update.setEstadoFinalizado(true);              
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
