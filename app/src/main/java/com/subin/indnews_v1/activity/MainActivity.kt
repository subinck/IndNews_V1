package com.subin.indnews_v1.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.FragmentManager
import com.subin.indnews_v1.R
import com.subin.indnews_v1.SharedPreference.AppPreference
import com.subin.indnews_v1.fragment.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appPreference: AppPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        WindowCompat.setDecorFitsSystemWindows(window, true)
      val isLogin=appPreference.isLogin

        if (isLogin){
            moveToHomeActivity()
        }else{
            moveToLoginPage()
        }
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)){view,insets->

        val bottom=insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
        view.updatePadding(bottom=bottom)
        insets

    }
    }

    private fun moveToLoginPage(){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, LoginFragment())
        transaction.commit()
    }
    private fun moveToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
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
            else -> {
                super.onBackPressed()
            }
        }
    }
}


