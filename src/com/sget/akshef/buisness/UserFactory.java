package com.sget.akshef.buisness;


import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.dao.UsersDAO;
import com.sget.akshef.hibernate.entities.UsersEntity;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Vip
 */
public class UserFactory {
    
    
    
   UsersDAO userDao;
   
   
   
   public Integer login(String username,String pass)
   {
	   Integer userId=0;
       if(userDao==null)
       {
         userDao=new UsersDAO();  
         userId= userDao.isUserExist(username, pass);
       }
     return userId;   
       
   }
   
  public int insertNewUser(UsersBean user)
   
   {
	   int id=0;
	   if(userDao==null)
       {
         userDao=new UsersDAO();  
       UsersEntity userEnt=new UsersEntity();
       userEnt.setUsername(user.getUsername());
       userEnt.setEmail(user.getEmail());
//       userEnt.setPassword(user.getPassword());
       userEnt.setNationalId(user.getNationalId());
       id=userDao.insertById(userEnt);
       }
	   return id;
   }
    
}
