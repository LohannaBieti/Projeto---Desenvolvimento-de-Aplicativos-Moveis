import com.example.biblioteca.data.model.Book
import kotlinx.coroutines.flow.Flow

// data/repository/BookRepository.kt
interface BookRepository {
    fun booksFlow(): Flow<List<Book>>
    suspend fun getBook(id: String): Book?
    suspend fun saveBook(book: Book)
    suspend fun toggleRead(bookId: String, read: Boolean)
    suspend fun toggleFavorite(bookId: String, favorite: Boolean)
    suspend fun search(query: String): List<Book>
    suspend fun filterByCategory(category: String): List<Book>
}
