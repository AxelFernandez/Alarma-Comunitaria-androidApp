package com.axelfernandez.alarmacomunitaria.ui.Login

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.axelfernandez.alarmacomunitaria.HomeActivity
import com.axelfernandez.alarmacomunitaria.R
import com.axelfernandez.alarmacomunitaria.utils.ViewUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.login_fragment.view.*
import java.util.concurrent.atomic.AtomicBoolean

class Login : Fragment() {
    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    companion object {
        fun newInstance() = Login()
    }

    private lateinit var viewModel: LoginViewModel
    var atomicBoolean = AtomicBoolean(false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val v = view?:return
        val isCompletedRegistered = ViewUtil.getIsCompletedRegistered(requireContext())
        if (isCompletedRegistered){
            launchMainActivity()
        }
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestProfile()
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity?.application!!, mGoogleSignInOptions)

        v.google_button_sigin.setOnClickListener{
            v.google_button_sigin.isEnabled = false
            signIn()
        }
    }
    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        view?.login_progress_bar?.visibility = View.VISIBLE

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val view = view?:return
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)?:return
                val user = viewModel.createUser(account)
                ViewUtil.putUserToSharedPreferences(requireContext(),user)
                viewModel.registryUser(user)
                viewModel.returnResponse().observe(viewLifecycleOwner, Observer {
                    user.id = it.clientId
                    user.username = it.username

                    ViewUtil.putUserToSharedPreferences(requireContext(),user)
                    view.login_progress_bar.visibility = View.GONE
                    if (it.isCompletedRegistered){
                        user.address = it.address
                        user.phone = it.phone
                        ViewUtil.putUserToSharedPreferences(requireContext(),user)
                        launchMainActivity()
                    }else {
                        if (atomicBoolean.compareAndSet(false, true)){
                            findNavController().navigate(LoginDirections.actionLoginToInformationFragment())
                        }
                    }

                })
            }catch (e:ApiException){
                Log.e("Error", e.message)

            }

        }
    }
    fun launchMainActivity(){
        val intent = Intent(context, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        activity?.finish()
    }
}