package com.app.base.ui.register.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.base.R
import com.app.base.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_verification_code.*

class VerificationCodeFragment : BaseFragment() {

    private lateinit var mOtp: String
    private lateinit var mEmail: String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_verification_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachClickListeners()
        getDataFromBundle()
    }

    private fun getDataFromBundle() {
        mOtp = requireArguments().getString("otp")!!
        mEmail = requireArguments().getString("email")!!

        //update ui
        textView3.text = getString(R.string.verfi_code_sent_to, mEmail)
    }

    private fun attachClickListeners() {
        btnSubmitCode.setOnClickListener {
            if (etCode.text.toString().isNotEmpty()) {
                if (etCode.text.toString() == mOtp) {
                    moveToNextScreen()
                } else {
                    showMessage(getString(R.string.invalid_otp))
                }
            } else {
                showMessage(getString(R.string.plz_enter_codee))
            }
        }

        ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun moveToNextScreen() {
        val fragment = RegisterFragment()
        fragment.arguments = Bundle().apply {
            putString("email", mEmail)
        }
        addFragment(fragment, true, R.id.container_main)
    }


}
