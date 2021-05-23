package com.registro.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.registro.models.Pessoa;

@Repository
public interface PessoaRepo extends JpaRepository<Pessoa, Long>{

	@Query(value = "SELECT cpf FROM pessoas WHERE cpf = ?1", nativeQuery = true)
	String findByCpf(String cpf);

}
