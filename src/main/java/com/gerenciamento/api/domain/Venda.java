package com.gerenciamento.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "VENDA")
@SequenceGenerator(name = "VENDA_SEQ", sequenceName = "VENDA_SEQ", allocationSize = 1, initialValue = 1)
public class Venda implements Serializable {
	
	 private static final long serialVersionUID = -3081108257590723436L;

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "ID")
	 private Long id;
	 
	 @ManyToOne
	 @JoinColumn(name = "CLIENTE_ID", nullable = false)
	 private Cliente cliente;
	 
	 @Column(name = "DATA")
	 private Date data;
	 
	 @Column(name = "VALOR")
	 private BigDecimal valor;
	 
	 @Column(name = "OBS")
	 private String obs;

}
