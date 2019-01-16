package davidhwang.railwayvillage

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Button
import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.ImageButton
import android.content.ContentValues
import android.R.attr.data
import android.util.Log
import android.R.attr.bitmap
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.system.Os.mkdir
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files.exists
import android.R.attr.data
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import android.app.Activity




class Frame : AppCompatActivity() {

    lateinit var saveUri: Uri  //外部定義變數
    lateinit var savePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)
        val home: ImageButton = findViewById(R.id.back_frame)
        home.setOnTouchListener(ButtonTouchDark())
        home.setOnClickListener(android.view.View.OnClickListener() { accessHome()})

        val camera_button: Button =findViewById(R.id.camera)
        camera_button.setOnTouchListener(ButtonTouchDark())
        val gallery_button: Button =findViewById(R.id.gallery)
        gallery_button.setOnTouchListener(ButtonTouchDark())


        camera_button.setOnClickListener{accessCamera()}
        gallery_button.setOnClickListener{accessGallery()}

    }

    private fun accessCamera(){
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA),
                    1)
        }
        else{
            val cameraIntent = Intent()
            cameraIntent.action = MediaStore.ACTION_IMAGE_CAPTURE
            val tmpFile = File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).toString(), System.currentTimeMillis().toString() + ".jpg")
            savePath=tmpFile.absolutePath

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //如果在Android7.0以上,使用FileProvider獲取Uri
                cameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                val uriForCamera = FileProvider.getUriForFile(this, "davidhwang.railwayvillage", tmpFile)
                //saveUri = uriForCamera//將 Uri 存進變數供後續 onActivityResult 使用
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForCamera)
            }else{
                //saveUri = Uri.fromFile(tmpFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tmpFile))
            }
            startActivityForResult(cameraIntent, 2)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions:
    Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1-> if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED&&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED) {

                    val cameraIntent = Intent()
                    cameraIntent.action = MediaStore.ACTION_IMAGE_CAPTURE
                    val tmpFile = File(Environment.getExternalStorageDirectory().toString(), System.currentTimeMillis().toString() + ".jpg")
                    savePath=tmpFile.absolutePath

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //如果在Android7.0以上,使用FileProvider獲取Uri
                        cameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                        val uriForCamera = FileProvider.getUriForFile(this, "davidhwang.railwayvillage", tmpFile)
                        //saveUri = uriForCamera//將 Uri 存進變數供後續 onActivityResult 使用
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForCamera)
                    }else{
                        //saveUri = Uri.fromFile(tmpFile)
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tmpFile))
                    }

                    startActivityForResult(cameraIntent, 2)

                } else { }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode:Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                /*val bmp = data.extras.get("data") as Bitmap
                val imgdir = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)
                val fOut: FileOutputStream
                val tmp = imgdir.toString() + "/tmp.jpg"
                try {
                    val dir = File(imgdir.toString())
                    if (!dir.exists()) {
                        dir.mkdir()
                    }

                    fOut = FileOutputStream(tmp)
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut)

                    try {
                        fOut.flush()
                        fOut.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }*/
                val intent = Intent()
                intent.setClass(this, FrameEdit::class.java)
                Log.i("URI", "uri_camera : " + savePath)
                intent.putExtra("URI", savePath)
                startActivity(intent)
            }
        }
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                val uri = data!!.data
                //val path=getRealPathFromUri(this,uri)
                val path1=pick_image.getPath(this,uri)
                Log.i("URI", "uri_gallery : " + path1)
                val intent = Intent()
                intent.setClass(this, FrameEdit::class.java)
                intent.putExtra("URI", path1)
                startActivity(intent)
            }
        }
    }

    fun getRealPathFromUri(activity: Activity, contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity.managedQuery(contentUri, proj, null, null, null)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    private fun accessGallery() {
        val intent = Intent()
        //開啟Pictures畫面Type設定為image
        intent.setType("image/*")
        //使用Intent.ACTION_GET_CONTENT這個Action
        intent.setAction(Intent.ACTION_GET_CONTENT)
        //取得照片後返回此畫面
        startActivityForResult(intent, 3)
    }

    private fun accessHome(){
        val intent = Intent()
        intent.setClass(this,
                Home::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
    }
}
