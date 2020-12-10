package com.kotlin.marvelgeek.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterAdapter(val listener: OnClickItemListener) : RecyclerView.Adapter<CharacterAdapter.ItemCharacter>() {

    var listCharacter = ArrayList<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCharacter {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_character, parent, false)
        return ItemCharacter(itemView)
    }

    override fun onBindViewHolder(holder: ItemCharacter, position: Int) {
        val character = listCharacter[position]
        holder.nome.text = character.name
        holder.descricao.text = character.description
        Picasso.get().load("${character.thumbnail.path}.${character.thumbnail.extension}")
            .fit()
            .transform(CropCircleTransformation())
            .into(holder.imagem)
    }

    override fun getItemCount() = listCharacter.size

    fun addListCharacter(list: ArrayList<Character>){
        listCharacter.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClickItemListener {
        fun OnClickItem(position: Int)
    }

    inner class ItemCharacter(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val nome: TextView = itemView.tvNome
        val descricao: TextView = itemView.tvDescricao
        val imagem: ImageView = itemView.ivAvatar

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                listener.OnClickItem(position)
        }
    }
}