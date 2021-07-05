package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.services.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping(value = "/api/pictures")
@RestController
public class PictureController {

	private final PictureService service;

	@ResponseBody
	@PostMapping(value = "upload", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<?> upload(@RequestPart(required = true) MultipartFile file, HttpServletRequest httpRequest) {
		service.upload(file);
		return ResponseEntity.ok().build();
	}

	@ResponseBody
	@GetMapping(value = "/{name}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public byte[] showPicture(@PathVariable("name") String name, HttpServletRequest request) throws IOException {
		return service.getPicture(name);
	}

}
