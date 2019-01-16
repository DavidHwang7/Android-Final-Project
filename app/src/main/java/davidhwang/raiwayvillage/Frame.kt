package davidhwang.raiwayvillage

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Button
import android.Manifest
import android.content.Intent
import android.provider.MediaStore

class Frame : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)
        val camera_button: Button =findViewById(R.id.camera)
        val gallery_button: Button =findViewById(R.id.gallery)

        camera_button.setOnClickListener{accessCamera()}
        gallery_button.setOnClickListener{accessGallery()}

    }

    private fun accessCamera(){
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1)
            }
            if(ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        2)
            }
            if(ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.CAMERA),
                        3)
            }
        }
        else{
            val cameraIntent = Intent()
            cameraIntent.action = MediaStore.ACTION_IMAGE_CAPTURE
            startActivityForResult(cameraIntent, 6)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions:
    Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var numOfPermission_camera=0
        var numOfPermission_gallery=0
        when(requestCode) {
            1,2,3-> if ((grantResults.isNotEmpty() && grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED)) {
                    numOfPermission_camera+=1
                } else { }
            4,5->if ((grantResults.isNotEmpty() && grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED)) {
                numOfPermission_gallery+=1
            } else { }
        }
        if(numOfPermission_camera==3){
            val cameraIntent = Intent()
            cameraIntent.action = MediaStore.ACTION_IMAGE_CAPTURE
            startActivityForResult(cameraIntent, 4)
        }
        else if(numOfPermission_gallery==2){
            val intent = Intent()
            //開啟Pictures畫面Type設定為image
            intent.setType("image/*")
            //使用Intent.ACTION_GET_CONTENT這個Action
            intent.setAction(Intent.ACTION_GET_CONTENT)
            //取得照片後返回此畫面
            startActivityForResult(intent, 7)
        }
    }

    private fun accessGallery() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        4)
            }
            if(ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        5)
            }

        }
        else{
            val intent = Intent()
            //開啟Pictures畫面Type設定為image
            intent.setType("image/*")
            //使用Intent.ACTION_GET_CONTENT這個Action
            intent.setAction(Intent.ACTION_GET_CONTENT)
            //取得照片後返回此畫面
            startActivityForResult(intent, 7)
        }
    }
}
