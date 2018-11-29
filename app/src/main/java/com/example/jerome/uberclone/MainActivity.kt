package com.example.jerome.uberclone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Switch
import com.parse.ParseAnonymousUtils
import com.parse.ParseUser

class MainActivity : AppCompatActivity() {

    fun getStart(v : View){
        var switch = findViewById<Switch>(R.id.switch1)
        Log.i("Switch value", switch.isChecked.toString())
        var usertype = "Rider"
        if ( switch.isChecked){
            usertype = "Driver"
        }
        ParseUser.getCurrentUser().put("Rider or Driver",usertype)
        Log.i("Info","Hello " + usertype)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.hide()

        if( ParseUser.getCurrentUser() == null){
            ParseAnonymousUtils.logIn { user, e ->
                if (e == null){
                    Log.i("Info","Log in successful")
                }else{
                    Log.i("Info",e.message)
                }
            }
        }else{
            if (ParseUser.getCurrentUser().get("Rider or Driver") != null){
                Log.i("Info",ParseUser.getCurrentUser().get("Rider or Driver").toString())
            }
        }
    }
}
