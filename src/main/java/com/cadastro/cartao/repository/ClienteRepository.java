package com.cadastro.cartao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cadastro.cartao.modelo.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	public Optional <Cliente>findByCpf(String cpf);

	public boolean existsByCpf(String cpfDTO);

	public void deleteByCpf(String cpf);


}
