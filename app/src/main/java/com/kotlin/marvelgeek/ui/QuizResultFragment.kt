package com.kotlin.marvelgeek.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kotlin.marvelgeek.R
import kotlinx.android.synthetic.main.fragment_quiz_result.*
import kotlinx.android.synthetic.main.fragment_quiz_result.view.*

class QuizResultFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quiz_result, container, false)

        view.quiResFraBtnNewGame.setOnClickListener {
            findNavController().navigate(R.id.action_quizResultFragment_to_quizFragment)
        }

        view.quiResFraBtnBack.setOnClickListener {
            findNavController().navigate(R.id.action_quizResultFragment_to_homeFragment)
        }

        return view
    }
}