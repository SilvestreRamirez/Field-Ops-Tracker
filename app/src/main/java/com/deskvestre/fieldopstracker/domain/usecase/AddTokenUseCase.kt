package com.deskvestre.fieldopstracker.domain.usecase

import com.deskvestre.fieldopstracker.domain.repository.TokenRepository
import javax.inject.Inject

class AddTokenUseCase @Inject constructor(
    private val repository: TokenRepository
) {
    suspend operator fun invoke(token: String) {
        repository.saveToken(token)
    }
}