package com.pkrob.ApiDocLoL.util

import com.auth0.android.jwt.JWT

object JwtUtils {
    fun getUserIdFromToken(token: String): String? {
        val jwt = JWT(token)
        return jwt.getClaim("userId").asString()
    }
}
