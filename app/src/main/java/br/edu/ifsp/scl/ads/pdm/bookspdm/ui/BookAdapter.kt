package br.edu.ifsp.scl.ads.pdm.bookspdm.ui

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.ads.pdm.bookspdm.R
import br.edu.ifsp.scl.ads.pdm.bookspdm.databinding.TileBookBinding
import br.edu.ifsp.scl.ads.pdm.bookspdm.model.Book

class BookAdapter(
    context: Context,
    private val bookList: MutableList<Book>): ArrayAdapter<Book> (context, R.layout.tile_book, bookList) {

    private data class BookTileHolder(
        val titleTv: TextView,
        val firstAuthorTv: TextView,
        val editionTv: TextView
    )

    // pegar o livro que vai ser usado para preencher a celula
    override fun getView(position: Int, convertView: View?, parent:ViewGroup): View {
        lateinit var tbb: TileBookBinding

        val book = bookList[position]

        //verificando se a celula foi inflada ou não, se nãp foi, infla uma nova celula
        var bookTile = convertView
        if(bookTile == null) {
            //se não foi, infla

            tbb = TileBookBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )

            bookTile = tbb.root

            val newBookTileHolder = BookTileHolder(tbb.titleTv, tbb.firstAuthorTv, tbb.editionTv)

            bookTile.tag = newBookTileHolder
        }

        val holder = bookTile?.tag as BookTileHolder
        holder.let {
            it.titleTv.text =book.title
            it.firstAuthorTv.text = book.firstAuthor
            it.editionTv.text = book.edition.toString()
        }

        return bookTile!!
    }

}