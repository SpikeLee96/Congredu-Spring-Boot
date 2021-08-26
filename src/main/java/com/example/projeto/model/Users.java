package com.example.projeto.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.projeto.dto.RequisicaoNovoUsuario;

@Entity
@Table(name = "users")
public class Users extends User{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank
	@Column(name = "username", unique = true)
	private String email;
	
	@NotBlank
	@Column(name = "nome")
	private String nome;
	
	@NotBlank
	@Column(name = "sobrenome")
	private String sobrenome;
	
	@NotBlank
	@Column(name = "apelido")
	private String apelido;
	
	@NotBlank
	@Column(name = "password")
	private String senha;
	
	@Column(name = "presenca")
	private boolean presenca = false;

	@Column(name = "enabled")
	private boolean enabled = true;

	public Users(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, 
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	
	public Users() {
		super(" ", " ", true, true, true, true, extracted());
	}

	private static Collection<? extends GrantedAuthority> extracted() {
		List<GrantedAuthority> n = new ArrayList<GrantedAuthority>();
		n.add(new SimpleGrantedAuthority("ROLE_USER"));
		return n;
	}
	
	public boolean isPresenca() {
		return presenca;
	}

	public void setPresenca(boolean presenca) {
		this.presenca = presenca;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public RequisicaoNovoUsuario toReq() {
		RequisicaoNovoUsuario req = new RequisicaoNovoUsuario();
		req.setId(id);
		req.setNome(nome);
		req.setSobrenome(sobrenome);
		req.setApelido(apelido);
		req.setEmail(email);
		req.setSenha(senha);
		return req;
	}
	

}
