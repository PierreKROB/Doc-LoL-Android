package com.pkrob.ApiDocLoL.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkrob.ApiDocLoL.model.User
import com.pkrob.ApiDocLoL.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData

    fun fetchUserData(userId: String) {
        viewModelScope.launch {
            try {
                Log.d("UserViewModel", "Fetching user data for userId: $userId")
                val response = RetrofitInstance.api.getUserData(userId)
                if (response.isSuccessful) {
                    _userData.value = response.body()
                    Log.d("UserViewModel", "User data fetched successfully: ${response.body()}")
                } else {
                    Log.e("UserViewModel", "Error fetching user data: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Exception fetching user data", e)
            }
        }
    }

    fun saveUserData(user: User) {
        viewModelScope.launch {
            try {
                Log.d("UserViewModel", "Saving user data for userId: ${user.userId}")
                val response = RetrofitInstance.api.saveUserData(user.userId, user)
                if (response.isSuccessful) {
                    Log.d("UserViewModel", "User data saved successfully")
                } else {
                    Log.e("UserViewModel", "Error saving user data: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Exception saving user data", e)
            }
        }
    }
}
