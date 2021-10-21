package com.cadastro.cartao.controller;

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
import com.cadastro.cartao.controller.dto.ClienteComCartaoDTO;
import com.cadastro.cartao.controller.dto.ClienteConsultaDTO;
import com.cadastro.cartao.controller.dto.ClienteDTO;
import com.cadastro.cartao.modelo.CartaoDeCredito;
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
		
		
		if(clienteRepository.existsByCpf(clienteDTO.getCpf())) {
			return ResponseEntity.badRequest().build();
		}
		
		Cliente cliente = new Cliente(clienteDTO);
		CartaoDeCredito cartaoCredito = clienteService.inserir(clienteDTO, cliente);
		
		if (cartaoCredito != null) {
			cliente.setPossuiCartao("sim");
			clienteRepository.save(cliente);
			return ResponseEntity.ok(new ClienteComCartaoDTO(cliente, cartaoCredito));
		}
		
		clienteRepository.save(cliente);
		clienteDTO.setId_cliente(cliente.getId_cliente());
		return ResponseEntity.ok(clienteDTO);
	}
	
	
	@GetMapping("/{cpf}")
	public ResponseEntity<?> consultaPorCpf(@PathVariable String cpf) {
		Cliente cliente = clienteService.consultaPorCpf(cpf);
		
		
		CartaoDeCredito cartaoDeCredito = cartaoDeCreditoRepository.findByClienteId_cliente(cliente.getCpf());
		
		return ResponseEntity.ok(cliente);
	}
	
}
