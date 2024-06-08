package com.sahilpvns.roomnoteapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sahilpvns.roomnoteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , NoteAdapter.OnItemClickListener {

    private val noteViewModel: NoteViewModel by viewModels()
    private val mAdapter by lazy { NoteAdapter(arrayListOf(), this) }
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding?.rvItem?.layoutManager = LinearLayoutManager(this)
        binding?.rvItem?.adapter = mAdapter

        noteViewModel.allNotes.observe(this) {
            mAdapter.setNotes(it)
        }

        binding?.fab?.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(note: Note) {
        // Open activity to edit note
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra("note_id", note.id)
        intent.putExtra("note_title", note.title)
        intent.putExtra("note_content", note.content)
        startActivity(intent)
    }

    override fun onItemLongClick(note: Note) {
        // Handle long click to delete note
        noteViewModel.delete(note)
    }
}