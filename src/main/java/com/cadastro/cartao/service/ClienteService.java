package com.cadastro.cartao.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Cliente validaScore(ClienteDTO clienteDTO) {

		int score = random.nextInt(1000);

		if (score >= 700 && clienteDTO.getIdade() >= 20) {
			Cliente cliente = cadastraCartao(clienteDTO);
			return cliente;
		}

		return null;
	}

	private Cliente cadastraCartao(ClienteDTO clienteDTO) {

		CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 16; i++) {
			int digitos = random.nextInt(10);
			sb.append(digitos);
			if (i == 3 || i == 7 || i == 11) {
				sb.append(" ");
			}
		}

		cartaoDeCredito.setNumeroCartao(sb.toString());

		int codigoSeguranca = 0;
		while (codigoSeguranca < 100) {
			codigoSeguranca = random.nextInt(1000);
		}
		cartaoDeCredito.setCodigoSegurança(codigoSeguranca);
		cartaoDeCreditoRepository.save(cartaoDeCredito);

		Cliente cliente = new Cliente(clienteDTO, cartaoDeCredito);

		return cliente;

	}

	public Cliente consultaPorCpf(String cpf) {
		return clienteRepository.findByCpf(cpf);
	}

	public List<Cliente> listaCadastroPorCpf() {

		List<Cliente> listaDeClientes = clienteRepository.findAll();
		
		return listaDeClientes;
	}

}
