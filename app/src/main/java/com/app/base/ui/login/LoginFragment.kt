package com.app.base.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.base.R
import com.app.base.base.BaseFragment
import com.app.base.ui.dashboard.DashboardActivity
import com.app.base.ui.forget.ForgetPasswordFragment
import com.app.base.ui.register.view.VerificationEmailFragment
import com.app.base.utils.Utils
import com.app.base.utils.security.ApiFailureTypes
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment() {

    private lateinit var mViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        attachClickListeners()
        attachObservers()
    }

    private fun attachObservers() {
        mViewModel.response.observe(viewLifecycleOwner, Observer {
            it?.let {
                showMessage(it.message!!)
                if (it.status == 1) {
                    //login successfully
                    saveLoginInfo(it.data)
                    val intent = Intent(requireContext(), DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        })

        mViewModel.apiError.observe(viewLifecycleOwner, Observer {
            it?.let {
                showSnackBar(it)
            }
        })

        mViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            it?.let { showLoading(it) }
        })

        mViewModel.onFailure.observe(viewLifecycleOwner, Observer {
            it?.let {
                showSnackBar(ApiFailureTypes().getFailureMessage(it))
            }
        })
    }

    private fun checkValidation(): Boolean {
        if (!Utils.isEmailValid(etEmail.text.toString())) {
            showMessage("Please enter valid email address")
            return false
        }

        if (etPassword.text.toString().isEmpty()) {
            showMessage("Please enter password")
            return false
        }

        return true
    }

    private fun attachClickListeners() {
        btnLogin.setOnClickListener {
            if (checkValidation()) {
                mViewModel.doLogin(makeLoginRequestAPI())
            }
        }

        btnRegister.setOnClickListener {
            addFragment(VerificationEmailFragment(), true, R.id.container_main)
        }

        tvForgotPass.setOnClickListener {
            addFragment(ForgetPasswordFragment(), true, R.id.container_main)
        }
    }

    private fun makeLoginRequestAPI(): HashMap<String, String> {
        return HashMap<String, String>().apply {
            put("email", etEmail.text.toString())
            put("password", etPassword.text.toString())
        }
    }

}
