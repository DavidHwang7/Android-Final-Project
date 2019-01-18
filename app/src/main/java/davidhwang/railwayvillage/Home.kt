package davidhwang.railwayvillage

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.widget.ImageButton
import android.support.v7.app.AlertDialog
import android.widget.Toast
import android.R.raw
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.media.AudioManager


class Home : AppCompatActivity() {

    var mplayer : MediaPlayer? = null
    lateinit var smgr: SensorManager
    lateinit var slist: List<Sensor>

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
        init();
    }

    private fun init(){
        try {
            mplayer = MediaPlayer.create(this, R.raw.music);
            mplayer?.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mplayer?.setLooping(true);

        }catch (e:IllegalStateException) {
            e.printStackTrace();
        }
        mplayer?.start()
        smgr = getSystemService(SENSOR_SERVICE) as SensorManager

        slist = smgr.getSensorList(Sensor.TYPE_ACCELEROMETER)
        val notice = AlertDialog.Builder(this@Home)
        if (slist.size == 0) {

            notice.setTitle("貼心小提示")
            notice.setMessage("您的手機沒有加速度感測器用以控制背景音樂的開關，是否現在關閉背景音樂?")
            notice.setPositiveButton("是"){
                _,_->
                mplayer?.stop()
            }
            notice.setNegativeButton("否"){
                _,_->
                ;
            }
        }else{
            notice.setTitle("貼心小提示")
            notice.setMessage("手機向右傾倒可關閉音樂，\n向左傾倒可再開啟音樂。")
            notice.setPositiveButton("我知道了"){
                _,_->
                ;
            }
        }


        val dialog: AlertDialog = notice.create()
        dialog.show()

    }

    override fun onStart() {
        super.onStart()
        smgr.registerListener(listener, slist[0],
                SensorManager.SENSOR_DELAY_UI)
    }

    override fun onStop() {
        super.onStop()
        smgr.unregisterListener(listener, slist[0])
    }
    var X_pos_pre: Boolean = true
    var X_neg_pre: Boolean = true
    val listener = object: SensorEventListener {

        override fun onSensorChanged(event: SensorEvent) {

            var X_pos: Boolean = (event.values[0]>7.81)&&(event.values[0]<11.81)
            var X_neg: Boolean = (event.values[0]>-11.81)&&(event.values[0]<-7.81)
            if(X_pos&&!X_pos_pre) {

                try {
                    mplayer?.start()
                    Toast.makeText(this@Home, "背景音樂開啟", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                }
            }
            else if(X_neg&&!X_neg_pre) {

                try {
                    mplayer?.pause()
                    Toast.makeText(this@Home, "背景音樂關閉", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                }
            }
            X_pos_pre=X_pos
            X_neg_pre=X_neg
        }

        override fun onAccuracyChanged(s: Sensor, a: Int) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mplayer?.release()
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
                    Toast.makeText(this@Home,"取消退出",Toast.LENGTH_SHORT).show()
                }

                val dialog: AlertDialog = isExit.create()
                dialog.show()
            }
        return super.onKeyDown(keyCode, event)

    }
}
