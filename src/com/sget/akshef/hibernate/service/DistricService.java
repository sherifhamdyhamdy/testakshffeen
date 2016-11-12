package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.CityBean;
import com.sget.akshef.hibernate.beans.DistricBean;
import com.sget.akshef.hibernate.dao.DistricDAO;
import com.sget.akshef.hibernate.entities.CityEntity;
import com.sget.akshef.hibernate.entities.DistricEntity;

/**
 * 
 * @author JDeeb
 * District Service
 */
public class DistricService {
	DistricDAO dao = null ;
	
	public DistricService(){
		dao = new DistricDAO();
	}
	
	public void insert(DistricBean bean){
		if(bean == null)
			return;
		DistricEntity entity = new DistricEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(DistricBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        DistricEntity entity = new DistricEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(DistricBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        DistricEntity entity = new DistricEntity();
		//BranchService ser=new BranchService();
		fillEntity(entity, bean);
		/*for(BranchBean branchBean:   bean.getBranches())
		{
			branchBean.setDistric(null);
			boolean isUpdated=	ser.update(branchBean);
			 System.out.println("is update --  "+isUpdated);
		}*/
		
		
		return dao.delete(entity);
		
    }
    // Get By ID
    public DistricBean getById(int id) {
    	if(id < 1)
    		return null ;
    	DistricBean bean = new DistricBean();
    	DistricEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<DistricBean> getAll() {
    	List<DistricEntity> entities = dao.getAll();
    	List<DistricBean> beans = new ArrayList<DistricBean>();
    	DistricBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(DistricEntity entity : entities){
	    		bean = new DistricBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(DistricBean bean , DistricEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new DistricBean();
		// Basics Data
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		CityBean cityBean=new CityBean();
		cityBean.setId(entity.getCity().getId());
		bean.setCity(cityBean);
		
		 /*Set<BranchEntity> set=entity.getBranchSet();
		 BranchBean branchBean;
		 List<BranchBean> listBranchs=new ArrayList<BranchBean>();
		 BranchService branchService=new BranchService();
		 for(BranchEntity entityBranch:set)
		 {
			 branchBean =new BranchBean();
			 branchService.fillBean(branchBean, entityBranch);
			 listBranchs.add(branchBean);
			 
			 
		 }
		 bean.setBranches(listBranchs);*/
		
	}
	
	
	
	public DistricBean fillBean(DistricEntity entity)
	{
		if (entity == null)
			return null;
		
		
		DistricBean bean = new DistricBean();
		// Basics Data
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		CityBean cityBean = new CityBean();
		cityBean.setId(entity.getCity().getId());
		cityBean.setNameAr(entity.getCity().getNameAr());
		cityBean.setNameEn(entity.getCity().getNameEn());
		bean.setCity(cityBean);
		
		/*
		 * Set<BranchEntity> set=entity.getBranchSet(); BranchBean branchBean;
		 * List<BranchBean> listBranchs=new ArrayList<BranchBean>();
		 * BranchService branchService=new BranchService(); for(BranchEntity
		 * entityBranch:set) { branchBean =new BranchBean();
		 * branchService.fillBean(branchBean, entityBranch);
		 * listBranchs.add(branchBean);
		 * 
		 * 
		 * } bean.setBranches(listBranchs);
		 */
		
		return bean;
	}
	
	// Fill Entity From Bean
	public void fillEntity(DistricEntity entity,DistricBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new DistricEntity();
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setNameEn(bean.getNameEn());
		entity.setNameAr(bean.getNameAr());
		
		if(entity.getCity() == null)
			entity.setCity(new CityEntity());
		entity.getCity().setId(bean.getCity().getId());
		entity.getCity().setNameAr(bean.getCity().getNameAr());
		entity.getCity().setNameEn(bean.getCity().getNameEn());
		
		/*Set<BranchEntity> set=new HashSet<BranchEntity>();
		BranchEntity branchEntity;
		 
		 List<BranchBean> listBranchs=bean.getBranches();
		 BranchService branchService=new BranchService();
		 for(BranchBean branchBean:listBranchs)
		 {
			 branchEntity =new BranchEntity();
			 branchService.fillEntity(branchEntity, branchBean);
			 set.add(branchEntity); 
		 }
		 entity.setBranchSet(set);*/
		
	}
	public List<DistricBean> getDistrictName(String name_en,String name_ar) {
    	List<DistricEntity> entities = dao.getDistrictName( name_en, name_ar);
    	List<DistricBean> beans = new ArrayList<DistricBean>();
    	DistricBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(DistricEntity entity : entities){
	    		bean = new DistricBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Get getAllDistrictByCity
    public List<DistricBean> getAllDistrictByCity(int city_id) {
    	List<Object> entities = dao.getAllDistrictByCity(city_id);
    	List<DistricBean> beans = new ArrayList<DistricBean>();
    	DistricBean bean ;
    	if(entities != null && entities.size() > 0){
    		  for(Object ent:entities)
    	      {
    	    	  Object[] arr=(Object[])ent;
    	    	  
    	    	  bean=new DistricBean();
    	          bean.setId((Integer)arr[0]);
    	          bean.setNameEn(arr[1].toString());
    	          bean.setNameAr(arr[2].toString());
    	          beans.add(bean);
	    	}
    	}
    	return beans;
    }
    // check if City exist or not
    public int checkIfDistrictExist(String nameAr, String nameEn){
    	return dao.checkIfDistrictExist(nameAr, nameEn);
    }
}
