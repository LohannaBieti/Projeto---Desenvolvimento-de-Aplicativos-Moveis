package com.example.biblioteca.data.model

// data/model/Book.kt
open class Book(
    open val id: String = "",
    open val title: String = "",
    open val author: String = "",
    open val categories: List<String> = emptyList(),
    open val description: String = "",
    open val isRead: Boolean = false,
    open val isFavorite: Boolean = false,
    open val coverUrl: String? = null
)
