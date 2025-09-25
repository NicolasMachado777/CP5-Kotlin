package nicolasmachado777.com.github.fundamentos_jetpack_compose_listas_lazy
// Pacote principal do app. Contém a Activity e a tela (Composable) principal.

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nicolasmachado777.com.github.fundamentos_jetpack_compose_listas_lazy.components.GameCard
import nicolasmachado777.com.github.fundamentos_jetpack_compose_listas_lazy.components.StudioCard
import nicolasmachado777.com.github.fundamentos_jetpack_compose_listas_lazy.model.Game
import nicolasmachado777.com.github.fundamentos_jetpack_compose_listas_lazy.repository.getAllGames
import nicolasmachado777.com.github.fundamentos_jetpack_compose_listas_lazy.repository.getGamesByStudio
import nicolasmachado777.com.github.fundamentos_jetpack_compose_listas_lazy.ui.theme.FundamentosjetpackcomposelistaslazyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Permite que o conteúdo ocupe a tela inteira (com paddings apropriados).
        setContent {
            FundamentosjetpackcomposelistaslazyTheme {
                // Scaffold fornece estrutura básica de tela (ex.: TopAppBar/BottomBar/FAB no futuro).
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // O innerPadding compensa barras/status para que o conteúdo não fique "por baixo".
                    GamesScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * Tela principal que exibe:
 * - Título;
 * - Campo de busca por estúdio (OutinedTextField) com ícone de "buscar";
 * - Botão textual "Limpar filtro" quando há busca/filtro aplicado;
 * - Uma LazyRow com estúdios (derivados dos jogos atuais) — ao clicar, filtra por estúdio;
 * - Uma LazyColumn com a lista de jogos (todos ou filtrados).
 */
@Composable
fun GamesScreen(modifier: Modifier = Modifier) {
    // Estado que guarda o texto digitado no campo de busca.
    var searchTextState by remember { mutableStateOf("") }

    // Estado que guarda a lista atualmente exibida na tela.
    // Inicia carregando todos os jogos (via repositório).
    var gamesListState by remember { mutableStateOf(getAllGames()) }

    Column(modifier = modifier.padding(16.dp)) {
        // Cabeçalho/título da tela.
        Text(
            text = "Meus jogos favoritos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto com label e ícone de busca na trailingIcon.
        // O filtro é acionado ao clicar no ícone (não enquanto digita, no código atual).
        OutlinedTextField(
            value = searchTextState,
            onValueChange = { searchTextState = it }, // Atualiza o estado com o que o usuário digita.
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Nome do estúdio") },
            trailingIcon = {
                IconButton(onClick = {
                    // Ao clicar na lupa, filtra por estúdio conforme o texto atual.
                    gamesListState = getGamesByStudio(searchTextState)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "" // Dica: pode descrever "Buscar por estúdio" p/ acessibilidade.
                    )
                }
            }
        )

        // "Botão" de texto para limpar o filtro.
        // É exibido quando:
        // 1) Existe texto no campo (usuário digitou algo), OU
        // 2) A lista atual é diferente de getAllGames() (ou seja, parece estar filtrada).
        // Observação: esta condição 2 chama getAllGames() de novo (pode ser custoso ou instável
        // se getAllGames() gera nova lista a cada chamada). Mas mantivemos exatamente como no seu código.
        if (searchTextState.isNotEmpty() || gamesListState != getAllGames()) {
            Text(
                text = "Limpar filtro",
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .clickable {
                        // Ao clicar, limpa o texto e restaura a lista completa.
                        searchTextState = ""
                        gamesListState = getAllGames()
                    },
                fontWeight = FontWeight.SemiBold,
                color = androidx.compose.ui.graphics.Color.Blue // Azul para dar aparência de link/botão textual.
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Carrossel horizontal de "cards" de estúdio.
        // Aqui você está usando cada Game atual para mostrar seu estúdio.
        // Ao clicar em um card, aplica o filtro por aquele estúdio e também preenche o texto da busca.
        LazyRow() {
            items(gamesListState) { game ->
                StudioCard(
                    game = game,
                    onClick = {
                        // Clique no estúdio: reflete no campo de busca e aplica o filtro correspondente.
                        searchTextState = game.studio
                        gamesListState = getGamesByStudio(game.studio)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista vertical (lazy) com os jogos atuais (todos ou filtrados).
        LazyColumn() {
            items(gamesListState) { game ->
                GameCard(game = game)
            }
        }
    }
}

/**
 * Previews para visualizar a tela e os componentes no Android Studio, sem rodar a app.
 */
@Preview(showBackground = true, name = "Games Screen Preview")
@Composable
fun PreviewGamesScreen() {
    FundamentosjetpackcomposelistaslazyTheme {
        GamesScreen()
    }
}

@Preview(showBackground = true, name = "Studio Card Preview")
@Composable
fun PreviewStudioCard() {
    FundamentosjetpackcomposelistaslazyTheme {
        StudioCard(game = Game(1, "Example Game", "Example Studio", 2023))
    }
}

@Preview(showBackground = true, name = "Game Card Preview")
@Composable
fun PreviewGameCard() {
    FundamentosjetpackcomposelistaslazyTheme {
        GameCard(game = Game(1, "Example Game", "Example Studio", 2023))
    }
}
