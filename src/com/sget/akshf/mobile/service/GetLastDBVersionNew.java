package com.sget.akshf.mobile.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.akshffeen.utils.Utils;
import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.DataBaseVersion;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.DataBaseVersionService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.mobile.api.ResponseFormatData;

/**
 * 
 * @author JDeeb Web Service For Get Last DB Version
 */
@Path("/getLastDBVersionNew")
public class GetLastDBVersionNew
{
	private Utils utils = Utils.getInstance();

	private Gson gson = new Gson();
	
	private DataBaseVersionService service = new DataBaseVersionService();
	
//	@Context
//	private HttpServletRequest httpRequest;
	
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getLastDBVersion(@FormParam("version") int version)
	{
		if (version > 0)
		{
			try
			{
				DataBaseVersion dataBaseVersion = service.isVersionLastNew(version);
				
				if(dataBaseVersion==null)
					dataBaseVersion = service.getLastVersion();

				if (dataBaseVersion != null)
				{
					if (dataBaseVersion != null && dataBaseVersion.getVersion() > 0 && !dataBaseVersion.getDataBaseName().trim().equalsIgnoreCase(""))
					{
						/*
						 * if(version >= bean.getVersion()) return
						 * gson.toJson(new
						 * ErrorAPI(ResponseFactory.CODE_NO_NEW_VERSIONS
						 * ,ResponseFactory.MESSAGE_NO_NEW_VERSIONS));
						 */
//						StringBuffer baseURL = httpRequest.getRequestURL();
//						String path = httpRequest.getServletPath();
//						String url = utils.getXMLkey("AkshffeenAdmin");
//						System.out.println("URL : " + url);
						dataBaseVersion.setDataBaseName(utils.getXMLkey("AkshffeenAdmin") + DBConstants.SQLITE_DB_FOLDER + dataBaseVersion.getDataBaseName());
						
						ResponseFormatData data = new ResponseFormatData();
						
						data.setCode(ResponseFactory.CODE_SUCCESS);
						data.setMessage(ResponseFactory.MESSAGE_SUCCESS);
						data.setData(dataBaseVersion);
						
						return gson.toJson(data);
					}
					else
					{
						return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL, ResponseFactory.MESSAGE_FAIL));
					}
				}
				else
				{
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_NO_NEW_VERSIONS, ResponseFactory.MESSAGE_NO_NEW_VERSIONS));
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX, ResponseFactory.MESSAGE_FAIL_EX));
			}
		}
		else
		{
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
		}
	}
}
