package com.cadastro.cartao.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadastro.cartao.controller.dto.ClienteDTO;
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
		return clienteService.cadastraCliente(clienteDTO);
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<?> consultaPorCpf(@PathVariable String cpf) {
		return clienteService.consultaPorCpf(cpf);
	}

	@GetMapping("/listar")
	public ResponseEntity<?> listaCadastroPorCpf() {
		return clienteService.listaCadastroPorCpf();

	}

	@DeleteMapping("/{cpf}")
	public ResponseEntity<?> deletaCadastroPorCpf(@PathVariable String cpf) {
		return clienteService.deletarUsuario(cpf);
	}

}
