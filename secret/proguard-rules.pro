# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

## 代码混淆压缩比，在0和7之间，默认为5，一般不需要改
#-optimizationpasses 5
#
## 混淆时不使用大小写混合，混淆后的类名为小写
#-dontusemixedcaseclassnames
#
## 指定不去忽略非公共的库的类
#-dontskipnonpubliclibraryclasses
#
## 指定不去忽略非公共的库的类的成员
#-dontskipnonpubliclibraryclassmembers
#
## 不做预校验，preverify是proguard的4个步骤之一
## Android不需要preverify，去掉这一步可加快混淆速度
#-dontpreverify
#
## 有了verbose这句话，混淆后就会生成映射文件
## 包含有类名->混淆后类名的映射关系
## 然后使用printmapping指定映射文件的名称
#-verbose
#-printmapping proguardMapping.txt
#
## 指定混淆时采用的算法，后面的参数是一个过滤器
## 这个过滤器是谷歌推荐的算法，一般不改变
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#
## 保护代码中的Annotation不被混淆，这在JSON实体映射时非常重要，比如fastJson
#-keepattributes *Annotation*
#
## 避免混淆泛型，这在JSON实体映射时非常重要，比如fastJson
#-keepattributes Signature
#
##抛出异常时保留代码行号，在异常分析中可以方便定位
#-keepattributes SourceFile,LineNumberTable
#
#-dontskipnonpubliclibraryclasses
##用于告诉ProGuard，不要跳过对非公开类的处理。默认情况下是跳过的，因为程序中不会引用它们，有些情况下人们编写的代码与类库中的类在同一个包下，并且对包中内容加以引用，此时需要加入此条声明。
#
#-dontusemixedcaseclassnames
##这个是给Microsoft Windows用户的，因为ProGuard假定使用的操作系统是能区分两个只是大小写不同的文件名，但是Microsoft Windows不是这样的操作系统，所以必须为ProGuard指定-dontusemixedcaseclassnames选项
#
## 保留所有的本地native方法不被混淆
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
## 保留了继承自Activity、Application这些类的子类
## 因为这些子类，都有可能被外部调用
## 比如说，第一行就保证了所有Activity的子类不要被混淆
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class * extends android.view.View
#-keep public class com.android.vending.licensing.ILicensingService
#
## 如果有引用android-support-v4.jar包，可以添加下面这行
#-keep public class com.xxxx.app.ui.fragment.** {*;}
#
## 保留在Activity中的方法参数是view的方法，
## 从而我们在layout里面编写onClick就不会被影响
#-keepclassmembers class * extends android.app.Activity {
#    public void *(android.view.View);
#}
#
## 枚举类不能被混淆
#-keepclassmembers enum * {
#public static **[] values();
#public static ** valueOf(java.lang.String);
#}
#
## 保留自定义控件（继承自View）不被混淆
#-keep public class * extends android.view.View {
#    *** get*();
#    void set*(***);
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#
## 保留Parcelable序列化的类不被混淆
#-keep class * implements android.os.Parcelable {
#    public static final android.os.Parcelable$Creator *;
#}
#
## 保留Serializable序列化的类不被混淆
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#
## 对于R（资源）下的所有类及其方法，都不能被混淆
#-keep class **.R$* {
#    *;
#}
#
## 对于带有回调函数onXXEvent的，不能被混淆
#-keepclassmembers class * {
#    void *(**On*Event);
#}
#
##-------------------------------以上未基本混淆部分----------------------------------

#-------------------------------------------定制化区域----------------------------------------------

#---------------------------------1.实体类---------------------------------
-keep class com.intro.project.secret.modle.**{*;}



#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------

-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature-keepattributes Exceptions
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod



#log4j  混淆直接加入的jar包
#-libraryjars log4j-1.2.17.jar
#-dontwarn org.apache.log4j.**
#-keep class  org.apache.log4j.** { *;}
#参考地址 https://www.jianshu.com/p/f3455ecaa56e

#-------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------
-keepclassmembers com.intro.hao.mytools.customview.richeditor {
    <methods>;
}


#-------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------



#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------

#-------------------------------------------基本不用动区域--------------------------------------------


#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------
