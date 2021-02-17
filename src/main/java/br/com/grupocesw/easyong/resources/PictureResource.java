package br.com.grupocesw.easyong.resources;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.grupocesw.easyong.services.PictureService;

@RequestMapping(value = "/api/pictures")
@RestController
public class PictureResource {

	@Autowired
	private PictureService service;

	@ResponseBody
	@PostMapping(value = "upload", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<Void> uploadImage(@RequestParam MultipartFile file) {
		service.upload(file);
		return ResponseEntity.noContent().build();
	}

	@ResponseBody
	@GetMapping(value = "/{pictureName}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public byte[] showPicture(@PathVariable("pictureName") String pictureName, HttpServletRequest request) throws IOException {
//		Picture picture = service.findById(id);
		return service.getPicture(pictureName);
	}

}
