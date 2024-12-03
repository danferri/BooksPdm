package br.edu.ifsp.scl.ads.pdm.bookspdm.controller

import br.edu.ifsp.scl.ads.pdm.bookspdm.model.Book
import br.edu.ifsp.scl.ads.pdm.bookspdm.model.BookDao
import br.edu.ifsp.scl.ads.pdm.bookspdm.model.BookSqLiteImpl
import br.edu.ifsp.scl.ads.pdm.bookspdm.ui.MainActivity

class MainController(mainActivity: MainActivity) {
    private val bookDao: BookDao = BookSqLiteImpl(mainActivity)

    fun insertBook(book: Book) = bookDao.createBook(book)
    fun getBook(isbn: String) = bookDao.retrieveBook(isbn)
    fun getBook() = bookDao.retrieveBook()
    fun modifyBook(book: Book) = bookDao.updateBook(book)
    fun removeBook(isbn: String) = bookDao.deleteBook(isbn)
}
