package davidhwang.raiwayvillage

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.util.Linkify
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.text.NumberFormat
import android.Manifest
import android.location.Criteria

class Info : AppCompatActivity() {

    lateinit var bmp: Bitmap
    lateinit var InfoImage: ImageView
    var tv_loc: TextView? = null
    var tv_sign: TextView? = null
    var locmgr: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        bmp= getResizedBitmapFromDrawable(R.drawable.info0,this)
        InfoImage=findViewById(R.id.info_image)
        InfoImage.setImageBitmap(bmp)

        val home: ImageButton = findViewById(R.id.back_info)
        val btn_call: TextView = findViewById(R.id.btn_call)
        val btn_map: TextView = findViewById(R.id.btn_map)
        val btn_web: TextView = findViewById(R.id.btn_web)

        Linkify.addLinks(btn_call, Linkify.PHONE_NUMBERS)
        Linkify.addLinks(btn_map, Linkify.MAP_ADDRESSES)
        Linkify.addLinks(btn_web, Linkify.WEB_URLS)

        home.setOnTouchListener(ButtonTouchDark())
        home.setOnClickListener { accessHome()}

        btn_call.setOnClickListener {
            btn_call.setTextColor(Color.RED)
            openCallApp()}


        btn_map.setOnClickListener {
            btn_map.setTextColor(Color.RED)
            openMap()}

        btn_web.setOnClickListener {
            btn_web.setTextColor(Color.RED)
            openWeb()}

        tv_loc = findViewById(R.id.tv_loc)
        tv_sign = findViewById(R.id.tv_sign)

        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION),
                    1)
        } else {
            initLoc()
        }
    }

    override fun onStop(){
        super.onStop()
        bmp.recycle()
        InfoImage.setImageDrawable(null)
        InfoImage.setImageBitmap(null)
    }

    override fun onRestart() {
        super.onRestart()
        var btn: TextView = findViewById(R.id.btn_call)
        btn.setTextColor(Color.BLUE)

        btn=findViewById(R.id.btn_map)
        btn.setTextColor(Color.BLUE)

        btn=findViewById(R.id.btn_web)
        btn.setTextColor(Color.BLUE)

        bmp= getResizedBitmapFromDrawable(R.drawable.info0,this)
        InfoImage.setImageBitmap(bmp)
    }

    private fun accessHome(){
        val intent = Intent()
        intent.setClass(this,
                Home::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
    }

    private fun openCallApp() {
        val uri: Uri = Uri.parse("tel:03-5628933")
        val intent: Intent = Intent()
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setData(uri);
        startActivity(intent);
    }

    private fun openMap() {
        val uri: Uri = Uri.parse("geo:24.8041358,120.9769182?q=新竹鐵道藝術村")
        val intent: Intent = Intent()
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setData(uri);
        startActivity(intent);
    }

    private fun openWeb() {
        val uri: Uri = Uri.parse("https://culture.hccg.gov.tw/ch/home.jsp?id=153&parentpath=0,145")
        val intent: Intent = Intent()
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setData(uri);
        startActivity(intent);
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions:
    Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if ((grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED)) {
            initLoc()
        }
    }

    private fun initLoc() {
        locmgr = getSystemService(LOCATION_SERVICE) as LocationManager //location manager的物件(轉型成location mana

        var loc: Location? = null
        try {
            loc = locmgr?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (loc == null) {
                loc = locmgr?.getLastKnownLocation(
                        LocationManager.NETWORK_PROVIDER);
            }
        } catch (e: SecurityException) {
        } //52~60:取得系統上一次取得的最後位置

        if (loc != null) {
            tv_loc?.setText(showLocation(loc));
        } else {
            tv_loc?.setText("Cannot get location!");
        }

        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        val provider: String? = locmgr?.getBestProvider(
                criteria, true)

        try {
            if (provider != null) {
                locmgr?.requestLocationUpdates(provider,
                        1000, 1f, loclistener)
            } else {
                locmgr?.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        1000, 1f, loclistener)
            }
        } catch (e: SecurityException) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locmgr?.removeUpdates(loclistener)
    }

    var loclistener = object: LocationListener {

        override fun onLocationChanged(loc: Location?) {
            if (loc != null) {
                tv_loc?.setText(showLocation(loc));
                tv_sign?.setText(showSign(loc));
            } else {
                tv_loc?.setText("Cannot get location!");
            }
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }

        override fun onStatusChanged(provider: String?, status: Int,
                                     extras: Bundle?) {
        }
    }

    private fun showLocation(loc: Location): String {

        val msg = StringBuffer()

        msg.append("目前緯度(Latitude): ")
        msg.append(loc.getLatitude().toString())
        msg.append("\n目前經度(Longitude): ")
        msg.append(loc.getLongitude().toString())

        val results = FloatArray(1)
        //現在緯度,現在經度,目標緯度,目標經度,
        Location.distanceBetween(loc.getLatitude(), loc.getLongitude(), 24.789481 ,120.9757595, results)
        val distance = NumberFormat.getInstance().format(results[0])
        msg.append("\n距離鐵道藝術村: ")
        msg.append(distance+" 公尺")
        return msg.toString()
    }

    private fun showSign(loc: Location): String {

        val msg = StringBuffer()
        val results = FloatArray(1)
        //現在緯度,現在經度,目標緯度,目標經度,
        Location.distanceBetween(loc.getLatitude(), loc.getLongitude(), 24.789481 ,120.9757595, results)
        val distance = NumberFormat.getInstance().format(results[0])
        if(results[0]<2000)
            msg.append("您已進入鐵道藝術村範圍")

        return msg.toString()
    }

}
