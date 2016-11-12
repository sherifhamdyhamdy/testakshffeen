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
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.CategoryHasSubcategoryBean;
import com.sget.akshef.hibernate.beans.SubcategoryBean;
import com.sget.akshef.hibernate.service.CategoryHasSubcategoryService;
import com.sget.akshef.hibernate.service.CategoryService;
import com.sget.akshef.hibernate.service.SubcategoryService;

/**
 * 
 * @author JDeeb
 * 
 */

public class CategoryControllerAdd  implements Serializable{
	
	private static final long serialVersionUID = -5467452545597400086L;
	
	private  CategoryHasSubcategoryService categoryHasSubcategoryService = null;
	private  CategoryService categoryService = null;
	private  SubcategoryService subcategoryService = null;

	private CategoryHasSubcategoryBean categoryHasSubcategoryBean = null;
	private CategoryBean categoryBean = null;
 
	private String SelectedId = null;
	private String saveOrUpdateCategory = "saveCategory";

	private DualListModel<SubcategoryBean> modelSubcategoryBeans = null;
	
	List<SubcategoryBean>  source= null;
	List<SubcategoryBean>  target=null;
	List<SubcategoryBean> allSubcategoryBeans = null;
	List<SubcategoryBean> targetSubcategoryBeans = null;
	List<SubcategoryBean> sourceSubcategoryBeans = null;

	public CategoryControllerAdd() {
		categoryHasSubcategoryService = new CategoryHasSubcategoryService();
 		source = new ArrayList<SubcategoryBean>();
		target = new ArrayList<SubcategoryBean>();
		categoryBean = new CategoryBean();
 		allSubcategoryBeans = new ArrayList<SubcategoryBean>();
		targetSubcategoryBeans = new ArrayList<SubcategoryBean>();
		sourceSubcategoryBeans = new ArrayList<SubcategoryBean>();
 		categoryService = new CategoryService();
		subcategoryService = new SubcategoryService();
		
		//////////////////////////////////////////////////////////////////
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		allSubcategoryBeans = subcategoryService.getAll();
		try {
			Map<String, String> maps = context.getRequestParameterMap();
			String id = maps.get("id");
			if (id != null && !id.trim().equalsIgnoreCase("")) {
				SelectedId = id;
				saveOrUpdateCategory = "updateCategory";
			}
			
			if (SelectedId != null && !SelectedId.equalsIgnoreCase("")) {
				categoryBean = categoryService.getById(Integer.parseInt(SelectedId));
				if (categoryBean != null && categoryBean.getId() != 0) {
					targetSubcategoryBeans = subcategoryService.getAllByCategory(categoryBean);
					if(targetSubcategoryBeans != null   &&  targetSubcategoryBeans .size()>0){
						for (SubcategoryBean subBeanall : allSubcategoryBeans) {
							if (!checkSubcategoryExist(subBeanall.getId())) {
								sourceSubcategoryBeans.add(subBeanall);
							}
						}
						target.addAll(targetSubcategoryBeans);
						source.addAll(sourceSubcategoryBeans);
					}else{
						fillAllSubcategoryBeans();
					}
				}
			} else {
				fillAllSubcategoryBeans();
			}
			modelSubcategoryBeans = new DualListModel<SubcategoryBean>(source,target);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillAllSubcategoryBeans() {
		source =  new  ArrayList<SubcategoryBean>();
		source.addAll(allSubcategoryBeans);
		target =  new  ArrayList<SubcategoryBean>();
		modelSubcategoryBeans = new DualListModel<SubcategoryBean>(source,target);	
	}

	private boolean checkSubcategoryExist(int id) {
		for (SubcategoryBean sub : targetSubcategoryBeans) {
			if (sub.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public void insertCategoryHasSubcategory(List<SubcategoryBean> listInsert) {
		if(listInsert != null){
	 		for ( SubcategoryBean subcategoryBean3 : listInsert) {
	 			categoryHasSubcategoryBean=  new CategoryHasSubcategoryBean();
				categoryHasSubcategoryBean.setCategory(categoryBean);
				categoryHasSubcategoryBean.setSubcategory(subcategoryBean3);
				categoryHasSubcategoryService.insert(categoryHasSubcategoryBean);
			}
		}
	}
	public void updateCategoryHasSubcategory() {
		if (modelSubcategoryBeans.getTarget() == null || categoryBean == null)
			return;
		Map<String,List<SubcategoryBean>> updateList = prepareForUpdate();
		if(updateList != null && updateList.get("delete") != null && updateList.get("delete").size() > 0){ 
	 		boolean isDeleded= false;
			for ( SubcategoryBean subcategoryBean7 : updateList.get("delete")) {
	 			try{
		 			categoryHasSubcategoryBean=  new CategoryHasSubcategoryBean();
					categoryHasSubcategoryBean.setCategory(categoryBean);
					categoryHasSubcategoryBean.setSubcategory(subcategoryBean7);
					isDeleded = categoryHasSubcategoryService.deleteByCategory(categoryHasSubcategoryBean);
					if(!isDeleded)
						notDeleted++;
	 			}catch(Exception ex){
	 				ex.printStackTrace();
	 			}
			}
		}
		if(updateList != null && updateList.get("insert") != null && updateList.get("insert").size() > 0){
			insertCategoryHasSubcategory(updateList.get("insert"));
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<String,List<SubcategoryBean>> prepareForUpdate(){
		if(modelSubcategoryBeans.getTarget() != null && modelSubcategoryBeans.getTarget().size() > 0
				&& targetSubcategoryBeans != null && targetSubcategoryBeans.size() > 0){
			// list of deletedList
			Map<String,List<SubcategoryBean>> updateList = new HashedMap();
			List<SubcategoryBean> deleteList = new ArrayList<SubcategoryBean>();
			List<SubcategoryBean> insertList = new ArrayList<SubcategoryBean>();
			boolean exist = false;
			for(SubcategoryBean bean : targetSubcategoryBeans){
				exist = false;
				for(SubcategoryBean temp : modelSubcategoryBeans.getTarget()){
					if(bean.getId() == temp.getId())
						exist = true;
				}
				if(!exist)
					deleteList.add(bean);
					
			}
			for(SubcategoryBean bean : modelSubcategoryBeans.getTarget()){
				exist = false;
				for(SubcategoryBean temp : targetSubcategoryBeans){
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
	
	String categoryMsg="";
	private int notDeleted = 0 ;
	public void saveCategory(ActionEvent action) {
		if (categoryBean.getId() != 0) {			
			categoryService.update(categoryBean);
			updateCategoryHasSubcategory();
			String text = " Updated Successfully !! ";
			if(notDeleted > 0)
				text += "<br>but cannot delet some Sections !!<br>because its parent";
			RequestContext.getCurrentInstance().showMessageInDialog(
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated", text));
		} else {
			List<CategoryBean>	beans=  categoryService.getCategoryName(categoryBean.getNameEn(), categoryBean.getNameAr());
			if(beans.size()>0){
				categoryMsg = "This category already exist";
				return;
			}
			else{
				categoryMsg="";
			}
			categoryService.insert(categoryBean);
			insertCategoryHasSubcategory(modelSubcategoryBeans.getTarget());
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Inserted", "Inserted Successfully !!"));
		}
		categoryBean = new CategoryBean();
		categoryHasSubcategoryBean = new CategoryHasSubcategoryBean();
		fillAllSubcategoryBeans();
		saveOrUpdateCategory ="saveCategory";

	}
	// Setters & Getters
	public CategoryBean getCategoryBean() {
		return categoryBean;
	}
	public void setCategoryBean(CategoryBean categoryBean) {
		this.categoryBean = categoryBean;
	}
	public CategoryService getCategoryService() {
		return categoryService;
	}
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public String getSelectedId() {
		return SelectedId;
	}
	public void setSelectedId(String selectedId) {
		SelectedId = selectedId;
	}
	public String getSaveOrUpdateCategory() {
		return saveOrUpdateCategory;
	}
	public void setSaveOrUpdateCategory(String saveOrUpdateCategory) {
		this.saveOrUpdateCategory = saveOrUpdateCategory;
	}
	public SubcategoryService getSubcategoryService() {
		return subcategoryService;
	}
	public void setSubcategoryService(SubcategoryService subcategoryService) {
		this.subcategoryService = subcategoryService;
	} 
	public DualListModel<SubcategoryBean> getModelSubcategoryBeans() {
		return modelSubcategoryBeans;
	}
	public void setModelSubcategoryBeans(DualListModel<SubcategoryBean> modelSubcategoryBeans) {
		this.modelSubcategoryBeans = modelSubcategoryBeans;
	}
	public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for(Object item : event.getItems()) {
            builder.append(((SubcategoryBean) item).getNameAr()).append("<br />");
        }
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Items Transferred");
        msg.setDetail(builder.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }
    public void onUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }
    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }
	public String getCategoryMsg() {
		return categoryMsg;
	}
	public void setCategoryMsg(String categoryMsg) {
		this.categoryMsg = categoryMsg;
	} 
}
