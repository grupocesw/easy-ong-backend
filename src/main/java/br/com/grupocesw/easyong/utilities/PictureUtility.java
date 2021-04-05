package br.com.grupocesw.easyong.utilities;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

public class PictureUtility {
	
    public static String getFileNameWithExtension(String path, String name) {
    	File directory = new File(path);
	  	  for (File file : directory.listFiles()) {
	  	    if (file.getName().startsWith(FilenameUtils.removeExtension(name))) {
	  	    	return file.getName();
	  	    }
	  	  }
  	  
	  	  return name;
    }
}
