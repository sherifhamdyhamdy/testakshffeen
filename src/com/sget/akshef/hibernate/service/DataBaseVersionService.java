package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.DataBaseVersion;
import com.sget.akshef.hibernate.dao.DatabaseVersionDAO;
import com.sget.akshef.hibernate.entities.DataBaseVersionEntity;

/**
 * 
 * @author JDeeb database Service
 */
public class DataBaseVersionService
{
	DatabaseVersionDAO dao = null;
	
	
	
	public DataBaseVersionService()
	{
		dao = new DatabaseVersionDAO();
	}
	
	
	
	public void insert(DataBaseVersion bean)
	{
		if (bean == null)
			return;
		DataBaseVersionEntity entity = new DataBaseVersionEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	
	
	
	public boolean update(DataBaseVersion bean)
	{
		if (bean == null || bean.getId() < 1)
			return false;
		
		DataBaseVersionEntity entity = new DataBaseVersionEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
	}
	
	
	
	public boolean delete(DataBaseVersion bean)
	{
		if (bean == null || bean.getId() < 1)
			return false;
		DataBaseVersionEntity entity = new DataBaseVersionEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
	}
	
	
	
	// Get By ID
	public DataBaseVersion getById(int id)
	{
		if (id < 1)
			return null;
		DataBaseVersion bean = new DataBaseVersion();
		DataBaseVersionEntity entity = dao.getById(id);
		fillBean(bean, entity);
		return bean;
	}
	
	
	
	// Get All
	public List<DataBaseVersion> getAll()
	{
		List<DataBaseVersionEntity> entities = dao.getAll();
		List<DataBaseVersion> beans = new ArrayList<DataBaseVersion>();
		DataBaseVersion bean;
		if (entities != null && entities.size() > 0)
		{
			for (DataBaseVersionEntity entity : entities)
			{
				bean = new DataBaseVersion();
				fillBean(bean, entity);
				beans.add(bean);
			}
		}
		return beans;
	}
	
	
	
	/**
	 * Return Entity Of LastDBVersion
	 * 
	 * @return
	 */
	public DataBaseVersion getLastVersion()
	{
		DataBaseVersionEntity entity = dao.getLastVersion();
		DataBaseVersion bean = new DataBaseVersion();
		fillBean(bean, entity);
		return bean;
	}
	
	
	
	/**
	 * Check if this version is the last or not
	 * 
	 * @return 1 if it is the last version or 0 if it is not the last version
	 */
	public int isVersionLast(int version)
	{
		return dao.isVersionLast(version);
	}
    
	public DataBaseVersion isVersionLastNew(int version)
	{
		DataBaseVersionEntity dataBaseVersion = dao.isVersionLastNew(version);
				
		 DataBaseVersion dataBaseVersionBean = fillBean(dataBaseVersion);
		
		return dataBaseVersionBean;
	}
	
	
	public DataBaseVersion fillBean(DataBaseVersionEntity entity)
	{		
		if (entity == null)
			return null;
		
		DataBaseVersion dataBaseVersionBean = new DataBaseVersion();
		
		// Basics Data
		dataBaseVersionBean.setId(entity.getId());
		dataBaseVersionBean.setVersion(entity.getVersion());
		dataBaseVersionBean.setDataBaseName(entity.getDataBaseName());
		
		return dataBaseVersionBean;
	}
	
	
	// Fill Bean From Entity
	public void fillBean(DataBaseVersion bean, DataBaseVersionEntity entity)
	{
		if (entity == null)
			return;
		if (bean == null)
			bean = new DataBaseVersion();
		// Basics Data
		bean.setId(entity.getId());
		bean.setVersion(entity.getVersion());
		bean.setDataBaseName(entity.getDataBaseName());
	}
	
	
	
	// Fill Entity From Bean
	public void fillEntity(DataBaseVersionEntity entity, DataBaseVersion bean)
	{
		if (bean == null)
			return;
		if (entity == null)
			entity = new DataBaseVersionEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setDataBaseName(bean.getDataBaseName());
		entity.setVersion(bean.getVersion());
	}
}
