/**
 * 
 */
package com.sget.akshef.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.MessageTypesBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.MessageTypesService;

/**
 * @author Abdelazeem
 * 
 */
public class MessageTypeControllerAll {
	private MessageTypesBean messageTypeBean;
	private MessageTypesService messageTypeService;
	private List<MessageTypesBean> messageTypeBeans = null;
	private MessageTypesBean[] selectedMessageType;
	private int selectedMessageTypeLength;
	private String selected;
	private String message = "";
	private boolean updaterender = false;
	private boolean deleterender = false;

	public boolean isUpdaterender() {
		return updaterender;
	}

	public void setUpdaterender(boolean updaterender) {
		this.updaterender = updaterender;
	}

	public boolean isDeleterender() {
		return deleterender;
	}

	public void setDeleterender(boolean deleterender) {
		this.deleterender = deleterender;
	}

	public MessageTypeControllerAll() {

		messageTypeBean = new MessageTypesBean();
		messageTypeService = new MessageTypesService();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		// messageTypeBeans = new ArrayList<MessageTypeBean>();
		// if (messageTypeBeans == null)
		// getAll();
		// if (messageTypeBeans != null && messageTypeBeans.size() == 0)
		// getAll();
		// ////////////////////////////////////////////////////////////////////////for
		// ALL

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		List<RoleHasPermissionBean> roleHasPermissionBeans = (List<RoleHasPermissionBean>) session
				.getAttribute("roleHasPermissionBeans");
		if (roleHasPermissionBeans != null && roleHasPermissionBeans.size() > 0) {
			// System.out.println("roleHasPermissionBeans.size()"+ roleHasPermissionBeans.size());
			for (RoleHasPermissionBean roleHasPermissionBean : roleHasPermissionBeans) {
				
				if (roleHasPermissionBean.getRole().getName().trim()
						.equalsIgnoreCase("MessageType")) {
					if (roleHasPermissionBean.getPermision().getId() == 4) {
						deleterender = true;
					}
					if (roleHasPermissionBean.getPermision().getId() == 3) {
						updaterender = true;
					}
				}
			}
		}
		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		getAll();

		int arrayCount = messageTypeBeans.size();
		selectedMessageType = new MessageTypesBean[arrayCount];

		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		// System.out.println("selectedMessageType  delete fun ::: "+ selectedMessageType.length);

		if (selectedMessageType.length < 1) {
			message = "select at least one";
			return;
		}

		for (int count = 0; count < selectedMessageType.length; count++) {

			messageTypeBean = selectedMessageType[count];

			deleteStatus = messageTypeService.delete(messageTypeBean);
			if (deleteStatus)
				deleteCount++;

		}
		if (deleteCount == selectedMessageType.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();

		/*
		 * messageTypeBean = new MessageTypeBean();
		 * messageTypeBean.setId(Integer.parseInt(selected));
		 * messageTypeService.delete(messageTypeBean);
		 */
	}

	// update
	public void update() {

		// if (!allowUpdate)
		// return;
		//
		// System.out.println("herer update fu :::  ");


		if (selectedMessageType.length != 1) {
			message = "select one";
			return;
		}

		if (selectedMessageType != null && selectedMessageType.length == 1) {
			String page = "/systemadmin/messageTypeAdd.xhtml?id="
					+ selectedMessageType[0].getId();
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			try {
				context.redirect(context.getRequestContextPath() + page);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void redirecttoPage(String page) {
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {
			ec.redirect(ec.getRequestContextPath() + page);

		} catch (IOException e) {

			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					e.getMessage());
		}
	}

	// getAll
	public void getAll() {

		messageTypeBeans = new ArrayList<MessageTypesBean>();
		messageTypeBeans.addAll(messageTypeService.getAll());

		// add to test only
		// System.out.println("messageTypeBeans.size(): "+ messageTypeBeans.size());
		/*for (MessageTypesBean bean : messageTypeBeans) {
			// System.out.println("bean.getId() : " + bean.getId());
			// System.out.println("bean.getNameAr() : " + bean.getName());
		}*/

	}

	// /geter setter

	public MessageTypesBean getMessageTypeBean() {
		return messageTypeBean;
	}

	public void setMessageTypeBean(MessageTypesBean messageTypeBean) {
		this.messageTypeBean = messageTypeBean;
	}

	public MessageTypesService getMessageTypeService() {
		return messageTypeService;
	}

	public void setMessageTypeService(MessageTypesService messageTypeService) {
		this.messageTypeService = messageTypeService;
	}

	public List<MessageTypesBean> getMessageTypeBeans() {
		return messageTypeBeans;
	}

	public void setMessageTypeBeans(List<MessageTypesBean> messageTypeBeans) {
		this.messageTypeBeans = messageTypeBeans;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public MessageTypesBean[] getSelectedMessageType() {
		return selectedMessageType;
	}

	public void setSelectedMessageType(MessageTypesBean[] selectedMessageType) {
		this.selectedMessageType = selectedMessageType;
		selectedMessageTypeLength = selectedMessageType.length;
	}

	public int getSelectedMessageTypeLength() {
		return selectedMessageTypeLength;
	}

	public void setSelectedMessageTypeLength(int selectedMessageTypeLength) {
		this.selectedMessageTypeLength = selectedMessageTypeLength;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
