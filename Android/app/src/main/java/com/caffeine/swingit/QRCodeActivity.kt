package com.caffeine.swingit

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import android.Manifest.permission.CAMERA
import android.app.Activity

class QRCodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private var scannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
        val currentApiVersion = Build.VERSION.SDK_INT

        if (currentApiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(applicationContext, "Permission already granted!", Toast.LENGTH_LONG).show()
            } else {
                requestPermission()
            }
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(applicationContext, CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(CAMERA), REQUEST_CAMERA)
    }

    public override fun onResume() {
        super.onResume()

        val currentapiVersion = android.os.Build.VERSION.SDK_INT
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (scannerView == null) {
                    scannerView = ZXingScannerView(this)
                    setContentView(scannerView)
                }
                scannerView!!.setResultHandler(this)
                scannerView!!.startCamera()
            } else {
                requestPermission()
            }
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        scannerView!!.stopCamera()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CAMERA -> if (grantResults.size > 0) {

                val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (cameraAccepted) {
                    Toast.makeText(applicationContext, "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(CAMERA)) {
                            showMessageOKCancel("You need to allow access to both the permissions",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(arrayOf(CAMERA),
                                                    REQUEST_CAMERA)
                                        }
                                    })
                            return
                        }
                    }
                }
            }
        }
    }


    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        android.support.v7.app.AlertDialog.Builder(this@QRCodeActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show()
    }


    override fun handleResult(result: Result) {
        val intent = Intent()
        println("result = ${result.text}")
        intent.putExtra("text", result.text)
        setResult(Activity.RESULT_OK, intent)
        finish()
//        val myResult = result.text
//        Log.d("QRCodeScanner", result.text)
//        Log.d("QRCodeScanner", result.barcodeFormat.toString())
//
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Scan Result")
//        builder.setPositiveButton("OK") { dialog, which -> scannerView!!.resumeCameraPreview(this@QRCodeActivity) }
//        builder.setNeutralButton("Visit") { dialog, which ->
//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(myResult))
//            startActivity(browserIntent)
//        }
//        builder.setMessage(result.text)
//        val alert1 = builder.create()
//        alert1.show()
    }

    companion object {
        private val REQUEST_CAMERA = 1
        private val camId = Camera.CameraInfo.CAMERA_FACING_BACK
    }
}
