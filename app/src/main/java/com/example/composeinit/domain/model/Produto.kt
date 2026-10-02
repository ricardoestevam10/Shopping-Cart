package com.example.composeinit.domain.model

import com.example.composeinit.domain.precoComDesconto


data class Produto(
    val nome: String,
    val preco: Double,
    val descricao: String? = null,
    val descontoPercentual: Double = 0.0
) : Pagavel {

    init {
        require(preco >= 0.0) { "O preço não pode ser negativo" }
        require(descontoPercentual in 0.0..100.0) { "Desconto deve estar entre 0 e 100" }
    }

    override fun calcularTotal(): Double = precoComDesconto(this)
}
