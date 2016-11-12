package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sget.akshef.hibernate.beans.BranchBean;

import com.sget.akshef.hibernate.beans.CountryBean;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sget.akshef.hibernate.dao.CategoryDAO;
import com.sget.akshef.hibernate.dao.CountryDAO;
import com.sget.akshef.hibernate.dao.SearchDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.CountryEntity;
import com.sget.akshef.hibernate.entities.DistricEntity;
import com.sget.akshf.searchcriteria.AdvancedSearchCriteria;
import com.sget.akshf.searchcriteria.ResultSearchBean;

/**
 * 
 * @author JDeeb
 * Country Service
 */
public class SearchService {
	SearchDAO dao = null ;
	
	public SearchService(){
		dao = new SearchDAO();
	}
	public ArrayList<BranchBean>searchBranch(Map searchCriteria)
	{
		ArrayList<BranchBean> beans=new ArrayList<BranchBean>();
		ArrayList<BranchBean> list=new 	ArrayList<BranchBean>();
		BranchService branchService=new BranchService();
		ArrayList<BranchEntity> entities=(ArrayList<BranchEntity>) dao.searchBranch(searchCriteria);
		BranchBean bean ;
		for(BranchEntity entity : entities){
    		bean = new BranchBean();
    		branchService.fillBean(bean, entity);
    		branchService.getBranchRating(bean);
    		beans.add(bean);
    	}
		
		return beans;
		
	}
	
//	public List<ResultSearchBean> advancedSearchForBranches(AdvancedSearchCriteria criteria)
//	{
//		//branch.id,branch.name_ar,branch.name_en,branch.address,branch.tel,branch.distric_id,branch.mobile,branch.mobile,branch.rating
//		List<ResultSearchBean> lists=new ArrayList<ResultSearchBean>();
//		ResultSearchBean row;
//		CategoryDAO catdao=new CategoryDAO();
//		List<BranchEntity> entities=(List<BranchEntity>) dao.advancedSearchForBranchesWithoutDimensions(criteria);
//		for(BranchEntity entity : entities){
//			//branch.id,branch.name_ar,branch.name_en,branch.address,branch.tel,branch.distric_id,branch.mobile,branch.mobile,branch.rating
//            row.setBranchId(((BranchEntity) entity.getId());//branch.id
//            row.setBranchNameAr(( ((BranchEntity) entity[0]).getNameAr().toString()));//name_ar
//            row.setBranchNameEn(( ((BranchEntity) entity[0]).getNameEn().toString()));//name_en
//            row.setBranchAdress(( ((BranchEntity) entity[0]).getAddress().toString()));//address
//            row.setBranchTel(( ((BranchEntity) entity[0]).getTel().toString()));//tel
//            row.setDistrict(( ((DistricEntity)((BranchEntity)entity[0]).getDistric()).getNameEn()));//district
//            row.setBranchrating(( ((BranchEntity) entity[0]).getBranch_rating()));//rating
//          
//           row.setCategory((((CategoryEntity) catdao.getById((Integer)entity[1])).getNameEn()));
//            lists.add(row);
//			
//    	}
//		return lists;
//	}        row=new ResultSearchBean();
//    
	
	
	
	
}
