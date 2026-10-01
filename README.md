# Calculadora de Carrinho de Compras

**Aluno:** _[SEU NOME COMPLETO AQUI]_

Aplicativo Android (Kotlin + Jetpack Compose + Material 3) que carrega um catálogo fixo de produtos,
aplica descontos, mostra o carrinho de forma parametrizada e gera um relatório no Logcat.

## Capturas de tela

| Emulador | Logcat |
|---|---|
| _[inserir print do emulador com o cenário de validação]_ | _[inserir print do Logcat, filtro `RelatorioCarrinho`]_ |

## Como validar o "Cenário de Validação"

O carrinho começa **vazio** (nada vem pré-preenchido). No catálogo, toque em **Adicionar**:

1. **Notebook Dell Inspiron** duas vezes (ou uma vez e depois **+**)
2. **Mouse sem fio** uma vez
3. **Teclado mecânico RGB** uma vez

Resumo esperado na tela: Subtotal **R$ 7.437,80** · Descontos **-R$ 349,90** · TOTAL **R$ 7.087,90**.
O mesmo cenário é testado automaticamente em `app/src/test/.../CarrinhoTest.kt`.

## Arquitetura

```
domain/
  model/Pagavel.kt        interface Pagavel { calcularTotal() }
  model/Produto.kt        nome, preco, descricao: String? , descontoPercentual = 0.0   (implementa Pagavel)
  model/ItemCarrinho.kt   produto + quantidade                                        (implementa Pagavel)
  CalculosCarrinho.kt     funções PURAS: totais, descontos e operações imutáveis do carrinho
data/CatalogoProdutos.kt  catálogo fixo (7 produtos)
report/RelatorioCarrinho  filter + sortedByDescending + mapIndexed + reduce -> Logcat (tag RelatorioCarrinho)
util/Formatadores.kt      moeda em pt-BR
ui/components/            ProdutoLinha (reutilizável, só parâmetros), ProdutoCatalogo, ResumoCarrinho
ui/screens/CarrinhoScreen único estado: var itens by remember { mutableStateOf(emptyList<ItemCarrinho>()) }
```

Decisões principais:

- **Domínio puro:** nenhuma função de cálculo depende de Android ou de estado; a UI só chama e exibe.
- **Estado imutável:** cada ação (adicionar, +, −, remover) gera uma nova lista e a atribui ao `mutableStateOf`,
  o que dispara a recomposição. Um `LaunchedEffect(itens)` reimprime o relatório no Logcat a cada mudança.
- **Arredondamento em centavos** (`arredondarCentavos`) para evitar ruído de ponto flutuante.
- **Tipografia:** somente `MaterialTheme.typography.*`; `Type.kt` usa `Typography()` padrão (sem `sp` manual).
- **Truncamento:** nome `maxLines = 1` e descrição `maxLines = 2`, ambos com `TextOverflow.Ellipsis`.
- **Nulos:** `descricao?.takeIf { it.isNotBlank() } ?: "Sem descrição"` — nunca `!!`.
- **Laços na UI:** `for (item in itens) { ProdutoLinha(...) }` e `for (produto in catalogo) { ... }`.

## Executar

Abra a pasta no Android Studio, aguarde o Gradle sync e execute o módulo `app` (minSdk 24).
Testes: `./gradlew test`. Logcat: filtre por `RelatorioCarrinho`.
