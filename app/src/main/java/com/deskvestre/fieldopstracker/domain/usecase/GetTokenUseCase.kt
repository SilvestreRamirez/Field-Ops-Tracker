package com.deskvestre.fieldopstracker.domain.usecase

import com.deskvestre.fieldopstracker.domain.repository.TokenRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repository: TokenRepository
) {
    suspend operator fun invoke(): String? {
        return repository.getToken()
    }
}