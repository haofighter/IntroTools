package com.intro.hao.mytools.Utils

import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Created by hao on 2018/2/27.
 */
class ReflectUtils {
    /**
     * 打印类的信息，包括类的成员函数、成员变量(只获取成员函数)
     *
     * @param object
     * 该对象所属类的信息
     */
    fun printClassMethodMessage(obj: Any): Class<Any> {// 该类所属 的信息
        // 要获取类的信息，首先要获取类的类类型
        val class1 = obj.javaClass// 传递的是哪个子类的对象，class1就是该子类的类类型
        // 获取类的名称
        println("类的名称是：" + class1.name)
        /**
         * Method类，方法对象 一个成员方法就是一个Method对象 getMehtod()方法
         * 获取的是所有得public的函数，包括父类继承的 getDeclaredMethods()获取的是所有该类声明的方法，不同访问权限
         */
        val ms = class1.methods
        for (i in ms.indices) {
            // 得到方法的返回值类型的类类型
            val returnType = ms[i].returnType
            print(returnType.name + " ")
            // 得到方法的名称
            print(ms[i].name + "(")
            // 获取参数类型
            val paramTypes = ms[i].parameterTypes
            for (class2 in paramTypes) {
                print(class2.name + ",")
            }
            printFieldMessage(class1, "")
        }
        return class1
    }

    /**
     * 获取成员变量信息
     *
     * @param object
     */
    fun printFieldMessage(obj: Any, str: String): Field? {
        // 要获取类的信息，首先要获取类的类类型
        val class1 = obj.javaClass// 传递的是哪个子类的对象，class1就是该子类的类类型
        // 获取类的名称
        println("类的名称是：" + class1.name)
        /**
         * 成员变量也是对象 java.lang.reflect.Field Field类封装了关于成员变量的操作
         * getFields()方法获取的是所有的public的成员变量的信息
         * getDeclaredFields获取的是该类自己声明的成员变量的信息
         */
        // Field[] fs = class1.getFields();
        val fs = class1.declaredFields
        for (field in fs) {
            // 得到成员变量的类型的类类型
            val filedType = field.type
            val typeName = filedType.name
            val fieldName = field.name
            println(typeName + " " + fieldName)
            if (str.equals(fieldName)) {
                return field
            }
        }
        return null
    }


    /**
     * 打印对象的构造函数的信息
     *
     * @param object
     */
    fun printConMessage(obj: Any) {
        val class1 = obj.javaClass
        /**
         * 构造函数也是对象 java.lang.Constructor中封装了构造函数的信息
         * getConstructors获取所有的public的构造函数 getDeclaredConstructors得到所有的构造函数
         */
        // Constructor[] cs = class1.getConstructors();
        val cs = class1.declaredConstructors
        for (constructor in cs) {
            print(constructor.name + "(")
            // 获取构造函数的参数列表--->得到的是参数列表的类类型
            val paramTypes = constructor.parameterTypes
            for (class2 in paramTypes) {
                print(class2.name + ",")
            }
            println(")")
        }
    }

}

