import com.example.biblioteca.data.model.*
// data/remote/BookFirestoreDTO.kt
data class BookFirestoreDTO(
    val title: String = "",
    val author: String = "",
    val categories: List<String> = emptyList(),
    val description: String = "",
    val isRead: Boolean = false,
    val isFavorite: Boolean = false,
    val coverUrl: String? = null,
    val type: String? = null, // "ebook" | "physical"
    val extra: Map<String, Any>? = null
) {
    fun toDomain(id: String): Book {
        return when (type) {
            "ebook" -> EBook(id, title, author, categories, description, isRead, isFavorite, coverUrl, extra?.get("downloadUrl") as? String, (extra?.get("fileSize") as? Long))
            "physical" -> PhysicalBook(id, title, author, categories, description, isRead, isFavorite, coverUrl, extra?.get("shelf") as? String)
            else -> Book(id, title, author, categories, description, isRead, isFavorite, coverUrl)
        }
    }

    companion object {
        fun fromDomain(book: Book): BookFirestoreDTO {
            val type = when (book) {
                is EBook -> "ebook"
                is PhysicalBook -> "physical"
                else -> "book"
            }
            val extra = when (book) {
                is EBook -> mapOf("downloadUrl" to (book.downloadUrl ?: ""), "fileSize" to (book.fileSizeBytes ?: 0L))
                is PhysicalBook -> mapOf("shelf" to (book.shelfLocation ?: ""))
                else -> null
            }
            return BookFirestoreDTO(
                title = book.title,
                author = book.author,
                categories = book.categories,
                description = book.description,
                isRead = book.isRead,
                isFavorite = book.isFavorite,
                coverUrl = book.coverUrl,
                type = type,
                extra = extra
            )
        }
    }
}
