package com.cadastro.cartao.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadastro.cartao.controller.dto.CartaoDeCreditoDTO;
import com.cadastro.cartao.controller.dto.ClienteConsultaDTO;
import com.cadastro.cartao.controller.dto.ClienteDTO;
import com.cadastro.cartao.modelo.Cliente;
import com.cadastro.cartao.repository.CartaoDeCreditoRepository;
import com.cadastro.cartao.repository.ClienteRepository;
import com.cadastro.cartao.service.ClienteService;

@RestController
@RequestMapping("/cadastro")
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	CartaoDeCreditoRepository cartaoDeCreditoRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastroCliente(@RequestBody ClienteDTO clienteDTO) {

		if (clienteRepository.existsByCpf(clienteDTO.getCpf())) {
			return ResponseEntity.badRequest().build();
		}

		Cliente cliente = clienteService.validaScore(clienteDTO);
		if (cliente != null) {
			clienteRepository.save(cliente);
			return ResponseEntity.ok(new ClienteDTO(cliente));
		}

		cliente = new Cliente(clienteDTO);
		clienteRepository.save(cliente);
		return ResponseEntity.ok(new ClienteDTO(cliente));
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<?> consultaPorCpf(@PathVariable String cpf) {
		Cliente cliente = clienteService.consultaPorCpf(cpf);
		if (cliente != null) {
			if (cliente.getCartaoDeCredito() != null) {
				CartaoDeCreditoDTO cartaoDeCreditoDTO = new CartaoDeCreditoDTO(cliente);
				return ResponseEntity.ok(new ClienteConsultaDTO(cliente, cartaoDeCreditoDTO));
			}
			return ResponseEntity.ok(new ClienteDTO(cliente));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/listar")
	public ResponseEntity<?> listaCadastroPorCpf() {

		List<Cliente> listaClientes = clienteService.listaCadastroPorCpf();

		List<ClienteConsultaDTO> listaDTO = new ArrayList<ClienteConsultaDTO>();

		for (Cliente list : listaClientes) {

			ClienteConsultaDTO clienteConsultaDTO;

			if (list.getCartaoDeCredito() != null) {
				CartaoDeCreditoDTO cartaoDeCreditoDto = new CartaoDeCreditoDTO();
				cartaoDeCreditoDto.setId_cartao(list.getCartaoDeCredito().getId_cartao());
				cartaoDeCreditoDto.setNumeroCartao(list.getCartaoDeCredito().getNumeroCartao());
				clienteConsultaDTO = new ClienteConsultaDTO(cartaoDeCreditoDto);
			} else {
				clienteConsultaDTO = new ClienteConsultaDTO();
			}
			
			clienteConsultaDTO.setId_cliente(list.getId_cliente());
			clienteConsultaDTO.setNome(list.getNome());
			clienteConsultaDTO.setIdade(list.getIdade());
			clienteConsultaDTO.setCpf(list.getCpf());

			listaDTO.add(clienteConsultaDTO);
		}

		return ResponseEntity.ok(listaDTO);

	}

}
