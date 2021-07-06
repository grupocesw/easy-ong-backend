package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.services.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags = "Picture Controller")
public class PictureController {

	private final PictureService service;

	@ApiOperation(value = "Upload picture")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Upload successfully"),
			@ApiResponse(code = 400, message = "Not allowed type file. Use only these: image/jpg, image/jpeg, image/png"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@ResponseBody
	@PostMapping(value = "upload", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<?> upload(@RequestPart(required = true) MultipartFile file, HttpServletRequest httpRequest) {
		service.upload(file);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Show picture")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Show successfully")
	})
	@ResponseBody
	@GetMapping(value = "/{name}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public byte[] showPicture(@PathVariable("name") String name, HttpServletRequest request) throws IOException {
		return service.getPicture(name);
	}

}
