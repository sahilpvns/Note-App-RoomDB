package com.sahilpvns.roomnoteapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sahilpvns.roomnoteapp.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModels()
    private var noteId: Int? = null
    private var binding: ActivityAddEditBinding? = null

    @SuppressLint("UseCompatLoadingForDrawables")
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

            val gd = GradientDrawable()
            gd.setColor(Color.WHITE)
            gd.cornerRadius = 10f
            gd.setStroke(3, Color.RED)

            if (TextUtils.isEmpty(title)) {
                binding?.etTitle?.setBackgroundDrawable(gd)
                binding?.etContent?.background = getDrawable(R.drawable.edit_shape)
            } else if (TextUtils.isEmpty(content)) {
                binding?.etContent?.setBackgroundDrawable(gd)
                binding?.etTitle?.background = getDrawable(R.drawable.edit_shape)
            } else if (title.isNotEmpty() && content.isNotEmpty()) {
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