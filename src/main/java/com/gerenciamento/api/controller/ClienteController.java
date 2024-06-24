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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gerenciamento.api.domain.Cliente;
import com.gerenciamento.api.service.ClienteService;
import com.gerenciamento.processplans.ClienteProcessPlan;

@RequestMapping("/cliente")
@RestController
public class ClienteController {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	ClienteProcessPlan clienteProcessPlan;
	
	@PostMapping("/create")
	public ResponseEntity<Cliente> handlePost(@RequestBody Cliente cliente) {
		try {
			clienteService.save(cliente);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PostMapping("/upload")
	public ResponseEntity<Cliente> upload(@RequestParam("file") MultipartFile multipartFile) {
		try {
			File file = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			clienteProcessPlan.processPlan(file);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/all")
	public ResponseEntity<List<Cliente>> all() {
		List<Cliente> list = clienteService.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Cliente> delete(@PathVariable("id") Long id){
		try {
			Optional<Cliente> cliente = clienteService.findById(id);
			clienteService.delete(cliente.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
