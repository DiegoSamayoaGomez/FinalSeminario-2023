/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import POJOs.Cliente;
import POJOs.Usuario;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author juanl
 */
public class CRUDCliente {
   
    public static boolean insert(String nombre, String apellido, String nit, String telefono, String direccion, int usuario) {
        boolean flag = false;
        Date fecha = new Date();
        Session session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Cliente.class);
        criteria.add(Restrictions.eq("nombre", nombre));
        criteria.add(Restrictions.eq("apellido", apellido));

        criteria.add(Restrictions.eq("estado", true));
        Cliente insert = (Cliente) criteria.uniqueResult();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            if (insert == null) {
                insert = new Cliente();
                insert.setEstado(true);
                insert.setNombre(nombre);
                insert.setApellido(apellido);
                insert.setNit(nit);
                insert.setTelefono(telefono);
                insert.setDireccion(direccion);


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
}
