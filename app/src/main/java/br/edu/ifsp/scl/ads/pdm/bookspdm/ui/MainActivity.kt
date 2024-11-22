package br.edu.ifsp.scl.ads.pdm.bookspdm.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.bookspdm.R
import br.edu.ifsp.scl.ads.pdm.bookspdm.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.pdm.bookspdm.model.Book

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //Data source
    private val bookList: MutableList<Book> = mutableListOf()

    //Adapter
    private val bookAdapter: ArrayAdapter<String> by lazy {
        //val bookTitleList: MutableList<String> = mutableListOf()
        //bookList.forEach{ book -> bookTitleList.add(book.title)}
        //ArrayAdapter(this, android.R.layout.simple_list_item_1, bookTitleList)

        ArrayAdapter(this, android.R.layout.simple_list_item_1, bookList.run{
            val bookTitleList: MutableList<String> = mutableListOf()
            this.forEach { bookTitleList.add(it.title)}
            bookTitleList
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        amb.toolbarIn.toolbar.let {
            it.subtitle = getString(R.string.book_list)
            setSupportActionBar(it)
        }

        fillBookList()

        amb.booksLv.adapter = bookAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        // Abrir tela para adicionar novo livro
        R.id.addBookMi -> {
            true
        }
        else -> {
            false
        }
    }


    private fun fillBookList() {
        for (index in 1..50) {
            bookList.add (
                Book(
                    "Title $index",
                    "ISBN $index",
                    "Author $index",
                    "Publisher $index",
                    index,
                    index
                )
            )
        }
    }
}