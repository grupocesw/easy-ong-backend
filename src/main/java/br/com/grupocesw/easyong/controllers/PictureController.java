package br.com.grupocesw.easyong.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.grupocesw.easyong.services.PictureService;

@RequestMapping(value = "/api/pictures")
@RestController
public class PictureController {

	@Autowired
	private PictureService service;

	@ResponseBody
	@PostMapping(value = "upload", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<Void> uploadImage(@RequestPart(required = true) MultipartFile file) {
		service.insert(file);
		return ResponseEntity.noContent().build();
	}

	@ResponseBody
	@GetMapping(value = "/{name}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public byte[] showPicture(@PathVariable("name") String name, HttpServletRequest request) throws IOException {
		return service.getPicture(name);
	}

}
