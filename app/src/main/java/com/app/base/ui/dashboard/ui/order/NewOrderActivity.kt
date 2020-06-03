package com.app.base.ui.dashboard.ui.order

import Preferences
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import com.app.base.R
import com.app.base.base.BaseActivity
import com.app.base.callBack.SpinnerWindowInterface
import com.app.base.data.order.OrderModel
import com.app.base.data.streets.StreetResponse
import com.app.base.ui.dashboard.ui.home.SetReminderActivity
import com.app.base.utils.SpinnerWindow
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_new_order.*

class NewOrderActivity : BaseActivity(), SpinnerWindowInterface {

    private lateinit var streetList: StreetResponse
    private lateinit var selectedStreetId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_order)
        attachClickListeners()
        streetList = Gson().fromJson(Preferences.prefs!!.getString("streets", null), StreetResponse::class.java)
        etEmailAddress.setText(getLoginInfo()!!.email.toString())
    }

    private fun attachClickListeners() {
        btnCancel.setOnClickListener {
            onBackPressed()
        }

        tvSetReminder.setOnClickListener {
            startActivity(Intent(this, SetReminderActivity::class.java))
        }

        tvEstate.setOnClickListener {
            val list = ArrayList<String>()
            streetList.data!!.forEachIndexed { index, streetItem ->
                list.add(streetItem!!.name!!)
            }
            SpinnerWindow(this)
            SpinnerWindow.showSpinner(this, list)
        }

        btnSave.setOnClickListener {
            if (checkValidation()) {
                val orderData = OrderModel(
                        user_id = getLoginInfo()!!.customerId!!,
                        user_name = etName.text.toString(),
                        phone_number = etNumber.text.toString(),
                        user_email = etEmailAddress.text.toString(),
                        street_number = selectedStreetId,
                        building_name = etBuildingName.text.toString(),
                        floor_number = etFloor.text.toString(),
                        flat_number = etFlat.text.toString()
                )
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("data", orderData)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
        }
    }

    private fun checkValidation(): Boolean {
        if (etName.text.isEmpty()) {
            showToastMsg(getString(R.string.plz_enter_name))
            return false
        }
        if (etNumber.text.isEmpty()) {
            showToastMsg(getString(R.string.plz_enter_number))
            return false
        }
        if (tvEstate.text.isEmpty()) {
            showToastMsg(getString(R.string.plz_choose_estate))
            return false
        }
        if (etBuildingName.text.isEmpty()) {
            showToastMsg(getString(R.string.plz_bld_name))
            return false
        }
        if (etFloor.text.isEmpty()) {
            showToastMsg(getString(R.string.plz_floor))
            return false
        }
        if (etFlat.text.isEmpty()) {
            showToastMsg(getString(R.string.plz_flat))
            return false
        }

        if (!checkbox.isChecked) {
            showToastMsg(getString(R.string.plz_accept_tm))
            return false
        }



        return true
    }

    override fun selectedPosition(pos: Int) {
        tvEstate.text = streetList.data!!.get(pos)!!.name
        selectedStreetId = streetList.data!!.get(pos)!!.id.toString()
    }


}
