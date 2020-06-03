package com.app.base.base

import Preferences
import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.base.R
import com.app.base.data.Status
import com.app.base.data.login.Data
import com.app.base.utils.Constants
import com.app.base.utils.Utils
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.File

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    protected var mDoubleBackToExitPressedOnce = false

    private val PERMISSION_REQUEST = 121

    protected val TAG: String = javaClass.simpleName

    private var mProgressDialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Preferences.initPreferences(this)
    }

    fun getLoginInfo(): Data? {
        val gson = Gson()
        return gson.fromJson(Preferences.prefs!!.getString("user_detail", null), Data::class.java)
    }

    fun showSnackBar(message: String, content: View) {
        this.let {
            Snackbar.make(content, message, Snackbar.LENGTH_LONG).show()
        }
    }

    fun showToastMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // pass activity's  result to the fragments
        val fragment = supportFragmentManager.findFragmentById(R.id.container_main)
        fragment?.onActivityResult(requestCode, resultCode, data)

    }

    override fun onPause() {
        super.onPause()
        Utils.hideKeyboard(this)
    }

    override fun onStart() {
        super.onStart()
        Utils.hideKeyboard(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val fragment = supportFragmentManager.findFragmentById(R.id.container_main)
        fragment?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun replaceFragment(fragment: androidx.fragment.app.Fragment, container: Int) {
        val tag: String = fragment::class.java.simpleName
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_in,
                R.anim.fade_out)
        transaction.replace(container, fragment, tag)
                .commitAllowingStateLoss()
    }


    /**
     * Add fragment with or without addToBackStack
     *
     * @param fragment       which needs to be attached
     * @param addToBackStack is fragment needed to backstack
     */
    fun addFragment(fragment: androidx.fragment.app.Fragment, addToBackStack: Boolean, container_id: Int) {
        val tag = fragment.javaClass.simpleName
        val fragmentManager = this.supportFragmentManager
        val fragmentOldObject = fragmentManager.findFragmentByTag(tag)
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.anim_in, R.anim.anim_out, R.anim.anim_in_reverse, R.anim.anim_out_reverse)
        if (fragmentOldObject != null) {
            fragmentManager.popBackStackImmediate(tag, 0)
        } else {
            if (addToBackStack) {
                transaction.addToBackStack(tag)
            }
            transaction.add(container_id, fragment, tag)
                    .commit()
        }
    }

    /**
     **************** handle API response code and manage action accordingly *******
     */

    fun handleResponseCode(response: Status) {
        when (response.status) {
            Constants.httpcodes.STATUS_OK -> Log.e("status", "result found")
            Constants.httpcodes.STATUS_BAD_REQUEST -> Log.e("status", "api error")
            Constants.httpcodes.STATUS_SERVER_ERROR -> Log.e("status", "server error")
            Constants.httpcodes.STATUS_SESSION_EXPIRED -> {
                Handler().postDelayed(Runnable { Utils.logout(this) }, 3000)

            }
            Constants.httpcodes.STATUS_PLAN_EXPIRED -> Log.e("status", "Plan expired")
            Constants.httpcodes.STATUS_VALIDATION_ERROR -> Log.e("status", "VALIDATION ERROR")
            Constants.httpcodes.STATUS_UNKNOWN_ERROR -> Log.e("status", "UNKNOW ERROR")

        }
    }


    fun showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = Dialog(this, android.R.style.Theme_Translucent)
            mProgressDialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            mProgressDialog?.setContentView(R.layout.loader_half__layout)
            mProgressDialog?.setCancelable(false)

        }
        mProgressDialog!!.show()
    }

    private fun hideProgress() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }

    fun showLoading(show: Boolean?) {
        if (show!!) showProgress() else hideProgress()
    }

    /**
     ***************** when permission is diabled then show user a dialog
     * force to enable permission from setting
     */

    fun permissionDenied() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)

        builder.setMessage(getString(R.string.permission_denied))

        val positiveText = getString(android.R.string.ok)
        builder.setPositiveButton(positiveText
        ) { dialog, which ->
            enablePermission()
        }

        val negativeText = getString(android.R.string.cancel)
        builder.setNegativeButton(negativeText
        ) { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        // display dialog
        dialog.show()
    }

    /**
     ************** start app detail activity to enable disabled permissions ***********
     */

    fun enablePermission() {
        val packageName = packageName

        try {
            //Open the specific App Info page:
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            startActivityForResult(intent, PERMISSION_REQUEST)

        } catch (e: ActivityNotFoundException) {
            //e.printStackTrace();
            //Open the generic Apps page:
            val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
            startActivityForResult(intent, PERMISSION_REQUEST)

        }

    }

    /**
     ************ clear images stored in folder bestyme of map snaps *******
     */
    fun clearCacheImages() {
        if (checkForStoragePermission()) {
            val directoryName = Environment.getExternalStorageDirectory().toString() + Constants.APP_HIDDEN_FOLDER
            val mapSnapDirectory = File(directoryName)
            val list = mapSnapDirectory.listFiles()

            if (list != null) {
                for (tempFile in list) {
                    tempFile.delete()
                }
            }
        }

    }


    fun checkForStoragePermission(): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                return false
            }
        }
        return true
    }

    //use later
    fun requestStoragePermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE), Constants.PERMISSION_REQUEST_CODE)
                return false
            }
        }
        return true
    }


    /**
     * This method will check for runtime permission
     */


    /**
     * This method will request permission
     */
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION), Constants.LOCATION_PERMISSION_REQUEST_CODE)

    }


}