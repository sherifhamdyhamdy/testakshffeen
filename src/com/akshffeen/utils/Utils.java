package com.akshffeen.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.sget.akshef.beans.ScheduleBean;
import com.sget.akshef.beans.Schedules;
import com.sget.akshef.beans.SchedulesBean;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.utils.Performance;

public class Utils implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static Utils uniqInstance;
	
	public static final byte[] sharedvector = { 0x01, 0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11 };
	public static final String encryption_key = "!@#@!$#$$%$#%#%";
	public static final String UTF8 = "UTF-8";

	
	private Utils()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static Utils getInstance()
	{
		if (uniqInstance == null)
		{
			uniqInstance = new Utils();
		}
		
		return uniqInstance;
	}
	
	
	
	public String fixRequest(String value)
	{
		if (value == null || value.equals("") || value.equals("null"))
			return "";
		
		return value;
	}
	
	
	
	public boolean checkRequest(Object value)
	{
		if (value == null || value.toString().trim().length() == 0)
			return false;
		else
			return true;
	}
	
	
	
	public boolean checkBlankSpace(Object value)
	{
		if (value == null || value.toString().trim().length() == 0)
			return true;
		else
			return false;
	}
	
	
	
	public boolean checkImage(File file)
	{
		String title = file.getName();
		int index = title.lastIndexOf(".");
		
		if (index == -1)
			return false;
		
		String extension = title.substring(index + 1);
		
		return checkImageExt(extension);
	}
	
	
	
	public boolean checkImageExt(String extension)
	{
		List<String> extensions = new ArrayList<String>();
		
		extensions.add("jpg");
		extensions.add("jpeg");
		extensions.add("png");
		extensions.add("gif");
		extensions.add("tif");
		extensions.add("tiff");
		
		return extensions.contains(extension.toLowerCase());
	}
	
	
	
	public String getMessageResourceString(String bundleName, String key, Locale locale)
	{
		String text;
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
		
		try
		{
			text = bundle.getString(key);
		}
		catch (MissingResourceException e)
		{
			text = "?? key " + key + " not found ??";
		}
		
		return text;
	}
	
	
	public void deleteFile(String path)
	{
		File file = new File(path);
		
		if (file.exists())
			file.delete();
	}
	
	
	
	public void deleteFile(File file)
	{
		if (file.exists())
			file.delete();
	}
	
	
	
	public String generateRandomString()
	{
		Random ran = new Random();
		int top = 30;
		char data = ' ';
		StringBuffer dat = new StringBuffer();
		
		for (int i = 0; i <= top; i++)
		{
			data = (char) (ran.nextInt(25) + 97);
			
			dat.append(data);
		}
		
		return dat.toString();
	}
	
	
	
	public String renameFile(File file)
	{
		String title = file.getName();
		int index = title.lastIndexOf(".");
		
		StringBuffer newTitle = new StringBuffer();
		
		if (index == -1)
			newTitle.append(generateRandomString());
		else
		{
			String extension = title.substring(index + 1);
			newTitle.append(generateRandomString() + "." + extension);
		}
		
		return newTitle.toString();
	}
	
	
	
	public String renameFile(String fileNewName, File file)
	{
		String title = file.getName();
		int index = title.lastIndexOf(".");
		
		StringBuffer newTitle = new StringBuffer();
		
		if (index == -1)
			newTitle.append(generateRandomString());
		else
		{
			String extension = title.substring(index + 1);
			newTitle.append(fileNewName + "." + extension);
		}
		
		return newTitle.toString();
	}
	
	
	
	public void copyFile(File source, File target)
	{
		try
		{
			InputStream in = new FileInputStream(source);
			OutputStream out = new FileOutputStream(target);
			
			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
			
			in.close();
			out.close();
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	
	
	public String getRandomCopiedFileName(File file)
	{
		String newFileName = generateRandomString();
		String title = file.getName();
		
		int index = title.lastIndexOf(".");
		
		if (index == -1)
			return newFileName;
		
		String extension = title.substring(index + 1);
		
		return newFileName + "." + extension;
	}
	
	
	

	
	
	
	public boolean AnumberFound(Object string)
	{
		// Pattern myPattern = Pattern.compile("\\d");
		Pattern myPattern = Pattern.compile("\\W");
		
		Matcher myMatcher = myPattern.matcher(string.toString());
		
		if (myMatcher.find())
			return true;
		
		return false;
	}
	
	
	
	public boolean validateString(Object string)
	{
		// Pattern pattern = Pattern.compile("\\W");
		// Pattern pattern = Pattern.compile("\\d");
		// Pattern pattern = Pattern.compile("[\\pL&&\\p{L1}]+");
		// Pattern pattern = Pattern.compile("[a-zA-Z][\\pL&&\\p{L1}]+");
		Pattern pattern = Pattern.compile("^\\p{L}+$");
		
		Matcher matcher = pattern.matcher(string.toString().replace(" ", ""));
		
		if (!matcher.find())
			return false;
		
		return true;
	}
	
	
	
	public double round(double Rval, int Rpl)
	{
		double p = Math.pow(10, Rpl);
		Rval = Rval * p;
		double tmp = Math.round(Rval);
		return tmp / p;
	}
	
	
	
	
	public Timestamp getCurrentTimeStamp()
	{
		Date date = new Date();
		return new Timestamp(date.getTime());
	}
	
	
	
	
	public String checkLanguage(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("language") == null)
			return "ar";
		else
			return (String) session.getAttribute("language");
	}
	
	
	
	
	public Timestamp getTimeStamp(int days)
	{
		Date today = new Date();
		
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		c.add(Calendar.DATE, days);
		Date date = c.getTime();
		
		// utils.print("today ------> "+today);
		// utils.print("date "+days+" ------> "+date);
		
		return new Timestamp(date.getTime());
	}
	
	
	
	
	public void print(Object... objects)
	{
		int count = 0;
		StringBuilder console = new StringBuilder();
		
		for (Object obj : objects)
		{
			if (count != 0 && count % 2 != 0)
				console.append("-------->");
			else if (count != 0 && count % 2 == 0)
				console.append("\t");
			
			console.append(obj);
			
			count++;
		}
		
		System.out.println(console);
	}
	
	
	
	public String encryptMD5(String text)
	{
		MessageDigest md = null;
		try
		{
			md = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		md.update(text.getBytes());
		
		byte byteData[] = md.digest();
		
		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++)
		{
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		
		print("Hex format : " + sb.toString());
		
		// convert the byte to hex format method 2
		// StringBuffer hexString = new StringBuffer();
		// for (int i=0;i<byteData.length;i++)
		// {
		// String hex=Integer.toHexString(0xff & byteData[i]);
		// if(hex.length()==1) hexString.append('0');
		// hexString.append(hex);
		// }
		//
		// print("Hex format : " + hexString.toString());
		
		return sb.toString();
	}
	
	
	public boolean isInteger(String s)
	{
		try
		{
			Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		// only got here if we didn't return false
		return true;
	}
	
	public boolean isEmpty(List<?> list)
	{
		if(list!=null && !list.isEmpty())
			return false;
		
		return true;
	}

	public boolean isEmpty(String str)
	{
		if(str!=null && str.trim().length()>0)
			return false;
		
		return true;
	}
	
	public boolean hasValue(List<?> list)
	{
		if(list!=null && !list.isEmpty())
			return true;
		
		return false;
	}

	public boolean hasValue(Object obj)
	{
		if(obj!=null)
			return true;
		
		return false;
	}
	
	public String getValue(String str)
	{
		if(str!=null)
			return str;
		
		return "";
	}
	
	public boolean hasValue(Map<?, ?> map)
	{
		if(map!=null && !map.isEmpty())
			return true;
		
		return false;
	}
	
	public boolean hasValue(String str)
	{
		if(str!=null && str.trim().length()>0)
			return true;
		
		return false;
	}

	public boolean hasValue(Double num)
	{
		if(num!=null && num>0)
			return true;
		
		return false;
	}


	public String encryptText(String text) 
	{
		String EncText = "";
		byte[] keyArray = new byte[24];
		byte[] temporaryKey;
		byte[] toEncryptArray = null;

		try 
		{
			toEncryptArray = text.getBytes(UTF8);
			MessageDigest m = MessageDigest.getInstance("MD5");
			temporaryKey = m.digest(encryption_key.getBytes(UTF8));

			if (temporaryKey.length < 24) // DESede require 24 byte length key
			{
				int index = 0;
				for (int i = temporaryKey.length; i < 24; i++) 
				{
					keyArray[i] = temporaryKey[index];
				}
			}

			Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyArray, "DESede"),new IvParameterSpec(sharedvector));
			byte[] encrypted = c.doFinal(toEncryptArray);
			EncText = Base64.encodeBase64String(encrypted);

		}
		catch (Exception e) 
		{
			System.out.println("exception -- > " + e);
		}

		return EncText;
	}

	public String decryptText(String text) 
	{
		String RawText = "";
		byte[] keyArray = new byte[24];
		byte[] temporaryKey;

		try 
		{
			MessageDigest m = MessageDigest.getInstance("MD5");
			temporaryKey = m.digest(encryption_key.getBytes(UTF8));

			if (temporaryKey.length < 24) // DESede require 24 byte length key
			{
				int index = 0;
				for (int i = temporaryKey.length; i < 24; i++) 
				{
					keyArray[i] = temporaryKey[index];
				}
			}

			Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyArray, "DESede"),new IvParameterSpec(sharedvector));
			byte[] decrypted = c.doFinal(Base64.decodeBase64(text));

			RawText = new String(decrypted, "UTF-8");
		}
		catch (Exception e) 
		{
			System.out.println("exception -- > " + e);
		}

		return RawText;
	}



	public String[] generateArray(String value)
	{		
		String[] arr = null;
		
		if(hasValue(value))
		{
			if(value.contains(","))
			{
				String[] values = value.split(",");
				int i=0;
				
				if(hasValue(values))
				{
					arr = new String[values.length];
					
					for(String element : values)
					{
						if(!hasValue(element))
							continue;
						
						arr[i] = element;
						
						i++;
					}
				}
			}
			else
				arr = new String[]{value};			
		}
		
		return arr;
	}

	public List<String> generateList(String value)
	{		
		List<String> list = new ArrayList<String>();
		
		if(hasValue(value))
		{
			if(value.contains(","))
			{
				String[] values = value.split(",");
				
				if(hasValue(values))
				{					
					for(String element : values)
					{
						list.add(element);
					}
				}
			}
			else
				list.add(value);			
		}
		
		return list;
	}

	public Map<String, Boolean> generateMap(String value)
	{		
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		
		if(hasValue(value))
		{
			if(value.contains(","))
			{
				String[] values = value.split(",");
				
				if(hasValue(values))
				{					
					for(String element : values)
					{
						map.put(element, true);
					}
				}
			}
			else
				map.put(value, true);			
		}
		
		return map;
	}
	
	public Integer[] generateArrayInteger(String value)
	{		
		Integer[] arr = null;
		
		try
		{
			if(hasValue(value))
			{
				if(value.contains(","))
				{
					String[] values = value.split(",");
					int i=0;
					
					if(hasValue(values))
					{
						arr = new Integer[values.length];
						
						for(String element : values)
						{
							if(!hasValue(element))
								continue;
							
							arr[i] = Integer.parseInt(element);
							
							i++;
						}
					}
				}
				else
					arr = new Integer[]{Integer.parseInt(value)};			
			}
		}
		catch(NumberFormatException e)
		{
			
		}
		
		
		return arr;
	}
	
	public String generateٍString(String arr[])
	{		
		StringBuilder value = new StringBuilder();

		int i=0;
		
		for(String element : arr)
		{
			if(i>0)
				value.append(",");
			
			value.append(element);
			
			i++;
		}

		return value.toString();
	}
	
	public String generateInt(List<Integer> arr)
	{		
		StringBuilder value = new StringBuilder();

		int i=0;
		
		for(Integer element : arr)
		{
			if(i>0)
				value.append(",");
			
			value.append(element);
			
			i++;
		}

		return value.toString();
	}
	
	public String generateٍString(List<String> arr)
	{		
		StringBuilder value = new StringBuilder();

		int i=0;
		
		for(String element : arr)
		{
			if(i>0)
				value.append(",");
			
			value.append(element);
			
			i++;
		}

		return value.toString();
	}

	public List<Integer> generateListInteger(String value)
	{		
		List<Integer> list = new ArrayList<Integer>();
		
		try
		{
			if(hasValue(value))
			{
				if(value.contains(","))
				{
					String[] values = value.split(",");
					int i=0;
					
					if(hasValue(values))
					{
						for(String element : values)
						{
							if(!hasValue(element))
								continue;
							
							list.add(Integer.parseInt(element));
							
							i++;
						}
					}
				}
				else
					list.add(Integer.parseInt(value));			
			}
		}
		catch(NumberFormatException e)
		{
			
		}
		
		
		return list;
	}
	

	
	public void sendMail(String email, String subject, String user_name, String content) 
	{			
		StringBuilder message = new StringBuilder();
		
		message.append(
						"<html>" +
						"<head>" +
						"<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />\r\n" + 

						"</head>" );
		
		message.append("<body><div>");
	
		message.append("Dear "+user_name+" <br/>");							

		
		message.append(content);
		
		message.append(getMailContacts());
		
		message.append(							
						"</div></body>" +
						"</head>" +
						"</html>" +
						""
						);
		
		Performance.releaseMemory();

		SendMail sendMail = new SendMail(email, subject, message.toString(), 1);
		
		sendMail.send();
	}

	public String getMailContacts()
	{
		String contacts = 			
							"<br/>"+
							"Thanks <br/>" + 
							"Akshffeen. <br/>" + 
							"Nasr City, Egypt <br/>" + 
							"http://aksheffeen.com/" ;
		
		return contacts;
	}

	
	public String getXMLkey(String key)
	{
		System.out.println("Utils.getXMLkey() key : "+key);
		
		try
		{
			String path = this.getClass().getResource("").toString();
			
			path = path.replace("file:/", "");
			
			int index1 = path.indexOf("/WEB-INF");
			
			String cut1 = path.substring(index1);
			
			// path =
			// (Constants.platform.equals(Constants.linux)?Constants.URL_SEPARATOR:"")+path.replace(cut1,
			// "/xml/utils.xml");
			path = Constants.URL_SEPARATOR + path.replace(cut1, "/xml/utils.xml");
//			print("path : " + path);
			
			
			File fXmlFile = new File(path);
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("elements");
			
			Node nNode = nList.item(0);
			
			String value = "";
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element eElement = (Element) nNode;
				
				value = eElement.getElementsByTagName(key).item(0).getTextContent();
//				print(key + " ---------- > " + value);
			}
			
			System.out.println("key : "+key+" & value : "+value);
			
			return value;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			return "";
		}
	}

	public SchedulesBean getSchedules(Map<String, TreeSet<Integer>> schedules_map)
	{
		Schedules schedules = new Schedules();
		
		Map<String, TreeSet<Integer>> period_schedules_map = new HashMap<String, TreeSet<Integer>>();
		Map<String, TreeSet<Integer>> days_schedules_map = new HashMap<String, TreeSet<Integer>>();
		Set<Integer> working_days = new HashSet<Integer>();
		
		String schedule = "";
		TreeSet<Integer> days = null;
		
		int day;
		int lastDay;
		boolean next_step;
		TreeSet<Integer> new_days  = null;
		
		for(Entry<String, TreeSet<Integer>> entry : schedules_map.entrySet())
		{
			schedule = entry.getKey();
			days = entry.getValue();
			
			working_days.addAll(days);
			
			Iterator<Integer> iterator = days.iterator();
			
			while(iterator.hasNext())
			{
				day = iterator.next();
				next_step = true;
				
				if(period_schedules_map.containsKey("period_1_"+schedule))
				{
					lastDay = (Integer) getLastElement(period_schedules_map.get("period_1_"+schedule));
							
					if(lastDay!=-1 && day==(lastDay+1))
					{
						period_schedules_map.get("period_1_"+schedule).remove(lastDay);
						period_schedules_map.get("period_1_"+schedule).add(day);
						
						next_step = false;
					}
				}
				else if(period_schedules_map.containsKey("period_2_"+schedule))
				{
					lastDay = (Integer) getLastElement(period_schedules_map.get("period_2_"+schedule));
							
					if(lastDay!=-1 && day==(lastDay+1))
					{
						period_schedules_map.get("period_2_"+schedule).remove(lastDay);
						period_schedules_map.get("period_2_"+schedule).add(day);
						
						next_step = false;
					}
				}
				
				
//				if(days_schedules_map.containsKey("days_"+schedule) || next_step)
				if(next_step)
				{
					lastDay = (Integer) getLastElement(days_schedules_map.get("days_"+schedule));
					
					if(lastDay!=-1 && day==(lastDay+1))
					{
						if(!period_schedules_map.containsKey("period_1_"+schedule))
						{
							new_days = new TreeSet<Integer>();
							new_days.add(day);
							new_days.add(lastDay);
							
							period_schedules_map.put("period_1_"+schedule, new_days);	
						}
						else
						{
							new_days = new TreeSet<Integer>();
							new_days.add(day);
							new_days.add(lastDay);
							
							period_schedules_map.put("period_2_"+schedule, new_days);	
						}
						
						days_schedules_map.get("days_"+schedule).remove(lastDay);
					}
					else
					{
						if(days_schedules_map.containsKey("days_"+schedule))
						{
							days_schedules_map.get("days_"+schedule).add(day);
						}
						else
						{					
							new_days = new TreeSet<Integer>();
							new_days.add(day);
							
							days_schedules_map.put("days_"+schedule, new_days);	
						}
					}
				}
			}
		}
				
		schedules.setPeriod_schedules_map(period_schedules_map);
		schedules.setDays_schedules_map(days_schedules_map);
		schedules.setWorking_days(working_days);
		
		return filterSchedules(schedules);
	}

	public SchedulesBean filterSchedules(Schedules schedules)
	{
		List<ScheduleBean> scheduleList = new ArrayList<ScheduleBean>();
		
		SchedulesBean schedulesBean = new SchedulesBean();
		ScheduleBean scheduleBean = new ScheduleBean();
		
		Map<String, TreeSet<Integer>> period_schedules_map = schedules.getPeriod_schedules_map();
		Map<String, TreeSet<Integer>> days_schedules_map = schedules.getDays_schedules_map();
		List<Integer> working_days = new ArrayList<Integer>();
		List<Integer> vacations = new ArrayList<Integer>();

		String period = "";
		TreeSet<Integer> days;
		for(Entry<String, TreeSet<Integer>> entry : period_schedules_map.entrySet())
		{
			period = entry.getKey().replace("period_1_", "").replace("period_2_", "");
			days = entry.getValue();
			
			scheduleBean = new ScheduleBean();
			scheduleBean.setFrom_hour(period.substring(0,period.indexOf("-")));
			scheduleBean.setTo_hour(period.substring(period.indexOf("-")+1));
			scheduleBean.setDays(days.pollFirst() + "-" + days.pollLast());
			
			scheduleList.add(scheduleBean);
		}
		
		StringBuilder days_str = null;
		for(Entry<String, TreeSet<Integer>> entry : days_schedules_map.entrySet())
		{
			period = entry.getKey().replace("days_", "");
			days = entry.getValue();
			
			if(days==null || days.isEmpty())
				continue;
				
			days_str = new StringBuilder();
			
			scheduleBean = new ScheduleBean();
			scheduleBean.setFrom_hour(period.substring(0,period.indexOf("-")));
			scheduleBean.setTo_hour(period.substring(period.indexOf("-")+1));
			
			Iterator<Integer> iterator = days.iterator();
			while (iterator.hasNext())
			{
				if(days_str.length()>0)
					days_str.append(",");
				
				days_str.append(iterator.next());
			}
			
			scheduleBean.setDays(days_str.toString());

			scheduleList.add(scheduleBean);
		}		
		
		working_days.addAll(schedules.getWorking_days());

		
		for(int i=1 ; i<=7 ; i++)
		{
			if(!working_days.contains(i))
				vacations.add(i);
		}
			
		schedulesBean.setScheduleList(scheduleList);
		schedulesBean.setVacations(vacations);

		return schedulesBean;
	}
	
	public Object getLastElement(final Collection c)
	{
		if(c==null || c.isEmpty())
			return -1;
		
		final Iterator itr = c.iterator();
		Object lastElement = itr.next();
		while (itr.hasNext())
		{
			lastElement = itr.next();
		}
		return lastElement;
	}
	
	
	public List<Integer> getIds(List<Object[]> results)
	{
		List<Integer> ids = new ArrayList<Integer>();
		
		if (results != null)
		{
			for (Object[] inst : results)
			{
				BranchEntity entity = (BranchEntity) inst[0];
				
				ids.add(entity.getId());
			}
		}
		
		return ids;
	}
	
	



	
	
	
	public String sendImage(String query)
	{
		try 
		{	
			System.out.println("Utils.sendImage()");

			String akshffeenAdmin = getXMLkey("AkshffeenAdmin");
			
			System.out.println("akshffeenAdmin from xml : "+akshffeenAdmin);
			
			URL url = new URL(akshffeenAdmin+"/ImageServlet");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
	 	 
			OutputStream os = conn.getOutputStream();
			os.write(query.getBytes());
			os.flush();
	 
//			utils.print("response code ------------- > "+conn.getResponseCode());
//			
//			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED && conn.getResponseCode() != HttpURLConnection.HTTP_OK)
//			{
//				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
//			}
	 
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	 
			String output;
			String response = "";
//			utils.print("Output from Server .... \n");
			while ((output = br.readLine()) != null) 
			{
//				utils.print(output);
				response = response.concat(output);
			}
	 
			conn.disconnect();
			
			return response;

	 
		  } catch (MalformedURLException e) {
	 
			//e.printStackTrace();
			
			return Constants.fail;
		  } catch (IOException e) {
	 
			//e.printStackTrace();
			
			return Constants.fail;
		 }
		 finally
		 {
			 Performance.releaseMemory();
		 }
	}
		

	
}
