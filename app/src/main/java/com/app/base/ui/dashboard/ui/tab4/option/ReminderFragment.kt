package com.app.base.ui.dashboard.ui.tab4.option

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.base.R
import com.app.base.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_reminder.*


class ReminderFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reminder, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitle.text = getString(R.string.set_reminder)
        attachClickListeners()
    }

    private fun attachClickListeners() {
        ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}
