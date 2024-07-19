package com.sahilpvns.roomnoteapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sahilpvns.roomnoteapp.databinding.NoteItemBinding

class NoteAdapter(private var notes: List<Note>, private val mListener: OnItemClickListener) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = notes[position]
        holder.bind(currentNote, mListener)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NoteHolder(private var binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentNote: Note, mListener: OnItemClickListener) {
            binding.tvTitle.text = currentNote.title
            binding.tvContent.text = currentNote.content
            binding.root.setOnClickListener { mListener.onItemClick(currentNote) }
            binding.ivDelete.setOnClickListener { mListener.onItemLongClick(currentNote) }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
        fun onItemLongClick(note: Note)
    }
}