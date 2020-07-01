package com.app.base.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.app.base.R
import com.app.base.base.BaseActivity
import com.app.base.ui.dashboard.DashboardActivity
import com.app.base.ui.login.LoginFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //addFragment(LoginFragment(), false, R.id.container_main)
        Log.d("ttttt", getLoginInfo().toString())
        if (getLoginInfo()==null) {
            addFragment(LoginFragment(), false, R.id.container_main)
        } else {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

}
