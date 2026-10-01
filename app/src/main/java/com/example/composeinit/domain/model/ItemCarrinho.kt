package com.example.composeinit.domain.model

import com.example.composeinit.domain.totalFinalItem

/**
 * Relaciona um [Produto] a uma quantidade dentro do carrinho.
 * Como [Pagavel], o total é (preço × quantidade) já com o desconto aplicado.
 */
data class ItemCarrinho(
    val produto: Produto,
    val quantidade: Int
) : Pagavel {

    init {
        require(quantidade >= 0) { "A quantidade não pode ser negativa" }
    }

    override fun calcularTotal(): Double = totalFinalItem(this)
}
