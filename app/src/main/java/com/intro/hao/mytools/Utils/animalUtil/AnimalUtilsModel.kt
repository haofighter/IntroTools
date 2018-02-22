package com.intro.hao.mytools.Utils.animalUtil

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet

/**
 * Created by hao on 2018/2/22.
 */
class AnimalUtilsModel {
    var v: View? = null
    var animation: AnimationSet = AnimationSet(true)
    var animationTime: Long = 200 //控制动画的时间
    var type: AnimalUtils.AnimalType = AnimalUtils.AnimalType.top //仅针对于平移  和缩放动画的类型设置

    constructor(v: View?, type: AnimalUtils.AnimalType) {
        this.v = v
        this.type = type
    }

    constructor()

}