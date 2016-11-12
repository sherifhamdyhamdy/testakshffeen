package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SubcategoryBean;
import com.sget.akshef.hibernate.dao.SectionsDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.SubcategoryEntity;

/**
 * 
 * @author JDeeb Sections Service
 */
public class SectionsService
{
	SectionsDAO dao = null;
	
	
	
	public SectionsService()
	{
		dao = new SectionsDAO();
	}
	
	
	
	public void insert(SectionsBean bean)
	{
		if (bean == null)
			return;
		SectionsEntity entity = new SectionsEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	
	
	
	public boolean update(SectionsBean bean)
	{
		if (bean == null || bean.getId() < 1)
			return false;
		
		SectionsEntity entity = new SectionsEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
	}
	
	
	
	public boolean delete(SectionsBean bean)
	{
		if (bean == null || bean.getId() < 1)
			return false;
		SectionsEntity entity = new SectionsEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
	}
	
	
	
	// Get By ID
	public SectionsBean getById(int id)
	{
		if (id < 1)
			return null;
		SectionsBean bean = new SectionsBean();
		SectionsEntity entity = dao.getById(id);
		fillBean(bean, entity);
		return bean;
	}
	
	
	
	// Get All
	public List<SectionsBean> getAll()
	{
		List<SectionsEntity> entities = dao.getAll();
		List<SectionsBean> beans = new ArrayList<SectionsBean>();
		SectionsBean bean;
		if (entities != null && entities.size() > 0)
		{
			for (SectionsEntity entity : entities)
			{
				bean = new SectionsBean();
				fillBean(bean, entity);
				beans.add(bean);
			}
		}
		return beans;
	}
	
	
	
	// Fill Bean From Entity
	public void fillBean(SectionsBean bean, SectionsEntity entity)
	{
		if (entity == null)
			return;
		if (bean == null)
			bean = new SectionsBean();
		// Basics Data
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		bean.setSectionImage(entity.getImage());
	}
	
	
	
	// Fill Entity From Bean
	public void fillEntity(SectionsEntity entity, SectionsBean bean)
	{
		if (bean == null)
			return;
		if (entity == null)
			entity = new SectionsEntity();
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setNameEn(bean.getNameEn());
		entity.setNameAr(bean.getNameAr());
		entity.setImage(bean.getSectionImage());
	}
	
	
	
	public List<SectionsBean> getAllBySubCategory(SubcategoryBean subcategoryBean)
	{
		if (subcategoryBean == null)
			return null;
		SubcategoryEntity subcategoryEntity = new SubcategoryEntity();
		subcategoryEntity.setId(subcategoryBean.getId());
		List<SectionsEntity> entities = dao.getAllBySubCategory(subcategoryEntity);
		List<SectionsBean> beans = new ArrayList<SectionsBean>();
		SectionsBean bean;
		for (SectionsEntity entity : entities)
		{
			bean = new SectionsBean();
			fillBean(bean, entity);
			beans.add(bean);
		}
		return beans;
	}
	
	
	
	// getAllByBranch
	public List<SectionsBean> getAllByBranch(BranchBean branchBean, SubcategoryBean subcategoryBean)
	{
		if (branchBean == null)
			return null;
		if (subcategoryBean == null)
			return null;
		
		BranchEntity branchEntity = new BranchEntity();
		branchEntity.setId(branchBean.getId());
		
		SubcategoryEntity subcategoryEntity = new SubcategoryEntity();
		subcategoryEntity.setId(subcategoryBean.getId());
		
		
		List<SectionsEntity> entities = dao.getAllByBranch(branchEntity, subcategoryEntity);
		List<SectionsBean> beans = new ArrayList<SectionsBean>();
		SectionsBean bean;
		for (SectionsEntity entity : entities)
		{
			bean = new SectionsBean();
			fillBean(bean, entity);
			beans.add(bean);
		}
		return beans;
	}
	
	
	
	public List<SectionsBean> getSectionName(String name_en, String name_ar)
	{
		List<SectionsEntity> entities = dao.getSectionName(name_en, name_ar);
		List<SectionsBean> beans = new ArrayList<SectionsBean>();
		SectionsBean bean;
		if (entities != null && entities.size() > 0)
		{
			for (SectionsEntity entity : entities)
			{
				bean = new SectionsBean();
				fillBean(bean, entity);
				beans.add(bean);
			}
		}
		return beans;
	}
	
	
	
	// Check Sections Exist Or Not
	public int checkSectionExist(String nameAr, String nameEn)
	{
		return dao.checkSectionExist(nameAr, nameEn);
	}
	
	
	
	public List<SectionsBean> getSpecialistSections()
	{
		List<SectionsEntity> entities = dao.getSpecialistSections();
		List<SectionsBean> beans = new ArrayList<SectionsBean>();
		SectionsBean bean;
		if (entities != null && entities.size() > 0)
		{
			for (SectionsEntity entity : entities)
			{
				bean = new SectionsBean();
				fillBean(bean, entity);
				beans.add(bean);
			}
		}
		return beans;
	}
}
