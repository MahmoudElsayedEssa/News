package com.example.news.presentation.navigation


sealed class ArticleListNavigationEvent {
    data class NavigateToDetail(val articleJson: String) : ArticleListNavigationEvent()
}