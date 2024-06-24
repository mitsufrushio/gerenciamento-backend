package com.gerenciamento.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gerenciamento.api.domain.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{

}
