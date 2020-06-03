package com.app.base.ui.forget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.base.R
import com.app.base.base.BaseFragment
import com.app.base.data.forgot.ForgetResponse
import com.app.base.utils.Utils
import com.app.base.utils.security.ApiFailureTypes
import kotlinx.android.synthetic.main.fragment_forget_password.*


class ForgetPasswordFragment : BaseFragment() {
    private lateinit var mViewModel: ForgetViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forget_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(ForgetViewModel::class.java)
        attachClickListeners()
        attachObservers()
    }

    private fun attachObservers() {

        mViewModel.response.observe(viewLifecycleOwner, Observer {
            it?.let {
                showMessage(it.message!!)
                if (it.status == 1) {
                    //login successfully
                    moveToNextScreen(it)
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

    private fun moveToNextScreen(it: ForgetResponse) {
        val fragment = ForgetPassword2Fragment()
        fragment.arguments = Bundle().apply {
            putString("otp", it.otp)
            putString("email", etEmail.text.toString())
            putString("token", it.token)
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

        btnSubmit.setOnClickListener {
            if (Utils.isEmailValid(etEmail.text.toString())) {
                mViewModel.sendCodeOnMail(makeForgetRequestAPI())
            } else {
                showMessage("Please enter valid email address")
            }
        }
    }

    private fun makeForgetRequestAPI(): HashMap<String, String> {
        return HashMap<String, String>().apply {
            put("email", etEmail.text.toString())
        }
    }

}
