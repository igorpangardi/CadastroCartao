package com.cadastro.cartao.controller.dto;

import com.cadastro.cartao.modelo.CartaoDeCredito;
import com.cadastro.cartao.modelo.Cliente;

import lombok.Data;

@Data
public class ClienteComCartaoDTO {

	private Long id_cliente;
	private String nome;
	private Long idade;
	private String cpf;
	private CartaoDeCredito cartaoDeCredito;
	
	public ClienteComCartaoDTO(Cliente cliente, CartaoDeCredito cartaoDeCredito) {
		this.id_cliente = cliente.getId_cliente();
		this.nome = cliente.getNome();
		this.idade = cliente.getIdade();
		this.cpf = cliente.getCpf();
		this.cartaoDeCredito = cartaoDeCredito;
	}
	
	
	
}
