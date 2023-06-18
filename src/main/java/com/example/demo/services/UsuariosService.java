package com.example.demo.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuarios;

@Service
public class UsuariosService extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		errorHandler(deadLetterChannel("direct:error"));

		from("direct:select")
			.to("sql:select * from usuarios").process(new Processor() {
				public void process(Exchange xchg) throws Exception {
					//the camel sql select query has been executed. We get the list of users.
					@SuppressWarnings("unchecked")
					ArrayList<Map<String, Object>> dataList = (ArrayList<Map<String, Object>>) xchg.getIn().getBody();
					List<Usuarios> usuarios = new ArrayList<Usuarios>();
					System.out.println(dataList);
					dataList.stream().forEach(user -> {
						Usuarios usuario = new Usuarios();
						usuario.setId(((BigInteger) user.get("id")).intValue());
						usuario.setNome((String) user.get("nome"));
						usuarios.add(usuario);					
					});
					xchg.getIn().setBody(usuarios);
				}
			});

        from("direct:add-users")
        	.routeId("route.user.add")
        	.log("Novo usuário: ${body}")
        	.to(ExchangePattern.InOnly,"seda:persist")
        	.end();
        	
        
        from("seda:persist")
        	.routeId("route.user.persist")
        	.setBody(simple("INSERT INTO usuarios(nome) VALUES ('${body.nome}')"))
        	.to("jdbc:default");
	}

}
