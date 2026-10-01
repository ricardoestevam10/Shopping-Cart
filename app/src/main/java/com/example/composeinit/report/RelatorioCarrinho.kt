package com.example.composeinit.report

import android.util.Log
import com.example.composeinit.domain.model.ItemCarrinho
import com.example.composeinit.util.formatarMoeda

/**
 * Relatório do carrinho para o Logcat.
 *
 * Filtro: só entram itens cujo produto tem desconto.
 * Ordem: do maior valor total (já com desconto) para o menor.
 */
object RelatorioCarrinho {

    const val TAG = "RelatorioCarrinho"

    /** Parte pura: monta as linhas do relatório com operações funcionais em coleções. */
    fun gerarLinhas(itens: List<ItemCarrinho>): List<String> {
        val comDesconto = itens
            .filter { it.produto.descontoPercentual > 0.0 }          // filtragem
            .sortedByDescending { it.calcularTotal() }               // ordenação (maior -> menor)

        if (comDesconto.isEmpty()) {
            return listOf("Nenhum produto com desconto no carrinho.")
        }

        val linhas = comDesconto.mapIndexed { indice, item ->        // mapeamento
            "${indice + 1}. ${item.produto.nome} -> ${formatarMoeda(item.calcularTotal())}"
        }

        val somaDosDescontados = comDesconto
            .map { it.calcularTotal() }
            .reduce { acumulado, valor -> acumulado + valor }        // redução

        return listOf("===== PRODUTOS COM DESCONTO =====") +
                linhas +
                listOf("---------------------------------",
                    "Soma (com desconto): ${formatarMoeda(somaDosDescontados)}")
    }

    /** Parte com efeito colateral: imprime cada linha no Logcat. */
    fun imprimirNoLogcat(itens: List<ItemCarrinho>) {
        gerarLinhas(itens).forEach { linha -> Log.d(TAG, linha) }
    }
}
