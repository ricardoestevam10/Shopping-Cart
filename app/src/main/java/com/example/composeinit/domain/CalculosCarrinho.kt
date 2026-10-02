package com.example.composeinit.domain

import com.example.composeinit.domain.model.ItemCarrinho
import com.example.composeinit.domain.model.Produto
import kotlin.math.round




fun arredondarCentavos(valor: Double): Double = round(valor * 100.0) / 100.0


fun precoComDesconto(produto: Produto): Double =
    arredondarCentavos(produto.preco * (1.0 - produto.descontoPercentual / 100.0))


fun totalBrutoItem(item: ItemCarrinho): Double =
    arredondarCentavos(item.produto.preco * item.quantidade)


fun descontoItem(item: ItemCarrinho): Double =
    arredondarCentavos(totalBrutoItem(item) * item.produto.descontoPercentual / 100.0)


fun totalFinalItem(item: ItemCarrinho): Double =
    arredondarCentavos(totalBrutoItem(item) - descontoItem(item))


fun subtotalBruto(itens: List<ItemCarrinho>): Double =
    arredondarCentavos(itens.sumOf { totalBrutoItem(it) })


fun totalDescontos(itens: List<ItemCarrinho>): Double =
    arredondarCentavos(itens.sumOf { descontoItem(it) })


fun totalFinal(itens: List<ItemCarrinho>): Double =
    arredondarCentavos(subtotalBruto(itens) - totalDescontos(itens))


fun adicionarProduto(itens: List<ItemCarrinho>, produto: Produto): List<ItemCarrinho> =
    if (itens.any { it.produto == produto }) {
        alterarQuantidade(itens, produto, delta = 1)
    } else {
        itens + ItemCarrinho(produto = produto, quantidade = 1)
    }

fun alterarQuantidade(itens: List<ItemCarrinho>, produto: Produto, delta: Int): List<ItemCarrinho> =
    itens
        .map { if (it.produto == produto) it.copy(quantidade = it.quantidade + delta) else it }
        .filter { it.quantidade > 0 }


fun removerProduto(itens: List<ItemCarrinho>, produto: Produto): List<ItemCarrinho> =
    itens.filterNot { it.produto == produto }
