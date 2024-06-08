package com.sahilpvns.roomnoteapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sahilpvns.roomnoteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , NoteAdapter.OnItemClickListener {

    private val noteViewModel: NoteViewModel by viewModels()
    private val mAdapter by lazy { NoteAdapter(arrayListOf(), this) }
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initLayoutManager()
        initViewModel()
        btnFab()

    }

    private fun btnFab() {
        binding?.fab?.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViewModel() {
        noteViewModel.allNotes.observe(this) {
            mAdapter.setNotes(it)
        }
    }

    private fun initLayoutManager() {
        binding?.rvItem?.layoutManager = LinearLayoutManager(this)
        binding?.rvItem?.adapter = mAdapter
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