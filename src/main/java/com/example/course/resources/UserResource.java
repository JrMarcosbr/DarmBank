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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.course.entities.User;
import com.example.course.services.UserService;

import net.bytebuddy.asm.Advice.Argument;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired 
	private UserService service;
	
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id){
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping(value = "/cpf/{cpf}")
	public ResponseEntity<User> findByCpf(@PathVariable String cpf){
		User obj = service.findByCpf(cpf);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/login/{cpf},{password}")
	@GetMapping
	public ResponseEntity<Long> login(@PathVariable String cpf, @PathVariable String password){
		User obj = service.login(cpf, password);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cpf},{password}")
				.buildAndExpand(obj.getCpf(), obj.getPassword()).toUri();
		return ResponseEntity.created(uri).body(obj.getId());
	}
	
	@RequestMapping(value = "/recoverPassword/{cpf},{name},{email}")
	@GetMapping
	public ResponseEntity<User> recoverPassword(@PathVariable String cpf, @PathVariable String name, @PathVariable String email){
		User obj = service.recoverPassword(cpf, name, email);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cpf},{name},{email}")
				.buildAndExpand(obj.getCpf(),obj.getName(), obj.getEmail()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	
	@RequestMapping(value = "/login/confirmPassword/{password},{confirm_password}")
	@GetMapping
	public ResponseEntity<Boolean> confirmPassword(@PathVariable String password, @PathVariable  String confirm_password){
		boolean obj = service.confirmPassword(password, confirm_password);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{password},{confirm_password}").buildAndExpand(obj).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@RequestMapping(value = "/createUser")
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}