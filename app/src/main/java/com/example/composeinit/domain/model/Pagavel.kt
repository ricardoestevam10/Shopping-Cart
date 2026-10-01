package com.example.composeinit.domain.model

/**
 * Contrato para qualquer entidade que possua um valor total a pagar.
 * Quem implementa decide como o total é calculado (ex.: aplicando desconto).
 */
interface Pagavel {
    fun calcularTotal(): Double
}
