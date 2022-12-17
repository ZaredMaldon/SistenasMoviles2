package com.example.sistemasmoviles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.Controller.ChatController
import com.example.sistemasmoviles.HTTP.RestEngine
import com.example.sistemasmoviles.HTTP.Service
import com.example.sistemasmoviles.Model.Respuesta
import com.example.sistemasmoviles.adapter.PostAdapter
import com.example.sistemasmoviles.databinding.ActivityBuscarBinding
import retrofit2.Call
import retrofit2.Callback

class FragmentBuscar : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar, container, false)
    }


}