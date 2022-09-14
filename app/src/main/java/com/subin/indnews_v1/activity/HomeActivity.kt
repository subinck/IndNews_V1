package com.subin.indnews_v1.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.FragmentManager
import com.subin.indnews_v1.R
import com.subin.indnews_v1.fragment.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private var pressedTime :Long=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        moveToHomePage()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)){ view, insets->

            val bottom=insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom=bottom)
            insets

        }
    }

    private fun moveToHomePage(){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.addToBackStack("home")
        transaction.replace(R.id.main_container, HomeFragment())
        transaction.commit()
    }
    @Override
    override fun onBackPressed() {
        when {
            supportFragmentManager.backStackEntryCount == 2 -> {
                val fragmentManager: FragmentManager = supportFragmentManager
                fragmentManager.popBackStack(
                    fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).id,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }

            supportFragmentManager.backStackEntryCount > 1 -> {
                val fragmentManager: FragmentManager = supportFragmentManager
                fragmentManager.popBackStack(
                    fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - supportFragmentManager.backStackEntryCount).id,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
            supportFragmentManager.backStackEntryCount==1 ->{
              finishAffinity()

            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}