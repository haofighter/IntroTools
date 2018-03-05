package com.intro.hao.mytools.Utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v8.renderscript.Allocation
import android.support.v8.renderscript.Element
import android.support.v8.renderscript.RenderScript
import android.support.v8.renderscript.ScriptIntrinsicBlur
import android.util.Log
import com.intro.hao.mytools.base.App


/**
 * Created by haozhang on 2018/1/3.
 */
class BitmapUtils {

    //用于对图片进行缩放处理  转换为正方形
    fun fitBitmap(target: Bitmap, newWidth: Int): Bitmap {
        val width = target.width
        val height = target.height
        val matrix = Matrix()
        val scaleWidth = newWidth.toFloat() / width
        matrix.postScale(scaleWidth, scaleWidth)
        // Bitmap result = Bitmap.createBitmap(target,0,0,width,height,
        // matrix,true);
        //手动调用回收机制
        //if (target != null && !target.equals(bmp) && !target.isRecycled()) {
        //target.recycle();
        //target = null;
        //}
        return Bitmap.createBitmap(target, 0, 0, width, height, matrix,
                true)
    }

    //decodeStream最大的秘密在于其直接调用JNI>>nativeDecodeAsset()来完成decode，无需再使用java层的createBitmap，从而节省了java层的空间。
    fun readBitMap(context: Context, resId: Int): Bitmap {
        val opt = BitmapFactory.Options()
        opt.inPreferredConfig = Bitmap.Config.RGB_565
        opt.inPurgeable = true
        opt.inInputShareable = true
        //  获取资源图片
        val `is` = context.resources.openRawResource(resId)
        return BitmapFactory.decodeStream(`is`, null, opt)
    }

    /**
     * drawable转bitmap
     */
    fun drawableToBitmap(drawable: Drawable): Bitmap {
        val bd = drawable as BitmapDrawable
        return bd.bitmap
    }


    fun blurDrawable(drawable: Drawable, radius: Float): Drawable {
        return BitmapDrawable(blurBitmap(drawableToBitmap(drawable), radius))
    }

    fun blurDrawableForBitmap(drawable: Drawable, radius: Float): Bitmap {
        return drawableToBitmap(BitmapDrawable(blurBitmap(drawableToBitmap(drawable), radius)))
    }

    /**
     * 高斯模糊
     * radius 高斯模糊的数值 0-25之间  >=0图片将不会显示
     */
    fun blurBitmap(setBitmap: Bitmap, radius: Float): Bitmap {
        //对图片进行缩小  提高处理速度
        var width = Math.round(setBitmap.getWidth() * 0.2);
        var height = Math.round(setBitmap.getHeight() * 0.2);
        var bitmap = Bitmap.createScaledBitmap(setBitmap, width.toInt(), height.toInt(), false);

        //用需要创建高斯模糊bitmap创建一个空的bitmap
        val outBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        // 初始化Renderscript，该类提供了RenderScript context，创建其他RS类之前必须先创建这个类，其控制RenderScript的初始化，资源管理及释放
        val rs = RenderScript.create(App.instance.applicationContext)
        // 创建高斯模糊对象
        val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        // 创建Allocations，此类是将数据传递给RenderScript内核的主要方 法，并制定一个后备类型存储给定类型
        val allIn = Allocation.createFromBitmap(rs, bitmap)
        val allOut = Allocation.createFromBitmap(rs, outBitmap)
        //设定模糊度(注：Radius最大只能设置25.f)
        blurScript.setRadius(radius)
        // Perform the Renderscript
        blurScript.setInput(allIn)
        blurScript.forEach(allOut)
        // Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap)
        // recycle the original bitmap
        // bitmap.recycle();
        // After finishing everything, we destroy the Renderscript.
        rs.destroy()
        return outBitmap
    }


    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    fun toRoundBitmap(bitmap: Bitmap): Bitmap {
        var width = bitmap.width
        var height = bitmap.height
        val roundPx: Float
        val left: Float
        val top: Float
        val right: Float
        val bottom: Float
        val dst_left: Float
        val dst_top: Float
        val dst_right: Float
        val dst_bottom: Float
        if (width <= height) {
            roundPx = (width / 2).toFloat()
            left = 0f
            top = 0f
            right = width.toFloat()
            bottom = width.toFloat()
            height = width
            dst_left = 0f
            dst_top = 0f
            dst_right = width.toFloat()
            dst_bottom = width.toFloat()
        } else {
            roundPx = (height / 2).toFloat()
            val clip = ((width - height) / 2).toFloat()
            left = clip
            right = width - clip
            top = 0f
            bottom = height.toFloat()
            width = height
            dst_left = 0f
            dst_top = 0f
            dst_right = height.toFloat()
            dst_bottom = height.toFloat()
        }
        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val color = -0xbdbdbe
        val paint = Paint()
        val src = Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        val dst = Rect(dst_left.toInt(), dst_top.toInt(), dst_right.toInt(), dst_bottom.toInt())
        val rectF = RectF(dst)

        paint.isAntiAlias = true// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0) // 填充整个Canvas
        paint.color = color

        // 以下有两种方法画圆,drawRounRect和drawCircle
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint) //以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output
    }


}