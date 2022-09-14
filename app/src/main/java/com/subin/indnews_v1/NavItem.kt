package com.subin.indnews_v1

sealed class NavItem(val route:String) {

    object LoginScreen : NavItem("login_screen")
    object RegisterScreen : NavItem("register_screen")
    object HomeScreen : NavItem("home_screen")
    object ArticleDetailsScreen : NavItem("article_details_screen")
    object TechNewsDetailsScreen : NavItem("tech_news_details_screen")
    object LiveNewsDetailsScreen : NavItem("live_news_details_screen")
    object CategoryScreen : NavItem("category_screen")
    object ProfileScreen : NavItem("profile_screen")
    object OTPScreen : NavItem("otp_screen")
}

