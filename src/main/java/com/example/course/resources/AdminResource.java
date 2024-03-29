package com.example.course.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.course.entities.Admin;
import com.example.course.entities.User;
import com.example.course.services.AdminService;

@RestController
@RequestMapping(value = "/admin")
public class AdminResource {

	@Autowired 
	private AdminService service;
	
	@GetMapping
	public ResponseEntity<List<Admin>> findAll() {
		List<Admin> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Admin> findById(@PathVariable Long id){
		Admin obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping(value = "/admin/{cpf}")
	public ResponseEntity<Admin> findByCpf(@PathVariable Long cpf){
		Admin obj = service.findById(cpf);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Admin> insert(@RequestBody Admin obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@RequestMapping(value = "/recoverPassword/{cpf},{name},{email}")
	@GetMapping
	public ResponseEntity<Admin> recoverPassword(@PathVariable String cpf, @PathVariable String name, @PathVariable String email){
		Admin obj = service.recoverPassword(cpf, name, email);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cpf},{name},{email}")
				.buildAndExpand(obj.getCpf(),obj.getName(), obj.getEmail()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Admin> update(@PathVariable Long id, @RequestBody Admin obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}