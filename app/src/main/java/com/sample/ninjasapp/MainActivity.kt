@file:OptIn(ExperimentalMaterial3Api::class)

package com.sample.ninjasapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.sample.ninjasapp.model.Animal
import com.sample.ninjasapp.viewmodel.AnimalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimalApp()
        }
    }
}
@Composable
fun AnimalApp() {
    val viewModel: AnimalViewModel = hiltViewModel()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(modifier = Modifier.padding(16.dp)) {
        SearchBar(viewModel = viewModel)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Dogs", style = MaterialTheme.typography.headlineSmall)
        if (isLandscape) {
            HorizontalAnimalList(animals = viewModel.filterAnimals(viewModel.dogList.value), animalType = "dog")
        } else {
            VerticalAnimalList(animals = viewModel.filterAnimals(viewModel.dogList.value), animalType = "dog")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Birds", style = MaterialTheme.typography.headlineSmall)
        if (isLandscape) {
            HorizontalAnimalList(animals = viewModel.filterAnimals(viewModel.birdList.value), animalType = "bird")
        } else {
            VerticalAnimalList(animals = viewModel.filterAnimals(viewModel.birdList.value), animalType = "bird")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Bugs", style = MaterialTheme.typography.headlineSmall)
        if (isLandscape) {
            HorizontalAnimalList(animals = viewModel.filterAnimals(viewModel.bugList.value), animalType = "bug")
        } else {
            VerticalAnimalList(animals = viewModel.filterAnimals(viewModel.bugList.value), animalType = "bug")
        }
    }
}

@Composable
fun HorizontalAnimalList(animals: List<Animal>, animalType: String) {
    LazyRow {
        items(animals) { animal ->
            AnimalItem(animal = animal, animalType = animalType)
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}


@Composable
fun VerticalAnimalList(animals: List<Animal>, animalType: String) {
    LazyColumn {
        items(animals) { animal ->
            AnimalItem(animal = animal, animalType = animalType)
            Divider()
        }
    }
}

@Composable
fun AnimalItem(animal: Animal, animalType: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Name: ${animal.name}", fontWeight = FontWeight.Bold)
            Text(text = "Phylum: ${animal.taxonomy.phylum}")
            Text(text = "Scientific Name: ${animal.taxonomy.scientific_name}")

            when (animalType) {
                "dog" -> {
                    animal.characteristics.slogan?.let { Text(text = "Slogan: $it") }
                    animal.characteristics.lifespan?.let { Text(text = "Lifespan: $it") }
                }
                "bird" -> {
                    animal.characteristics.wingspan?.let { Text(text = "Wingspan: $it") }
                    animal.characteristics.habitat?.let { Text(text = "Habitat: $it") }
                }
                "bug" -> {
                    animal.characteristics.prey?.let { Text(text = "Prey: $it") }
                    animal.characteristics.predators?.let { Text(text = "Predators: $it") }
                }
            }
        }
    }
}


@Composable
fun SearchBar(viewModel: AnimalViewModel) {
    TextField(
        value = viewModel.searchTerm.value,
        onValueChange = { viewModel.onSearchTermChange(it) },
        label = { Text("Search by name or common name") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
          //  backgroundColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnimalApp()
}