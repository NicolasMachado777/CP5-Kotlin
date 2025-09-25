package nicolasmachado777.com.github.fundamentos_jetpack_compose_listas_lazy.components
// Define o pacote onde este componente (Composable) está localizado.
// Isso organiza seu código e controla a visibilidade entre módulos/pacotes.

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nicolasmachado777.com.github.fundamentos_jetpack_compose_listas_lazy.model.Game
import nicolasmachado777.com.github.fundamentos_jetpack_compose_listas_lazy.ui.theme.FundamentosjetpackcomposelistaslazyTheme


/**
 * Composable que exibe um card para um estúdio (usando os dados de um Game).
 *
 * @param game Objeto Game, do qual usaremos o campo `studio` para mostrar o nome do estúdio.
 * @param onClick Ação opcional ao clicar no card (se for null, o clique é desabilitado).
 */
@Composable
fun StudioCard(game: Game, onClick: (() -> Unit)? = null) {
    Card(
        modifier = Modifier
            .size(100.dp)             // Define tamanho fixo do card (largura e altura = 100dp).
            .padding(end = 4.dp)      // Espaço à direita para separar dos próximos itens na LazyRow.
            // Torna o card clicável APENAS se onClick não for nulo.
            .clickable(enabled = onClick != null) { onClick?.invoke() }
    ) {
        // Coluna centralizada para posicionar o conteúdo no meio do card.
        Column(
            verticalArrangement = Arrangement.Center,       // Centraliza verticalmente.
            horizontalAlignment = Alignment.CenterHorizontally, // Centraliza horizontalmente.
            modifier = Modifier.fillMaxSize()               // Ocupa todo o espaço do card.
        ) {
            // Mostra o nome do estúdio (campo `studio` do Game).
            Text(text = game.studio)
        }
    }
}

/**
 * Preview para visualizar o StudioCard no Android Studio sem rodar no dispositivo.
 */
@Preview(showBackground = true, name = "Studio Card Preview")
@Composable
fun PreviewStudioCard() {
    FundamentosjetpackcomposelistaslazyTheme {
        // Exemplo de Game apenas para o preview.
        StudioCard(game = Game(1, "Example Game", "Example Studio", 2023))
    }
}
