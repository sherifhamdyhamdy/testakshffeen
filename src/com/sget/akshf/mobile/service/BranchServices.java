package com.sget.akshf.mobile.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.akshffeen.mapper.Mapping;
import com.akshffeen.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sget.akshef.beans.BranchContainer;
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.utils.Performance;
import com.sget.akshf.mobile.api.ResponseFactory;


@Path("/branch")
public class BranchServices
{
	private final BranchDAO branchDAO = new BranchDAO();
	private final Utils utils = Utils.getInstance();
	
	@POST
	@Produces("application/json")		//Encoding json formate
	@Path("/listUnitBranches")
	public String listUnitBranches(@FormParam("unitId") Integer unitId,@FormParam("pageNum") int pageNum,@FormParam("rowCount") int rowCount) 
	{		
		int start = ((pageNum-1)*rowCount);

		List<BranchEntity> branches = branchDAO.listUnitBranches(unitId, start, rowCount);
			
		BranchContainer branchContainer = new BranchContainer();
		List<BranchBean> branchBeanList = new ArrayList<BranchBean>();
		BranchBean branchBean = null;
		
		
		if(utils.hasValue(branches))
		{
			for(BranchEntity branch : branches)
			{
				branchBean = Mapping.mapBranchEntity(branch);
						
				if(utils.hasValue(branchBean.getDistric()))
					branchBean.getDistric().setBranches(null);
	
				if(utils.hasValue(branchBean.getUnit()))
				{
					branchBean.getUnit().setBranches(null);
					branchBean.getUnit().setUnitGroupses(null);
					branchBean.getUnit().setUnitHasInsuranceCompanies(null);
				}
				
				branchBeanList.add(branchBean);
			}
			

			branchContainer.setMessage(ResponseFactory.MESSAGE_SUCCESS);
			branchContainer.setCode(ResponseFactory.CODE_SUCCESS);
		}
		else
		{

			branchContainer.setMessage(ResponseFactory.MESSAGE_NO_DATA);
			branchContainer.setCode(ResponseFactory.CODE_NO_DATA);
		}
			
		branchContainer.setData(branchBeanList);	
		
		Gson gson = new Gson();

		StringBuilder news_gson = new StringBuilder(gson.toJson(branchContainer));
//		news_gson = Utils.convertToEntities(news_gson);
		branchContainer = gson.fromJson(news_gson.toString(), BranchContainer.class);
		
		Performance.releaseMemory();

		return news_gson.toString();
	}
		

}