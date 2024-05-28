package com.comercio.proyectofinal.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.comercio.proyectofinal.dto.UsuarioRegistroDTO;
import com.comercio.proyectofinal.model.Usuario;

public interface UsuarioService extends UserDetailsService{
	
	

	public Usuario save(UsuarioRegistroDTO registroDTO);

	
	public List<Usuario> listarUsuarios();


	public static void guardar(UsuarioRegistroDTO registroDTO) {
		// TODO Auto-generated method stub
		
	}
}


