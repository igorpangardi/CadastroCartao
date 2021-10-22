package com.cadastro.cartao.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.cadastro.cartao.controller.dto.ClienteDTO;

import lombok.Data;

@Entity
@Data
public class Cliente {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_cliente;
	private String nome;
	private Long idade;
	private String cpf;
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_cartao")
	private CartaoDeCredito cartaoDeCredito;
	
	public Cliente(ClienteDTO clienteDTO, CartaoDeCredito cartaoDeCredito) {
		this.id_cliente = clienteDTO.getId_cliente();
		this.nome = clienteDTO.getNome();
		this.idade = clienteDTO.getIdade();
		this.cpf = clienteDTO.getCpf();
		this.cartaoDeCredito = cartaoDeCredito;
	}
	
	public Cliente(ClienteDTO clienteDTO) {
		this.id_cliente = clienteDTO.getId_cliente();
		this.nome = clienteDTO.getNome();
		this.idade = clienteDTO.getIdade();
		this.cpf = clienteDTO.getCpf();
	}
	
	public Cliente() {
		
	}
	
}
