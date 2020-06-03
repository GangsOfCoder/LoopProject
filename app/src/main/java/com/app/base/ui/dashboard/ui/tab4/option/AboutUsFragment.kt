package com.app.base.ui.dashboard.ui.tab4.option

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.base.R
import com.app.base.base.BaseFragment
import kotlinx.android.synthetic.main.toolbar_back.*


class AboutUsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvToolbaarTitle.text = getString(R.string.about_us)
        attachClickListeners()
    }

    private fun attachClickListeners() {
        ivToolbarBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}
