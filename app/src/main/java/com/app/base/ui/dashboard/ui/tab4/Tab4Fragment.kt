package com.app.base.ui.dashboard.ui.tab4

import Preferences
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.base.R
import com.app.base.base.BaseFragment
import com.app.base.ui.dashboard.ui.tab4.option.AboutUsFragment
import com.app.base.ui.dashboard.ui.tab4.option.ReminderFragment
import com.app.base.ui.dashboard.ui.tab4.option.TermAndConditionFragment
import com.app.base.utils.clearValue
import com.varunjohn1990.iosdialogs4android.IOSDialog
import kotlinx.android.synthetic.main.fragment_tab4.*

class Tab4Fragment : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachClickListeners()
    }

    private fun attachClickListeners() {
        tvAboutUs.setOnClickListener(this)
        tvTermCondi.setOnClickListener(this)
        tvSettingAlarm.setOnClickListener(this)
        tvContactUs.setOnClickListener(this)
        tvLogout.setOnClickListener(this)
        ivFacebook.setOnClickListener(this)
        ivInstagram.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tvAboutUs -> {
                addFragment(AboutUsFragment(), true, R.id.container)
            }
            R.id.tvTermCondi -> {
                addFragment(TermAndConditionFragment(), true, R.id.container)
            }
            R.id.tvSettingAlarm -> {
                addFragment(ReminderFragment(), true, R.id.container)
            }
            R.id.tvContactUs -> {
                openSocialLink(getString(R.string.fb_link))
            }
            R.id.tvLogout -> {
                logout()
            }

            R.id.ivInstagram -> {
                openSocialLink(getString(R.string.insta_link))
            }

            R.id.ivFacebook -> {
                openSocialLink(getString(R.string.fb_link))
            }
        }
    }

    fun openSocialLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }

    private fun logout() {
        IOSDialog.Builder(requireContext()).title(getString(R.string.title_logout))
                .message(getString(R.string.desc_logout))
                .positiveButtonText("Yes")
                .negativeButtonText("No")
                .positiveClickListener {
                    Preferences.prefs?.clearValue("user_detail")
                    it.dismiss()
                    requireActivity().finish()
                }
                .negativeClickListener {
                    it.dismiss()
                }
                .build()
                .show()

    }
}
