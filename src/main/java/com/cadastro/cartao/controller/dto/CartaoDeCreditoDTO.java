package com.cadastro.cartao.controller.dto;

import com.cadastro.cartao.modelo.Cliente;

import lombok.Data;

@Data
public class CartaoDeCreditoDTO {

	private Long id_cartao;
	private String numeroCartao;

	public CartaoDeCreditoDTO(Cliente cliente) {
		this.id_cartao = cliente.getCartaoDeCredito().getId_cartao();
		this.numeroCartao = cliente.getCartaoDeCredito().getNumeroCartao();
	}
	
	public CartaoDeCreditoDTO() {
		
	}
	
}
