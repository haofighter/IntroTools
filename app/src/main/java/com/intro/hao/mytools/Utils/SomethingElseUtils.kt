package com.intro.hao.mytools.Utils

import android.util.Log
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * 用于添加一部分不常用的辅助类
 */
class SomethingElseUtils {
    //从一段html代码中查找出图片路径
    fun searchImgString(content: String): ArrayList<String> {
        var list = ArrayList<String>();
        var regex = "src=\"(.*?)\"";
        var pa = Pattern.compile(regex, Pattern.DOTALL);
        var ma = pa.matcher(content);
        while (ma.find()) {
            list.add(ma.group());
        }
        Log.i("查询的图片数量", "" + list.size)
        return list;
    }
}