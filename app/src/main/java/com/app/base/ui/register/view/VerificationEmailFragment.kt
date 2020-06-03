package com.app.base.ui.register.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.base.R
import com.app.base.base.BaseFragment
import com.app.base.data.register.EmailVerifyResponse
import com.app.base.ui.register.RegisterViewModel
import com.app.base.utils.Validations
import com.app.base.utils.security.ApiFailureTypes
import kotlinx.android.synthetic.main.fragment_verification_email.*


class VerificationEmailFragment : BaseFragment() {
    private lateinit var mViewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_verification_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        attachObservers()
        attachClickListeners()
    }


    private fun attachObservers() {
        mViewModel.emailVerifyResponse.observe(viewLifecycleOwner, Observer { it ->
            it?.let {
                showSnackBar(it.message!!)
                moveToNextScreen(it)
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

    private fun moveToNextScreen(data: EmailVerifyResponse) {
        val fragment = VerificationCodeFragment()
        fragment.arguments = Bundle().apply {
            putString("otp", data.otp)
            putString("email", etEmailVeri.text.toString().trim())
        }
        addFragment(fragment, true, R.id.container_main)
    }

    private fun attachClickListeners() {
        ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        btnGoToLogin.setOnClickListener {
            requireActivity().onBackPressed()
        }

        btnSendCode.setOnClickListener {
            if (Validations.isValidEmail(etEmailVeri)) {
                mViewModel.sendEmailVerificationCode(makeEmailRequestAPI())
            }

        }
    }

    private fun makeEmailRequestAPI(): HashMap<String, String> {
        return HashMap<String, String>().apply {
            put("email", etEmailVeri.text.toString().trim())
        }
    }

}
