package com.app.base.ui.forget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.base.R
import com.app.base.base.BaseFragment
import com.app.base.ui.login.LoginFragment
import com.app.base.utils.security.ApiFailureTypes
import kotlinx.android.synthetic.main.fragment_forget_password2.*


class ForgetPassword2Fragment : BaseFragment() {
    private lateinit var mViewModel: ForgetViewModel
    private lateinit var mOTP:String
    private lateinit var mEmail:String
    private lateinit var mToken:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forget_password2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(ForgetViewModel::class.java)
        attachClickListeners()
        attachObservers()
        getDataFromBundle()
    }

    private fun getDataFromBundle() {
        mOTP = requireArguments().getString("otp")!!
        mEmail = requireArguments().getString("email")!!
        mToken = requireArguments().getString("token")!!

        textView3.text = getString(R.string.fp2, mEmail)
    }

    private fun attachObservers() {
        mViewModel.response.observe(viewLifecycleOwner, Observer {
            it?.let {
                showMessage(it.message!!)
                if (it.status == 1) {
                    replaceFragment(LoginFragment(), true, R.id.container_main)
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

    private fun attachClickListeners() {
        ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        btnConfirmForg.setOnClickListener {
            if(checkValidation()){
                mViewModel.newPasswordToken(makeForgetPasswordRequestAPI(),mToken)
            }
        }
    }

    private fun checkValidation(): Boolean {
        if(etCode.text.toString() != mOTP){
            showMessage("Code doesn't match")
            return false
        }
        if (etPass.text.toString().isEmpty()) {
            showMessage("Please enter password")
            return false
        }

        if (etPass.text.toString() != etConPass.text.toString()) {
            showMessage("Password doesn't match")
            return false
        }
        return true
    }


    private fun makeForgetPasswordRequestAPI():HashMap<String,String>{
        return HashMap<String,String>().apply {
            put("password",etPass.text.toString())
        }
    }
}
