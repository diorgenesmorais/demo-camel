package com.example.demo.processors;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.example.demo.model.Usuarios;

@Component
public class UsuarioProcessor implements Processor {

	@Override
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

}
