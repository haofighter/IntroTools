package com.intro.hao.mytools.Utils.animalUtil

import android.util.Log
import android.view.View
import android.view.animation.*
import com.intro.hao.mytools.Utils.SystemUtils


/**
 *动画相关处理
 */
class AnimalUtils {
    var animalUtilsModel: AnimalUtilsModel = AnimalUtilsModel()

    companion object {
        fun get(): AnimalUtils {
            return AnimalUtils()
        }
    }

    fun setAnimalModel(animalUtilsModel: AnimalUtilsModel): AnimalUtils {
        this.animalUtilsModel = animalUtilsModel
        return this
    }


    //动画显示及消失的位置
    enum class AnimalType {
        left, right, top, bottom
    }

    fun addTranAnimal(isShow: Boolean): AnimalUtils {
        animalUtilsModel.animation.addAnimation(translateAnimal(animalUtilsModel.v, animalUtilsModel.animationTime, isShow, animalUtilsModel.type, null))
        return this
    }

    fun addAlphaAnimal(isShow: Boolean): AnimalUtils {
        animalUtilsModel.animation.addAnimation(alphaAnimal(animalUtilsModel.v, animalUtilsModel.animationTime, isShow, null))
        return this
    }

    fun addScaleAnimal(isShow: Boolean): AnimalUtils {
        animalUtilsModel.animation.addAnimation(scaleAnimal(animalUtilsModel.v, animalUtilsModel.animationTime, animalUtilsModel.type, isShow, null))
        return this
    }

    fun setAnimationListener(animationListener: Animation.AnimationListener): AnimalUtils {
        animalUtilsModel.animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
                animationIsRuning = true
                animationListener.onAnimationRepeat(p0)
            }

            override fun onAnimationEnd(p0: Animation?) {
                animationIsRuning = false
                animationListener.onAnimationEnd(p0)
            }

            override fun onAnimationStart(p0: Animation?) {
                animationIsRuning = true
                animationListener.onAnimationStart(p0)
            }
        })
        return this
    }

    var animationIsRuning: Boolean = false

    fun startAnimal() {
        if (!animationIsRuning)
            animalUtilsModel.v!!.startAnimation(animalUtilsModel.animation)
    }

    fun translateAnimal(v: View?, time: Long, isShow: Boolean, type: AnimalType, listener: Animation.AnimationListener?): TranslateAnimation? {
        if (v == null) {
            throw NullPointerException("动画的view是个空的")
        }
        var animal: TranslateAnimation? = null
        if (type == AnimalType.right) {
            if (isShow) {
                animal = TranslateAnimation(SystemUtils().getViewSize(v).measuredWidth.toFloat(), 0f, 0f, 0.0f)
            } else {
                false
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
                Log.i("动画距离", "" + -SystemUtils().getViewSize(v).measuredHeight.toFloat())
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
                        listener.onAnimationRepeat(p0)
                }

                override fun onAnimationEnd(p0: Animation?) {
                    if (listener != null)
                        listener.onAnimationRepeat(p0)
                }

                override fun onAnimationStart(p0: Animation?) {
                    if (listener != null)
                        listener.onAnimationRepeat(p0)
                }
            })
        }
        return animal
    }


    fun alphaAnimal(v: View?, time: Long, isShow: Boolean, listener: Animation.AnimationListener?): AlphaAnimation? {
        if (v == null) {
            throw NullPointerException("动画的view是个空的")
        }
        var animal: AlphaAnimation?
        if (isShow) {
            animal = AlphaAnimation(0f, 1f)
        } else {
            animal = AlphaAnimation(1f, 0f)
        }
        if (listener != null) {
            animal.setAnimationListener(listener)
        }
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

        return animal
    }


    fun scaleAnimal(v: View?, time: Long, type: AnimalType?, isShow: Boolean, listener: Animation.AnimationListener?): ScaleAnimation? {
        if (v == null) {
            throw NullPointerException("动画的view是个空的")
        }
        var animal: ScaleAnimation? = null
        if (isShow) {
            if (type == AnimalType.right) {
                animal = ScaleAnimation(0f, 1.0f, 0f, 1.0f, SystemUtils().getViewSize(v).measuredWidth.toFloat(), SystemUtils().getViewSize(v).measuredHeight.toFloat() / 2)
            } else if (type == AnimalType.left) {
                animal = ScaleAnimation(0f, 1.0f, 0f, 1.0f, 0f, SystemUtils().getViewSize(v).measuredHeight.toFloat() / 2)
            } else if (type == AnimalType.top) {
                animal = ScaleAnimation(0f, 1.0f, 0f, 1.0f, SystemUtils().getViewSize(v).measuredWidth.toFloat() / 2, 0f)
            } else if (type == AnimalType.bottom) {
                animal = ScaleAnimation(0f, 1.0f, 0f, 1.0f, SystemUtils().getViewSize(v).measuredWidth.toFloat() / 2, SystemUtils().getViewSize(v).measuredHeight.toFloat())
            }
        } else {
            if (type == AnimalType.right) {
                animal = ScaleAnimation(1.0f, 0f, 1.0f, 0f, SystemUtils().getViewSize(v).measuredWidth.toFloat(), SystemUtils().getViewSize(v).measuredHeight.toFloat() / 2)
            } else if (type == AnimalType.left) {
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
                        listener.onAnimationRepeat(p0)
                }

                override fun onAnimationEnd(p0: Animation?) {
                    v.clearAnimation()
                    if (isShow) {
                        v.visibility = View.VISIBLE
                    } else {
                        v.visibility = View.GONE
                    }
                    if (listener != null)
                        listener.onAnimationRepeat(p0)
                }

                override fun onAnimationStart(p0: Animation?) {
                    if (listener != null)
                        listener.onAnimationRepeat(p0)
                }
            })
        }
        return animal
    }

}