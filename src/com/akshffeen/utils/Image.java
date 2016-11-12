package com.akshffeen.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Image 
{
	Utils utils = Utils.getInstance();


	public String uploadImage(String imageDataString, String extension, String old_file_title, String image_path) 
	{	
		String path1 = this.getClass().getResource("").toString();
//		String path2 = this.getClass().getResource("/").toString();
				
		String fileName1 = utils.generateRandomString()+"."+extension;
//		String fileName2 = utils.generateRandomString();
		
		
		path1 = path1.replace("file:/", "");
//		path2 = path2.replace("file:/", "");
		
		int index1 = path1.indexOf("AkshffeenAdmin");
//		int index2 = path2.indexOf("Services/WEB-INF");
			
		String cut1 = path1.substring(index1);
//		String cut2 = path2.substring(index2);

		path1 = "/"+path1.replace(cut1, image_path+"/");
//		path2 = path2.replace(cut2, "/uploadedFiles/");
		
	    utils.deleteFile(path1+old_file_title);		
		
		utils.print(path1);
//		utils.print(path2);
		
		try
		{
			byte[] imageByteArray = Base64Image.decodeImage(imageDataString);
			
			/*
			 * Write a image byte array into file system  
			 */
			
			FileOutputStream imageOutFile1 = new FileOutputStream(path1 + fileName1);
//			FileOutputStream imageOutFile2 = new FileOutputStream(path2 + fileName2+".jpg");
			
			imageOutFile1.write(imageByteArray);
//			imageOutFile2.write(imageByteArray);
			
			imageOutFile1.close();
//			imageOutFile2.close();
			
			//utils.print("Image Successfully Manipulated!");
		} catch (FileNotFoundException e) {
			//utils.print("Image not found" + e);
		} catch (IOException ioe) {
			//utils.print("Exception while reading the Image " + ioe);
		}
		
		
//		return "1 --> "+path1+"\n"+ "2 --> " +path2;
		
//		return fileName1+".jpg";
		return fileName1;
	}



	
}
