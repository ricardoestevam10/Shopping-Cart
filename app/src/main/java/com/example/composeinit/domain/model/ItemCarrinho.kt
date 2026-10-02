package com.example.composeinit.domain.model

import com.example.composeinit.domain.totalFinalItem


data class ItemCarrinho(
    val produto: Produto,
    val quantidade: Int
) : Pagavel {

    init {
        require(quantidade >= 0) { "A quantidade não pode ser negativa" }
    }

    override fun calcularTotal(): Double = totalFinalItem(this)
}
