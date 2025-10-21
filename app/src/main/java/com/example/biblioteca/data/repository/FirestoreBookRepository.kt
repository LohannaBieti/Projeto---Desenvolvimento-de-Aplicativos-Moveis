// data/repository/FirestoreBookRepository.kt
class FirestoreBookRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : BookRepository {

    private val collection = db.collection("books")

    override fun booksFlow(): Flow<List<Book>> = callbackFlow {
        val listener = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val list = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(BookFirestoreDTO::class.java)?.toDomain(doc.id)
            } ?: emptyList()
            trySend(list)
        }
        awaitClose { listener.remove() }
    }

    override suspend fun getBook(id: String): Book? {
        val doc = collection.document(id).get().await()
        return if (doc.exists()) doc.toObject(BookFirestoreDTO::class.java)?.toDomain(doc.id) else null
    }

    override suspend fun saveBook(book: Book) {
        val id = if (book.id.isBlank()) collection.document().id else book.id
        collection.document(id).set(BookFirestoreDTO.fromDomain(book)).await()
    }

    override suspend fun toggleRead(bookId: String, read: Boolean) {
        collection.document(bookId).update("isRead", read).await()
    }

    override suspend fun toggleFavorite(bookId: String, favorite: Boolean) {
        collection.document(bookId).update("isFavorite", favorite).await()
    }

    override suspend fun search(query: String): List<Book> {
        // Simples: busca por título (case-insensitive requer index/transform)
        val snapshot = collection
            .whereGreaterThanOrEqualTo("title", query)
            .whereLessThanOrEqualTo("title", query + '\uf8ff')
            .get().await()
        return snapshot.documents.mapNotNull { it.toObject(BookFirestoreDTO::class.java)?.toDomain(it.id) }
    }

    override suspend fun filterByCategory(category: String): List<Book> {
        val snapshot = collection.whereArrayContains("categories", category).get().await()
        return snapshot.documents.mapNotNull { it.toObject(BookFirestoreDTO::class.java)?.toDomain(it.id) }
    }
}
