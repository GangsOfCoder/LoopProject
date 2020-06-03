package com.app.base.ui.dashboard.ui.tab4.option

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.base.R
import com.app.base.base.BaseFragment
import kotlinx.android.synthetic.main.toolbar_back.*


class TermAndConditionFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_term_and_condition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvToolbaarTitle.text = getString(R.string.tc)
        attachClickListeners()
    }

    private fun attachClickListeners() {
        ivToolbarBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}
