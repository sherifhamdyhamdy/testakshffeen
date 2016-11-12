package com.sget.akshef.hibernate.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.akshffeen.utils.Utils;
import com.sget.akshef.beans.SchedulesBean;
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.CityBean;
import com.sget.akshef.hibernate.beans.CommentsBean;
import com.sget.akshef.hibernate.beans.CountryBean;
import com.sget.akshef.hibernate.beans.DistricBean;
import com.sget.akshef.hibernate.beans.UnitBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sget.akshef.hibernate.dao.SectionsDAO;
import com.sget.akshef.hibernate.dao.UserRateBranchDAO;
import com.sget.akshef.hibernate.dao.UsersDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.Branch_scheduleEntity;
import com.sget.akshef.hibernate.entities.CommentsEntity;
import com.sget.akshef.hibernate.entities.DistricEntity;
import com.sget.akshef.hibernate.entities.Doctor_scheduleEntity;
import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.SubcategoryEntity;
import com.sget.akshef.hibernate.entities.UnitEntity;
import com.sget.akshef.hibernate.entities.UserRateBranchEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshf.mobile.api.AppConstants;
import com.sget.akshf.searchcriteria.AdvancedSearchCriteria;
import com.sget.akshf.searchcriteria.BranchesCriteria;
import com.sget.akshf.searchcriteria.SearchDoctorsCriteria;

/**
 * 
 * @author JDeeb Branch Service
 */
public class BranchService
{
	BranchDAO dao = null;
	
	private Utils utils = Utils.getInstance();
	
	public BranchService()
	{
		dao = new BranchDAO();
	}
	
	
	
	public void insert(BranchBean bean)
	{
		if (bean == null)
			return;
		BranchEntity entity = new BranchEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	
	
	
	public boolean update(BranchBean bean)
	{
		if (bean == null || bean.getId() < 1)
			return false;
		
		BranchEntity entity = new BranchEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
	}
	
	
	
	public boolean delete(BranchBean bean)
	{
		if (bean == null || bean.getId() < 1)
			return false;
		BranchEntity entity = new BranchEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
	}
	
	
	
	// Get By ID
	public BranchBean getById(int id)
	{
		if (id < 1)
			return null;
		BranchBean bean = new BranchBean();
		// BranchEntity entity = dao.getById(id);
		BranchEntity entity = dao.getBranchById(id);
		fillBean(bean, entity);
		getBranchRating(bean);
		return bean;
	}
	
	
	
	// Get All
	public List<BranchBean> getAll()
	{
		List<BranchEntity> entities = dao.getAll();
		List<BranchBean> beans = new ArrayList<BranchBean>();
		BranchBean bean;
		if (entities != null && entities.size() > 0)
		{
			for (BranchEntity entity : entities)
			{
				bean = new BranchBean();
				fillBean(bean, entity);
				getBranchRating(bean);
				beans.add(bean);
			}
		}
		return beans;
	}
	
	

	public List<BranchBean> getBranchesForWeb(BranchesCriteria criteria)
	{
		List<Object[]> results = dao.getBranchesForWeb(criteria);
		List<BranchBean> beans = new ArrayList<BranchBean>();
		BranchBean bean;
		DecimalFormat df = new DecimalFormat("#.00");
		if (results != null)
		{
			for (Object[] inst : results)
			{
				BranchEntity entity = (BranchEntity) inst[0];
				
				bean = new BranchBean();
				fillBean(bean, entity);
				bean.setImage(bean.getImage() != null && !bean.getImage().equalsIgnoreCase("") ? bean.getImage() : null);
				bean.setCategory_id((Integer) inst[2]);
				bean.setDistance(Double.valueOf(df.format((Double) inst[1])));
				// Add Rating to Bean
				// getBranchRating(bean);
				
				beans.add(bean);
			}
		}
		return beans;
	}
	
	
	
	// get Rating for Branch
	public void getBranchRating(BranchBean bean)
	{
		if (bean != null && bean.getId() > 0)
		{
			List<UserRateBranchEntity> lists = dao.getBranchRating(bean.getId());
			if (lists != null && lists.size() > 0)
			{
				int rating = 0;
				int ratingno = 0;
				for (UserRateBranchEntity ent : lists)
				{
					if (ent != null && ent.getRate() >= 0)
					{
						rating += ent.getRate();
						ratingno += 1;
					}
				}
				int avarge = (rating * 100 / (ratingno * AppConstants.APP_RATING_MAX));
				bean.setRating(avarge);
				bean.setRatingno(ratingno);
			}
		}
	}
		
	// get Schedules for Branch
	public void getBranchSchedules(BranchBean bean)
	{
		if (bean != null && bean.getId() > 0)
		{
			List<Branch_scheduleEntity> branch_scheduleList = dao.getBranchSchedules(bean.getId());
			
			Map<String, TreeSet<Integer>> schedules_map = new HashMap<String, TreeSet<Integer>>();
			
			String schedule = "";
			for(Branch_scheduleEntity branch_schedule : branch_scheduleList)
			{
				schedule = branch_schedule.getFrom_hour() + "-" + branch_schedule.getTo_hour();
				
				if(schedules_map.containsKey(schedule))
					schedules_map.get(schedule).add(branch_schedule.getDay_id());
				else
				{
					TreeSet<Integer> days = new TreeSet<Integer>();
					days.add(branch_schedule.getDay_id());
					schedules_map.put(schedule, days);
				}
			}
				
			SchedulesBean schedulesBean = utils.getSchedules(schedules_map);
			
			bean.setBranch_schedules(schedulesBean);
		}
	}
	
	public void getSubcategoriesByBranch(BranchBean bean)
	{
		if (bean != null && bean.getId() > 0)
		{
			List<SubcategoryEntity> subCategoryList = dao.getSubcategoriesByBranch(bean.getId());
			if(utils.hasValue(subCategoryList))
			{
//				List<String> subCategoryList = new ArrayList<String>();
				for(SubcategoryEntity subCategory : subCategoryList)
				{
					subCategory.setCategoryHasSubcategorySet(null);
					subCategory.setSubcategoryHasSectionsSet(null);
				}
				
				bean.setSubCategoryList(subCategoryList);
			}
		}
	}
	
	// Fill Bean From Entity
	public void fillBean(BranchBean bean, BranchEntity entity)
	{
		if (entity == null)
			return;
		if (bean == null)
			bean = new BranchBean();
		// Basics Data
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		
		bean.setCategory_id(entity.getCategory_id()!=null?entity.getCategory_id():0);
		
		bean.setAddress(entity.getAddress());
		bean.setEstablishdate(entity.getEstablishdate());
		bean.setLat(entity.getLat());
		bean.setLng(entity.getLng());
		// bean.setMobile(entity.getMobile());
		// bean.setRegion(entity.getRegion());
		bean.setTel(entity.getTel());
		bean.setWebsite(entity.getWebsite());
		bean.setBiography_ar(entity.getBiography_ar());
		bean.setBiography_en(entity.getBiography_en());
		bean.setImage(entity.getUnit().getUnitlogo() != null ? DBConstants.UNIT_IMAGES_UPLOADS + entity.getUnit().getUnitlogo() : null);
		bean.setRating(entity.getBranch_rating() != null ? entity.getBranch_rating() : 0);
		// New
		bean.setEmail(entity.getEmail());
		bean.setFax(entity.getFax());
		bean.setHotline(entity.getHotline());
		bean.setPostlcode(entity.getPostlcode());
		
		bean.setSponsored(entity.getSponsored()==null?0:entity.getSponsored());
		bean.setHas_delivery(entity.getHas_delivery()==null?0:entity.getHas_delivery());
		
		if (entity.getUnit() != null && entity.getUnit().getId() > 0)
		{
			if (bean.getUnit() == null)
				bean.setUnit(new UnitBean());
			bean.getUnit().setId(entity.getUnit().getId());
			bean.getUnit().setNameAr(entity.getUnit().getNameAr());
			bean.getUnit().setNameEn(entity.getUnit().getNameEn());
			if (entity.getUnit().getUnitlogo() != null && !entity.getUnit().getUnitlogo().equalsIgnoreCase(""))
				bean.setImage(entity.getUnit().getUnitlogo() != null && !entity.getUnit().getUnitlogo().equals("") ? DBConstants.UNIT_IMAGES_UPLOADS + entity.getUnit().getUnitlogo() : null);
			
//			if (entity.getUnit().getCategory() != null)
//			{
//				bean.getUnit().setCategory(new CategoryBean());
//				bean.getUnit().getCategory().setId(entity.getUnit().getCategory().getId());
//				bean.getUnit().getCategory().setNameAr(entity.getUnit().getCategory().getNameAr());
//				bean.getUnit().getCategory().setNameEn(entity.getUnit().getCategory().getNameEn());
//			}
		}
		if (entity.getDistric() != null && entity.getDistric().getId() > 0)
		{
			if (bean.getDistric() == null)
				bean.setDistric(new DistricBean());
			bean.getDistric().setId(entity.getDistric().getId());
			bean.getDistric().setNameAr(entity.getDistric().getNameAr());
			bean.getDistric().setNameEn(entity.getDistric().getNameEn());
			if (entity.getDistric().getCity() != null)
			{
				bean.getDistric().setCity(new CityBean());
				bean.getDistric().getCity().setId(entity.getDistric().getCity().getId());
				bean.getDistric().getCity().setNameAr(entity.getDistric().getCity().getNameAr());
				bean.getDistric().getCity().setNameEn(entity.getDistric().getCity().getNameEn());
				bean.getDistric().getCity().setDistrics(null);

				if (entity.getDistric().getCity().getCountry() != null)
				{
					bean.getDistric().getCity().setCountry(new CountryBean());
					bean.getDistric().getCity().getCountry().setId(entity.getDistric().getCity().getCountry().getId());
					bean.getDistric().getCity().getCountry().setNameAr(entity.getDistric().getCity().getCountry().getNameAr());
					bean.getDistric().getCity().getCountry().setNameEn(entity.getDistric().getCity().getCountry().getNameEn());
					bean.getDistric().getCity().getCountry().setCities(null);
				}
			}
		}
	}
	
	
	
	// Fill Entity From Bean
	public void fillEntity(BranchEntity entity, BranchBean bean)
	{
		if (bean == null || bean.getUnit() == null)
			return;
		if (entity == null)
			entity = new BranchEntity();
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setNameEn(bean.getNameEn());
		entity.setNameAr(bean.getNameAr());
		entity.setAddress(bean.getAddress());
		entity.setEstablishdate(bean.getEstablishdate());
		entity.setLat(bean.getLat());
		entity.setLng(bean.getLng());
		// entity.setMobile(bean.getMobile());
		// entity.setRegion(bean.getRegion());
		entity.setTel(bean.getTel());
		entity.setWebsite(bean.getWebsite());
		entity.setBiography_ar(bean.getBiography_ar());
		entity.setBiography_en(bean.getBiography_en());
		entity.setImage(bean.getImage());
		
		// New
		entity.setEmail(bean.getEmail());
		entity.setFax(bean.getFax());
		entity.setHotline(bean.getHotline());
		entity.setPostlcode(bean.getPostlcode());
		
		if (entity.getUnit() == null)
			entity.setUnit(new UnitEntity());
		entity.getUnit().setId(bean.getUnit().getId());
		entity.getUnit().setNameAr(bean.getUnit().getNameAr());
		entity.getUnit().setNameEn(bean.getUnit().getNameEn());
		
		if (entity.getDistric() == null)
			entity.setDistric(new DistricEntity());
		entity.getDistric().setId(bean.getDistric().getId());
	}
	
	
	
	public List<BranchBean> getAllByUnit(int id)
	{
		List<BranchEntity> entities = dao.getAllByUnit(id);
		List<BranchBean> beans = new ArrayList<BranchBean>();
		BranchBean bean;
		for (BranchEntity entity : entities)
		{
			bean = new BranchBean();
			fillBean(bean, entity);
			beans.add(bean);
		}
		return beans;
	}
	
	
	

	
	
	public List<BranchBean> advancedSearchForBranchesWithoutDimensions(AdvancedSearchCriteria criteria)
	{
		List<Object[]> results = dao.advancedSearchForBranchesWithoutDimensions(criteria);
		List<BranchBean> beans = new ArrayList<BranchBean>();
		BranchBean bean;
		DecimalFormat df = new DecimalFormat("#.00");
		if (results != null)
		{
			for (Object[] inst : results)
			{
				BranchEntity entity = (BranchEntity) inst[0];
				bean = new BranchBean();
				fillBean(bean, entity);
				Object district = (Object) inst[1];
				if (criteria.getMode() != null && criteria.getMode().equals("spec"))
				{
					Object specId = (Object) inst[2];
					Object SpecName = (Object) inst[3];
					bean.setSpecId(specId.toString());
					bean.setSpecName(SpecName.toString());
					
					if (criteria.getDistrict_id() > 0)
					{
						Object districtname = (Object) inst[4];
						bean.setDistrictName(districtname.toString());
					}
					else
					{
						bean.setDistrictName(bean.getDistric().getNameAr());
					}
				}
				else
				{
					if (criteria.getDistrict_id() > 0)
					{
						Object districtname = (Object) inst[2];
						bean.setDistrictName(districtname.toString());
					}
					else
					{
						bean.setDistrictName(bean.getDistric().getNameAr());
					}
					
					
				}
				
				
				beans.add(bean);
			}
		}
		return beans;
	}
	
	
	
	public List<BranchBean> getBranchsByLatLng(double lat, double lng, Map filters)
	{
		List<BranchBean> catBeanList = new ArrayList<BranchBean>();
		BranchBean bean;
		String sectionName = "";
		if (filters != null && !filters.isEmpty() && filters.get("section") != null && !filters.get("section").equals(""))
		{
			filters.get("section");
			SectionsDAO sectionDao = new SectionsDAO();
			int sectionId = ((Integer) filters.get("section")).intValue();
			SectionsEntity entity = sectionDao.getById(sectionId);
			sectionName = entity.getNameAr();
			// System.out.println("SectionName   "+sectionName);
		}
		
		List<BranchEntity> categories = dao.getAllBranchsByFilters(filters, lat, lng);
		for (BranchEntity entity : categories)
		{
			bean = new BranchBean();
			fillBean(bean, entity);
			catBeanList.add(bean);
		}
		
		
		// UnitDAO untDao=new UnitDAO();
		// untDao.getAllUnitsfromLatAndLong(lat,lng,null);
		
		return catBeanList;
	}
	
	
	
	public List<BranchBean> getBranchsByLatLngById(double lat, double lng, Map filters, int user_id)
	{
		List<BranchBean> catBeanList = new ArrayList<BranchBean>();
		
		String sectionName = "";
		if (filters != null && !filters.isEmpty() && filters.get("section") != null && !filters.get("section").equals(""))
		{
			filters.get("section");
			SectionsDAO sectionDao = new SectionsDAO();
			int sectionId = ((Integer) filters.get("section")).intValue();
			SectionsEntity entity = sectionDao.getById(sectionId);
			sectionName = entity.getNameAr();
			System.out.println("SectionName   " + sectionName);
		}
		
		
		List<BranchEntity> categories = dao.getAllBranchsByFiltersById(filters, lat, lng, user_id);
		BranchBean bean;
		for (BranchEntity entity : categories)
		{
			bean = new BranchBean();
			fillBean(bean, entity);
			catBeanList.add(bean);
		}
		
		
		return catBeanList;
	}
	
	
	
	public int saveRating(int branchId, int userId, int rating)
	{
		boolean isRatingSaved = true;
		UserRateBranchDAO ratDao = new UserRateBranchDAO();
		int totalRate = 0;
		String sequence = "";
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		sequence = month + "_" + year + "_" + "1";
		BranchDAO branchDao = new BranchDAO();
		UsersDAO userDao = new UsersDAO();
		BranchEntity branchEnt = branchDao.getById(branchId);
		UsersEntity userEntity = userDao.getById(userId);
		List<UserRateBranchEntity> listRat = ratDao.checkIfUserRate(branchId, userId);
		if (listRat == null || listRat.isEmpty())
		{
			UserRateBranchEntity entity = new UserRateBranchEntity();
			entity.setBranch(branchEnt);
			entity.setUsers(userEntity);
			entity.setRate(rating);
			entity.setRating_Date(sequence);
			ratDao.insert(entity);
		}
		else
		{
			UserRateBranchEntity entity = (UserRateBranchEntity) listRat.get(0);
			
			String ratDate = entity.getRating_Date();
			String a[] = ratDate.split("_");
			if (a != null && a.length != 0)
			{
				int monthStr = Integer.parseInt(a[0]);
				int yearStr = Integer.parseInt(a[1]);
				int serial = Integer.parseInt(a[2]);
				if (monthStr < month && yearStr <= year)
				{
					sequence = month + "_" + year + "_" + "1";
				}
				else if (monthStr == month && yearStr < year)
				{
					sequence = month + "_" + year + "_" + "1";
				}
				
				
				else if (monthStr == month && yearStr == year && serial == 1)
				{
					sequence = month + "_" + year + "_" + "2";
				}
				
				else if (monthStr == month && yearStr == year && serial == 2)
				{
					return -1;
				}
				
			}
			entity.setRating_Date(sequence);
			entity.setRate(rating);
			ratDao.update(entity);
			
			
		}
		List<Object> list = ratDao.getRatingToBesaved(branchId);
		if (list != null && !list.isEmpty())
		{
			Object[] arr = (Object[]) list.get(0);
			Number rate = (Number) arr[0];
			Number count = (Number) arr[1];
			totalRate = Math.round(rate.floatValue() / count.floatValue());
			branchEnt.setRating(totalRate);
			branchDao.update(branchEnt);
			
		}
		return totalRate;
	}
	
	
	
	public List<CommentsBean> getCommentsByBranchId(int branchId)
	{
		System.out.println("branchId  --- " + branchId);
		List<CommentsBean> commentsBeanList = new ArrayList<CommentsBean>();
		CommentsBean bean;
		
		List<CommentsEntity> comments = dao.getAllCommentsBybranch(branchId);
		
		
		for (CommentsEntity ent : comments)
		{
			bean = new CommentsBean();
			
			bean.setId(ent.getId());
			bean.setComment(ent.getComment());
			bean.getUsers().setId(ent.getUsers().getId());
			
			// System.out.println("commentsBeanList "+ent.toString());
			commentsBeanList.add(bean);
			
			
		}
		return commentsBeanList;
	}
	
	
	
	// JDeeb
	public void updateBranchRating(int rating, int branchId)
	{
		dao.updateBranchRating(rating, branchId);
	}
	
	
	
	public boolean insertBranchSubcategorySection(int[] subSect, int branchId)
	{
		return dao.insertBranchSubcategorySection(subSect, branchId);
	}
	
	
	
	// Delete multi SubcategorySection to Branch
	public boolean deleteBranchSubcategorySection(int[] subSect, int branchId)
	{
		return dao.deleteBranchSubcategorySection(subSect, branchId);
	}
	
	
	
	// check if unit exist or not
	public int checkIfBranchExist(String nameAr, String nameEn, double lat, double lng)
	{
		return dao.checkIfBranchExist(nameAr, nameEn, lat, lng);
	}
}
