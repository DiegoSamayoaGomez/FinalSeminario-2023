/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.Usuario;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Pit
 */
public class CRUDUsuario {
    
    
    public static Usuario select(String nombreUsuario){
        Session session=HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Usuario select=null;
        try{
            Criteria criteria=session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("nombre", nombreUsuario));
            criteria.add(Restrictions.eq("estado", true));
            select=(Usuario)criteria.uniqueResult();
        }catch(HibernateException e){
            System.out.println("Error = "+ e);
        }finally{
            session.close();
        }
        return select;
    }
    
    
    
    
    
    public static List<Usuario> universo(){
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
        List<Usuario>lista=null;
        try{
            session.beginTransaction();
            Criteria criteria=session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("estado", true));
            criteria.addOrder(Order.desc("idUsuario"));
            criteria.setMaxResults(500);
            lista=criteria.list();
        }catch(HibernateException e){
            System.out.println("Error="+e);
        }finally{
            session.getTransaction().commit();
        }
        return lista;
    }
}
