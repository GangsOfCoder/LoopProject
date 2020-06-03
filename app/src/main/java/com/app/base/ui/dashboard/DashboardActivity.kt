package com.app.base.ui.dashboard

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.base.R
import com.app.base.base.BaseActivity
import com.app.base.ui.dashboard.ui.home.HomeFragment
import com.app.base.ui.dashboard.ui.tab2.Tab2Fragment
import com.app.base.ui.dashboard.ui.tab3.Tab3Fragment
import com.app.base.ui.dashboard.ui.tab4.Tab4Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class DashboardActivity : BaseActivity() {

    var bottomNavigation: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        bottomNavigation = findViewById(R.id.bottomNavigationView)
        bottomNavigation!!.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        openFragment(HomeFragment())
    }

    //Called to handle bottom navigation option item click
    private var navigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.tab1 -> {
                openFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab2 -> {
                openFragment(Tab2Fragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab3 -> {
                openFragment(Tab3Fragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.tab4 -> {
                openFragment(Tab4Fragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment!!)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
