package com.sget.akshef.view;

import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.MessageTypesBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.MessageTypesService;

public class MessageTypeControllerAdd {
	private MessageTypesBean messageTypeBean = null;
	private MessageTypesService messageTypeService = null;
	private String SelectedId = null;
	private String saveOrUpdateMessageType = "saveMessageType";
	private boolean saverender = false;

	public boolean isSaverender() {
		return saverender;
	}

	public void setSaverender(boolean saverender) {
		this.saverender = saverender;
	}

	public MessageTypeControllerAdd() {
		messageTypeService = new MessageTypesService();
		messageTypeBean = new MessageTypesBean();
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

		// ////////////////////////////////////////////////////////////////////////forAdd
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		List<RoleHasPermissionBean> roleHasPermissionBeans = (List<RoleHasPermissionBean>) session
				.getAttribute("roleHasPermissionBeans");
		if (roleHasPermissionBeans != null && roleHasPermissionBeans.size() > 0) {
			// System.out.println("roleHasPermissionBeans.size()"+ roleHasPermissionBeans.size());
			for (RoleHasPermissionBean roleHasPermissionBean : roleHasPermissionBeans) {

				if (roleHasPermissionBean.getRole().getName().trim()
						.equalsIgnoreCase("MessageType")) {

					if (roleHasPermissionBean.getPermision().getId() == 2) {
						saverender = true;
						break;
					}
				}
			}
		}
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		try {

			Map<String, String> maps = context.getRequestParameterMap();
			String id = maps.get("id");
			// System.out.println("id    ===   " + id);
			if (id != null && !id.trim().equalsIgnoreCase("")) {
				SelectedId = id;
				saveOrUpdateMessageType = "updateMessageType";
			}
			// System.out.println("SelectedId    ===   " + SelectedId);
			if (SelectedId != null && !SelectedId.equalsIgnoreCase("")) {

				messageTypeBean = messageTypeService.getById(Integer
						.parseInt(SelectedId));

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// saveMessageType
	public void saveMessageType(ActionEvent action) {

		// System.out.println("messageTypeBean11    ===   ::save2");

		if (messageTypeBean.getId() != 0) {
			messageTypeService.update(messageTypeBean);
		} else {
			messageTypeService.insert(messageTypeBean);
		}
		messageTypeBean = new MessageTypesBean();

	}

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

	public String getSelectedId() {
		return SelectedId;
	}

	public void setSelectedId(String selectedId) {
		SelectedId = selectedId;
	}

	public String getSaveOrUpdateMessageType() {
		return saveOrUpdateMessageType;
	}

	public void setSaveOrUpdateMessageType(String saveOrUpdateMessageType) {
		this.saveOrUpdateMessageType = saveOrUpdateMessageType;
	}

}
