package com.intro.hao.mytools.Utils

import android.view.View
import android.view.animation.*


/**
 *动画相关处理
 */
class AnimalUtils {
    var animation: AnimationSet? = null

    constructor() {
        if (animation == null)
            animation = AnimationSet(true)
    }

    companion object {
        fun get(): AnimalUtils {
            return AnimalUtils()
        }
    }


    enum class AnimalType {
        left, right, top, bottom
    }

    fun setDissmisAnimal(v: View, type: AnimalType, listener: Animation.AnimationListener?) {
        animation!!.addAnimation(translateAnimalLeft(v, 200, false, type, null))
        animation!!.addAnimation(alphaAnimal(v, 200, false, null))
        animation!!.addAnimation(scaleAnimal(v, 200, type, false, null))
        animation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
                if (listener != null) {
                    listener.onAnimationRepeat(p0)
                }
            }

            override fun onAnimationEnd(p0: Animation?) {
                if (listener != null) {
                    listener.onAnimationRepeat(p0)
                }
            }

            override fun onAnimationStart(p0: Animation?) {
                if (listener != null) {
                    listener.onAnimationStart(p0)
                }
            }
        })
        v.startAnimation(animation)
        v.visibility = View.GONE
    }

    fun setShowAnimal(v: View, type: AnimalType, listener: Animation.AnimationListener?) {
        animation!!.addAnimation(translateAnimalLeft(v, 200, true, type, null))
        animation!!.addAnimation(alphaAnimal(v, 200, true, null))
        animation!!.addAnimation(scaleAnimal(v, 200, type, true, null))
        v.startAnimation(animation)
        v.visibility = View.VISIBLE
    }


    fun translateAnimalLeft(v: View, time: Long, isShow: Boolean, type: AnimalType, listener: Animation.AnimationListener?): TranslateAnimation? {
        var animal: TranslateAnimation? = null
        if (type == AnimalType.right) {
            if (isShow) {
                animal = TranslateAnimation(SystemUtils().getViewSize(v).measuredWidth.toFloat(), 0f, 0f, 0.0f)
            } else {
                animal = TranslateAnimation(0f, SystemUtils().getViewSize(v).measuredWidth.toFloat(), 0f, 0.0f)
            }
        } else if (type == AnimalType.left) {
            if (isShow) {
                animal = TranslateAnimation(-SystemUtils().getViewSize(v).measuredWidth.toFloat(), 0f, 0f, 0.0f)
            } else {
                animal = TranslateAnimation(0f, -SystemUtils().getViewSize(v).measuredWidth.toFloat(), 0f, 0.0f)
            }
        } else if (type == AnimalType.bottom) {
            if (isShow) {
                animal = TranslateAnimation(0f, 0f, SystemUtils().getViewSize(v).measuredHeight.toFloat(), 0.0f)
            } else {
                animal = TranslateAnimation(0f, 0.0f, 0f, SystemUtils().getViewSize(v).measuredHeight.toFloat())
            }
        } else if (type == AnimalType.top) {
            if (isShow) {
                animal = TranslateAnimation(0f, 0f, -SystemUtils().getViewSize(v).measuredHeight.toFloat(), 0.0f)
            } else {
                animal = TranslateAnimation(0f, 0.0f, 0f, -SystemUtils().getViewSize(v).measuredHeight.toFloat())
            }
        }
        if (animal != null) {
            animal.isFillEnabled = true
            animal.duration = time
            animal.fillBefore = false
            animal.fillAfter = true
            animal.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    if (listener != null)
                        listener!!.onAnimationRepeat(p0)
                }

                override fun onAnimationEnd(p0: Animation?) {
                    if (listener != null)
                        listener!!.onAnimationRepeat(p0)
                }

                override fun onAnimationStart(p0: Animation?) {
                    if (listener != null)
                        listener!!.onAnimationRepeat(p0)
                }
            })
        }
        return animal
    }


    fun alphaAnimal(v: View, time: Long, isShow: Boolean, listener: Animation.AnimationListener?): AlphaAnimation? {
        var animal: AlphaAnimation? = null
        if (isShow) {
            animal = AlphaAnimation(0f, 1f)
        } else {
            animal = AlphaAnimation(1f, 0f)
        }
        if (animal != null) {
            if (listener != null)
                animal.setAnimationListener(listener)
            animal.duration = time
            animal.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    v.clearAnimation()
                    if (isShow) {
                        v.visibility = View.VISIBLE
                    } else {
                        v.visibility = View.GONE
                    }
                }

                override fun onAnimationStart(p0: Animation?) {

                }
            })
        }
        return animal
    }


    fun scaleAnimal(v: View, time: Long, type: AnimalType?, isShow: Boolean, listener: Animation.AnimationListener?): ScaleAnimation? {
        var animal: ScaleAnimation? = null
        if (isShow) {
            if (type == AnimalType.left) {
                animal = ScaleAnimation(0f, 1.0f, 0f, 1.0f, SystemUtils().getViewSize(v).measuredWidth.toFloat(), SystemUtils().getViewSize(v).measuredHeight.toFloat() / 2)
            } else if (type == AnimalType.right) {
                animal = ScaleAnimation(0f, 1.0f, 0f, 1.0f, 0f, SystemUtils().getViewSize(v).measuredHeight.toFloat() / 2)
            } else if (type == AnimalType.top) {
                animal = ScaleAnimation(0f, 1.0f, 0f, 1.0f, SystemUtils().getViewSize(v).measuredWidth.toFloat() / 2, 0f)
            } else if (type == AnimalType.bottom) {
                animal = ScaleAnimation(0f, 1.0f, 0f, 1.0f, SystemUtils().getViewSize(v).measuredWidth.toFloat() / 2, SystemUtils().getViewSize(v).measuredHeight.toFloat())
            }
        } else {
            if (type == AnimalType.left) {
                animal = ScaleAnimation(1.0f, 0f, 1.0f, 0f, SystemUtils().getViewSize(v).measuredWidth.toFloat(), SystemUtils().getViewSize(v).measuredHeight.toFloat() / 2)
            } else if (type == AnimalType.right) {
                animal = ScaleAnimation(1.0f, 0f, 1.0f, 0f, 0f, SystemUtils().getViewSize(v).measuredHeight.toFloat() / 2)
            } else if (type == AnimalType.top) {
                animal = ScaleAnimation(1.0f, 0f, 1.0f, 0f, SystemUtils().getViewSize(v).measuredWidth.toFloat() / 2, 0f)
            } else if (type == AnimalType.bottom) {
                animal = ScaleAnimation(1.0f, 0f, 1.0f, 0f, SystemUtils().getViewSize(v).measuredWidth.toFloat() / 2, SystemUtils().getViewSize(v).measuredHeight.toFloat())

            }
        }
        if (animal != null) {
            if (listener != null)
                animal.setAnimationListener(listener)
            animal.duration = time
            animal.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    if (listener != null)
                        listener!!.onAnimationRepeat(p0)
                }

                override fun onAnimationEnd(p0: Animation?) {
                    v.clearAnimation()
                    if (isShow) {
                        v.visibility = View.VISIBLE
                    } else {
                        v.visibility = View.GONE
                    }
                    if (listener != null)
                        listener!!.onAnimationRepeat(p0)
                }

                override fun onAnimationStart(p0: Animation?) {
                    if (listener != null)
                        listener!!.onAnimationRepeat(p0)
                }
            })
        }
        return animal
    }

}