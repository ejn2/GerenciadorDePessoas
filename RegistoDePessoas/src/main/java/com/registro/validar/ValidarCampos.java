package com.registro.validar;

import java.util.ArrayList;
import java.util.List;

import com.registro.messages.Messages;
import com.registro.models.Pessoa;

public class ValidarCampos {
	
	public static List<Messages> validate(Pessoa pessoa) {
		
		List<Messages> errors = new ArrayList<Messages>();
		
		
		if(pessoa.getNome() == null || pessoa.getNome().trim().isEmpty()) {
			errors.add(new Messages("nome", "Obrigatório"));
			
		}else if(!pessoa.getNome().trim().matches("[A-zÁ-ú]+")) {
			errors.add(new Messages("nome", "Não deve conter números ou caracteres especiais"));
		}	
		
		
		if(pessoa.getSobrenome() == null || pessoa.getSobrenome().trim().isEmpty()) {
			errors.add(new Messages("sobrenome", "Obrigatório"));
		
		}else if(!pessoa.getSobrenome().trim().matches("[A-zÁ-ú]+")) {
			errors.add(new Messages("sobrenome", "Não deve conter números ou caracteres especiais"));
		}
		
		
		if(pessoa.getSexo() == null || pessoa.getSexo().trim().isEmpty()) {
			errors.add(new Messages("sexo", "Obrigatório"));
			
		}else if(!pessoa.getSexo().trim().matches("[A-zÁ-ú]+")) {
			errors.add(new Messages("sexo", "Não deve conter números ou caracteres especiais"));
		}
	
		
		if(pessoa.getCpf() == null || pessoa.getCpf().trim().isEmpty()) {
			errors.add(new Messages("cpf", "Obrigatório"));
	
		}else if(!pessoa.getCpf().matches("[0-9]+")) {
			errors.add(new Messages("cpf", "Apenas números inteiros"));
			
		}else if(pessoa.getCpf().length() != 11) {
			errors.add(new Messages("cpf", "Deve ter 11 caracteres"));
		}
		
		return errors;
		
	}
	
	
	
}
