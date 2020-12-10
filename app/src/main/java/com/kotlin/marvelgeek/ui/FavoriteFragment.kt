package com.kotlin.marvelgeek.ui

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.models.CharacterAdapter
import com.kotlin.marvelgeek.models.FavoriteAdapter
import com.kotlin.marvelgeek.models.MainViewModel
import com.kotlin.marvelgeek.services.repository
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment(),
        FavoriteAdapter.ListenerOnClickFavorito {

    val viewModel by viewModels<MainViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(repository) as T
            }
        }
    }
    private lateinit var adapter: FavoriteAdapter

    override fun onDestroyView() {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.app_name)
        super.onDestroyView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.tbFavoriteTitle)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FavoriteAdapter(this)
        rvFavorite.adapter = adapter
        rvFavorite.layoutManager = LinearLayoutManager(context)
        rvFavorite.setHasFixedSize(false)

        viewModel.listFavorite.observe(viewLifecycleOwner){
            adapter.addListFavorite(it)
        }

        //Atualizando os valores da lista
        var error: String? = viewModel.getFavorite()
        if (error != null){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Database problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(error)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    companion object{
        fun newInstance() = FavoriteFragment()
    }

    override fun onLongClickFavorito(position: Int) {
        removeFavorite(position)
    }

    override fun onClickFavorito(position: Int) {
//        val character = viewModel.listCharacter.value!!.get(position)
//        val bundle = Bundle()
//        bundle.putSerializable("character", character)
//        arguments = bundle
//        findNavController().navigate(R.id.action_favoriteFragment_to_characterFragment)
    }

    fun removeFavorite(position: Int) {
        val name = viewModel.listFavorite.value!!.get(position).nome

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Excluir")
        builder.setIcon(R.drawable.ic_delete)
        builder.setMessage("Você deseja excluir $name da sua lista de favoritos?")
        builder.setPositiveButton("Sim") { dialog, which ->
            viewModel.removeFavorite(position)
            adapter.listFavorite.removeAt(position)
            adapter.notifyDataSetChanged()
        }
        builder.setNegativeButton("Não") { dialog, which ->
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}