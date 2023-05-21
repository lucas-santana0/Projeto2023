package br.edu.toledoprudente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.edu.toledoprudente.dao.CategoriaDAO;
import br.edu.toledoprudente.pojo.Categoria;

@Component
public class StringToCategoriaConverter implements Converter<String, Categoria> {
	@Autowired
	private CategoriaDAO dao;

	@Override
	public Categoria convert(String idTexto) {
		if (idTexto.isEmpty()) {
			return null;
		}
		
		return dao.findById(Integer.parseInt(idTexto));
	}
}
