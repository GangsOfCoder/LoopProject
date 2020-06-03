package com.app.base.ui.dashboard.ui.home

import android.os.Bundle
import com.app.base.R
import com.app.base.base.BaseActivity
import kotlinx.android.synthetic.main.activity_set_reminder.*

class SetReminderActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_reminder)
        attachClickListeners()
    }

    private fun attachClickListeners() {
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}
