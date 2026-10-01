package com.example.composeinit.domain.model

import com.example.composeinit.domain.precoComDesconto

/**
 * Produto do catálogo.
 *
 * @property descricao pode ser nula (produto sem descrição).
 * @property descontoPercentual de 0.0 a 100.0; por padrão não há desconto.
 *
 * Como [Pagavel], o total de um produto é o preço de UMA unidade já com desconto.
 */
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
