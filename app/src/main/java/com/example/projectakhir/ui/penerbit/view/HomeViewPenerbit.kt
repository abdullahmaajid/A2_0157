package com.example.projectakhir.ui.penerbit.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projectakhir.R
import com.example.projectakhir.data.model.Penerbit
import com.example.projectakhir.ui.PenyediaViewModel
import com.example.projectakhir.ui.penerbit.viewmodel.HomeViewModelPenerbit
import com.example.projectakhir.ui.penerbit.viewmodel.PenerbitUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeViewPenerbit(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    navController: NavController,
    onBackClick: () -> Unit = {},
    viewModel: HomeViewModelPenerbit = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PenerbitTopBar(navController = navController, viewModel = viewModel, scrollBehavior = scrollBehavior)
        },
        floatingActionButton = {
            PenerbitFab(onClick = navigateToItemEntry)
        },
    ) { innerPadding ->
        PenerbitStatus(
            penerbitUiState = viewModel.penerbitUiState,
            retryAction = { viewModel.getPenerbit() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePenerbit(it.idPenerbit)
                viewModel.getPenerbit()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenerbitTopBar(
    navController: NavController,
    viewModel: HomeViewModelPenerbit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        modifier = Modifier, // Hapus Modifier.background di sini
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.white) // Atur warna latar belakang di sini
        ),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Gunakan fillMaxWidth agar title memenuhi lebar TopAppBar
                    .clip(MaterialTheme.shapes.medium),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Daftar Penerbit",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black // Atur warna ikon back agar sesuai dengan tema
                )
            }
        },
        actions = {
            IconButton(onClick = { viewModel.getPenerbit() }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = Color.Black // Atur warna ikon refresh agar sesuai dengan tema
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun PenerbitFab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(16.dp),
        containerColor = colorResource(id = R.color.white) // Warna latar belakang FAB
    ) {
        Icon(
            imageVector = Icons.Default.Add, // Ikon tambah
            contentDescription = "Tambah Penerbit",
            tint = Color.Black // Warna ikon hitam
        )
    }
}

@Composable
fun PenerbitStatus(
    penerbitUiState: PenerbitUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Penerbit) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (penerbitUiState) {
        is PenerbitUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PenerbitUiState.Success -> {
            if (penerbitUiState.penerbit.isEmpty()) {
                EmptyPenerbitView(modifier = modifier)
            } else {
                PenerbitLayout(
                    penerbit = penerbitUiState.penerbit,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idPenerbit) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is PenerbitUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun EmptyPenerbitView(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Tidak ada data penerbit", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun PenerbitLayout(
    penerbit: List<Penerbit>,
    modifier: Modifier = Modifier,
    onDetailClick: (Penerbit) -> Unit,
    onDeleteClick: (Penerbit) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.background(color = colorResource(id = R.color.white)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(penerbit) { penerbit ->
            PenerbitCard(
                penerbit = penerbit,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(penerbit) },
                onDeleteClick = { onDeleteClick(penerbit) }
            )
        }
    }
}




@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.no_wifi),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.no_wifi), contentDescription = "")
            Text(
                text = stringResource(R.string.loading_failed),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            Button(onClick = retryAction, modifier = Modifier.padding(top = 16.dp)) {
                Text(stringResource(R.string.retry))
            }
        }
    }
}

@Composable
fun PenerbitCard(
    penerbit: Penerbit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Penerbit) -> Unit = {},
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.black))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ganti ikon buku dengan ikon penerbit
                Icon(
                    painter = painterResource(id = R.drawable.penerbitputih), // Ganti dengan drawable penerbit yang sesuai
                    contentDescription = "Penerbit",
                    tint = Color.White
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = penerbit.namaPenerbit,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(penerbit) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Penerbit",
                        tint = Color.White
                    )
                }
            }
            Divider()
            Column {
                Text(
                    text = "ID Penerbit: ${penerbit.idPenerbit}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Alamat: ${penerbit.alamatPenerbit}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Telepon: ${penerbit.teleponPenerbit}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}
