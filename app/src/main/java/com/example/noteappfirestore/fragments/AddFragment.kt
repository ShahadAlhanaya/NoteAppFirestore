package com.example.noteappfirestore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.noteappfirestore.Note
import com.example.noteappfirestore.NotesViewModel
import com.example.noteappfirestore.R

class AddFragment : Fragment() {

    private val notesViewModel by lazy { ViewModelProvider(this).get(NotesViewModel::class.java) }

    lateinit var titleEditText: EditText
    lateinit var noteEditText: EditText
    lateinit var addButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        titleEditText = view.findViewById(R.id.edt_title)
        noteEditText = view.findViewById(R.id.edt_note)
        addButton = view.findViewById(R.id.btn_add)

        addButton.setOnClickListener {

            val title = titleEditText.text.toString()
            val note = noteEditText.text.toString()

            if (note.trim().isNotEmpty() && title.trim().isNotEmpty()) {
                //insert note
                notesViewModel.addNote(Note("", note, title))
                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.action_addFragment_to_notesFragment)
            } else {
                Toast.makeText(context, "please enter your note", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        return view
    }

}