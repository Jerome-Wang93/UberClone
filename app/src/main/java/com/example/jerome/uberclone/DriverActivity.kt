package com.example.jerome.uberclone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class DriverActivity : AppCompatActivity() {

    var requests  =  arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver)

        var requestList = findViewById<ListView>(R.id.request_list)
        requests.add("test")
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,requests)
        requestList.setAdapter(adapter)

    }
}
