package com.sget.akshef.hibernate.utils;

import org.hibernate.Session;
import org.hibernate.SessionException;

public class HibernateSession {

	private Session session = null ;
    public Session getSession(){
        if(session == null || !session.isOpen()){
            return makeSession();
        }
        return session ;
    }
    public Session makeSession(){
        session = HibernateUtil.getSessionFactory().openSession();
        if(session == null || !session.isOpen())
            throw new SessionException("Problem :: can't Create Session");
        if(session != null && session.isOpen())
            session.beginTransaction();
        return session;
    }
    public void closeSession(){
    	if(getSession() != null || getSession().isOpen())
            getSession().close();
    }
    
}
