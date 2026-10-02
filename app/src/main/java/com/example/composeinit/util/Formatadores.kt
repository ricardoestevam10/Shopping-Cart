package com.example.composeinit.util

import java.text.NumberFormat
import java.util.Locale

private val localePtBr: Locale = Locale.Builder().setLanguage("pt").setRegion("BR").build()


fun formatarMoeda(valor: Double): String =
    NumberFormat.getCurrencyInstance(localePtBr).format(valor)


fun formatarPercentual(valor: Double): String =
    if (valor % 1.0 == 0.0) valor.toInt().toString()
    else valor.toString().replace('.', ',')
