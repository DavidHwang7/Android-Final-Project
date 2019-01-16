package davidhwang.raiwayvillage

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.widget.ImageButton
import android.support.v7.app.AlertDialog
import android.widget.Toast

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.hide() //#303F9FDark  //#3F51B5Primary
        super.onCreate(savedInstanceState)
        val mWidth = resources.displayMetrics.widthPixels //手機的寬度(像素)
        val mHeight = resources.displayMetrics.heightPixels //手機的高度(像素)
        if(mWidth<=320&&mHeight<=480)
            setContentView(R.layout.activity_home_qvga)
        else
            setContentView(R.layout.activity_home_fhd)

        val intro_button: ImageButton = findViewById(R.id.intro_button)
        val frame_button: ImageButton = findViewById(R.id.camera_button)
        val info_button: ImageButton = findViewById(R.id.info_button)
        val history_button: ImageButton = findViewById(R.id.history_button)

        frame_button.setOnTouchListener(ButtonTouchLight())
        intro_button.setOnTouchListener(ButtonTouchDark())
        info_button.setOnTouchListener(ButtonTouchDark())
        history_button.setOnTouchListener(ButtonTouchLight())

        info_button.setOnClickListener { accessInfo() }
        history_button.setOnClickListener { accessHistory() }
        intro_button.setOnClickListener { accessEnv() }
        frame_button.setOnClickListener{accessFrame()}

    }

    private fun accessHistory(){
        val intent = Intent()
        intent.setClass(this,
                History::class.java)
        startActivity(intent)
    }

    private fun accessInfo(){
        val intent = Intent()
        intent.setClass(this,
                Info::class.java)
        startActivity(intent)
    }

    private fun accessEnv(){
        val intent = Intent()
        intent.setClass(this,
                Environment::class.java)
        startActivity(intent)
    }

    private fun accessFrame() {

        val intent = Intent()
        intent.setClass(this,
                Frame::class.java)
        startActivity(intent)
    }



    override fun onActivityResult(requestCode: Int, resultCode:Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data == null) return
        if (requestCode != 1) return

        val bm = data.extras.get("data") as Bitmap
        //iv.setImageBitmap(bm)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            val isExit = AlertDialog.Builder(this@Home)
                isExit.setTitle("貼心小提示")
                isExit.setMessage("是否退出應用程式?")
                isExit.setPositiveButton("退出"){
                    _,_->
                    finish()
                }//FF4081
                isExit.setNegativeButton("取消"){
                    _,_->
                    Toast.makeText(this,"取消退出",Toast.LENGTH_SHORT).show()
                }

                val dialog: AlertDialog = isExit.create()
                dialog.show()
            }
        return super.onKeyDown(keyCode, event)

    }
}
