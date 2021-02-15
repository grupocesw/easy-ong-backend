package br.com.grupocesw.easyong.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.grupocesw.easyong.dto.FrequentlyAskedQuestionDTO;
import br.com.grupocesw.easyong.entities.FrequentlyAskedQuestion;
import br.com.grupocesw.easyong.services.FrequentlyAskedQuestionService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping(value = "/api/faqs")
@RestController
public class FrequentlyAskedQuestionResource {

	@Autowired
	private FrequentlyAskedQuestionService us;
	
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a lista de Pergunta Frequente"),
	    @ApiResponse(code = 401, message = "Credencial inválida para acessar este recurso"),
	    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
	    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@GetMapping
	public ResponseEntity<List<FrequentlyAskedQuestionDTO>> list() {
		List<FrequentlyAskedQuestion> frequentlyAskedQuestions = us.findAll();
		
		List<FrequentlyAskedQuestionDTO> frequentlyAskedQuestionsDTO = frequentlyAskedQuestions.stream()
				.map(o -> new FrequentlyAskedQuestionDTO(o))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(frequentlyAskedQuestionsDTO);
	}
	
	@PostMapping
	public ResponseEntity<FrequentlyAskedQuestionDTO> create(@RequestBody FrequentlyAskedQuestion frequentlyAskedQuestion) {
		
		FrequentlyAskedQuestion frequentlyAskedQuestionSalvo = us.insert(frequentlyAskedQuestion);
		
		FrequentlyAskedQuestionDTO frequentlyAskedQuestionDTO = new FrequentlyAskedQuestionDTO(frequentlyAskedQuestionSalvo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(frequentlyAskedQuestionDTO.getId()).toUri();
		
		return ResponseEntity.created(uri).body(frequentlyAskedQuestionDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<FrequentlyAskedQuestionDTO> retrieve(@PathVariable Long id) {
		FrequentlyAskedQuestion frequentlyAskedQuestion = us.findById(id);
		
		FrequentlyAskedQuestionDTO frequentlyAskedQuestionDTO = new FrequentlyAskedQuestionDTO(frequentlyAskedQuestion);
		
		return ResponseEntity.ok(frequentlyAskedQuestionDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<FrequentlyAskedQuestionDTO> update(@PathVariable Long id, @RequestBody FrequentlyAskedQuestion frequentlyAskedQuestion) {
		frequentlyAskedQuestion = us.update(id, frequentlyAskedQuestion);
		
		FrequentlyAskedQuestionDTO frequentlyAskedQuestionDTO = new FrequentlyAskedQuestionDTO(frequentlyAskedQuestion);
		
		return ResponseEntity.ok().body(frequentlyAskedQuestionDTO);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		us.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	 
}
