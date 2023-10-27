package com.example.formdata

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CobaViewModel :ViewModel() {
    var namaUsr: String by mutableStateOf("")
        private set
    var noTlp: String by mutableStateOf("")
        private set
    var alamatRmh: String by mutableStateOf("")
        private set
    var jenisKl: String by mutableStateOf("")
        private set
    var emailLgkp: String by mutableStateOf("")
        private set
    var statusBM: String by mutableStateOf("")
        private set
    private val _uiState = MutableStateFlow(DataForm())
    val uiState: StateFlow<DataForm> = _uiState.asStateFlow()

    fun insertData(nm: String, tlp:String, almt:String, jk:String, st:String, email: String) {
        namaUsr = nm;
        noTlp = tlp;
        alamatRmh = almt;
        jenisKl = jk;
        emailLgkp = email;
        statusBM = st;
    }

    fun setJenisK(pilihJK: String) {
        _uiState.update { currentState -> currentState.copy(sex = pilihJK) }
    }

    fun setStatusM(pilihBM: String) {
        _uiState.update { currentState -> currentState.copy(stat = pilihBM) }
    }
}