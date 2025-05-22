package com.example.news.presentation.navigation

sealed class ArticleListNavigationEvent {
    data class NavigateToDetail(val articleUrl: String) : ArticleListNavigationEvent()
}
