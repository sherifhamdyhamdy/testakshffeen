package com.sget.akshef.hibernate.constants;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.primefaces.model.UploadedFile;

/**
 * 
 * @author JDeeb
 * Class For Upload Images and Delete Images
 */
public class AppUtil {

	public String uploadImage(UploadedFile file, String imagePath) {
		String pathFile = null;
		try {
			if (file != null) {
				String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
				SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
				String name = fmt.format(new Date())+ file.getFileName().substring(file.getFileName().lastIndexOf('.'));
				File filePath = new File(path + imagePath + name);
				InputStream is = file.getInputstream();
				FileUtils.copyInputStreamToFile(is, filePath);
				pathFile = name;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pathFile;
	}

	public void deleteImage(String imageName, String imagePath) {
		if (imageName != null) {
			try {
				String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
				String imgPath = path + imagePath + imageName;
				File delImg = new File(imgPath);
				delImg.delete();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public String uploadImageWithName(UploadedFile file,String imagePath) {
        String pathFile = null;
        try {
            if (file != null) {
                String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");                
                
                File filePath = new File(path + imagePath + file.getFileName());
                InputStream is = file.getInputstream();
                FileUtils.copyInputStreamToFile(is, filePath);
                pathFile = file.getFileName();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pathFile;
    }
}
