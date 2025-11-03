package com.example.p_4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.p_4.ui.theme.P4Theme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			P4Theme {
				App()
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun App() {
	Scaffold(
		topBar = {
			TopAppBar(title = { Text(text = "P_4 - Recomendaciones") })
		}
	) { innerPadding ->
		RecommendationScreen(innerPadding)
	}
}

@Composable
private fun RecommendationScreen(innerPadding: PaddingValues) {
	var season by remember { mutableStateOf("Primavera") }
	var condition by remember { mutableStateOf("Soleado") }
	var temperature by remember { mutableStateOf(22f) }
	var recommendations by remember { mutableStateOf(listOf<String>()) }

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(innerPadding)
			.padding(16.dp),
		horizontalAlignment = Alignment.Start,
		verticalArrangement = Arrangement.Top
	) {
		Text(text = "Temporada", style = MaterialTheme.typography.titleMedium)
		SimpleDropdown(
			value = season,
			options = listOf("Primavera", "Verano", "Otoño", "Invierno"),
			onValueChange = { season = it }
		)
		Spacer(modifier = Modifier.height(12.dp))

		Text(text = "Clima", style = MaterialTheme.typography.titleMedium)
		SimpleDropdown(
			value = condition,
			options = listOf("Soleado", "Nublado", "Lluvioso", "Ventoso"),
			onValueChange = { condition = it }
		)
		Spacer(modifier = Modifier.height(12.dp))

		Text(text = "Temperatura: ${"%.0f".format(temperature)} °C", style = MaterialTheme.typography.titleMedium)
		Row(verticalAlignment = Alignment.CenterVertically) {
			Slider(
				value = temperature,
				onValueChange = { temperature = it },
				valueRange = 0f..45f
			)
		}
		Spacer(modifier = Modifier.height(16.dp))

		Button(onClick = {
			recommendations = recommendOutfit(
				season = season,
				temperatureC = temperature.toInt(),
				condition = condition
			)
		}) {
			Text("Recomendar vestimenta")
		}

		Spacer(modifier = Modifier.height(16.dp))
		Text(text = "Sugerencias:", style = MaterialTheme.typography.titleMedium)
		recommendations.forEach { item ->
			Text("• $item")
		}
	}
}

@Composable
private fun SimpleDropdown(
	value: String,
	options: List<String>,
	onValueChange: (String) -> Unit
) {
	// Minimalista: usar TextField editable como pseudo dropdown para evitar dependencias extra
	TextField(
		value = value,
		onValueChange = { newValue ->
			if (options.any { it.startsWith(newValue, ignoreCase = true) }) {
				onValueChange(newValue)
			}
		},
		label = { Text("${options.joinToString(", ")}") }
	)
}

private fun recommendOutfit(
	season: String,
	temperatureC: Int,
	condition: String
): List<String> {
	val list = mutableListOf<String>()

	when (season) {
		"Primavera" -> {
			list += "Capas ligeras (suéter delgado o cárdigan)"
			list += "Pantalón ligero o jeans"
		}
		"Verano" -> {
			list += "Ropa fresca (playera/ropa de lino)"
			list += "Shorts o falda"
		}
		"Otoño" -> {
			list += "Capa media (sudadera/chamarra ligera)"
			list += "Pantalón"
		}
		"Invierno" -> {
			list += "Abrigo/chamarra gruesa"
			list += "Suéter térmico"
		}
	}

	when (condition) {
		"Lluvioso" -> {
			list += "Impermeable o paraguas"
			list += "Calzado impermeable"
		}
		"Ventoso" -> list += "Rompevientos"
		"Nublado" -> list += "Capa extra por si refresca"
	}

	when {
		temperatureC >= 30 -> list += listOf("Tela transpirable", "Gorra/sombrero", "Bloqueador")
		temperatureC in 20..29 -> list += listOf("Mangas cortas", "Tenis ligeros")
		temperatureC in 10..19 -> list += listOf("Manga larga", "Capa extra")
		temperatureC in 0..9 -> list += listOf("Guantes", "Bufanda", "Gorro")
		temperatureC < 0 -> list += listOf("Termal", "Botas de invierno")
	}

	return list.distinct()
}