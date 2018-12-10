package com.example.jerome.uberclone

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Switch
import com.parse.ParseAnonymousUtils
import com.parse.ParseUser

class MainActivity : AppCompatActivity() {

    var usertype = "Rider"

    fun redirectActivity (){
        if (usertype == "Rider"){
            var intent = Intent(applicationContext,RiderActivity :: class.java)
            startActivity(intent)
        }else{
            var intent = Intent(applicationContext,DriverActivity :: class.java)
            startActivity(intent)
        }
    }

    fun getStart(v : View){
        var switch = findViewById<Switch>(R.id.switch1)
        Log.i("Switch value", switch.isChecked.toString())
        if ( switch.isChecked){
            usertype = "Driver"
        }
        ParseUser.getCurrentUser().put("Rider or Driver",usertype)
        ParseUser.getCurrentUser().saveInBackground()
        redirectActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ParseUser.getCurrentUser() == null){
            Log.i("Info","no user now")
            ParseAnonymousUtils.logIn { user, e ->
                if (e == null){
                    Log.i("Info","Log in successful")
                }else{
                    Log.i("Info",e.message)
                }
            }
        }else{
            if ( ParseUser.getCurrentUser().get("Rider or Driver") != null){
                redirectActivity()
            }
        }
    }
}
