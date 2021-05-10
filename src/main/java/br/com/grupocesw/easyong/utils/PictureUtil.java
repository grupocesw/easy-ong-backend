package br.com.grupocesw.easyong.utils;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class PictureUtil {
	
	/**
	 * @param path
	 * @param name
	 * @return
	 */
    public static String getFileNameWithExtension(String path, String name) {
    	File directory = new File(path);
	  	  for (File file : directory.listFiles()) {
	  	    if (file.getName().startsWith(FilenameUtils.removeExtension(name))) {
	  	    	return file.getName();
	  	    }
	  	  }
  	  
	  	  return name;
    }
    
    public static String getServerUrl(String path, String pictureName) {
    	return ServletUriComponentsBuilder
			.fromCurrentContextPath()
			.build()
			.toUriString()
			.concat(path)
			.concat(pictureName);
    }
    
    public static boolean isURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
