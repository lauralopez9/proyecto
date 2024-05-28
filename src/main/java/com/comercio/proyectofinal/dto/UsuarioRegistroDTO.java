package com.comercio.proyectofinal.dto;

public class UsuarioRegistroDTO {

	private long id;
	private String nombre;
	private String apellido;
	private String email;
	private String direccion;s
	private String contraseña;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	
	public UsuarioRegistroDTO(long id, String nombre, String apellido, String email, String contraseña,
			String direccion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.direccion = direccion;
		this.contraseña = contraseña;
		
	}

	public UsuarioRegistroDTO(String nombre, String apellido, String email, String contraseña, String direccion) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.direccion = direccion;
		this.contraseña = contraseña;
		
	}

	public UsuarioRegistroDTO(String email) {
		super();
		this.email = email;
	}

	public UsuarioRegistroDTO() {
		super();
	}

}
