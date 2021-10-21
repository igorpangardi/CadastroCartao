package com.cadastro.cartao.controller.dto;

import com.cadastro.cartao.modelo.Cliente;

import lombok.Data;

@Data
public class ClienteConsultaDTO {

	private Long id_cliente;
	private String nome;
	private Long idade;
	private String cpf;	
	private CartaoDeCreditoDTO cartaoDeCreditoDTO;
	
	public ClienteConsultaDTO (Cliente cliente, CartaoDeCreditoDTO cartaoDeCreditoDTO) {
		this.id_cliente = cliente.getId_cliente();
		this.nome = cliente.getNome();
		this.idade = cliente.getIdade();
		this.cpf = cliente.getCpf();
		this.cartaoDeCreditoDTO = cartaoDeCreditoDTO;
	}
}
