package com.example.composeinit.data

import com.example.composeinit.domain.model.Produto

/**
 * Catálogo FIXO exigido pelo enunciado. É a única fonte de produtos do app;
 * o carrinho, as quantidades e todos os totais são gerados em tempo de execução
 * a partir das ações do usuário (nada do carrinho vem pré-preenchido).
 *
 * Cobre os requisitos:
 *  - mais de 6 produtos;
 *  - 3 produtos com desconto (Notebook, Monitor, Headset);
 *  - 2 produtos sem descrição (descricao = null);
 *  - 1 produto com nome longo (Monitor), para validar o truncamento do layout.
 */
object CatalogoProdutos {

    val produtos: List<Produto> = listOf(
        Produto(
            nome = "Notebook Dell Inspiron",
            preco = 3499.00,
            descricao = "Um notebook rápido para trabalho, estudos e multitarefa no dia a dia, " +
                    "com ótimo desempenho e bateria de longa duração",
            descontoPercentual = 5.0
        ),
        Produto(
            nome = "Mouse sem fio",
            preco = 89.90,
            descricao = null
        ),
        Produto(
            nome = "Teclado mecânico RGB",
            preco = 349.90,
            descricao = "Switch azul, ABNT2"
        ),
        Produto(
            nome = "Monitor Gamer Ultrawide Curvo 34 polegadas 165Hz com HDR e FreeSync Premium",
            preco = 2299.90,
            descricao = "Painel VA curvo com resolução WQHD, ideal para jogos e produtividade " +
                    "com várias janelas abertas ao mesmo tempo",
            descontoPercentual = 10.0
        ),
        Produto(
            nome = "Headset Gamer 7.1",
            preco = 299.90,
            descricao = "Áudio surround virtual 7.1 com microfone removível",
            descontoPercentual = 15.0
        ),
        Produto(
            nome = "Webcam Full HD",
            preco = 199.90,
            descricao = null
        ),
        Produto(
            nome = "Hub USB-C 7 em 1",
            preco = 159.90,
            descricao = "HDMI 4K, USB 3.0, leitor de cartões SD e carregamento passante"
        )
    )
}
