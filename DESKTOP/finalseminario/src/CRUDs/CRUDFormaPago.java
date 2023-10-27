/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.TipoPago;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Samayoa
 */
public class CRUDFormaPago {
    //SELECT

    public static List<TipoPago> universo() {
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<TipoPago> lista = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(TipoPago.class);
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.desc("idTipoPago"));
            criteria.setMaxResults(500); 
            lista = criteria.list();

        } catch (HibernateException e) {
            System.out.println("Error" + e);
        } finally {
            session.getTransaction().commit();
        }
        return lista;

    }
}
