package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;



import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.UnitBean;
import com.sget.akshef.hibernate.dao.UnitDAO;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.UnitEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;

/**
 * 
 * @author JDeeb
 * Unit Service
 */
public class UnitService {
	UnitDAO dao = null ;
	
	public UnitService(){
		dao = new UnitDAO();
	}
	
	public int  insert(UnitBean bean){
		if(bean == null)
			return 0;
		UnitEntity entity = new UnitEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
		return entity.getId();
	}
	public boolean update(UnitBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        UnitEntity entity = new UnitEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(UnitBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        UnitEntity entity = new UnitEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public UnitBean getById(int id) {
    	if(id < 1)
    		return null ;
    	UnitBean bean = new UnitBean();
    	UnitEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<UnitBean> getAll() {
    	List<UnitEntity> entities = dao.getAll();
    	List<UnitBean> beans = new ArrayList<UnitBean>();
    	UnitBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(UnitEntity entity : entities){
	    		bean = new UnitBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    // check if unit exist or not
    public int checkIfUnitExist(String nameAr , String nameEn){
    	return dao.checkIfUnitExist(nameAr, nameEn);
    }
    // Get All Units by Category
    public List<UnitBean> getAllUnitsByCat(int catId) {
    	List<UnitEntity> entities = dao.getAllUnitsByCat(catId);
    	List<UnitBean> beans = new ArrayList<UnitBean>();
    	UnitBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(UnitEntity entity : entities){
	    		bean = new UnitBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
	// Fill Bean From Entity
	public void fillBean(UnitBean bean , UnitEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new UnitBean();
		// Basics Data
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		bean.setBiographyAr(entity.getBiographyAr());
		bean.setBiographyEn(entity.getBiographyEn());
		bean.setUnitlogo(entity.getUnitlogo());
		bean.setWebsite(entity.getWebsite());
		
		
		CategoryBean catBean=new CategoryBean();
//		catBean.setId(entity.getCategory().getId());
//		catBean.setNameAr(entity.getCategory().getNameAr());
//		catBean.setNameEn(entity.getCategory().getNameEn());
		bean.setCategory(catBean);
	
		
	}
	// Fill Entity From Bean
	public void fillEntity(UnitEntity entity,UnitBean bean){
		if(bean == null || bean.getCategory() == null)
			return;
		if(entity == null)
			entity = new UnitEntity();
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setNameEn(bean.getNameEn());
		entity.setNameAr(bean.getNameAr());
		entity.setBiographyAr(bean.getBiographyAr());
		entity.setBiographyEn(bean.getBiographyEn());
		entity.setUnitlogo(bean.getUnitlogo());
		entity.setWebsite(bean.getWebsite());
		
		// fill category
//		if(entity.getCategory() == null)
//			entity.setCategory(new CategoryEntity());
//		entity.getCategory().setId(bean.getCategory().getId());
//		entity.getCategory().setNameAr(bean.getCategory().getNameAr());
//		entity.getCategory().setNameEn(bean.getCategory().getNameEn());
		
		// Fill User
		if(bean.getUser() != null && bean.getUser().getId() > 0){
			if(entity.getUsers() == null)
				entity.setUsers(new UsersEntity());
			entity.getUsers().setId(bean.getUser().getId());
		}
		
	}
}
