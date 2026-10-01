package com.example.composeinit.util

import java.text.NumberFormat
import java.util.Locale

private val localePtBr: Locale = Locale.Builder().setLanguage("pt").setRegion("BR").build()

/** Formata como moeda brasileira, ex.: 3499.0 -> "R$ 3.499,00". */
fun formatarMoeda(valor: Double): String =
    NumberFormat.getCurrencyInstance(localePtBr).format(valor)

/** Formata percentual sem casas inúteis, ex.: 5.0 -> "5", 7.5 -> "7,5". */
fun formatarPercentual(valor: Double): String =
    if (valor % 1.0 == 0.0) valor.toInt().toString()
    else valor.toString().replace('.', ',')
