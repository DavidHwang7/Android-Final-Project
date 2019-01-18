package davidhwang.railwayvillage

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.content.ContentResolver
import android.graphics.*
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.view.View.MeasureSpec
import android.view.View.MeasureSpec.UNSPECIFIED
import android.view.View.MeasureSpec.makeMeasureSpec
import java.io.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class FrameEdit : AppCompatActivity() {

    lateinit var ProcessImage: ImageView
    lateinit var image_bmp: Bitmap
    lateinit var montage_bmp:Bitmap
    lateinit var FROM:String
    lateinit var path:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame_edit)

        val mWidth = resources.displayMetrics.widthPixels //手機的寬度(像素)
        val mHeight = resources.displayMetrics.heightPixels //手機的高度(像素)

        val show_area : RelativeLayout= findViewById(R.id.show_area)
        val spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        show_area.measure(spec, spec)
        val show_area_height = show_area.getMeasuredHeight();

        val intent= getIntent()
        path = intent.getStringExtra("URI")
        FROM=intent.getStringExtra("FROM")
        image_bmp=getResizedBitmap(path,mWidth,show_area_height as Int)
        ProcessImage=findViewById(R.id.frame_image)
        applyFrame(2,1)

        val cancel: Button = findViewById(R.id.cancel)
        cancel.setOnTouchListener(ButtonTouchDark())
        cancel.setOnClickListener(android.view.View.OnClickListener() { cancel()})

        val black_solid_button: ImageButton =findViewById(R.id.black_solid)
        black_solid_button.setOnTouchListener(ButtonTouchLight())
        black_solid_button.setOnClickListener{applyFrame(1,1)}

        val black_dot_button: ImageButton =findViewById(R.id.black_dot)
        black_dot_button.setOnTouchListener(ButtonTouchLight())
        black_dot_button.setOnClickListener{applyFrame(1,2)}

        val white_solid_button: ImageButton =findViewById(R.id.white_solid)
        white_solid_button.setOnTouchListener(ButtonTouchDark())
        white_solid_button.setOnClickListener{applyFrame(2,1)}

        val white_dot_button: ImageButton =findViewById(R.id.white_dot)
        white_dot_button.setOnTouchListener(ButtonTouchDark())
        white_dot_button.setOnClickListener{applyFrame(2,2)}

        val save_button: Button =findViewById(R.id.save)
        save_button.setOnTouchListener(ButtonTouchDark())
        save_button.setOnClickListener{save(path)}

    }

    private fun applyFrame(color:Int,type:Int){
        lateinit var select_frame:Bitmap
        if(color==1){
            if(type==1) {
                if(image_bmp.width>image_bmp.height&&image_bmp.width/image_bmp.height>=4/3){
                    select_frame= getResizedBitmapFromDrawable(R.drawable.frame_black,this)
                } else{
                    select_frame= getResizedBitmapFromDrawable(R.drawable.frame_black_portrait_solid,this)
                }
            } else if(type==2){
                if(image_bmp.width>image_bmp.height&&image_bmp.width/image_bmp.height>=4/3){
                    select_frame= getResizedBitmapFromDrawable(R.drawable.frame_black_dot,this)
                } else{
                    select_frame= getResizedBitmapFromDrawable(R.drawable.frame_black_portrait_dot,this)
                }
            }
        }else if(color==2){
            if(type==1) {
                if(image_bmp.width>image_bmp.height&&image_bmp.width/image_bmp.height>=4/3){
                    select_frame= getResizedBitmapFromDrawable(R.drawable.frame_white,this)
                } else{
                    select_frame= getResizedBitmapFromDrawable(R.drawable.frame_white_portrait_solid,this)
                }
            } else if(type==2){
                if(image_bmp.width>image_bmp.height&&image_bmp.width/image_bmp.height>=4/3){
                    select_frame= getResizedBitmapFromDrawable(R.drawable.frame_white_dot,this)
                } else{
                    select_frame= getResizedBitmapFromDrawable(R.drawable.frame_white_portrait_dot,this)
                }
            }
        }
        montage_bmp=montageBitmap(select_frame,image_bmp,0f,0f)
        ProcessImage.setImageBitmap(montage_bmp)
    }


    private fun montageBitmap(frame: Bitmap, src: Bitmap, x: Float, y: Float): Bitmap {

        val w = src.width
        val h = src.height
        val sizeFrame = Bitmap.createScaledBitmap(frame, w, h, true)

        val newBM = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBM)
        val mBitPaint = Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
        canvas.drawBitmap(src, x , y , null)
        canvas.drawBitmap(sizeFrame, 0f , 0f , null)
        return newBM
    }


    private fun save(path:String){
        try {
            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdir()
            }
            val fOut = FileOutputStream(path)
            montage_bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut)

            try {
                fOut.flush()
                fOut.close()
                Toast.makeText(this,"儲存成功",Toast.LENGTH_SHORT).show()
                val intent=Intent()
                intent.setClass(this,Frame::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun cancel(){
        val isExit = AlertDialog.Builder(this)
        isExit.setTitle("貼心小提示")
        isExit.setMessage("檔案將不會保留\n是否確定離開編輯畫面?")
        isExit.setPositiveButton("離開"){
            _,_->
            if(FROM=="Camera"){
                val file = File(path)
                file.delete()
            }
            val intent = Intent()
            intent.setClass(this,
                    Frame::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)

        }
        isExit.setNegativeButton("取消"){
            _,_->
        }

        val dialog: AlertDialog = isExit.create()
        dialog.show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            val isExit = AlertDialog.Builder(this)
            isExit.setTitle("貼心小提示")
            isExit.setMessage("檔案將不會保留\n是否確定離開編輯畫面?")
            isExit.setPositiveButton("離開"){
                _,_->
                if(FROM=="Camera"){
                    val file = File(path)
                    file.delete()
                }
                val intent = Intent()
                intent.setClass(this,
                        Frame::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent)
            }
            isExit.setNegativeButton("取消"){
                _,_->
            }

            val dialog: AlertDialog = isExit.create()
            dialog.show()
        }
        return super.onKeyDown(keyCode, event)

    }


}
