package com.nagmekaraaslan.shooters_app.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nagmekaraaslan.shooters_app.R
import com.nagmekaraaslan.shooters_app.adapters.ShootsAdapter
import java.io.File

class ShootsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShootsAdapter
    private var photoUri: Uri? = null
    private val photoList = mutableListOf<Uri>()

    // Galeri Seçici
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            adapter.addPhoto(it)
        }
    }

    // Kamera Seçici
    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
        if (success) {
            photoUri?.let { adapter.addPhoto(it) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shoots, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerShoots)
        // Pinterest tarzı görünüm için StaggeredGridLayoutManager
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        adapter = ShootsAdapter(photoList)
        recyclerView.adapter = adapter

        val fab: FloatingActionButton = view.findViewById(R.id.fabAddPhoto)
        fab.setOnClickListener {
            showImageSourceDialog()
        }
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("Galeri", "Kamera")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Fotoğraf Ekle")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> getContent.launch("image/*")
                    1 -> openCamera()
                }
            }
            .show()
    }

    private fun openCamera() {
        val imageFile = File(requireContext().cacheDir, "temp_photo_${System.currentTimeMillis()}.jpg")
        photoUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            imageFile
        )
        takePicture.launch(photoUri)
    }
}