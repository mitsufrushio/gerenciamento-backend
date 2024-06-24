package com.gerenciamento.api.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gerenciamento.api.domain.Venda;
import com.gerenciamento.api.repository.VendaRepository;
import com.gerenciamento.processplans.VendaProcessPlan;

@RequestMapping("/venda")
@RestController
public class VendaController {
	
	@Autowired
	VendaRepository vendaRepository;
	
	@Autowired
	VendaProcessPlan vendaProcessPlan;
	
	@GetMapping("/all")
	public ResponseEntity<List<Venda>> all() {
		List<Venda> list = vendaRepository.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/upload")
	public ResponseEntity<Venda> upload(@RequestParam("file") MultipartFile multipartFile) {
		try {
			File file = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			vendaProcessPlan.processPlan(file);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Venda> delete(@PathVariable("id") Long id){
		try {
			Optional<Venda> cliente = vendaRepository.findById(id);
			vendaRepository.delete(cliente.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
