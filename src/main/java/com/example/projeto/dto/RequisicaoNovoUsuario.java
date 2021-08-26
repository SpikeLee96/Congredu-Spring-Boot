package com.example.projeto.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.projeto.model.Users;

public class RequisicaoNovoUsuario {
	
	private long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String sobrenome;
	@NotBlank
	private String apelido;
	@NotBlank
	private String email;
	@NotBlank
	private String senha;
	@NotBlank
	private String senha_confirmacao;
	private boolean update = false;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha_confirmacao() {
		return senha_confirmacao;
	}

	public void setSenha_confirmacao(String senha_confirmacao) {
		this.senha_confirmacao = senha_confirmacao;
	}

	public Users toUsers() {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Users usuario = new Users(email, encoder.encode(senha), true, true, true, true, authorities);
		usuario.setId(id);
		usuario.setNome(nome);
		usuario.setSobrenome(sobrenome);
		usuario.setApelido(apelido);
		usuario.setEmail(email);
		usuario.setSenha(encoder.encode(senha));
		return usuario;
	}

}
