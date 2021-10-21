package com.cadastro.cartao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cadastro.cartao.modelo.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	public Cliente findByCpf(String cpf);

	public boolean existsByCpf(String cpfDTO);


}
