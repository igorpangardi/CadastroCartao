package com.cadastro.cartao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cadastro.cartao.controller.dto.CartaoDeCreditoDTO;
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

	public ResponseEntity<?> cadastraCliente(ClienteDTO clienteDTO) {
		
		if (clienteRepository.existsByCpf(clienteDTO.getCpf())) {
			return ResponseEntity.badRequest().build();
		}
		
		Cliente cliente = validaScore(clienteDTO);
		if (cliente != null) {
			clienteRepository.save(cliente);
			return ResponseEntity.ok(new ClienteDTO(cliente));
		}

		cliente = new Cliente(clienteDTO);
		clienteRepository.save(cliente);
		return ResponseEntity.ok(new ClienteDTO(cliente));
	}

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

	public ResponseEntity<?> consultaPorCpf(String cpf) {

		Optional<Cliente> cliente = clienteRepository.findByCpf(cpf);
		
		if (cliente.isPresent()) {
			if (cliente.get().getCartaoDeCredito() != null) {
				CartaoDeCreditoDTO cartaoDeCreditoDTO = new CartaoDeCreditoDTO(cliente.get());
				return ResponseEntity.ok(new ClienteConsultaDTO(cliente.get(), cartaoDeCreditoDTO));
			}
			return ResponseEntity.ok(new ClienteDTO(cliente.get()));
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<List<Cliente>> listaCadastroPorCpf() {

		List<Cliente> listaDeClientes = clienteRepository.findAll();
		if(listaDeClientes == null || listaDeClientes.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		List<ClienteConsultaDTO> listaDTO = new ArrayList<ClienteConsultaDTO>();

		for (Cliente cliente : listaDeClientes) {
			ClienteConsultaDTO clienteConsultaDTO;
			
			if (cliente.getCartaoDeCredito() != null) {
				clienteConsultaDTO = new ClienteConsultaDTO(cliente, new CartaoDeCreditoDTO(cliente));
			} 
			
			clienteConsultaDTO = new ClienteConsultaDTO();
			
			
			listaDTO.add(clienteConsultaDTO);
		}
		return ResponseEntity.ok(listaDeClientes);
	}

	public ResponseEntity<?> deletarUsuario(String cpf) {
		Optional<Cliente> cliente = clienteRepository.findByCpf(cpf);
		
		if(cliente.isPresent()) {
			String mensagemRetorno = "Cliente: " + cliente.get().getNome() + " excluído";
			clienteRepository.delete(cliente.get());
			return ResponseEntity.ok(mensagemRetorno);
		}
		return ResponseEntity.notFound().build();
	}

}
