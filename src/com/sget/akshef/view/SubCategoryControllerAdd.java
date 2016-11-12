package com.sget.akshef.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.collections.map.HashedMap;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SubcategoryBean;
import com.sget.akshef.hibernate.beans.SubcategoryHasSectionsBean;
import com.sget.akshef.hibernate.service.SectionsService;
import com.sget.akshef.hibernate.service.SubcategoryHasSectionsService;
import com.sget.akshef.hibernate.service.SubcategoryService;

/**
 * 
 * @author JDeeb
 *
 */

public class SubCategoryControllerAdd implements Serializable {
	
	private static final long serialVersionUID = 2279988124105839000L;
	
	private SubcategoryBean subcategoryBean = null;
	private SubcategoryService subcategoryService = null;
	private SectionsService sectionsService = null;
	private SubcategoryHasSectionsBean subcategoryHasSectionsBean = null;
	private SubcategoryHasSectionsService subcategoryHasSectionsService = null;
		
	private DualListModel<SectionsBean> modelSectionsBeans = null;
	List<SectionsBean>  source= null;
	List<SectionsBean>  target=null;
	List<SectionsBean> allSectionsBeans = null;
	List<SectionsBean> targetSectionsBeans = null;
	List<SectionsBean> sourceSectionsBeans = null;
	
	public SubCategoryControllerAdd() {
		subcategoryService = new SubcategoryService();
		subcategoryBean = new SubcategoryBean();
		sectionsService = new SectionsService();
		subcategoryHasSectionsBean = new SubcategoryHasSectionsBean();
		subcategoryHasSectionsService = new SubcategoryHasSectionsService();
		
		source = new ArrayList<SectionsBean>();
		target = new ArrayList<SectionsBean>();
 		allSectionsBeans = new ArrayList<SectionsBean>();
		targetSectionsBeans = new ArrayList<SectionsBean>();
		sourceSectionsBeans = new ArrayList<SectionsBean>();
		
		////////////////////////////////////////////
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		/////////////////////////////////////////////
		allSectionsBeans = sectionsService.getAll();
		try {
			Map<String, String> maps = context.getRequestParameterMap();
			String id = maps.get("id");
			if (id != null && !id.equalsIgnoreCase("")) {
				subcategoryBean = subcategoryService.getById(Integer.parseInt(id));
				if (subcategoryBean != null && subcategoryBean.getId() != 0) {
					targetSectionsBeans = sectionsService.getAllBySubCategory(subcategoryBean);
					if(targetSectionsBeans != null   &&  targetSectionsBeans .size()>0){
						for (SectionsBean subBeanall : allSectionsBeans) {
							if (!checkSectionExist(subBeanall.getId())) {
								sourceSectionsBeans.add(subBeanall);
							}
						}
						target.addAll(targetSectionsBeans);
						source.addAll(sourceSectionsBeans);
					}else{
						fillAllSectionBeans();
					}
				}
			} else {
				fillAllSectionBeans();
			}
			
			modelSectionsBeans = new DualListModel<SectionsBean>(source,target);
		} catch (Exception e) {
			 System.out.println("SubCategoryControllerAdd Ex : " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void fillAllSectionBeans() {
		source =  new  ArrayList<SectionsBean>();
		source.addAll(allSectionsBeans);
		target =  new  ArrayList<SectionsBean>();
		modelSectionsBeans = new DualListModel<SectionsBean>(source,target);	
	}

	private boolean checkSectionExist(int id) {
		for (SectionsBean sub : targetSectionsBeans) {
			if (sub.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	// saveSubcategory
	private int notDeleted = 0 ;
	public void saveSubcategory(ActionEvent action) {
		if (subcategoryBean.getId() != 0) {		
			subcategoryService.update(subcategoryBean);
			updateSections();
			String text = " Updated Successfully !! ";
			if(notDeleted > 0)
				text += "<br>but cannot delet some Sections !!<br>because its parent";
			RequestContext.getCurrentInstance().showMessageInDialog(
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated", text));
		} else {
			List<SubcategoryBean> beans=	subcategoryService.getSubcategoryName( subcategoryBean.getNameEn(),  subcategoryBean.getNameAr());
			if(beans.size()>0){
				RequestContext.getCurrentInstance().showMessageInDialog(
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "This Subcategory already exist!!"));
				return;
			}
			
			subcategoryService.insert(subcategoryBean);
			insertSection(modelSectionsBeans.getTarget());
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
					"Inserted", "Inserted Successfully !!"));
		}
		subcategoryBean = new SubcategoryBean();
		subcategoryHasSectionsBean = new SubcategoryHasSectionsBean();
		fillAllSectionBeans();
	}
	public void insertSection(List<SectionsBean> listInsert) {
		if(listInsert != null && listInsert.size() > 0){
			for (SectionsBean sectionsBean3 : listInsert) {
				subcategoryHasSectionsBean = new SubcategoryHasSectionsBean();
				subcategoryHasSectionsBean.setSections(sectionsBean3);
				subcategoryHasSectionsBean.setSubcategory(subcategoryBean);
				subcategoryHasSectionsService.insert(subcategoryHasSectionsBean);
			}
		}
	}
	
	public void updateSections() {
		if (modelSectionsBeans.getTarget() == null || subcategoryBean == null)
			return;
		Map<String,List<SectionsBean>> updateList = prepareForUpdate();
		if(updateList != null && updateList.get("delete") != null && updateList.get("delete").size() > 0){ 
	 		boolean isDelet = false ;
			for ( SectionsBean section : updateList.get("delete")) {
	 			try{
					isDelet = subcategoryHasSectionsService.deleteBysubCategory(section.getId(), subcategoryBean.getId());
					if(!isDelet)
						notDeleted++;
	 			}catch(Exception ex){
	 				ex.printStackTrace();
	 			}
			}
		}
		if(updateList != null && updateList.get("insert") != null && updateList.get("insert").size() > 0){
			insertSection(updateList.get("insert"));
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String,List<SectionsBean>> prepareForUpdate(){
		if(modelSectionsBeans.getTarget() != null && modelSectionsBeans.getTarget().size() > 0
				&& targetSectionsBeans != null && targetSectionsBeans.size() > 0){
			// list of deletedList
			Map<String,List<SectionsBean>> updateList = new HashedMap();
			List<SectionsBean> deleteList = new ArrayList<SectionsBean>();
			List<SectionsBean> insertList = new ArrayList<SectionsBean>();
			boolean exist = false;
			for(SectionsBean bean : targetSectionsBeans){
				exist = false;
				for(SectionsBean temp : modelSectionsBeans.getTarget()){
					if(bean.getId() == temp.getId())
						exist = true;
				}
				if(!exist)
					deleteList.add(bean);
					
			}
			for(SectionsBean bean : modelSectionsBeans.getTarget()){
				exist = false;
				for(SectionsBean temp : targetSectionsBeans){
					if(bean.getId() == temp.getId())
						exist = true;
				}
				if(!exist)
					insertList.add(bean);
			}
			updateList.put("delete", deleteList);
			updateList.put("insert", insertList);
			
			return updateList;
		}else{
			return null;
		}
		
	}
	
	// Setters & getters
	public SubcategoryBean getSubcategoryBean() {
		return subcategoryBean;
	}
	public void setSubcategoryBean(SubcategoryBean subcategoryBean) {
		this.subcategoryBean = subcategoryBean;
	}
	public DualListModel<SectionsBean> getModelSectionsBeans() {
		return modelSectionsBeans;
	}
	public void setModelSectionsBeans(DualListModel<SectionsBean> modelSectionsBeans) {
		this.modelSectionsBeans = modelSectionsBeans;
	}
}
