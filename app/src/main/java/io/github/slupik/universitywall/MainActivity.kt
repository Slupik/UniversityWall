package io.github.slupik.universitywall

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector


class MainActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myImageView = findViewById<ImageView>(R.id.imgview)
        val myBitmap = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.puppy
        )
        myImageView.setImageBitmap(myBitmap)
        myImageView.setOnClickListener {
            Log.d("QR_DEBUG", "myImageView TEST method")
        }

        val btn = findViewById(R.id.button_process) as Button
        Log.d("QR_DEBUG", "before setOnClickListener")
        btn.setOnClickListener{
            Log.d("QR_DEBUG", "setOnClickListener")
            val txtView = findViewById(R.id.txtContent) as TextView

            Log.d("QR_DEBUG", "onClick")

            val detector = BarcodeDetector.Builder(applicationContext)
                .setBarcodeFormats(Barcode.DATA_MATRIX or Barcode.QR_CODE)
                .build()
            if (!detector.isOperational) {
                Log.d("QR_DEBUG", "isOperational")
                txtView.setText("Could not set up the detector!")
            } else {
                Log.d("QR_DEBUG", "is NOT operational")
                val frame = Frame.Builder().setBitmap(myBitmap).build()
                val barcodes = detector.detect(frame)

                val thisCode = barcodes.valueAt(0)
                txtView.setText(thisCode.rawValue)
            }
        }

    }

    fun startProcess(view: View) {
        Log.d("QR_DEBUG", "TEST method")
    }

    override fun onResume() {
        super.onResume()

//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(
//                Manifest.permission.CAMERA
//            ),
//            101
//        )
    }

}