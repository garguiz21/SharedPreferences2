package com.example.sharedpreferences2

import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.sharedpreferences2.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private var tvText: TextView? = null
    private var etText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val allPref = prefs.all
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_settings_activity
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        tvText = findViewById(R.id.tvResult)
        etText = findViewById(R.id.etText)

        findViewById<Button>(R.id.bPreview).setOnClickListener {
            tvText?.text = etText?.text.toString()
            val size = allPref["text_size"].toString()
            if (size !== "null"){
                tvText?.textSize = size.toFloat()
            }

            tvText?.setTextColor(getColor(allPref["fg_color"].toString()))
            tvText?.setBackgroundColor(getColor(allPref["bg_color"].toString()))

            if(allPref["bold"].toString().toBoolean() && allPref["italic"].toString().toBoolean()){
                tvText?.setTypeface(tvText?.typeface,  Typeface.BOLD_ITALIC)
            } else if(allPref["bold"].toString().toBoolean()) {
                tvText?.setTypeface(tvText?.typeface,  Typeface.BOLD)
            }else if(allPref["italic"].toString().toBoolean()){
                tvText?.setTypeface(tvText?.typeface,  Typeface.ITALIC)
            } else {
                tvText?.setTypeface(tvText?.typeface,  Typeface.NORMAL)
            }

            println(allPref["alpha"].toString())

            val alpha = allPref["alpha"].toString()
            val rotation = allPref["rotation"].toString()
            if(alpha !== "null"){
                tvText?.alpha = alpha.toFloat() / 100F
            }
            if(rotation !== "null"){
                tvText?.rotation = rotation.toFloat()
            }
        }

    }

    private fun getColor(color: String): Int {
        return when (color) {
            "purple" -> {
                resources.getColor(R.color.purple)
            }
            "blue" -> {
                resources.getColor(R.color.blue)
            }
            "red" -> {
                resources.getColor(R.color.red)
            }
            "green" -> {
                resources.getColor(R.color.green)
            }
            "yellow" -> {
                resources.getColor(R.color.yellow)
            }
            "black" -> {
                resources.getColor(R.color.black)
            }
            else -> {
                resources.getColor(R.color.white)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}