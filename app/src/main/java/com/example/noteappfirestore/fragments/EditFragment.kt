package com.example.noteappfirestore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.noteappfirestore.Note
import com.example.noteappfirestore.NotesViewModel
import com.example.noteappfirestore.R


class EditFragment : Fragment() {

    private val notesViewModel by lazy { ViewModelProvider(this).get(NotesViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit, container, false)

        //views
        val titleEditText = view.findViewById<TextView>(R.id.edt_titleDialog)
        val noteEditText = view.findViewById<TextView>(R.id.edt_noteDialog)
        val saveButton = view.findViewById<Button>(R.id.btn_saveDialog)

        val id = requireArguments().getString("note.id")
        val title = requireArguments().getString("note.title")
        val note = requireArguments().getString("note.note")

        titleEditText.text = title
        noteEditText.text = note


        saveButton.setOnClickListener {
            val updatedTitle = titleEditText.text.toString()
            val updatedNote = noteEditText.text.toString()
            if (updatedTitle.trim().isNotEmpty() && updatedNote.trim().isNotEmpty()) {
                if (updatedTitle != title || updatedNote != note) {
                    //update note
                    notesViewModel.updateNote(Note(id!!, updatedTitle, updatedNote))
                    Toast.makeText(activity, "Updated Successfully!", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(R.id.action_editFragment_to_notesFragment)

                }
            }
        }
        return view
    }

}