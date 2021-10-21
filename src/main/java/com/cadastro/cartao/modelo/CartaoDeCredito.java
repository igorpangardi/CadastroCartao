package com.cadastro.cartao.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class CartaoDeCredito {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_cartao;
	private String numeroCartao;
	private int codigoSegurança;
	@OneToOne
	@JoinColumn(name = "id_cliente")
	@JsonIgnore
	private Cliente cliente;
	
	public CartaoDeCredito(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
