package com.sget.akshf.mobile.service;

import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import com.akshffeen.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sget.akshef.hibernate.dao.SectionsDAO;
import com.sget.akshef.hibernate.dao.SubcategoryHasSectionsHasBranchDAO;
import com.sget.akshef.hibernate.entities.SubcategoryHasSectionsHasBranchEntity;
import com.sget.akshef.utils.Performance;


@Path("/section")
public class SectionServices
{
	private final SectionsDAO sectionDAO = new SectionsDAO();
	private final SubcategoryHasSectionsHasBranchDAO subcategoryHasSectionsHasBranchDAO = new SubcategoryHasSectionsHasBranchDAO();

	private final Utils utils = Utils.getInstance();
	
	@POST
	@Produces("application/json")		//Encoding json formate
	@Path("/getSectionsPrices")
	public String getSectionsPrices(@FormParam("branchId") Integer branchId,@FormParam("subCategoryId") int subCategoryId) 
	{
		List<SubcategoryHasSectionsHasBranchEntity> sections = subcategoryHasSectionsHasBranchDAO.getSectionsPrices(branchId, subCategoryId);

		if(utils.hasValue(sections))
		{
			for(SubcategoryHasSectionsHasBranchEntity section : sections)
			{
				section.setSection_ar(section.getSubcategoryHasSections().getSections().getNameAr());
				section.setSection_en(section.getSubcategoryHasSections().getSections().getNameEn());
				section.setSection_id(section.getSubcategoryHasSections().getSections().getId());
				section.setSubcategoryHasSections(null);		
				section.setBranch(null);		
				section.setId(null);		
			}
		}
			
		Gson gson = new Gson();
		
		StringBuilder news_gson = new StringBuilder(gson.toJson(sections));
//		news_gson = Utils.convertToEntities(news_gson);
		sections = gson.fromJson(news_gson.toString(), new TypeToken<List<?>>(){}.getType());
		
		Performance.releaseMemory();

		return news_gson.toString();
	}
		

}