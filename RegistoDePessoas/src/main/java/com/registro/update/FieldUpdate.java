package com.registro.update;

import com.registro.models.Pessoa;

public class FieldUpdate {
	
	public static void updateIn(Pessoa pessoaRequest, Pessoa pessoaResponse) {
		
		pessoaRequest.setId(pessoaResponse.getId());
		
		if(pessoaRequest.getNome() == null) {
			pessoaRequest.setNome(pessoaResponse.getNome());
		}
		
		if(pessoaRequest.getSobrenome() == null) {
			pessoaRequest.setSobrenome(pessoaResponse.getSobrenome());
		}
		
		if(pessoaRequest.getCpf() == null) {
			pessoaRequest.setCpf(pessoaResponse.getCpf());
			
		}
		
		if(pessoaRequest.getSexo() == null) {
			pessoaRequest.setSexo(pessoaResponse.getSexo());
		}
		
	}
	
}
