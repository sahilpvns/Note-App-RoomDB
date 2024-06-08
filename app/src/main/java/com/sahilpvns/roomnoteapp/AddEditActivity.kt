package com.sahilpvns.roomnoteapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.sahilpvns.roomnoteapp.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModels()
    private var noteId: Int? = null
    private var binding: ActivityAddEditBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_edit)


        val intent = intent
        if (intent.hasExtra("note_id")) {
            noteId = intent.getIntExtra("note_id", -1)
            binding?.etTitle?.setText(intent.getStringExtra("note_title"))
            binding?.etContent?.setText(intent.getStringExtra("note_content"))
        }

        binding?.btnSave?.setOnClickListener {
            val title = binding?.etTitle?.text.toString()
            val content = binding?.etContent?.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val note = Note(id = noteId ?: 0, title = title, content = content)
                if (noteId == null) {
                    noteViewModel.insert(note)
                } else {
                    noteViewModel.update(note)
                }
                finish()
            }
        }
    }
}