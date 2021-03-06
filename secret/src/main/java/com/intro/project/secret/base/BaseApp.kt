package com.intro.project.secret.base

import android.content.Intent
import com.intro.hao.mytools.base.App
import com.intro.project.secret.model.ClockInfo
import com.intro.project.secret.model.NoteInfo
import com.intro.project.secret.timechange.TimeChangeService
import com.vicpin.krealmextensions.RealmConfigStore
import io.realm.Realm
import io.realm.RealmConfiguration


/**
 * Created by haozhang on 2018/1/15.
 */
class BaseApp : App() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initRealm()
        startTimeService()
    }


    fun startTimeService() {
        val service = Intent(this, TimeChangeService::class.java)
        startService(service)
    }

    /**
     * 数据库框架初始化
     */
    private fun initRealm() {
        Realm.init(this)
        val userAddressConfig = RealmConfiguration.Builder().name("secret").schemaVersion(1).deleteRealmIfMigrationNeeded().build()
        // clear previous data for fresh start
        Realm.deleteRealm(Realm.getDefaultConfiguration())
        Realm.deleteRealm(userAddressConfig)

        //Optional: if you want to specify your own realm configuration, you have two ways:

        //1. If you want to specify a configuration for a specific module, you can use:
        //        RealmConfigStore.initModule(NoteInfo::class.java, userAddressConfig)
        //2. You can specify any configuration per model with:
        RealmConfigStore.init(NoteInfo::class.java, userAddressConfig)
        RealmConfigStore.init(ClockInfo::class.java, userAddressConfig)
        //RealmConfigStore.init(Address::class.java, userAddressConfig)
    }

}