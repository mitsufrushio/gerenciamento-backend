package com.gerenciamento.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerenciamento.api.domain.Cliente;
import com.gerenciamento.api.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	public <S extends Cliente> S save(S cliente) {
		return clienteRepository.save(cliente);
	}
	
	public void delete (Cliente cliente) {
		clienteRepository.delete(cliente);
	}
	
	public Optional<Cliente> findById(Long id) {
		return clienteRepository.findById(id);
	}
	
	public Optional<Cliente> findByCpfCnpj(String cpfCnpj) {
		return clienteRepository.findByCpfCnpj(cpfCnpj);
	}
	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	

}
