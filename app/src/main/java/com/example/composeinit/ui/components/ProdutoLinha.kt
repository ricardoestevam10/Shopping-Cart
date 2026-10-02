package com.example.composeinit.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.composeinit.R
import com.example.composeinit.util.formatarMoeda
import com.example.composeinit.util.formatarPercentual


@Composable
fun ProdutoLinha(
    nome: String,
    descricao: String?,
    precoUnitario: Double,
    quantidade: Int,
    total: Double,
    totalComDesconto: Double,
    descontoPercentual: Double,
    onDiminuir: () -> Unit,
    onAumentar: () -> Unit,
    onRemover: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = nome,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                if (descontoPercentual > 0.0) {
                    EtiquetaDesconto(descontoPercentual)
                }
            }

            TextoDescricao(descricao)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        R.string.preco_quantidade,
                        formatarMoeda(precoUnitario),
                        quantidade
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = formatarMoeda(total),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            if (descontoPercentual > 0.0) {
                Text(
                    text = stringResource(R.string.total_com_desconto, formatarMoeda(totalComDesconto)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                val descricaoDiminuir = stringResource(R.string.diminuir_quantidade)
                IconButton(
                    onClick = onDiminuir,
                    modifier = Modifier.semantics { contentDescription = descricaoDiminuir }
                ) {
                    Text(
                        text = "−",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Text(
                    text = quantidade.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                IconButton(onClick = onAumentar) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.aumentar_quantidade)
                    )
                }
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onRemover) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.remover_item)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun TextoDescricao(descricao: String?, modifier: Modifier = Modifier) {
    val textoSeguro = descricao?.takeIf { it.isNotBlank() }
        ?: stringResource(R.string.sem_descricao)
    Text(
        text = textoSeguro,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}


@Composable
fun EtiquetaDesconto(descontoPercentual: Double, modifier: Modifier = Modifier) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.tertiaryContainer,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.desconto_etiqueta, formatarPercentual(descontoPercentual)),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}
