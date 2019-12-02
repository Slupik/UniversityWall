package io.github.slupik.universitywall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import io.github.slupik.universitywall.google.GoogleMainActivity


class MainActivity  : AppCompatActivity() {

    override fun onResume() {
        super.onResume()

        val intent = Intent(this, GoogleMainActivity::class.java)
        startActivity(
            intent,
            null
        )
    }

//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val myImageView = findViewById<ImageView>(R.id.imgview)
//        val myBitmap = BitmapFactory.decodeResource(
//            applicationContext.resources,
//            R.drawable.puppy
//        )
//        myImageView.setImageBitmap(myBitmap)
//        myImageView.setOnClickListener {
//            Log.d("QR_DEBUG", "myImageView TEST method")
//        }
//
//        val btn = findViewById(R.id.button_process) as Button
//        Log.d("QR_DEBUG", "before setOnClickListener")
//        btn.setOnClickListener{
//            Log.d("QR_DEBUG", "setOnClickListener")
//            val txtView = findViewById(R.id.txtContent) as TextView
//
//            Log.d("QR_DEBUG", "onClick")
//
//            val detector = BarcodeDetector.Builder(applicationContext)
//                .setBarcodeFormats(Barcode.DATA_MATRIX or Barcode.QR_CODE)
//                .build()
//            if (!detector.isOperational) {
//                Log.d("QR_DEBUG", "isOperational")
//                txtView.setText("Could not set up the detector!")
//            } else {
//                Log.d("QR_DEBUG", "is NOT operational")
//                val frame = Frame.Builder().setBitmap(myBitmap).build()
//                val barcodes = detector.detect(frame)
//
//                val thisCode = barcodes.valueAt(0)
//                txtView.setText(thisCode.rawValue)
//            }
//        }
//
//    }
//
//    fun startProcess(view: View) {
//        Log.d("QR_DEBUG", "TEST method")
//    }
//
//    override fun onResume() {
//        super.onResume()
//
////        ActivityCompat.requestPermissions(
////            this,
////            arrayOf(
////                Manifest.permission.CAMERA
////            ),
////            101
////        )
//    }

}