package com.example.demo.controller;

import java.util.List;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Usuarios;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Users")
@RestController
@RequestMapping("/users")
public class UsuariosControler {

	@Autowired
	ProducerTemplate producerTemplate;
	
	@EndpointInject("direct:add-users")
	ProducerTemplate producerAdd;
	
	@ApiOperation("Lista os usuários")
	@SuppressWarnings("unchecked")
	@GetMapping
	public ResponseEntity<List<Usuarios>> getUsers() {
		List<Usuarios> users = producerTemplate.requestBody("direct:select", null, List.class);
		return ResponseEntity.ok(users);
	}
	
	@ApiOperation("Adiciona um ou mais usuários")
	@PostMapping
	public ResponseEntity<String> save(@RequestBody List<Usuarios> user) {
		// TODO: preciso resolver o status da camada service, se de fato foi criado.
		producerAdd.sendBody(user);
		return ResponseEntity.status(HttpStatus.CREATED).body("Usuário(s) criado(s)");	
	}
}
