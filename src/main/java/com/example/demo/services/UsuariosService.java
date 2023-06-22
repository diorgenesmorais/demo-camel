package com.example.demo.services;

import org.apache.camel.BeanInject;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuarios;
import com.example.demo.processors.UsuarioProcessor;

@Service
public class UsuariosService extends RouteBuilder {

	@BeanInject
	private UsuarioProcessor processor;
	
	@Override
	public void configure() throws Exception {
		errorHandler(deadLetterChannel("direct:error"));
		
		from("direct:select")
			.to("sql:select * from usuarios")
			.process(processor)
			.to(ExchangePattern.InOnly, "seda:send");

        from("direct:add-users")
        	.routeId("route.users.all")
        	.log("Corpo da mensagem: ${body}")
        	.split().body().parallelProcessing()
        	.to("direct:user-one");
        
        from("direct:user-one")
        	.routeId("route.user.add")
        	.transform().body(Usuarios.class) // transformar em um usuário (tipo/modelo)
        	.to(ExchangePattern.InOnly,"seda:persist")
        	.to(ExchangePattern.InOnly, "seda:send")
        	.end();
        	
        
        from("seda:persist")
        	.routeId("route.user.persist")
        	.transform().body(Usuarios.class)
        	.setBody(simple("INSERT INTO usuarios(nome) VALUES ('${body.nome}')"))
        	.to("jdbc:default");
        
        from("seda:send")
        	.routeId("route.logs")
        	.log("Novo usuário: ${body}");
			//.to("http://localhost:8080/logs");

	}

}
