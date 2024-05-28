package com.lidm.facillify.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidm.facillify.data.repository.AuthRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepo: AuthRepository
) : ViewModel() {
    fun logOut() {
        viewModelScope.launch {
            authRepo.clearSession()
        }
    }
}