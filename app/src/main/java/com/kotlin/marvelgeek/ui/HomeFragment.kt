package com.kotlin.marvelgeek.ui

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.models.Character
import com.kotlin.marvelgeek.models.CharacterAdapter
import com.kotlin.marvelgeek.models.MainViewModel
import com.kotlin.marvelgeek.services.repository
import kotlinx.android.synthetic.main.bottom_menu.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(), CharacterAdapter.OnClickItemListener {

    val viewModel by viewModels<MainViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(repository) as T
            }
        }
    }

    private lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.show()
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        val inflater = inflater
        inflater.inflate(R.menu.searchbar_menu, menu)

        val manager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search_icon_menu)
        val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = getString(R.string.foundCharacter) + "..."
        searchView.setSearchableInfo(manager.getSearchableInfo(requireActivity().componentName))

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                Toast.makeText(activity, "Query: ${query}", Toast.LENGTH_LONG).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        adapter = CharacterAdapter(this)
        view.rvHome.adapter = adapter
        view.rvHome.setLayoutManager(LinearLayoutManager(activity))

        viewModel.listCharacter.observe(viewLifecycleOwner){
            adapter.addListCharacter(it)
        }

        //Atualizando os valores da lista
        var error = viewModel.getCharacter(18,0)
        if (error != null){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Server problem:")
            builder.setIcon(R.drawable.ic_info)
            builder.setMessage(error)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        view.abHome.setNavigationOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }

        view.fbQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
        }

        return view
    }

    override fun OnClickItem(position: Int) {
        val character = viewModel.listCharacter.value!!.get(position)
        val bundle = Bundle()
        bundle.putSerializable("character", character)
        arguments = bundle
        findNavController().navigate(R.id.action_homeFragment_to_characterFragment,bundle)
    }
}