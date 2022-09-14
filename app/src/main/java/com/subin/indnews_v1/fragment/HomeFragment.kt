package com.subin.indnews_v1.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.subin.indnews_v1.Constants.PrefConstants
import com.subin.indnews_v1.NavItem
import com.subin.indnews_v1.SharedPreference.IndNewsSharedPref
import com.subin.indnews_v1.Utility.theme.AppTheme
import com.subin.indnews_v1.designs.*
import com.subin.indnews_v1.model.response.Article
import com.subin.indnews_v1.model.response.LiveNewsArticle
import com.subin.indnews_v1.model.response.TechNewsArticle
import com.subin.indnews_v1.ui.theme.IndNews_V1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireActivity()).apply {
            setContent {
                AppTheme(darkTheme = isSystemInDarkTheme()) {
                    ProvideWindowInsets() {
                        HomeScreen(requireActivity())
                    }

                }
            }
        }
    }
}


@Composable
fun HomeScreen(activity: Activity){
    val pref=IndNewsSharedPref(LocalContext.current)
    val navController = rememberNavController()
    var startDest:String? =null
    startDest = if (pref.getIsSetCategoryItems(PrefConstants.IS_SET_CATEGORY_ITEMS,false)){
        NavItem.HomeScreen.route
    }else{
        NavItem.CategoryScreen.route
    }
    NavHost(navController = navController,
        startDestination =startDest, builder = {
        composable(
            NavItem.CategoryScreen.route,
            content =
            {
                CategoryScreen(){
                    pref.setIsSetCategoryItems(PrefConstants.IS_SET_CATEGORY_ITEMS,true)
                    navController.navigate(NavItem.HomeScreen.route)
                }
            })

         composable(
            NavItem.HomeScreen.route,
            content =
            {
                HomeScreenDesign(navController)
            })
            composable(
                NavItem.ProfileScreen.route,
                content =
                {
                    ProfileScreenDesign (navController,activity)
                })


          composable(
              route = NavItem.ArticleDetailsScreen.route){
              val result=navController.previousBackStackEntry?.savedStateHandle?.get<Article>("Article")
              result?.let { article -> ArticleDetailsScreen(navController, article) }
          }

          composable(
            route = NavItem.TechNewsDetailsScreen.route){
            val result=navController.previousBackStackEntry?.savedStateHandle?.get<TechNewsArticle>("TechNews")
            result?.let { techArticle -> TechNewsDetailsScreen( navController,techArticle) }
        }
          composable(
              route = NavItem.LiveNewsDetailsScreen.route){
               val result=navController.previousBackStackEntry?.savedStateHandle?.get<LiveNewsArticle>("LiveNews")
               result?.let { liveArticle -> LiveNewsDetailsScreen( navController,liveArticle) }
            }
    })


}



