package com.example.noteappfirestore

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    var notesList: MutableLiveData<List<Note>> = MutableLiveData()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getAllNotes(): LiveData<List<Note>> {
        val tempList: ArrayList<Note> = arrayListOf()
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("notes")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            var note = Note(document.id, document["title"].toString(),document["note"].toString())
                            tempList.add(note)
                            Log.d("HELP", document.id + " => " + document.data["note"])
                        }
                        notesList.postValue(tempList)
                    } else {
                        Log.w("HELP", "Error getting documents.", task.exception)
                    }
                }
        }
        return notesList
    }


    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("notes")
                .add(note)
                .addOnSuccessListener { documentReference ->
                    getAllNotes()
                    Log.d(
                        "HELP", "DocumentSnapshot added with ID: " + documentReference.id
                    )
                }
                .addOnFailureListener { e -> Log.w("HELP", "Error adding document", e) }
        }
    }

    fun updateNote(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("notes").document(note.id).set(note).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getAllNotes()
                    Log.d("HELP", "DocumentSnapshot successfully updated!")
                } else {
                    Log.w("HELP", "Error getting documents.", task.exception)
                }
            }
        }
    }

    fun deleteNote(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("notes").document(note.id).delete().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getAllNotes()
                    Log.d("HELP", "DocumentSnapshot successfully updated!")
                } else {
                    Log.w("HELP", "Error getting documents.", task.exception)
                }
            }
        }
    }
}