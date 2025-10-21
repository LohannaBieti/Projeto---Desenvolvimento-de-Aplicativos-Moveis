package com.example.biblioteca.data.model

// data/model/EBook.kt
class EBook(
    override val id: String = "",
    override val title: String = "",
    override val author: String = "",
    override val categories: List<String> = emptyList(),
    override val description: String = "",
    override val isRead: Boolean = false,
    override val isFavorite: Boolean = false,
    override val coverUrl: String? = null,
    val downloadUrl: String? = null,
    val fileSizeBytes: Long? = null
) : Book(id, title, author, categories, description, isRead, isFavorite, coverUrl)
