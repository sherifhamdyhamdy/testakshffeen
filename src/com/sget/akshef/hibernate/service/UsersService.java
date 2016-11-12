package com.sget.akshef.hibernate.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Set;

import com.akshffeen.utils.Constants;
import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.beans.CertificationBean;
import com.sget.akshef.hibernate.beans.MedicalhistoryBean;
import com.sget.akshef.hibernate.beans.PermisionBean;
import com.sget.akshef.hibernate.beans.ProfessionalExpBean;
import com.sget.akshef.hibernate.beans.RoleBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.dao.UsersDAO;
import com.sget.akshef.hibernate.entities.CertificationEntity;
import com.sget.akshef.hibernate.entities.DistricEntity;
import com.sget.akshef.hibernate.entities.MedicalhistoryEntity;
import com.sget.akshef.hibernate.entities.ProfessionalExpEntity;
import com.sget.akshef.hibernate.entities.RoleHasPermissionEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;

/**
 * 
 * @author JDeeb Users Service
 */
public class UsersService {
	UsersDAO dao = null;
	CompanyService compServ=new CompanyService();

	private Utils utils = Utils.getInstance();
	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

	public UsersService() {
		dao = new UsersDAO();
	}

	public void insert(UsersBean bean) {
		if (bean == null)
			return;
		UsersEntity entity = new UsersEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
		int type=checkUserBranchOrSpecialist(entity.getId());
		if(type==6)
		calculateProgress(entity.getId());
	}
	
	public UsersEntity insertEnt(UsersBean bean) {
		if (bean == null)
			return new UsersEntity();
		UsersEntity entity = new UsersEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
		return entity;
	}
	public void calculateProgress(int id)
	{
		UsersEntity userEnt=	dao.getById(id);
		//checkUserBranchOrSpecialist(userEnt.getId()));
		int countProgress=0;
		
		 
		  if(userEnt.getNationalId()!=null && !userEnt.getNationalId().equals(""))//1
			  countProgress++;
		  if(userEnt.getUsername()!=null && !userEnt.getUsername().equals(""))//2
			  countProgress++;
		  if(userEnt.getPassword()!=null && !userEnt.getPassword().equals(""))//3
			  countProgress++;
		  if(userEnt.getEmail()!=null && !userEnt.getEmail().equals(""))//4
			  countProgress++;
		  if(userEnt.getGender()!=null && !userEnt.getGender().equals("")&& !userEnt.getGender().equals("0"))//5
			  countProgress++;
		  if(userEnt.getProfile_img()!=null && !userEnt.getProfile_img().equals(""))//6
			  countProgress++;
		  if(userEnt.getFirstName()!=null && !userEnt.getFirstName().equals(""))//7
			  countProgress++;
		  if(userEnt.getLastName()!=null && !userEnt.getLastName().equals(""))//8
			  countProgress++;
		  if(userEnt.getAddress()!=null && !userEnt.getAddress().equals(""))//9
			  countProgress++;
		  if(userEnt.getTelephone()!=null && !userEnt.getTelephone().equals("")&& !userEnt.getTelephone().equals("0"))//10
			  countProgress++;
		  if(userEnt.getMobile()!=null && !userEnt.getMobile().equals("")&& !userEnt.getMobile().equals("0"))//11
			  countProgress++;
		  if(userEnt.getMarital_stat()!=null && !userEnt.getMarital_stat().equals(""))//12
			  countProgress++;
		  if(userEnt.getEmergency_contact()!=null && !userEnt.getEmergency_contact().equals(""))//13
			  countProgress++;
		  if(userEnt.getDistric()!=null && !userEnt.getDistric().getId().equals("") && !userEnt.getDistric().getId().equals("0"))//14
			  countProgress++;
		  if(userEnt.getBirthdate()!=null && userEnt.getBirthdate().getTime()!=0)//15
			  countProgress++;
		  if(userEnt.getBlood_type()!=null &&! userEnt.getBlood_type().equals(""))//16
			  countProgress++;
//		  if(userEnt.getMedicalhistorySet()!=null &&! userEnt.getMedicalhistorySet().isEmpty())//17
//			  countProgress++;
		  
		  int progress=countProgress*100/18;
		  userEnt.setProgress(progress+"");
		  dao.update(userEnt);
		  
		  
		  
		
	}

	public boolean updateByMed(UsersBean bean) {
		if (bean == null || bean.getId() < 1)
			return false;

		UsersEntity entity = new UsersEntity();
		fillEntity(entity, bean);
		//insert history list
		MedicalhistoryService medservice=new MedicalhistoryService();
	
	
		if(bean.getMedicalHist()!=null)
			for(MedicalhistoryBean medBean:bean.getMedicalHist())
			{
				
				medservice.insert(medBean);
				
			}
		boolean updated=dao.update(entity);
		CertificationEntity certEnt=new CertificationEntity();
		ProfessionalExpEntity profEnt=new ProfessionalExpEntity();
		
		for(CertificationBean certBean:bean.getCertList())
		{
			certEnt=new CertificationEntity();
//			if(certBean.getDate()!=null && certBean.getDate().getTime()!=0)
//			{
//				certEnt.setDate(new java.sql.Date(certBean.getDate().getTime()));
//			}
			certEnt.setName_ar(certBean.getName_ar());
			certEnt.setDetails_ar(certBean.getDetails_ar());
			certEnt.setDetails_en(certBean.getDetails_en());
			certEnt.setUsers(entity);
			dao.insertCertification(certEnt);
			
			
		}
		
		
		for(ProfessionalExpBean profBean:bean.getProfList())
		{
			profEnt=new ProfessionalExpEntity();
			if(profBean.getStartDate()!=null && profBean.getStartDate().getTime()!=0)
			{
				profEnt.setStartDate(new java.sql.Date(profBean.getStartDate().getTime()));
			}
			if(profBean.getEndDate()!=null && profBean.getEndDate().getTime()!=0)
			{
				profEnt.setEndDate(new java.sql.Date(profBean.getEndDate().getTime()));
			}
			
			profEnt.setDetails(profBean.getDetails());
			profEnt.setPlace(profBean.getPlace());
			profEnt.setPosition(profBean.getPosition());
			profEnt.setUsers(entity);
			dao.insertProfessional(profEnt);
		}
		
		
		int type=checkUserBranchOrSpecialist(entity.getId());
		if(type==6)
		calculateProgress(entity.getId());
		return updated;
		
		

	}
	
	
	public boolean update(UsersBean bean) {
		if (bean == null || bean.getId() < 1)
			return false;

		UsersEntity entity = new UsersEntity();
		fillEntity(entity, bean);
		boolean updated=dao.update(entity);
		int type=checkUserBranchOrSpecialist(entity.getId());
		if(type==6)
		calculateProgress(entity.getId());
		return updated;
	}
		
	

	public boolean delete(UsersBean bean) {
		if (bean == null || bean.getId() < 1)
			return false;
		UsersEntity entity = new UsersEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);

	}

	// Get By ID
	public UsersBean getById(int id) {
		if (id < 1)
			return null;
		UsersBean bean = new UsersBean();
		UsersEntity entity = dao.getById(id);
		fillBean(bean, entity);
		return bean;
	}

	// Get All
	public List<UsersBean> getAll() {
		List<UsersEntity> entities = dao.getAll();
		List<UsersBean> beans = new ArrayList<UsersBean>();
		UsersBean bean;
		if (entities != null && entities.size() > 0) {
			for (UsersEntity entity : entities) {
				bean = new UsersBean();
				fillBean(bean, entity);
				beans.add(bean);
			}
		}
		return beans;
	}

	// Login Service
	public UsersBean loginUser(String username, String pass) {
		if (username != null && !username.trim().equalsIgnoreCase("") && pass != null && !pass.trim().equalsIgnoreCase("")) {
			UsersBean bean = new UsersBean();
			UsersEntity entity = dao.loginUser(username, pass, null, 0);
			fillBean(bean, entity);
			int type=dao.checkUserBranchOrSpecialist(bean.getId());
			bean.setUserType(type);
			return bean;
		} else
			return null;
	}

	// Login Service by url
	public UsersBean loginUser(String username, String pass, String url, String token, int platform)
	{
		if (username != null && !username.trim().equalsIgnoreCase("") && pass != null && !pass.trim().equalsIgnoreCase(""))
		{
			UsersBean bean = new UsersBean();
			UsersEntity entity = dao.loginUser(username, pass, token, platform);
			fillBeanByurl(bean, entity, url);
			return bean;
		}
		else
			return null;
	}
	
	//validate Email liliane 29/11/2015
	
	  public UsersBean validateEmail(String email){
		  UsersBean bean = new UsersBean();
			if (email != null && !email.trim().equalsIgnoreCase("") ) {
				UsersEntity entity = dao.validateEmail(email);
				fillBean(bean, entity);
				
			}
			return bean; 	
	  }
	

	// Check User if exist
	public boolean checkUserInRegisterByUsername(String username) {
		return dao.checkUserInRegisterByUsername(username);
	}

	public boolean checkUserInRegisterByEmail(String email) {
		return dao.checkUserInRegisterByEmail(email);
	}
		
	public boolean checkUserInRegisterByMobile(String email) {
		return dao.checkUserInRegisterByMobile(email);
	}
	//check if user or email exist where the id exist
	
	
	public boolean checkUserInRegisterByUsername(String username,int id) {
		return dao.checkUserInRegisterByUsername(username, id);
	}

	public boolean checkUserInRegisterByEmail(String email,int id) {
		return dao.checkUserInRegisterByEmail(email, id);
	}
	
	public boolean updatePassword(String username,String password){
		return dao.updatePassword( username, password);
	}
	
	// Fill Bean From Entity
	// Get By ID
	public UsersBean checkUser(UsersBean bean) {
		if (bean == null)
			return null;

		UsersEntity entity = new UsersEntity();
		
		
		int flag=compServ.checkIfUserInCompany(bean.getId());
		bean.setUserType(checkUserBranchOrSpecialist(bean.getId()));
		if(flag<=1)
		bean.setUserCompany(false);	
		else
		bean.setUserCompany(true);		
		return bean;
	}

	public List<RoleHasPermissionBean> getAllRolesPermission(UsersBean bean) {
		if (bean == null || bean.getId() < 1)
			return null;

		UsersEntity entity = new UsersEntity();
		fillEntity(entity, bean);
		List<RoleHasPermissionEntity> roleHasPermissionEntities = dao.getAllRolesPermission(entity);
		List<RoleHasPermissionBean> beans = new ArrayList<RoleHasPermissionBean>();
		RoleHasPermissionBean bean2;
		for (RoleHasPermissionEntity entity2 : roleHasPermissionEntities) {
			bean2 = new RoleHasPermissionBean();
			fillRolePermission(bean2, entity2);
			beans.add(bean2);
		}
		return beans;

	}

	private void fillRolePermission(RoleHasPermissionBean bean2, RoleHasPermissionEntity entity2) {

		if (bean2.getRole() == null)
			bean2.setRole(new RoleBean());


		if (bean2.getPermision() == null)
			bean2.setPermision(new PermisionBean());



	}

	// Fill Bean From Entity
	public void fillBean(UsersBean bean, UsersEntity entity) {
		if (entity == null)
			return;
		if (bean == null)
			bean = new UsersBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		bean.setUsername(entity.getUsername());
//		bean.setPassword(entity.getPassword());
		bean.setNationalId(entity.getNationalId());
		bean.setEmail(entity.getEmail());
		bean.setGender(entity.getGender());
		bean.setFirstName(entity.getFirstName());
		bean.setLastName(entity.getLastName());
		bean.setAddress(entity.getAddress());
		bean.setDistrict_id(entity.getDistric()!=null?entity.getDistric().getId():0);
		bean.setDistrictST(entity .getDistric()!=null?entity .getDistric().getNameAr():"");
		bean.setCityST(entity.getDistric()!=null?entity.getDistric().getCity().getNameAr():"");
		bean.setCountryST(entity .getDistric()!=null?entity.getDistric().getCity().getCountry().getNameAr():"");
		if(entity.getBirthdate()!=null && entity.getBirthdate().getTime()!=0)
		bean.setBirthdate(new java.sql.Date(entity.getBirthdate().getTime()));
		bean.setTelephone(entity.getTelephone());
		bean.setMobile(entity.getMobile());
		bean.setMarital_stat(entity.getMarital_stat());
		bean.setEmergency_contact(entity.getEmergency_contact());
		bean.setGenderSt(entity.getGender()==1?"ذكر":"أنثى");
		bean.setProfile_img(entity.getProfile_img()!=null &&!entity.getProfile_img().equals("")  ?entity.getProfile_img():"");
		if (entity.getActive() != null)
			bean.setActive(entity.getActive());
		bean.setCreationDate(entity.getCreationDate()!=null?new java.util.Date(entity.getCreationDate().getTime()):new java.util.Date());
		bean.setEmailCount(entity.getEmailCount()!=null?entity.getEmailCount():0);
		bean.setPass_creationDate(entity.getPass_creationDate());
		bean.setPass_emailCount(entity.getPass_emailCount()!=null?entity.getPass_emailCount():0);
		bean.setActive_email(entity.getActive_email()!=null?entity.getActive_email():0);
		bean.setBlood_type(entity.getBlood_type());
		bean.setProgress(entity.getProgress());
		
		//fill medical hist
		List<MedicalhistoryBean> medHistBean=new ArrayList<MedicalhistoryBean>();
		MedicalhistoryBean medBean;
		if(entity.getMedicalhistorySet()!=null)
			for(MedicalhistoryEntity entityMed:entity.getMedicalhistorySet())
			{
				medBean=new MedicalhistoryBean();
				medBean.setId(entity.getId());
				medBean.setDate_from(entityMed.getDate_from()!=null?new java.util.Date(entityMed.getDate_from().getTime()) :new java.util.Date());
				medBean.setDate_to(entityMed.getDate_to()!=null?new java.util.Date(entityMed.getDate_to().getTime()) :new java.util.Date());
				medBean.setSpec_name(entityMed.getSpec_name());
				medBean.setSickness(entityMed.getSickness());
				medBean.setSick_type(entityMed.getSick_type());
				medBean.setReport_type(entityMed.getReport_type());
				medBean.setReport_details(entityMed.getReport_details());
				medBean.setDetails(entityMed.getDetails());
				medBean.setReport_attach(entityMed.getReport_attach());
				medHistBean.add(medBean);
			}
		
  	  bean.setMedicalHist(medHistBean);
  	List<CertificationBean> certificationBeans=new ArrayList<CertificationBean>();
	List<ProfessionalExpBean> profBeans= new ArrayList<ProfessionalExpBean>();
  	CertificationBean cetbean;
//	if(entity.getCertificationSet() != null){
//		for(CertificationEntity cetEntity:entity.getCertificationSet())
//		{
//			cetbean=new CertificationBean();
//			cetbean.setId(cetEntity.getId());
//			cetbean.setName_ar(cetEntity.getName_ar());
//			cetbean.setDate(cetEntity.getDate()+"");
//
//			cetbean.setDetails_ar(cetEntity.getDetails_ar());
//			cetbean.setDetails_en(cetEntity.getDetails_en());
//			
//			certificationBeans.add(cetbean);
//		}
//		
//		bean.setCertList(certificationBeans);
//		
//	}
	
	ProfessionalExpBean profBean;
	if(entity.getProfessionalSet() != null){
		for(ProfessionalExpEntity professionalExpEntity:entity.getProfessionalSet())
		{
			profBean=new ProfessionalExpBean();
			profBean.setId(professionalExpEntity.getId());
			profBean.setPlace(professionalExpEntity.getPlace());
			profBean.setStartDate(professionalExpEntity.getStartDate());
			profBean.setEndDate(professionalExpEntity.getEndDate());
			profBean.setPosition(professionalExpEntity.getPosition());
			profBean.setDetails(professionalExpEntity.getDetails());
			profBeans.add(profBean);
		}
		
		bean.setProfList(profBeans);
		
	}
		
	}

	
	
	// Fill Bean From Entity
	public void fillBeanByurl(UsersBean bean, UsersEntity entity, String url)
	{
		if (entity == null)
			return;
		if (bean == null)
			bean = new UsersBean();
		// Basics Data
		if (entity == null)
			return;
		if (bean == null)
			bean = new UsersBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		bean.setUsername(entity.getUsername());
		// bean.setPassword(entity.getPassword());
		bean.setNationalId(entity.getNationalId());
		bean.setEmail(entity.getEmail());
		bean.setGender(entity.getGender());
		bean.setFirstName(entity.getFirstName());
		bean.setLastName(entity.getLastName());
		bean.setAddress(entity.getAddress());
		bean.setDistrict_id(entity.getDistric() != null ? entity.getDistric().getId() : 0);
		bean.setDistrictST(entity.getDistric() != null ? entity.getDistric().getNameAr() : "");
		bean.setCityST(entity.getDistric() != null ? entity.getDistric().getCity().getNameAr() : "");
		bean.setCountryST(entity.getDistric() != null ? entity.getDistric().getCity().getCountry().getNameAr() : "");
		
		if (entity.getBirthdate() != null && entity.getBirthdate().getTime() != 0)
			bean.setBirthdate(new java.sql.Date(entity.getBirthdate().getTime()));
		
		bean.setBirthdateStr(formatter.format(entity.getBirthdate()));

		bean.setTelephone(entity.getTelephone());
		bean.setMobile(entity.getMobile());
		bean.setMarital_stat(entity.getMarital_stat());
		bean.setEmergency_contact(entity.getEmergency_contact());
		bean.setGenderSt(entity.getGender() == 1 ? "ذكر" : "أنثى");
		bean.setProfile_img(utils.hasValue(entity.getProfile_img()) ? url + "/images/users/" + entity.getProfile_img() : "");
		
		if (entity.getActive() != null)
			bean.setActive(entity.getActive());
		bean.setCreationDate(entity.getCreationDate() != null ? new java.util.Date(entity.getCreationDate().getTime()) : new java.util.Date());
		bean.setEmailCount(entity.getEmailCount() != null ? entity.getEmailCount() : 0);
		bean.setPass_creationDate(entity.getPass_creationDate());
		bean.setPass_emailCount(entity.getPass_emailCount() != null ? entity.getPass_emailCount() : 0);
		bean.setActive_email(entity.getActive_email() != null ? entity.getActive_email() : 0);
		bean.setBlood_type(entity.getBlood_type());
		bean.setProgress(entity.getProgress());
		
		bean.setUser_type(entity.getUser_type());

		
		// fill medical hist
		List<MedicalhistoryBean> medHistBean = new ArrayList<MedicalhistoryBean>();
		MedicalhistoryBean medBean;
		if (entity.getMedicalhistorySet() != null)
			for (MedicalhistoryEntity entityMed : entity.getMedicalhistorySet())
			{
				medBean = new MedicalhistoryBean();
				medBean.setId(entity.getId());
				medBean.setDate_from(entityMed.getDate_from() != null ? new java.util.Date(entityMed.getDate_from().getTime()) : new java.util.Date());
				medBean.setDate_to(entityMed.getDate_to() != null ? new java.util.Date(entityMed.getDate_to().getTime()) : new java.util.Date());
				medBean.setSpec_name(entityMed.getSpec_name());
				medBean.setSickness(entityMed.getSickness());
				medBean.setSick_type(entityMed.getSick_type());
				medBean.setReport_type(entityMed.getReport_type());
				medBean.setReport_details(entityMed.getReport_details());
				medBean.setDetails(entityMed.getDetails());
				medBean.setReport_attach(entityMed.getReport_attach());
				medHistBean.add(medBean);
			}
		
		bean.setMedicalHist(medHistBean);
		
		List<CertificationBean> certificationBeans = new ArrayList<CertificationBean>();
		List<ProfessionalExpBean> profBeans = new ArrayList<ProfessionalExpBean>();
		CertificationBean cetbean;
//		if (entity.getCertificationSet() != null)
//		{
//			for (CertificationEntity cetEntity : entity.getCertificationSet())
//			{
//				cetbean = new CertificationBean();
//				cetbean.setId(cetEntity.getId());
//				cetbean.setName_ar(cetEntity.getName_ar());
//				cetbean.setDate(cetEntity.getDate() + "");
//				
//				cetbean.setDetails_ar(cetEntity.getDetails_ar());
//				cetbean.setDetails_en(cetEntity.getDetails_en());
//				
//				certificationBeans.add(cetbean);
//			}
//			
//			bean.setCertList(certificationBeans);
//			
//		}
		
		ProfessionalExpBean profBean;
		if (entity.getProfessionalSet() != null)
		{
			for (ProfessionalExpEntity professionalExpEntity : entity.getProfessionalSet())
			{
				profBean = new ProfessionalExpBean();
				profBean.setId(professionalExpEntity.getId());
				profBean.setPlace(professionalExpEntity.getPlace());
				profBean.setStartDate(professionalExpEntity.getStartDate());
				profBean.setEndDate(professionalExpEntity.getEndDate());
				profBean.setPosition(professionalExpEntity.getPosition());
				profBeans.add(profBean);
			}
			
			bean.setProfList(profBeans);
			
		}
		
	}



	// Fill Entity From Bean
	public void fillEntity(UsersEntity entity, UsersBean bean) {
		if (bean == null)
			return;
		if (entity == null)
			entity = new UsersEntity();
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setNameEn(bean.getNameEn());
		entity.setNameAr(bean.getNameAr());
		entity.setUsername(bean.getUsername());
		entity.setPassword(utils.encryptText(bean.getPassword()));
		entity.setNationalId(bean.getNationalId());
		entity.setEmail(bean.getEmail());
		entity.setGender(bean.getGender());
		entity.setFirstName(bean.getFirstName());
		entity.setLastName(bean.getLastName());
		entity.setAddress(bean.getAddress());
		if(bean.getBirthdate()!=null && bean.getBirthdate().getTime()!=0)
		entity.setBirthdate(new java.sql.Date(bean.getBirthdate().getTime()));
		entity.setTelephone(bean.getTelephone());
		entity.setMobile(bean.getMobile());
		entity.setMarital_stat(bean.getMarital_stat());
		entity.setEmergency_contact(bean.getEmergency_contact());
		entity.setProfile_img(bean.getProfile_img()!=null && !bean.getProfile_img().equals("")? bean.getProfile_img():"");
		entity.setCreationDate(bean.getCreationDate()!=null ?  new java.sql.Date(bean.getCreationDate().getTime())   :new java.sql.Date(new java.util.Date().getTime()));
		entity.setEmailCount(bean.getEmailCount());
		entity.setActive_email(bean.getActive_email());
		entity.setPass_creationDate(bean.getPass_creationDate()!=null ?  new java.sql.Date(bean.getPass_creationDate().getTime())   :new java.sql.Date(new java.util.Date().getTime()));
		entity.setPass_emailCount(bean.getPass_emailCount());
		entity.setBlood_type(bean.getBlood_type());
		entity.setProgress(bean.getProgress());
		if(bean.getDistrict_id()!=0)
		{
		if(entity.getDistric() == null)
			entity.setDistric(new DistricEntity());
		entity.getDistric().setId(bean.getDistrict_id());
		}
  	
		
		entity.setToken(bean.getToken());
		entity.setPlatform(bean.getPlatform());
		entity.setLogin(bean.getLogin());

	}
	
	
	public UsersEntity fillEntity( UsersBean bean) {
		UsersEntity entity=new UsersEntity();
		if (bean == null)
			return null;
	
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setNameEn(bean.getNameEn());
		entity.setNameAr(bean.getNameAr());
		entity.setUsername(bean.getUsername());
		entity.setPassword(utils.encryptText(bean.getPassword()));
		entity.setNationalId(bean.getNationalId());
		entity.setEmail(bean.getEmail());
		entity.setGender(bean.getGender());
		entity.setFirstName(bean.getFirstName());
		entity.setLastName(bean.getLastName());
		entity.setAddress(bean.getAddress());
		if(bean.getBirthdate()!=null && bean.getBirthdate().getTime()!=0)
		entity.setBirthdate(new java.sql.Date(bean.getBirthdate().getTime()));
		entity.setTelephone(bean.getTelephone());
		entity.setMobile(bean.getMobile());
		entity.setMarital_stat(bean.getMarital_stat());
		entity.setEmergency_contact(bean.getEmergency_contact());
		entity.setProfile_img(bean.getProfile_img()!=null&& bean.getProfile_img()!=null? bean.getProfile_img():"");
		entity.setCreationDate(bean.getCreationDate()!=null ?  new java.sql.Date(bean.getCreationDate().getTime())   :new java.sql.Date(new java.util.Date().getTime()));
		entity.setPass_creationDate(bean.getPass_creationDate()!=null ?  new java.sql.Date(bean.getPass_creationDate().getTime())   :new java.sql.Date(new java.util.Date().getTime()));
		entity.setPass_emailCount(bean.getPass_emailCount());
		entity.setEmailCount(bean.getEmailCount());
		entity.setActive_email(bean.getActive_email());
		entity.setBlood_type(bean.getBlood_type());
		entity.setProgress(bean.getProgress());
		if(bean.getDistrict_id()!=0)
		{
		if(entity.getDistric() == null)
			entity.setDistric(new DistricEntity());
		entity.getDistric().setId(bean.getDistrict_id());
		}
		
		
  	
		return entity;
		
	}

	public int checkUserByNatId(String nationalId) {
		int user=0;
		if (nationalId != null && !nationalId.equals("")) {
			UsersDAO userDao = new UsersDAO();
			user = userDao.checkNationalId(nationalId);
		}

		return user;
	}

	public int checkUserBranchOrSpecialist(int userID){
		return dao.checkUserBranchOrSpecialist(userID);
	}
	
	// JDeeb Get Users whom i send MSGs to them or recieve
	public List<UsersBean> getCommunications(int userId) {
		List<UsersEntity> entities = dao.getCommunications(userId);
		List<UsersBean> beans = new ArrayList<UsersBean>();
		UsersBean bean;
		if (entities != null && entities.size() > 0) {
			for (UsersEntity entity : entities) {
				bean = new UsersBean();
				fillBean(bean, entity);
				bean.setUserType(checkUserBranchOrSpecialist(entity.getId()));
				beans.add(bean);
			}
		}
		return beans;
	}
	// Old Project Functions missed
	public List<UsersBean> getByMatchNationalId(String NationalId) {

    	List<UsersEntity> entities = dao.getByMatchNationalId(NationalId);
    	List<UsersBean> beans = new ArrayList<UsersBean>();
    	UsersBean bean ;
    	for(UsersEntity entity : entities){
    		bean = new UsersBean();
    		fillBean(bean, entity);
    		beans.add(bean);
    	}
    	return beans;
    }
	/**
	 * JDeeb get all users in specified group
	 * 
	 * @param groupId
	 * @return List UsersBean
	 */
	  public Integer login(String username,String pass)
	   {
		   Integer userId=0;
	       
	        
	         userId= dao.isUserExist(username, pass);
	       
	     return userId;   
	       
	   }
	
	
	
	
	
	public List<UsersBean> getAllByGroupId(int groupId) {
		List<UsersEntity> entities = dao.getAllByGroupId(groupId);
		List<UsersBean> beans = new ArrayList<UsersBean>();
		UsersBean bean;
		if (entities != null && entities.size() > 0) {
			for (UsersEntity entity : entities) {
				bean = new UsersBean();
				fillBean(bean, entity);
				beans.add(bean);
			}
		}
		return beans;
	}
}
