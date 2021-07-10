package com.hllbr.streamerbooksqliteprojectkotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val streamerNameList = ArrayList<String>()
        val streamerIdList = ArrayList<Int>()

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,streamerNameList)
        listView.adapter = arrayAdapter

        try{
            val database = this.openOrCreateDatabase("Streamer", Context.MODE_PRIVATE,null)

            val cursor = database.rawQuery("SELECT * FROM str",null)

            val ytbrIx = cursor.getColumnIndex("name")
            val idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()){
                streamerNameList.add(cursor.getString(ytbrIx))
                streamerIdList.add(cursor.getInt(idIx))

            }
            arrayAdapter.notifyDataSetChanged()

            cursor.close()
        }catch (e : Exception){
            println("MainActivity Problem : "+e.printStackTrace().toString())
        }
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,StreamerDetails::class.java)
            intent.putExtra("info","old")
            intent.putExtra("id",streamerIdList[position])
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inflater
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_streamer,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_streamer_item){
            val intent = Intent(this,StreamerDetails::class.java)
            intent.putExtra("info","new")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}