package com.axelfernandez.alarmacomunitaria.ui.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.alarmacomunitaria.api.RetrofitFactory
import com.axelfernandez.alarmacomunitaria.models.User
import com.axelfernandez.alarmacomunitaria.models.UserResponse
import com.axelfernandez.alarmacomunitaria.repository.LoginRepository
import com.axelfernandez.deliverylavalle.api.Api
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val loginRepository = LoginRepository(RetrofitFactory.buildService(Api::class.java))


    fun registryUser(user: User){
        loginRepository.registryUser(user)
    }
    fun returnResponse(): LiveData<UserResponse> {
        return loginRepository.returnData()
    }


    fun createUser(account: GoogleSignInAccount): User {

        return User(email = account.email!!,
            givenName = account.givenName!!,
            familyName = account.familyName!!,
            photo = account.photoUrl.toString().split('=').get(0),
            username = null,
            phone = null,
            address = null,
            id = null
        )
    }
}