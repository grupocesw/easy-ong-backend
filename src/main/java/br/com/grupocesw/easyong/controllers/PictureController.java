package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.services.impl.PictureServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@AllArgsConstructor
@RequestMapping(value = "/api/pictures")
@RestController
public class PictureController {

	private final PictureServiceImpl service;

	@ResponseBody
	@PostMapping(value = "upload", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<Void> uploadImage(@RequestPart(required = true) MultipartFile file) {
		service.create(file);
		return ResponseEntity.noContent().build();
	}

	@ResponseBody
	@GetMapping(value = "/{name}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public byte[] showPicture(@PathVariable("name") String name, HttpServletRequest request) throws IOException {
		return service.getPicture(name);
	}

}
