package com.example.composeinit.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.composeinit.R
import com.example.composeinit.util.formatarMoeda

@Composable
fun ProdutoCatalogo(
    nome: String,
    descricao: String?,
    preco: Double,
    descontoPercentual: Double,
    onAdicionar: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = nome,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                TextoDescricao(descricao)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = formatarMoeda(preco),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (descontoPercentual > 0.0) {
                        EtiquetaDesconto(descontoPercentual)
                    }
                }
            }
            FilledTonalButton(onClick = onAdicionar) {
                Text(
                    text = stringResource(R.string.adicionar),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
