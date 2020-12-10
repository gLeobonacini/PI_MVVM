package com.kotlin.marvelgeek.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.R
import kotlinx.android.synthetic.main.item_character.view.*


class FavoriteAdapter(val listener: ListenerOnClickFavorito): RecyclerView.Adapter<FavoriteAdapter.ItemFavorite>() {

    var listFavorite = ArrayList<Personagem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFavorite {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return ItemFavorite(itemView)
    }

    override fun getItemCount() = listFavorite.size

    override fun onBindViewHolder(holder: ItemFavorite, position: Int) {
        var favorito = listFavorite.get(position)
        holder.nome.text = favorito.nome
        holder.descricao.text = favorito.descricao
        holder.imagem.setImageResource(favorito.avatar)
    }

    fun addListFavorite(list: ArrayList<Personagem>){
        listFavorite.addAll(list)
        notifyDataSetChanged()
    }

    fun removeFavorite(position: Int){
        listFavorite.removeAt(position)
        notifyItemRemoved(position)
    }

    interface ListenerOnClickFavorito{
        fun onLongClickFavorito(position: Int)
        fun onClickFavorito(position: Int)
    }

    inner class ItemFavorite(itemView: View):RecyclerView.ViewHolder(itemView), View.OnLongClickListener, View.OnClickListener{
        val nome: TextView = itemView.tvNome
        val descricao: TextView = itemView.tvDescricao
        val imagem: ImageView = itemView.ivAvatar

        init{
            itemView.setOnLongClickListener(this)
            itemView.setOnClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onLongClickFavorito(position)
            }
            return true
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onClickFavorito(position)
            }
        }
    }
}