package com.subin.indnews_v1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.subin.indnews_v1.NavItem
import com.subin.indnews_v1.R
import com.subin.indnews_v1.Utility.theme.AppTheme
import com.subin.indnews_v1.designs.LoginScreenDesign
import com.subin.indnews_v1.designs.OtpScreenDesign
import com.subin.indnews_v1.designs.RegisterScreenDesign
import com.subin.indnews_v1.ui.theme.IndNews_V1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireActivity()).apply {
            setContent {
                AppTheme(darkTheme = isSystemInDarkTheme()) {
                    ProvideWindowInsets() {
                            LoginScreen(requireActivity())
                    }
                }

            }
        }
    }
}
@Composable
fun LoginScreen(activity: FragmentActivity){
    val navController = rememberNavController()
    val context= LocalContext.current

    NavHost(navController = navController, startDestination = NavItem.LoginScreen.route, builder = {
        composable(NavItem.LoginScreen.route,
            content =
            { LoginScreenDesign( navController = navController,activity) })

        composable( NavItem.RegisterScreen.route,
            content = { RegisterScreenDesign( navController = navController, activity =activity )})

        composable(
              route = NavItem.OTPScreen.route){
              val result=navController.previousBackStackEntry?.savedStateHandle?.get<String>("ScreenType")
               result?.let { screenType->
              OtpScreenDesign(navController = navController, activity = activity, screenType = screenType)
          }
            }
    })

}
