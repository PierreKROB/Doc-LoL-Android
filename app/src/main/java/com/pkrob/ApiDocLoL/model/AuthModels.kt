package com.pkrob.ApiDocLoL.model

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String, val userId: String)
data class RegisterRequest(val username: String, val password: String, val role: String)
data class RegisterResponse(val message: String)
