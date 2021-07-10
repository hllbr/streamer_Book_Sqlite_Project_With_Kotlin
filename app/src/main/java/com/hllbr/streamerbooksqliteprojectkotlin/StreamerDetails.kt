package com.hllbr.streamerbooksqliteprojectkotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_streamer_details.*

class StreamerDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_streamer_details)
        val intent = intent
        val info = intent.getStringExtra("info")
        if (info.equals("new")){
            streamerNameText.setText("")
            streamTimeText.setText("")
            button.visibility = View.VISIBLE
        }else{
            button.visibility = View.INVISIBLE
            val selectedId = intent.getIntExtra("id",1)

            try {
                val database = this.openOrCreateDatabase("Streamer", MODE_PRIVATE,null)
                val cursor = database.rawQuery("SELECT * FROM str WHERE id = ?", arrayOf(selectedId.toString()))
                val namerIx =  cursor.getColumnIndex("name")
                val timerIx = cursor.getColumnIndex("tme")

                while (cursor.moveToNext()){
                    streamerNameText.setText(cursor.getString(namerIx))
                    streamTimeText.setText(cursor.getString(timerIx))
                }
                cursor.close()
            }catch (e : Exception){
                println("DetailActivity Failed : (Problem) " +e.printStackTrace().toString())
            }
        }
    }
    fun save( view:View) {
        val streamerName = streamerNameText.text.toString()
        val streamTime = streamTimeText.text.toString()
        try{
            val database = this.openOrCreateDatabase("Streamer", Context.MODE_PRIVATE,null)
            database.execSQL("CREATE TABLE IF NOT EXISTS str(id INTEGER PRIMARY KEY ,name VARCHAR,tme VARCHAR)")
            val sqlString = "INSERT INTO str(name,tme) VALUES(?,?)"
            val statement = database.compileStatement(sqlString)
            statement.bindString(1,streamerName)
            statement.bindString(2,streamTime)
            statement.execute()
        }catch (e : Exception){
            println("DetailActivity Failed = "+e.printStackTrace().toString())
        }
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }}
