package com.sget.akshef.buisness;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */





import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SubcategoryBean;
import com.sget.akshef.hibernate.dao.CategoryDAO;
import com.sget.akshef.hibernate.dao.SectionsDAO;
import com.sget.akshef.hibernate.dao.SubcategoryDAO;
import com.sget.akshef.hibernate.dao.UnitDAO;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.CategoryHasSubcategoryEntity;

/**
 *
 * @author Vip
 */
public class UnitsFactory {
     CategoryDAO  catDao;
    
    public List<CategoryBean>  getAllCategories()
    {
       List<CategoryBean> catBeanList=new ArrayList<CategoryBean>();
       CategoryBean bean;
    if(catDao==null)     
    {
       catDao=new  CategoryDAO();
      List<CategoryEntity> categories= catDao.getAll();
      for(CategoryEntity ent:categories)
      {
          bean=new CategoryBean();
          bean.setNameAr(ent.getNameAr());
          bean.setNameEn(ent.getNameEn());
          bean.setId(ent.getId());
          catBeanList.add(bean);
      }
      
    }
    UnitDAO untDao=new UnitDAO();
   // untDao.getAllUnitsfromLatAndLong("","");
        
     return catBeanList;  
    }
    public List<CategoryBean>  getAllByUserId(int user_id)
    {
       List<CategoryBean> catBeanList=new ArrayList<CategoryBean>();
       CategoryBean bean;
    if(catDao==null)     
    {
       catDao=new  CategoryDAO();
      List<CategoryEntity> categories= catDao.getAllByUserId(user_id);
      for(CategoryEntity ent:categories)
      {
          bean=new CategoryBean();
          bean.setNameAr(ent.getNameAr());
          bean.setNameEn(ent.getNameEn());
          bean.setId(ent.getId());
          catBeanList.add(bean);
      }
      
    }
    UnitDAO untDao=new UnitDAO();
   // untDao.getAllUnitsfromLatAndLong("","");
        
     return catBeanList;  
    }
    
    
    
    
    
    public List<SubcategoryBean>  getAllSubCategories(int categoryId)
    {
       List<SubcategoryBean> subcatBeanList=new ArrayList<SubcategoryBean>();
       SubcategoryBean bean;
       SubcategoryDAO subDao=new SubcategoryDAO();
    
      List<Object> subCategories= subDao.getSubCategoryByCatId(categoryId);
      for(Object ent:subCategories)
      {
    	  Object[] arr=(Object[])ent;
    	  
    	  
    	  
          bean=new SubcategoryBean();
          bean.setId((Integer)arr[0]);
          bean.setNameAr(arr[1].toString());
          bean.setNameEn(arr[2].toString());
        //  bean.setCategoryId(categoryId);
         
               List<SectionsBean> sectionsBeans= getAllSectionsBySubCategory(categoryId,bean.getId());
          
          if(sectionsBeans!=null && !sectionsBeans.isEmpty())
          {
        	  subcatBeanList.add(bean);
          }
          else
          {
        	  continue;
          }
         
      }
      
    return subcatBeanList;
   
    }

    
    public List<SectionsBean> getAllSectionsBySubCategory(int categoryId,int subcategory)
    		{
    	
//    	sections.name_ar,sections.name_en,sections.id sectionID,category.id categoryId," +
//		"subcategory.id subcategoryId 
    	
    	
    	List<SectionsBean> sectionBeanList=new ArrayList<SectionsBean>();
    	SectionsBean bean;
        SectionsDAO sectionDao=new SectionsDAO();
     
       List<Object> sections= sectionDao.getAllSectionsBySubCategory(categoryId, subcategory);
       for(Object ent:sections)
       {
     	  Object[] arr=(Object[])ent;
     	  
     	  /**/
     	  
           bean=new SectionsBean();
           bean.setNameAr(arr[0].toString());
           bean.setNameEn(arr[1].toString());
           bean.setId((Integer)arr[2]);
//           bean.setCategoryId((Integer)arr[3]);
//           bean.setSubCategoryId((Integer)arr[4]);
         
          
           sectionBeanList.add(bean);
       }
    		return sectionBeanList;	
    		}
    
    
    
    
}
