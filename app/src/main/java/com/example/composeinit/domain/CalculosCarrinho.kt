package com.example.composeinit.domain

import com.example.composeinit.domain.model.ItemCarrinho
import com.example.composeinit.domain.model.Produto
import kotlin.math.round

// ---------------------------------------------------------------------------
// Camada de domínio: SOMENTE funções puras (sem Android, sem estado, sem I/O).
// Mesma entrada -> mesma saída, o que também as torna fáceis de testar.
// ---------------------------------------------------------------------------

/** Arredonda para 2 casas decimais (centavos), evitando ruído de ponto flutuante. */
fun arredondarCentavos(valor: Double): Double = round(valor * 100.0) / 100.0

/** Preço de uma unidade com o desconto percentual do produto aplicado. */
fun precoComDesconto(produto: Produto): Double =
    arredondarCentavos(produto.preco * (1.0 - produto.descontoPercentual / 100.0))

/** Total do item SEM desconto: preço × quantidade. */
fun totalBrutoItem(item: ItemCarrinho): Double =
    arredondarCentavos(item.produto.preco * item.quantidade)

/** Valor economizado no item: (preço × quantidade) × percentual. */
fun descontoItem(item: ItemCarrinho): Double =
    arredondarCentavos(totalBrutoItem(item) * item.produto.descontoPercentual / 100.0)

/** Total do item COM desconto: bruto − desconto. */
fun totalFinalItem(item: ItemCarrinho): Double =
    arredondarCentavos(totalBrutoItem(item) - descontoItem(item))

/** Soma dos preços sem desconto de todos os itens. */
fun subtotalBruto(itens: List<ItemCarrinho>): Double =
    arredondarCentavos(itens.sumOf { totalBrutoItem(it) })

/** Soma dos descontos aplicados em todos os itens. */
fun totalDescontos(itens: List<ItemCarrinho>): Double =
    arredondarCentavos(itens.sumOf { descontoItem(it) })

/** Valor final a pagar: subtotal bruto − descontos. */
fun totalFinal(itens: List<ItemCarrinho>): Double =
    arredondarCentavos(subtotalBruto(itens) - totalDescontos(itens))

// ---------------------------------------------------------------------------
// Operações sobre o carrinho. Imutáveis: devolvem uma NOVA lista, o que permite
// ao Compose detectar a mudança quando o resultado é atribuído a um mutableState.
// ---------------------------------------------------------------------------

/** Adiciona 1 unidade do produto; se já existir no carrinho, incrementa a quantidade. */
fun adicionarProduto(itens: List<ItemCarrinho>, produto: Produto): List<ItemCarrinho> =
    if (itens.any { it.produto == produto }) {
        alterarQuantidade(itens, produto, delta = 1)
    } else {
        itens + ItemCarrinho(produto = produto, quantidade = 1)
    }

/** Soma [delta] à quantidade do produto; itens que chegam a 0 saem do carrinho. */
fun alterarQuantidade(itens: List<ItemCarrinho>, produto: Produto, delta: Int): List<ItemCarrinho> =
    itens
        .map { if (it.produto == produto) it.copy(quantidade = it.quantidade + delta) else it }
        .filter { it.quantidade > 0 }

/** Remove o produto do carrinho por completo. */
fun removerProduto(itens: List<ItemCarrinho>, produto: Produto): List<ItemCarrinho> =
    itens.filterNot { it.produto == produto }
