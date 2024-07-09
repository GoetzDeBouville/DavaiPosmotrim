package com.davay.android.feature.registration.domain

class UserDataInteractorImpl(
    private val repository: UserDataRepository
) : UserDataInteractor {
    override fun getUserId(): String {
        return repository.getUserId()
    }

    override fun getUserName(): String {
        return repository.getUserName()
    }

    override fun setUserId(userId: String) {
        repository.setUserId(userId)
    }

    override fun setUserName(userName: String) {
        repository.setUserName(userName)
    }

}