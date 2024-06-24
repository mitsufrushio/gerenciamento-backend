package com.gerenciamento.api.domain;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "CLIENTE")
@SequenceGenerator(name = "CLIENTE_SEQ", sequenceName = "CLIENTE_SEQ", allocationSize = 1, initialValue = 1)
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 2873530630057140252L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CLIENTE_SEQ")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "FONE")
	private String fone;
	
	@Column(name = "CPF_CNPJ")
	private String cpfCnpj;
	
	@Column(name = "DT_ENTR_SIS")
	private Date dtEntrSis;

}
