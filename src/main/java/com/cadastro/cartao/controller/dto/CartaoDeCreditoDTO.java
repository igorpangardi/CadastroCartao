package com.cadastro.cartao.controller.dto;

import com.cadastro.cartao.modelo.CartaoDeCredito;

import lombok.Data;

@Data
public class CartaoDeCreditoDTO {

	private Long id_cartao;
	private String numeroCartao;
	
	
	public CartaoDeCreditoDTO(CartaoDeCredito cartaoDeCredito) {
		this.id_cartao = cartaoDeCredito.getId_cartao();
		this.numeroCartao = cartaoDeCredito.getNumeroCartao();
	}
	
}
