package com.example.composeinit.report

import android.util.Log
import com.example.composeinit.domain.model.ItemCarrinho
import com.example.composeinit.util.formatarMoeda


object RelatorioCarrinho {

    const val TAG = "RelatorioCarrinho"


    fun gerarLinhas(itens: List<ItemCarrinho>): List<String> {
        val comDesconto = itens
            .filter { it.produto.descontoPercentual > 0.0 }
            .sortedByDescending { it.calcularTotal() }

        if (comDesconto.isEmpty()) {
            return listOf("Nenhum produto com desconto no carrinho.")
        }

        val linhas = comDesconto.mapIndexed { indice, item ->
            "${indice + 1}. ${item.produto.nome} -> ${formatarMoeda(item.calcularTotal())}"
        }

        val somaDosDescontados = comDesconto
            .map { it.calcularTotal() }
            .reduce { acumulado, valor -> acumulado + valor }

        return listOf("===== PRODUTOS COM DESCONTO =====") +
                linhas +
                listOf("---------------------------------",
                    "Soma (com desconto): ${formatarMoeda(somaDosDescontados)}")
    }


    fun imprimirNoLogcat(itens: List<ItemCarrinho>) {
        gerarLinhas(itens).forEach { linha -> Log.d(TAG, linha) }
    }
}
