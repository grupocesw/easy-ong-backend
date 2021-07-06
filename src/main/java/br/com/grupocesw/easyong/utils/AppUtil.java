package br.com.grupocesw.easyong.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class AppUtil {
    
    public static String getRootUrlApp() {
		return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }

    public static String getRootUrlAppConcatPath(String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString().concat(path);
    }
}
