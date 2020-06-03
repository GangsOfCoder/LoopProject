package com.app.base.ui.dashboard.ui.home

import Preferences
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.base.R
import com.app.base.base.BaseFragment
import com.app.base.ui.dashboard.ui.order.NewOrderActivity
import com.app.base.utils.saveValue
import com.app.base.utils.security.ApiFailureTypes
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), View.OnClickListener {
    private lateinit var mViewModel: HomeViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        attachClickListeners()
        attachObservers()
        mViewModel.getStreets()
    }

    private fun attachObservers() {
        mViewModel.response.observe(viewLifecycleOwner, Observer {
            it?.let {
                //showMessage(it.message!!)
                if (it.status == 1) {
                    val gson = Gson()
                    Preferences.prefs!!.saveValue("streets", gson.toJson(it))
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
        tvNewOrder.setOnClickListener(this)
        btnAdd.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tvNewOrder, R.id.btnAdd -> {
                startActivity(Intent(requireContext(), NewOrderActivity::class.java))
            }
        }
    }

}
