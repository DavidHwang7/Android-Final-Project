package davidhwang.raiwayvillage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log


public fun getResizedBitmap(imagePath: String): Bitmap {
    val MAX_WIDTH = 1024 // 新圖的寬要小於等於這個值

    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true //只讀取寬度和高度
    BitmapFactory.decodeFile(imagePath, options)
    var width = options.outWidth
    var height = options.outHeight

    // 求出要縮小的 scale 值，必需是2的次方，ex: 1,2,4,8,16...
    var scale = 1
    while (width > MAX_WIDTH * 2) {
        width /= 2
        height /= 2
        scale *= 2
    }

    // 使用 scale 值產生縮小的圖檔
    val scaledOptions = BitmapFactory.Options()
    scaledOptions.inSampleSize = scale
    val scaledBitmap = BitmapFactory.decodeFile(imagePath, scaledOptions)

    var resize = 1f //縮小值 resize 可為任意小數
    if (width > MAX_WIDTH) {
        resize = MAX_WIDTH.toFloat() / width
    }

    val matrix = Matrix() // 產生縮圖需要的參數 matrix
    matrix.postScale(resize, resize) // 設定寬與高的縮放比例

    // 產生縮小後的圖
    return Bitmap.createBitmap(scaledBitmap, 0, 0, width, height, matrix, true)
}

public fun getResizedBitmapFromDrawable(resID: Int, context: Context): Bitmap {
    var MAX_WIDTH = 1024 // 新圖的寬要小於等於這個值

    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true //只讀取寬度和高度
    BitmapFactory.decodeResource(context.getResources(),resID,options)
    var width = options.outWidth
    var height = options.outHeight

    Log.i("resize", "壓縮前圖片大小" + "寬度:" + width + " 高度:" + height)

    // 求出要縮小的 scale 值，必需是2的次方，ex: 1,2,4,8,16...
    var scale = 1
    while (width > MAX_WIDTH * 2) {
        width /= 2
        height /= 2
        scale *= 2
    }
    Log.i("resize", "width: " + width+" height: "+height+" scale: "+scale)

    // 使用 scale 值產生縮小的圖檔
    val scaledOptions = BitmapFactory.Options()
    scaledOptions.inSampleSize = scale
    val scaledBitmap =BitmapFactory.decodeResource(context.getResources(),resID,scaledOptions)
    Log.i("resize", "壓縮後圖片大小1" + "寬度:" + scaledBitmap.width + " 高度:" + scaledBitmap.height)

    MAX_WIDTH=1500
    var resize = 1f //縮小值 resize 可為任意小數
    if (scaledBitmap.width > MAX_WIDTH) {
        resize = MAX_WIDTH.toFloat() / scaledBitmap.width
    }

    val matrix = Matrix() // 產生縮圖需要的參數 matrix
    matrix.postScale(resize, resize) // 設定寬與高的縮放比例
    Log.i("resize", "resize: " + resize)

    // 產生縮小後的圖
    val ResizeBitmap=Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.width, scaledBitmap.height, matrix, true)
    Log.i("resize", "壓縮後圖片大小2" + "寬度:" + ResizeBitmap.width + " 高度:" + ResizeBitmap.height)
    return ResizeBitmap

}