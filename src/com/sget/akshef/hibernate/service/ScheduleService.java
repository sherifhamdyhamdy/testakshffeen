package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.ScheduleBean;
import com.sget.akshef.hibernate.dao.ScheduleDAO;
import com.sget.akshef.hibernate.entities.ScheduleEntity;

/**
 * 
 * @author JDeeb
 * Schedule Service
 */
public class ScheduleService {
	ScheduleDAO dao = null ;
	
	public ScheduleService(){
		dao = new ScheduleDAO();
	}
	
	public void insert(ScheduleBean bean){
		if(bean == null)
			return;
		ScheduleEntity entity = new ScheduleEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(ScheduleBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        ScheduleEntity entity = new ScheduleEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(ScheduleBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        ScheduleEntity entity = new ScheduleEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public ScheduleBean getById(int id) {
    	if(id < 1)
    		return null ;
    	ScheduleBean bean = new ScheduleBean();
    	ScheduleEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<ScheduleBean> getAll() {
    	List<ScheduleEntity> entities = dao.getAll();
    	List<ScheduleBean> beans = new ArrayList<ScheduleBean>();
    	ScheduleBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(ScheduleEntity entity : entities){
	    		bean = new ScheduleBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(ScheduleBean bean , ScheduleEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new ScheduleBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setActive(entity.getActive());
		bean.setFrom_date(entity.getFrom_date());
		bean.setTo_date(entity.getTo_date());
	}
	// Fill Entity From Bean
	public void fillEntity(ScheduleEntity entity,ScheduleBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new ScheduleEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setActive(bean.getActive());
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String yemp = format.format(bean.getFrom_date());
		entity.setFrom_date(bean.getFrom_date());
		entity.setTo_date(bean.getTo_date());
	}
}
