package br.com.grupocesw.easyong.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.grupocesw.easyong.dto.FrequentlyAskedQuestionDTO;
import br.com.grupocesw.easyong.entities.FrequentlyAskedQuestion;
import br.com.grupocesw.easyong.services.FrequentlyAskedQuestionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping(value = "/api/faqs")
@RestController
public class FrequentlyAskedQuestionResource {

	@Autowired
	private FrequentlyAskedQuestionService service;
	
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a lista de Pergunta Frequente"),
	    @ApiResponse(code = 401, message = "Credencial inválida para acessar este recurso"),
	    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
	    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Find faqs")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                value = "Sorting criteria in the format: property(,asc|desc). " +
                        "Default sort order is ascending. " +
                        "Multiple sort criteria are supported.")
    })
	public Page<FrequentlyAskedQuestionDTO> list(Pageable pageable) {
		final Page<FrequentlyAskedQuestion> frequentlyAskedQuestions = service.findAll(pageable);
		
		return frequentlyAskedQuestions.map(faq -> new FrequentlyAskedQuestionDTO(faq));
	}
	
	@PostMapping
	public ResponseEntity<FrequentlyAskedQuestionDTO> create(@RequestBody FrequentlyAskedQuestion frequentlyAskedQuestion) {
		
		FrequentlyAskedQuestion frequentlyAskedQuestionSalvo = service.insert(frequentlyAskedQuestion);
		
		FrequentlyAskedQuestionDTO frequentlyAskedQuestionDTO = new FrequentlyAskedQuestionDTO(frequentlyAskedQuestionSalvo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(frequentlyAskedQuestionDTO.getId()).toUri();
		
		return ResponseEntity.created(uri).body(frequentlyAskedQuestionDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<FrequentlyAskedQuestionDTO> retrieve(@PathVariable Long id) {
		FrequentlyAskedQuestion frequentlyAskedQuestion = service.findById(id);
		
		FrequentlyAskedQuestionDTO frequentlyAskedQuestionDTO = new FrequentlyAskedQuestionDTO(frequentlyAskedQuestion);
		
		return ResponseEntity.ok(frequentlyAskedQuestionDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<FrequentlyAskedQuestionDTO> update(@PathVariable Long id, @RequestBody FrequentlyAskedQuestion frequentlyAskedQuestion) {
		frequentlyAskedQuestion = service.update(id, frequentlyAskedQuestion);
		
		FrequentlyAskedQuestionDTO frequentlyAskedQuestionDTO = new FrequentlyAskedQuestionDTO(frequentlyAskedQuestion);
		
		return ResponseEntity.ok().body(frequentlyAskedQuestionDTO);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	 
}
