package br.com.grupocesw.easyong.utils;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.net.URL;

public class PictureUtil {

    public static String getFileNameWithExtension(String path, String name) {
    	File directory = new File(path);
	  	  for (File file : directory.listFiles()) {
	  	    if (file.getName().startsWith(FilenameUtils.removeExtension(name))) {
	  	    	return file.getName();
	  	    }
	  	  }
  	  
	  	  return name;
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
