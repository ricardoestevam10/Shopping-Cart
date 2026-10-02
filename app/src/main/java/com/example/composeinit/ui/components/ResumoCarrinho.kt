package com.example.composeinit.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composeinit.R
import com.example.composeinit.util.formatarMoeda


@Composable
fun ResumoCarrinho(
    subtotal: Double,
    descontos: Double,
    total: Double,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        tonalElevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            LinhaResumo(
                rotulo = stringResource(R.string.resumo_subtotal),
                valor = formatarMoeda(subtotal),
                style = MaterialTheme.typography.bodyLarge
            )
            LinhaResumo(
                rotulo = stringResource(R.string.resumo_descontos),
                valor = "-" + formatarMoeda(descontos),
                style = MaterialTheme.typography.bodyLarge
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            LinhaResumo(
                rotulo = stringResource(R.string.resumo_total),
                valor = formatarMoeda(total),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
private fun LinhaResumo(
    rotulo: String,
    valor: String,
    style: androidx.compose.ui.text.TextStyle
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = rotulo, style = style)
        Text(text = valor, style = style)
    }
}
