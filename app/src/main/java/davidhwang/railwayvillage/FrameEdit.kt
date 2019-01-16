package davidhwang.railwayvillage

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.content.ContentResolver
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.view.ViewGroup




class FrameEdit : AppCompatActivity() {

    lateinit var ProcessImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame_edit)

        val mWidth = resources.displayMetrics.widthPixels //手機的寬度(像素)
        val mHeight = resources.displayMetrics.heightPixels //手機的高度(像素)

        val intent= getIntent()
        val path = intent.getStringExtra("URI")
        val bmp=getResizedBitmap(path,mWidth,mHeight*2/5 as Int)

        ProcessImage=findViewById(R.id.frame_image)
        ProcessImage.setImageBitmap(bmp)

        val home: ImageButton = findViewById(R.id.back_frame_edit)
        home.setOnTouchListener(ButtonTouchDark())
        home.setOnClickListener(android.view.View.OnClickListener() { accessHome()})

        val black_button: Button =findViewById(R.id.black)
        black_button.setOnTouchListener(ButtonTouchDark())
        val lp1 = black_button.getLayoutParams()
        lp1.height=lp1.width
        black_button.setLayoutParams(lp1)

        val white_button: Button =findViewById(R.id.white)
        white_button.setOnTouchListener(ButtonTouchDark())
        val lp2 = white_button.getLayoutParams()
        lp2.height=lp2.width
        white_button.setLayoutParams(lp2)

        val save_button: Button =findViewById(R.id.save)
        save_button.setOnTouchListener(ButtonTouchDark())
        val lp3 = save_button.getLayoutParams()
        lp3.height=lp3.width
        save_button.setLayoutParams(lp3)


        black_button.setOnClickListener{applyBlack()}
        white_button.setOnClickListener{applyWhite()}
        save_button.setOnClickListener{save()}
        //val day: Int = intent.getIntExtra(SetBirthdayActivity.KEY_DAY, 1)
        /*val cr = this.contentResolver

        try {
            //讀取照片，型態為Bitmap
            val bmp=getResizedBitmap(uri)
            val bitmap = BitmapFactory.decodeStream(cr.openInputStream(Uri.parse(uri)))

            //判斷照片為橫向或者為直向，並進入ScalePic判斷圖片是否要進行縮放
            if (bitmap.width > bitmap.height)
                ScalePic(bitmap,
                        mPhone.heightPixels)
            else
                ScalePic(bitmap, mPhone.widthPixels)
        } catch (e: FileNotFoundException) {
        }*/
    }

    private fun applyBlack(){

    }

    private fun applyWhite(){

    }

    private fun save(){


    }

    private fun accessHome(){
        val intent = Intent()
        intent.setClass(this,
                Home::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
    }

}
