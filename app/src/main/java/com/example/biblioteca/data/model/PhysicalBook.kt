package com.example.biblioteca.data.model

// data/model/PhysicalBook.kt
class PhysicalBook(
    override val id: String = "",
    override val title: String = "",
    override val author: String = "",
    override val categories: List<String> = emptyList(),
    override val description: String = "",
    override val isRead: Boolean = false,
    override val isFavorite: Boolean = false,
    override val coverUrl: String? = null,
    val shelfLocation: String? = null
) : Book(id, title, author, categories, description, isRead, isFavorite, coverUrl)
