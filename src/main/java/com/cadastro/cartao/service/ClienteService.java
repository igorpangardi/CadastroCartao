package com.cadastro.cartao.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadastro.cartao.controller.dto.ClienteConsultaDTO;
import com.cadastro.cartao.controller.dto.ClienteDTO;
import com.cadastro.cartao.modelo.CartaoDeCredito;
import com.cadastro.cartao.modelo.Cliente;
import com.cadastro.cartao.repository.CartaoDeCreditoRepository;
import com.cadastro.cartao.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	CartaoDeCreditoRepository cartaoDeCreditoRepository;

	Random random = new Random();

	public CartaoDeCredito inserir(ClienteDTO clienteDTO, Cliente cliente) {
		
		int score = random.nextInt(1000);
		
		if (score >= 700 && clienteDTO.getIdade() >= 20) {
			CartaoDeCredito cartaoCredito = cadastraCartao(cliente);
			return cartaoCredito;
		}
		
		return null;
	}

	private CartaoDeCredito cadastraCartao(Cliente cliente) {

		CartaoDeCredito cartaoCredito = new CartaoDeCredito(cliente);
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 16; i++) {
			int digitos = random.nextInt(10);
			sb.append(digitos);
			if (i == 3 || i == 7 || i == 11) {
				sb.append(" ");
			}
		}

		int codigoSeguranca = 0;
		
		cartaoCredito.setNumeroCartao(sb.toString());
		while (codigoSeguranca < 100) {
			codigoSeguranca = random.nextInt(1000);
		}
		
		cartaoCredito.setCodigoSegurança(codigoSeguranca);

		cartaoDeCreditoRepository.save(cartaoCredito);
	
		return cartaoCredito;

	}

	public Cliente consultaPorCpf(String cpf) {
		return clienteRepository.findByCpf(cpf);
	}

}
