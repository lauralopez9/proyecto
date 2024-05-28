package com.comercio.proyectofinal.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.comercio.proyectofinal.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import com.comercio.proyectofinal.dto.UsuarioRegistroDTO;
import com.comercio.proyectofinal.model.Rol;
import com.comercio.proyectofinal.model.Usuario;

@Service

public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;

	private UsuarioRepository usuarioRepository;

	public UsuarioServiceImpl(com.comercio.proyectofinal.repository.UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}
	
	public Usuario obtenerUsuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Usuario) {
            return (Usuario) auth.getPrincipal();
        } else {
            return null; // Usuario no autenticado
        }
	}

	@Override
    @Transactional
    public Usuario save(UsuarioRegistroDTO registroDTO) {
        Usuario usuario = new Usuario(
            registroDTO.getNombre(),
            registroDTO.getApellido(),
            registroDTO.getEmail(),
            registroDTO.getDireccion(),
            passwordEncoder.encode(registroDTO.getContraseña()),
            Arrays.asList(new Rol("ROLE_USER"))
        );
        return usuarioRepository.save(usuario);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username);
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario o Password invalidos");
		}
		return new User(usuario.getEmail(), usuario.getContraseña(), mapearAutoridadesRoles(usuario.getRoles()));
	}
	private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}
	
	@Override
	public List<Usuario> listarUsuarios(){
		return usuarioRepository.findAll();
	}
	
}


