package com.comercio.proyectofinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.comercio.proyectofinal.service.UsuarioService;

@Controller
@Lazy
public class RegistroController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping("/login")
	public String iniciarSesion() {
		return "login";
		
	}
	
	@GetMapping("/")
	public String verPerfil(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rol = auth.getAuthorities().iterator().next().getAuthority(); 
        model.addAttribute("rol", rol);
		return "index";
		
	}
	@GetMapping("index")
	public String PaginaDeInicio(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rol = auth.getAuthorities().iterator().next().getAuthority(); 
        model.addAttribute("rol", rol);
		return "index";
	}
	@GetMapping ("/tabla")
	public String Tablalist(Model modelo) {
	   modelo.addAttribute("usuarios", service.listarUsuarios());
	return "tabla";
	}
	
	@GetMapping ("/productos")
	public String MostrarProductos() {
		return "productos";
	}
	
	@GetMapping ("/sobre")
	public String MostrarSobre() {
		return "sobre";
	}
	
	
	
	

}
