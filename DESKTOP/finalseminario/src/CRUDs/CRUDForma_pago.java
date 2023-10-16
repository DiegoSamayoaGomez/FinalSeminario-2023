/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.TipoPago;
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
public class CRUDForma_pago {
    public static List<TipoPago> universo() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<TipoPago> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(TipoPago.class);
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.desc("idTipoPago"));
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
    public static TipoPago select(Integer idTipoPago) {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TipoPago.class);
        criteria.add(Restrictions.eq("idTipoPago", idTipoPago));
        TipoPago select = (TipoPago) criteria.uniqueResult();
        if (select == null) {
            select = new TipoPago();
            select.setIdTipoPago(0);
        }
        session.close();
        return select;
    }
}
