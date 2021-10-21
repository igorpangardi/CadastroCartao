package com.cadastro.cartao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cadastro.cartao.modelo.CartaoDeCredito;

public interface CartaoDeCreditoRepository extends JpaRepository<CartaoDeCredito, Long>{

	CartaoDeCredito findByClienteId_cliente(String cpf);

}
