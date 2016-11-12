package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.DaysBean;
import com.sget.akshef.hibernate.beans.ScheduleBean;
import com.sget.akshef.hibernate.beans.ScheduleHasDaysBean;
import com.sget.akshef.hibernate.dao.ScheduleHasDaysDAO;
import com.sget.akshef.hibernate.entities.DaysEntity;
import com.sget.akshef.hibernate.entities.ScheduleEntity;
import com.sget.akshef.hibernate.entities.ScheduleHasDaysEntity;
import com.sget.akshef.hibernate.entities.SpecialistHasBranchEntity;

/**
 * 
 * @author JDeeb
 * Schedule Service
 */
public class ScheduleHasDaysService {
	ScheduleHasDaysDAO dao = null ;
	
	public ScheduleHasDaysService(){
		dao = new ScheduleHasDaysDAO();
	}
	
	public void insert(ScheduleHasDaysBean bean){
		if(bean == null)
			return;
		ScheduleHasDaysEntity entity = new ScheduleHasDaysEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(ScheduleHasDaysBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        ScheduleHasDaysEntity entity = new ScheduleHasDaysEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(ScheduleHasDaysBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        ScheduleHasDaysEntity entity = new ScheduleHasDaysEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public ScheduleHasDaysBean getById(int id) {
    	if(id < 1)
    		return null ;
    	ScheduleHasDaysBean bean = new ScheduleHasDaysBean();
    	ScheduleHasDaysEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<ScheduleHasDaysBean> getAll() {
    	List<ScheduleHasDaysEntity> entities = dao.getAll();
    	List<ScheduleHasDaysBean> beans = new ArrayList<ScheduleHasDaysBean>();
    	ScheduleHasDaysBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(ScheduleHasDaysEntity entity : entities){
	    		bean = new ScheduleHasDaysBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(ScheduleHasDaysBean bean , ScheduleHasDaysEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new ScheduleHasDaysBean();
		// Basics Data
		bean.setId(entity.getId());
		if(bean.getDays() == null)
			bean.setDays(new DaysBean());
		bean.getDays().setId(entity.getDays().getId());
		if(bean.getSchedule() == null)
			bean.setSchedule(new ScheduleBean());
		bean.getSchedule().setId(entity.getSchedule().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(ScheduleHasDaysEntity entity,ScheduleHasDaysBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new ScheduleHasDaysEntity();
		// Basics Data
		entity.setId(bean.getId());
		if(entity.getDays() == null)
			entity.setDays(new DaysEntity());
		entity.getDays().setId(bean.getDays().getId());
		if(entity.getSchedule() == null)
			entity.setSchedule(new ScheduleEntity());
		entity.getSchedule().setId(bean.getSchedule().getId());
		if(entity.getSpecialistHasBranch() == null)
			entity.setSpecialistHasBranch(new SpecialistHasBranchEntity());
		entity.getSpecialistHasBranch().setId(bean.getSpecialistHasBranch().getId());
	}
}
