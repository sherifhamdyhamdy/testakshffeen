package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.CommentsBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.beans.VisitorsInteractiveBean;
import com.sget.akshef.hibernate.entities.CommentsEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;


/**
 * 
 * @author JDeeb
 * This Class contains SOME functions for Mobile Web Services
 * 
 */
public class MobileService {
	private MobileServiceDAO dao = null ;
	
	public MobileService(){
		dao = new MobileServiceDAO();
	}
	// For ( addBranchComment ) Web Service 
	public int addBranchComment(int branchid , int userid , String text){
		return dao.addBranchComment(branchid, userid, text);
	}
	// For ( addSpecialistComment ) Web Service 
	public int addSpecialistComment(int specid , int userid , String text){
		return dao.addSpecialistComment(specid, userid, text);
	}
	// For ( addContentComment ) Web Service 
	public int addContentComment(int contid , int userid , String text){
		return dao.addContentComment(contid, userid, text);
	}
	// For ( addBranchComplaint ) Web Service 
	public int addBranchComplaint(int branchid , int fromUser , String text){
		return dao.addBranchComplaint(branchid, fromUser, text);
	}
	// For ( addBranchMessage ) Web Service	
	public int addBranchMessage(int branchid , int fromUser ,String title , String text){
		return dao.addBranchMessage(branchid, fromUser, title , text);
	}
	// For ( addDoctorMessage ) Web Service	
	public int addDoctorMessage(int doctorId ,int fromUser , String title , String text){
		return dao.addDoctorMessage(doctorId, fromUser, title, text);
	}
	// For ( addBranchToFav ) Web Service 
	public int addBranchToFav(int branchid , int userid){
		return dao.addBranchToFav(branchid, userid);
	}
	// For ( addSpecialistToFav ) Web Service
	public int addSpecialistToFav(int specId , int userId){
		return dao.addSpecialistToFav(specId, userId);
	}
	// For ( addSpecialistToFav ) Web Service
	public int addContentToFav(int contId , int userId){
		return dao.addContentToFav(contId, userId);
	}
	// For ( addUserBranchRating ) Web Service
	public int addUserBranchRating(int branchId,int userId,int rating){
		return dao.addUserBranchRating(branchId, userId, rating);
	}
	// For ( addUserSpecialistRating ) Web Service
	public int addUserSpecialistRating(int specialistId,int userId,int rating){
		return dao.addUserSpecialistRating(specialistId, userId, rating);
	}
	// For ( addUserContentRating ) Web Service
	public int addUserContentRating(int contentId,int userId,int rating){
		return dao.addUserContentRating(contentId, userId, rating);
	}
	// Check If Branch Already in User Favorites
	public boolean checkIfBranchAlreadyFav(int branchid , int userid){
		return dao.checkIfBranchAlreadyFav(branchid , userid);
	}
	// Check If Specialist Already in User Favorites
	public boolean checkIfSpecialistAlreadyFav(int specialist_id , int userid){
		return dao.checkIfSpecialistAlreadyFav(specialist_id, userid);
	}
	// Check If Content Already in User Favorites
	public boolean checkIfContentAlreadyFav(int contentId , int userid){
		return dao.checkIfContentAlreadyFav(contentId, userid);
	}
	public int addVisitorSuggetComplain(VisitorsInteractiveBean bean){
		return dao.addVisitorSuggetComplain(bean);
	}
	public int deleteFavorite(int appCat , int id , int userId){
		return dao.deleteFavorite(appCat , id , userId);
	}
	// Push Notifications
	public int addAndroidDeviceID(String registerationId, int platform){
		return dao.addAndroidDeviceID(registerationId, platform);
	}
	// Get All Android Devices RegisterationID
	public List<String> getAllAndroidDeviceID(){
		return dao.getAllAndroidDeviceID();
	}
	
	
	////// Get Comments
	// Get Branch Comments
	public List<CommentsBean> getBranchComments(int start , int limit , int branchID){
		List<Object[]> results = dao.getBranchComments(start, limit, branchID);
    	List<CommentsBean> beans = new ArrayList<CommentsBean>();
    	CommentsBean bean ;
    	if(results != null){
	    	for(Object[] inst : results){
	    		CommentsEntity commEntity = (CommentsEntity) inst[0] ;
	    		UsersEntity userEntity = (UsersEntity) inst[1];
	    		
	    		bean = new CommentsBean();
	    		fillComment(bean, commEntity , userEntity);
	    		
	    		beans.add(bean);    		
	    	}
    	}
    	return beans;
	}
	// getSpecialistComments
	public List<CommentsBean> getSpecialistComments(int start , int limit , int specID){
		List<Object[]> results = dao.getSpecialistComments(start, limit, specID);
    	List<CommentsBean> beans = new ArrayList<CommentsBean>();
    	CommentsBean bean ;
    	if(results != null){
	    	for(Object[] inst : results){
	    		CommentsEntity commEntity = (CommentsEntity) inst[0] ;
	    		UsersEntity userEntity = (UsersEntity) inst[1];
	    		
	    		bean = new CommentsBean();
	    		fillComment(bean, commEntity , userEntity);
	    		
	    		beans.add(bean);    		
	    	}
    	}
    	return beans;
	}
	// get Content Comments
	public List<CommentsBean> getContentComments(int start , int limit , int contID){
		List<Object[]> results = dao.getContentComments(start, limit, contID);
    	List<CommentsBean> beans = new ArrayList<CommentsBean>();
    	CommentsBean bean ;
    	if(results != null){
	    	for(Object[] inst : results){
	    		CommentsEntity commEntity = (CommentsEntity) inst[0] ;
	    		UsersEntity userEntity = (UsersEntity) inst[1];
	    		
	    		bean = new CommentsBean();
	    		fillComment(bean, commEntity , userEntity);
	    		
	    		beans.add(bean);    		
	    	}
    	}
    	return beans;
	}
	private void fillComment(CommentsBean bean , CommentsEntity commEntity , UsersEntity userEntity){
		if(commEntity != null && userEntity != null){
			if(bean == null)
				bean = new CommentsBean();
			
			bean.setId(commEntity.getId());
			bean.setComment(commEntity.getComment());
			bean.setComment_date(commEntity.getComment_date());
			if(commEntity.getBranch() != null){	
				if(bean.getBranch() == null)
					bean.setBranch(new BranchBean());
				bean.getBranch().setId(commEntity.getBranch().getId());
			}
			if(commEntity.getContentDetails() != null){
				if(bean.getContentDetails() == null)
					bean.setContentDetails(new ContentDetailsBean());
				bean.getContentDetails().setId(commEntity.getContentDetails().getId());
			}
			if(commEntity.getSpecialist() != null){
				if(bean.getSpecialist() == null)
					bean.setSpecialist(new SpecialistBean());
				bean.getSpecialist().setId(commEntity.getSpecialist().getId());
			}
			if(bean.getUsers() == null)
				bean.setUsers(new UsersBean());
			bean.getUsers().setId(commEntity.getUsers().getId());
	}
}
}
