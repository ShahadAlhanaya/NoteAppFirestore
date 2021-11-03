package com.example.noteappfirestore.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappfirestore.Note
import com.example.noteappfirestore.NotesAdapter
import com.example.noteappfirestore.NotesViewModel
import com.example.noteappfirestore.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: NotesAdapter
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var fragmentView: View

    private val notesViewModel by lazy { ViewModelProvider(this).get(NotesViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_notes, container, false)

        //initialize recyclerView
        recyclerView = fragmentView.findViewById(R.id.recyclerView)
        adapter = NotesAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        notesViewModel.getAllNotes().observe(viewLifecycleOwner, { notes ->
            adapter.setData(notes)
        })

//        navController =  Navigation.findNavController(view)

        floatingActionButton = fragmentView.findViewById(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            Navigation.findNavController(fragmentView)
                .navigate(R.id.action_notesFragment_to_addFragment)
        }

        return fragmentView
    }

    fun editNote(note: Note) {

        val bundle = Bundle()
        bundle!!.putString("note.id", note.id)
        bundle!!.putString("note.title", note.title)
        bundle!!.putString("note.note", note.note)

        Navigation.findNavController(fragmentView)
            .navigate(R.id.action_notesFragment_to_editFragment, bundle)
    }

    fun showDeleteDialog(note: Note) {

        val id = note.id
        val title = note.title
        val note = note.note

        notesViewModel.deleteNote(Note(id, title, note))
        Toast.makeText(activity, "Deleted Successfully!", Toast.LENGTH_SHORT).show()

    }

}

