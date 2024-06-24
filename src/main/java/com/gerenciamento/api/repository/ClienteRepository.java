package com.gerenciamento.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gerenciamento.api.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
//	@Query("SELECT c FROM Cliente c WHERE c.cpfCnpj = :cpjCnpj")
//	Optional<Object> findByCpfCnpj(@Param("cpfCnpj") String cpjCnpj);
	
	@Query("SELECT c FROM Cliente c WHERE c.cpfCnpj = :cpfCnpj")
    Optional<Cliente> findByCpfCnpj(@Param("cpfCnpj") String cpfCnpj);

}
