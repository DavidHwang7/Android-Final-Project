package davidhwang.railwayvillage

import android.accessibilityservice.AccessibilityService
import android.app.Instrumentation
import android.content.ContentResolver
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.KeyEvent
import android.view.View


class History : AppCompatActivity() {

    lateinit var bmp:Bitmap
    lateinit var historyImage:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        bmp= getResizedBitmapFromDrawable(R.drawable.history0,this)
        historyImage=findViewById(R.id.history_image)
        historyImage.setImageBitmap(bmp)

        val home: ImageButton = findViewById(R.id.back_history)
        home.setOnTouchListener(ButtonTouchDark())
        home.setOnClickListener(android.view.View.OnClickListener() { accessHome()})
    }

    override fun onStop(){
        super.onStop()
        bmp.recycle()
        historyImage.setImageDrawable(null)
        historyImage.setImageBitmap(null)
    }

    override fun onRestart(){
        super.onRestart()
        bmp= getResizedBitmapFromDrawable(R.drawable.history0,this)
        historyImage.setImageBitmap(bmp)
    }

    private fun accessHome(){
        finish()
    }

}



