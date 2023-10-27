package com.example.formdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.formdata.DataSource.jenis
import com.example.formdata.ui.theme.FormDataTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormDataTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TampilLayout()
                }
            }
        }
    }
}

@Composable
fun TampilLayout(modifier: Modifier = Modifier) {
    Card (
        modifier = Modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "",
                    modifier = Modifier
                )
                Text(text = "Register")
            }
            Divider()
            Text(
                text = "Create Your Account",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            TampilForm()
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TampilForm(cobaViewModel: CobaViewModel = viewModel()) {

    var textNama by remember { mutableStateOf("") }
    var textTlp by remember { mutableStateOf("") }
    var textAlamat by remember { mutableStateOf("") }

    val context = LocalContext.current
    val dataForm: DataForm
    val uiState by cobaViewModel.uiState.collectAsState()
    dataForm = uiState

    OutlinedTextField(
        value = textNama,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = "Nama Lengkap")},
        onValueChange = {
            textNama = it
        }
    )

    OutlinedTextField(
        value = textTlp,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = "Nomor Telepon")},
        onValueChange = {
            textTlp = it
        }
    )

    OutlinedTextField(
        value = textAlamat,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = "Alamat Lengkap")},
        onValueChange = {
            textAlamat = it
        }
    )

    SelectJK(
        options = jenis.map {id -> context.resources.getString(id)},
        onSelectionChanged = {cobaViewModel.setJenisK(it)}
    )

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            cobaViewModel.insertData(textNama, textTlp, textAlamat, dataForm.sex)
        }
    ) {
        Text(
            text = stringResource(R.string.submit),
            fontSize = 16.sp
            )
    }
    Spacer(modifier = Modifier.height(100.dp))
    TextHasil(
        namanya = cobaViewModel.namaUsr,
        telponnya = cobaViewModel.noTlp,
        alamatnya = cobaViewModel.alamatRmh,
        jenisnya = cobaViewModel.jenisKl
    )
}

@Composable
fun TextHasil(namanya: String, telponnya: String, alamatnya: String, jenisnya: String) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Nama : " + namanya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(
            text = "Telepon : " + telponnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(
            text = "Alamat : " + alamatnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(
            text = "Jenis Kelamin : " + jenisnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}

@Composable
fun SelectJK(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {}
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        options.forEach { item ->
            Row(
                modifier = Modifier.selectable(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                )
                Text(item)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FormDataTheme {
        TampilLayout()
    }
}