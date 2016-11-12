package com.sget.akshef.hibernate.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * @author JDeeb
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
        	System.out.println("HibernateUtil.enclosing_method()");
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Exception ex) {
        	System.out.println(ex.getMessage());
        	ex.printStackTrace();
            // Log the exception. 
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
