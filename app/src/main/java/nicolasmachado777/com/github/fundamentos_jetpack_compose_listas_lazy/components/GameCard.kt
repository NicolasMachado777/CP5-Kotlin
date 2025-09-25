package nicolasmachado777.com.github.fundamentos_jetpack_compose_listas_lazy.components
// Pacote onde este componente (Composable) está localizado.
// Mantém organização e controle de visibilidade.

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nicolasmachado777.com.github.fundamentos_jetpack_compose_listas_lazy.model.Game
import nicolasmachado777.com.github.fundamentos_jetpack_compose_listas_lazy.ui.theme.FundamentosjetpackcomposelistaslazyTheme


/**
 * Composable que renderiza um cartão (Card) exibindo as informações principais de um jogo.
 *
 * Estrutura visual:
 * - Card como contêiner
 *   - Row (linha) dividindo a área em duas partes:
 *     [Coluna à esquerda (título e estúdio)]   [Ano à direita]
 *
 * @param game Objeto contendo os dados do jogo (id, title, studio, releaseYear).
 */
@Composable
fun GameCard(game: Game) {
    Card(
        // Espaço inferior para separar este card de outros elementos na lista.
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Row(
            // Alinha os filhos verticalmente ao centro (texto da esquerda e ano da direita).
            verticalAlignment = Alignment.CenterVertically,
            // Distribui espaço entre os filhos (esquerda e direita), colocando-os nas extremidades.
            horizontalArrangement = Arrangement.SpaceBetween,
            // Faz a Row ocupar toda a largura disponível do Card.
            modifier = Modifier.fillMaxWidth()
        ) {
            // Coluna da esquerda: título (maior/destaque) e estúdio (menor).
            Column(
                modifier = Modifier
                    // Pede para a Column ocupar a largura disponível dentro do seu espaço.
                    .fillMaxWidth()
                    // Padding interno para afastar o conteúdo das bordas do Card.
                    .padding(16.dp)
                    // Peso 3f: dentro da Row, esta Column recebe 3 partes de largura
                    // em relação aos outros elementos com weight (abaixo o ano recebe 1f).
                    .weight(3f)
            ) {
                // Título do jogo: texto maior e em negrito (destaque).
                Text(
                    text = game.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                // Nome do estúdio: menor e com peso normal.
                Text(
                    text = game.studio,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            // Texto do ano de lançamento (à direita).
            Text(
                text = game.releaseYear.toString(),
                modifier = Modifier
                    // Peso 1f: dentro da Row, este Text recebe 1 parte de largura
                    // (total: Column 3f + Text 1f => 4 partes; proporção 3:1).
                    .weight(1f)
                    // Faz o Text ocupar a largura do seu espaço reservado (do weight).
                    .fillMaxWidth(),
                // Tamanho de fonte maior e negrito para dar destaque ao ano.
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                // Azul para destacar visualmente o ano.
                color = Color.Blue
            )
        }
    }
}

/**
 * Preview para visualizar o GameCard no Android Studio sem rodar o app.
 * Fornece um Game de exemplo para renderização.
 */
@Preview(showBackground = true, name = "Game Card Preview")
@Composable
fun PreviewGameCard() {
    FundamentosjetpackcomposelistaslazyTheme {
        GameCard(game = Game(1, "Example Game", "Example Studio", 2023))
    }
}
