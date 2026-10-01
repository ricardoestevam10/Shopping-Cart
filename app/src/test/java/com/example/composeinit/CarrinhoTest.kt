package com.example.composeinit

import com.example.composeinit.data.CatalogoProdutos
import com.example.composeinit.domain.adicionarProduto
import com.example.composeinit.domain.alterarQuantidade
import com.example.composeinit.domain.model.ItemCarrinho
import com.example.composeinit.domain.model.Pagavel
import com.example.composeinit.domain.model.Produto
import com.example.composeinit.domain.removerProduto
import com.example.composeinit.domain.subtotalBruto
import com.example.composeinit.domain.totalDescontos
import com.example.composeinit.domain.totalFinal
import com.example.composeinit.report.RelatorioCarrinho
import com.example.composeinit.util.formatarMoeda
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CarrinhoTest {

    private fun produto(nome: String) = CatalogoProdutos.produtos.first { it.nome == nome }

    /** Monta o carrinho do "Cenário de Validação" usando as mesmas ações da UI. */
    private fun carrinhoValidacao(): List<ItemCarrinho> {
        val notebook = produto("Notebook Dell Inspiron")
        val mouse = produto("Mouse sem fio")
        val teclado = produto("Teclado mecânico RGB")
        var itens = emptyList<ItemCarrinho>()
        itens = adicionarProduto(itens, notebook)
        itens = adicionarProduto(itens, notebook) // quantidade 2
        itens = adicionarProduto(itens, mouse)
        itens = adicionarProduto(itens, teclado)
        return itens
    }

    private fun normaliza(s: String) = s.replace('\u00A0', ' ')

    @Test
    fun cenarioDeValidacao_bateComValoresEsperados() {
        val itens = carrinhoValidacao()
        assertEquals(7437.80, subtotalBruto(itens), 0.001)
        assertEquals(349.90, totalDescontos(itens), 0.001)
        assertEquals(7087.90, totalFinal(itens), 0.001)
        assertEquals("R$ 7.437,80", normaliza(formatarMoeda(subtotalBruto(itens))))
        assertEquals("R$ 349,90", normaliza(formatarMoeda(totalDescontos(itens))))
        assertEquals("R$ 7.087,90", normaliza(formatarMoeda(totalFinal(itens))))
    }

    @Test
    fun catalogo_atendeRequisitos() {
        val p = CatalogoProdutos.produtos
        assertTrue(p.size >= 6)
        assertTrue(p.count { it.descontoPercentual > 0.0 } >= 2)
        assertTrue(p.any { it.descricao == null })
        assertTrue(p.any { it.nome.length > 40 })
    }

    @Test
    fun produto_descontoPadraoEhZero_eDescricaoPodeSerNula() {
        val p = Produto(nome = "X", preco = 10.0)
        assertEquals(0.0, p.descontoPercentual, 0.0)
        assertNull(p.descricao)
    }

    @Test
    fun entidadesImplementamPagavel() {
        val p = Produto("X", 200.0, descontoPercentual = 10.0)
        val pagaveis: List<Pagavel> = listOf(p, ItemCarrinho(p, 3))
        assertEquals(180.0, pagaveis[0].calcularTotal(), 0.001)
        assertEquals(540.0, pagaveis[1].calcularTotal(), 0.001)
    }

    @Test
    fun operacoesDoCarrinho_diminuirERemover() {
        val mouse = produto("Mouse sem fio")
        var itens = adicionarProduto(emptyList(), mouse)
        itens = alterarQuantidade(itens, mouse, +1)
        assertEquals(2, itens.single().quantidade)
        itens = alterarQuantidade(itens, mouse, -1)
        itens = alterarQuantidade(itens, mouse, -1) // chega a 0 -> sai do carrinho
        assertTrue(itens.isEmpty())
        itens = removerProduto(adicionarProduto(emptyList(), mouse), mouse)
        assertTrue(itens.isEmpty())
    }

    @Test
    fun relatorio_soTemProdutosComDesconto_ordenadosDoMaiorParaOMenor() {
        val itens = carrinhoValidacao() +
                ItemCarrinho(produto("Headset Gamer 7.1"), 1) +
                ItemCarrinho(produto("Monitor Gamer Ultrawide Curvo 34 polegadas 165Hz com HDR e FreeSync Premium"), 1)
        val linhas = RelatorioCarrinho.gerarLinhas(itens).map(::normaliza)
        val texto = linhas.joinToString("\n")
        assertTrue(!texto.contains("Mouse"))
        assertTrue(!texto.contains("Teclado"))
        val ordem = linhas.filter { it.matches(Regex("""^\d+\..*""")) }
        assertEquals(3, ordem.size)
        assertTrue(ordem[0].contains("Notebook"))   // 6.648,10
        assertTrue(ordem[1].contains("Monitor"))    // 2.069,91
        assertTrue(ordem[2].contains("Headset"))    //   254,92
        assertTrue(ordem[0].contains("R$ 6.648,10"))
    }

    @Test
    fun relatorio_semDescontos() {
        val linhas = RelatorioCarrinho.gerarLinhas(listOf(ItemCarrinho(produto("Mouse sem fio"), 1)))
        assertEquals(1, linhas.size)
    }
}
