package com.gerenciamento.api.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ROTINA")
@SequenceGenerator(name = "ROTINA_SEQ", sequenceName = "ROTINA_SEQ", allocationSize = 1, initialValue = 1)
public class Rotina implements Serializable {

	private static final long serialVersionUID = -4954217044832135L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@Column(name = "ATIVO")
	private Boolean ativo;

}
