package com.example.admobdemo

import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var mButton: Button
    lateinit var mMultipleChoiceButton: Button
    lateinit var mSingleChoiceButton: Button
    lateinit var txtView :TextView
    private var progressBar: ProgressBar? = null
    private lateinit var textView: TextView
    private var progressStatus = 0
    private val handler: Handler = Handler()
    lateinit var mDatePickerDialogButton:Button
    lateinit var mtxtDisplayDate:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mButton = findViewById(R.id.button)
        mMultipleChoiceButton = findViewById(R.id.MultiChoice)
        mSingleChoiceButton = findViewById(R.id.SingleChoice)
        txtView = findViewById(R.id.txtView)

        progressBar = findViewById(R.id.progressbar)
        textView = findViewById(R.id.textview)

        mDatePickerDialogButton = findViewById(R.id.DatePickerDialog)
        mtxtDisplayDate = findViewById(R.id.txtDisplayDate)

    }

    override fun onStart() {
        super.onStart()

        Thread {
            while (progressStatus < 100){
                progressStatus += 1
                handler.post{
                    progressBar!!.progress = progressStatus
                    textView.setText(progressStatus.toString() + "/"+ progressBar!!.max)
                }
            }
            try {
                Thread.sleep(400)
            }catch (ex:Exception){
                ex.printStackTrace()
            }
        }.start()

        var colorsItem:Array<CharSequence> = arrayOf("Red","Orange","Green","Blue")
        val icount = BooleanArray(colorsItem.size)
        var selectedColors = ArrayList<Int>()
       // var checkedItem:Array<Boolean> = Array(colorsItem.size)

        mButton.setOnClickListener{

            var dialog = AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Are You sure you want to delete this item")
                .setPositiveButton("Delete",object:DialogInterface.OnClickListener{
                    override fun onClick(dialogInterface: DialogInterface?, which: Int) {
                        Toast.makeText(this@MainActivity,"Item Deleted Successfully.",Toast.LENGTH_LONG).show()
                    }

                })
                .setNegativeButton("Cancel",object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Toast.makeText(this@MainActivity,"Cancel the delete operation.",Toast.LENGTH_LONG).show()
                    }
                })
                .create()

            dialog.show()
        }

        var value:String = ""

        mMultipleChoiceButton.setOnClickListener{

            var dialog = AlertDialog.Builder(this)
                .setTitle("Choose Colors")
                .setMultiChoiceItems(colorsItem,icount,object :DialogInterface.OnMultiChoiceClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int, p2: Boolean) {

                        if(p2){
                            selectedColors.add(p1)
                        }else{
                            selectedColors.remove(p1)
                        }

                    }

                })
                .setCancelable(false)
                .setPositiveButton("Done",object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        for (i in 0..selectedColors.size-1){
                            value = value + "\n" + colorsItem[selectedColors.get(i)]
                            Toast.makeText(this@MainActivity,value,Toast.LENGTH_LONG).show()
                        }
                    }

                })
                .create()

            dialog.show()
        }

        mSingleChoiceButton.setOnClickListener {
            val listItems = arrayOf("Item 1", "Item 2", "Item 3")
            val mBuilder = AlertDialog.Builder(this@MainActivity)
            mBuilder.setTitle("Choose an item")
            mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->
                txtView.text = listItems[i]
                dialogInterface.dismiss()
            }
            // Set the neutral/cancel button click listener
            mBuilder.setNeutralButton("Cancel") { dialog, which ->
                // Do something when click the neutral button
                dialog.cancel()
            }

            val mDialog = mBuilder.create()
            mDialog.show()
        }

        mDatePickerDialogButton.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            var dialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener {view,year,month,dayOfMonth ->

                mtxtDisplayDate.setText("You have selected:$dayOfMonth-"+(month+1)+"-$year")
            },year,month,day)
            dialog.show()
        }
    }
}