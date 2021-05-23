package com.registro.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.registro.errors.ApiNotFoundException;
import com.registro.messages.Messages;
import com.registro.models.Pessoa;
import com.registro.repo.PessoaRepo;
import com.registro.update.FieldUpdate;
import com.registro.validar.ValidarCampos;

@RestController
@RequestMapping("/api/v1/pessoas")
public class PessoaController {
	
	@Autowired
	PessoaRepo pessoaRepo;


	@GetMapping
	public List<Pessoa> listaDePessoas() {
		return this.pessoaRepo.findAll();
	}



	@GetMapping("/{id}")
	public ResponseEntity<?> umaPessoa(@PathVariable Long id) {
		Optional<Pessoa> pessoa = this.pessoaRepo.findById(id);

		if (!pessoa.isPresent()) {
			throw new ApiNotFoundException("Nada Encontrado");
		}

		return new ResponseEntity<>(pessoa, HttpStatus.OK);
	}



	@PostMapping
	public ResponseEntity<?> criarNovo(@RequestBody Pessoa pessoa) {

		List<Messages> errors = ValidarCampos.validate(pessoa);

		var cpf = this.pessoaRepo.findByCpf(pessoa.getCpf());

		if (cpf != null) {
			errors.add(new Messages("cpf", "Indisponível para esse cadastro"));
		}

		if (!errors.isEmpty()) {
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(this.pessoaRepo.save(pessoa), HttpStatus.CREATED);
	}



	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Pessoa pessoaRequest) {

		Optional<Pessoa> pessoaResponse = this.pessoaRepo.findById(id);

		if (!pessoaResponse.isPresent()) {
			throw new ApiNotFoundException("Nada encontrado");
		}

		FieldUpdate.updateIn(pessoaRequest, pessoaResponse.get());
		List<Messages> errors = ValidarCampos.validate(pessoaRequest);


		if (!pessoaRequest.getCpf().equals(pessoaResponse.get().getCpf())) {
			var cpf = this.pessoaRepo.findByCpf(pessoaRequest.getCpf());

			if (cpf != null) {
				errors.add(new Messages("cpf", "Indisponível, verifique se você já está cadastrado"));
			}
		}

		if (!errors.isEmpty()) {
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(this.pessoaRepo.save(pessoaRequest), HttpStatus.OK);
	}



	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Pessoa> pessoaResponse = this.pessoaRepo.findById(id);

		if (!pessoaResponse.isPresent()) {
			throw new ApiNotFoundException("Nada Encontrado");
		}

		this.pessoaRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
