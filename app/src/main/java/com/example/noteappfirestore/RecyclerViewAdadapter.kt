package com.example.noteappfirestore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_row.view.*

class NotesAdapter(private val context: MainActivity): RecyclerView.Adapter<NotesAdapter.UserViewHolder>() {

    private var notesList = emptyList<Note>()

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTextView: TextView = itemView.tv_noteCardnote
        val titleTextView: TextView = itemView.tv_noteCardTitle
        val editButton: ImageButton = itemView.imgBtn_noteCardEdit
        val deleteButton: ImageButton = itemView.imgBtn_noteCardDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_row,
            parent,
            false
        )
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.noteTextView.text = notesList[position].note
        holder.titleTextView.text = notesList[position].title

        holder.editButton.setOnClickListener {
            context.showEditDialog(notesList[position])
        }
        holder.deleteButton.setOnClickListener {
            context.showDeleteDialog(notesList[position])
        }
    }

    fun setData(notes: List<Note>){
        this.notesList = notes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notesList.size

}