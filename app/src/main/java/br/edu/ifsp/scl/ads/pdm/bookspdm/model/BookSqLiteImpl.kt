package br.edu.ifsp.scl.ads.pdm.bookspdm.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.edu.ifsp.scl.ads.pdm.bookspdm.R
import java.sql.SQLException

class BookSqLiteImpl(context: Context): BookDao {

    companion object {
        private const val BOOK_DATABASE_FILE = "book"
        private const val BOOK_TABLE = "book"
        private const val TITLE_COLLUMN = "title"
        private const val ISBN_COLLUMN = "isbn"
        private const val FIRST_AUTHOR_COLLMN = "first_author"
        private const val PUBLISHER_COLLUMN = "publisher"
        private const val EDITION_COLLUMN = "edition"
        private const val PAGES_COLLUMN = "pages"

        private const val CREATE_BOOK_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS $BOOK_TABLE (" +
                "$TITLE_COLLUMN TEXT NOT NULL, " +
                "$ISBN_COLLUMN TEXT NOT NULL PRIMARY KEY, " +
                "$FIRST_AUTHOR_COLLMN TEXT NOT NULL, " +
                "$PUBLISHER_COLLUMN TEXT NOT NULL, " +
                "$EDITION_COLLUMN INTEGER NOT NULL, " +
                "$PAGES_COLLUMN INTEGER NOT NULL );"


    }

    private val bookDatabase: SQLiteDatabase = context.openOrCreateDatabase(BOOK_DATABASE_FILE, MODE_PRIVATE, null)

    init {
        try {
            bookDatabase.execSQL(CREATE_BOOK_TABLE_STATEMENT)
        }
        catch (se: SQLException) {
            Log.e(context.getString(R.string.app_name), se.toString())
        }
    }

    override fun createBook(book: Book) = bookDatabase.insert(BOOK_TABLE, null, bookToContentValues(book))

    override fun retrieveBook(isbn: String): Book {
        val cursor = bookDatabase.query(
            true,
            BOOK_TABLE,
            null,
            "$ISBN_COLLUMN = ?",
            arrayOf(isbn),
            null,
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            cursorToBook(cursor)
        } else {
            Book()
        }
    }

    override fun retrieveBook(): MutableList<Book> {
        val bookList = mutableListOf<Book>()

        val cursor = bookDatabase.rawQuery("SELECT * FROM $BOOK_TABLE", null)
        while (cursor.moveToNext()) {
            bookList.add(cursorToBook(cursor))
        }
        return bookList
    }

    override fun updateBook(book: Book) =  bookDatabase.update(
            BOOK_TABLE,
            bookToContentValues(book),
            "$ISBN_COLLUMN = ?",
            arrayOf(book.isbn)
    )

    override fun deleteBook(isbn: String) = bookDatabase.delete(
        BOOK_TABLE,
        "$ISBN_COLLUMN = ?",
        arrayOf(isbn)
    )

    private fun bookToContentValues(book: Book) = ContentValues().apply {
        with(book) {
            put(TITLE_COLLUMN, title)
            put(ISBN_COLLUMN, isbn)
            put(FIRST_AUTHOR_COLLMN, firstAuthor)
            put(PUBLISHER_COLLUMN, publisher)
            put(EDITION_COLLUMN, edition)
            put(PAGES_COLLUMN, pages)
        }
    }

    private fun cursorToBook(cursor: Cursor) = with(cursor) {
        Book(
            getString(getColumnIndexOrThrow(TITLE_COLLUMN)),
            getString(getColumnIndexOrThrow(ISBN_COLLUMN)),
            getString(getColumnIndexOrThrow(FIRST_AUTHOR_COLLMN)),
            getString(getColumnIndexOrThrow(PUBLISHER_COLLUMN)),
            getInt(getColumnIndexOrThrow(EDITION_COLLUMN)),
            getInt(getColumnIndexOrThrow(PAGES_COLLUMN))
        )
    }
}