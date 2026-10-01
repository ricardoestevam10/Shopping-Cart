package com.example.composeinit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composeinit.R
import com.example.composeinit.data.CatalogoProdutos
import com.example.composeinit.domain.adicionarProduto
import com.example.composeinit.domain.alterarQuantidade
import com.example.composeinit.domain.model.ItemCarrinho
import com.example.composeinit.domain.removerProduto
import com.example.composeinit.domain.subtotalBruto
import com.example.composeinit.domain.totalBrutoItem
import com.example.composeinit.domain.totalDescontos
import com.example.composeinit.domain.totalFinal
import com.example.composeinit.report.RelatorioCarrinho
import com.example.composeinit.ui.components.ProdutoCatalogo
import com.example.composeinit.ui.components.ProdutoLinha
import com.example.composeinit.ui.components.ResumoCarrinho

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarrinhoScreen(modifier: Modifier = Modifier) {
    // Único estado da tela: o carrinho. Começa vazio e só muda por ações do usuário.
    // Cada ação cria uma NOVA lista (funções puras do domínio) e atribui ao mutableState.
    var itens by remember { mutableStateOf(emptyList<ItemCarrinho>()) }

    // Sempre que o carrinho muda, o relatório é reimpresso no Logcat (tag "RelatorioCarrinho").
    LaunchedEffect(itens) {
        RelatorioCarrinho.imprimirNoLogcat(itens)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = stringResource(R.string.icone_carrinho)
                        )
                        Text(text = stringResource(R.string.meu_carrinho))
                    }
                }
            )
        },
        bottomBar = {
            ResumoCarrinho(
                subtotal = subtotalBruto(itens),
                descontos = totalDescontos(itens),
                total = totalFinal(itens)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ---- Itens do carrinho (laço de repetição + componente reutilizável) ----
            if (itens.isEmpty()) {
                Text(
                    text = stringResource(R.string.carrinho_vazio),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                for (item in itens) {
                    key(item.produto) {
                        ProdutoLinha(
                            nome = item.produto.nome,
                            descricao = item.produto.descricao,
                            precoUnitario = item.produto.preco,
                            quantidade = item.quantidade,
                            total = totalBrutoItem(item),
                            totalComDesconto = item.calcularTotal(),
                            descontoPercentual = item.produto.descontoPercentual,
                            onDiminuir = { itens = alterarQuantidade(itens, item.produto, -1) },
                            onAumentar = { itens = alterarQuantidade(itens, item.produto, +1) },
                            onRemover = { itens = removerProduto(itens, item.produto) }
                        )
                    }
                }
            }

            // ---- Catálogo: o usuário escolhe o que entra no carrinho ----
            Text(
                text = stringResource(R.string.catalogo_titulo),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 12.dp)
            )
            for (produto in CatalogoProdutos.produtos) {
                key(produto) {
                    ProdutoCatalogo(
                        nome = produto.nome,
                        descricao = produto.descricao,
                        preco = produto.preco,
                        descontoPercentual = produto.descontoPercentual,
                        onAdicionar = { itens = adicionarProduto(itens, produto) }
                    )
                }
            }
        }
    }
}
