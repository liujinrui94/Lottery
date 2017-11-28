package com.lottery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cn.jpush.android.api.JPushInterface

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("AAA","AAAAAAAAA"+JPushInterface.getRegistrationID(this));
    }
}
