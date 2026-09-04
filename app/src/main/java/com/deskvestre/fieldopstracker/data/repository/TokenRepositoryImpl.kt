package com.deskvestre.fieldopstracker.data.repository

import com.deskvestre.fieldopstracker.data.local.SecureTokenManager
import com.deskvestre.fieldopstracker.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenManager: SecureTokenManager
) : TokenRepository {
    override suspend fun getToken(): String? = tokenManager.getToken()

    override suspend fun saveToken(token: String) {
        tokenManager.saveToken(token)
    }
}