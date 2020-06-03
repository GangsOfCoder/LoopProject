package com.app.base.ui.register.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.base.R
import com.app.base.base.BaseFragment
import com.app.base.ui.login.LoginFragment
import com.app.base.ui.register.RegisterViewModel
import com.app.base.utils.security.ApiFailureTypes
import kotlinx.android.synthetic.main.register_fragment.*


class RegisterFragment : BaseFragment() {

    private lateinit var mViewModel: RegisterViewModel
    private lateinit var mEmail: String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        getDataFromBundle()
        attachObservers()
        attachClickListeners()
    }

    private fun getDataFromBundle() {
        mEmail = requireArguments().getString("email")!!
        etEmail.setText(mEmail)
        etEmail.isEnabled = false
    }

    private fun attachClickListeners() {
        ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        btnConfirm.setOnClickListener {
            if (checkValidation()) {
                //hit the api
                mViewModel.doRegister(makeRegisterRequestAPI())
            }
        }
    }

    private fun checkValidation(): Boolean {
        if (etName.text.isEmpty()) {
            showMessage("Please enter name")
            return false
        }
        if (etPassword.text.toString().isEmpty()) {
            showMessage("Please enter password")
            return false
        }

        if (etPassword.text.toString() != etConPassword.text.toString()) {
            showMessage("Password doesn't match")
            return false
        }
        return true
    }


    private fun attachObservers() {
        mViewModel.response.observe(viewLifecycleOwner, Observer { it ->
            it?.let {
                showMessage(it.message!!)
                replaceFragment(LoginFragment(), true, R.id.container_main)
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

    private fun makeRegisterRequestAPI(): HashMap<String, String> {
        return HashMap<String, String>().apply {
            put("email", mEmail)
            put("password", etPassword.text.toString().trim())
            put("name", etName.text.toString().trim())
            /*put("device_manufacture", etPassword.text.toString().trim())
            put("model_number", etPassword.text.toString().trim())
            put("country_name", etPassword.text.toString().trim())
            put("device_model", etPassword.text.toString().trim())
            put("os_name", etPassword.text.toString().trim())
            put("device_token", etPassword.text.toString().trim())
            put("device_token", etPassword.text.toString().trim())
            put("os_version", etPassword.text.toString().trim())*/
        }
    }

}
