package com.usfzy.runtim_permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.usfzy.permissions.R

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG: String = "MainActivity"
        private const val PERMISSION_CODE_REQUEST_LOCATION: Int = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        val hasLocationPermission = getHasLocationPermission()
    }

    private fun getLastLocation() {
        Log.d(TAG, "getLastLocation: ")
    }

    private fun hasLocationPermission() =
        ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED



    private fun getHasLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                showPermissionRationale { requestLocationPermission() }
            } else {
                requestLocationPermission()
            }
            return false
        }
    }

    private fun showPermissionRationaleIfNeeded(positiveAction: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle("Location permission")
            .setMessage("This app will not work without knowing your current location")
            .setPositiveButton(
                "OK"
            ) { _, _ -> positiveAction() }
            .create()
            .show()
    }

    private fun showPermissionRationale(positiveAction: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle("Location Permission")
            .setMessage("We need you permission to find your current position")
            .setPositiveButton("OK") { _, _ -> positiveAction() }
            .create()
            .show()
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_CODE_REQUEST_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_CODE_REQUEST_LOCATION -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }
}