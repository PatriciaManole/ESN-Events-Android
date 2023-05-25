package com.example.esneventsandroid.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import com.example.esneventsandroid.R

class   NationalEventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_national_events, container, false)
        val videoView = view.findViewById<VideoView>(R.id.etvv)
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        val path ="android.resource://" + requireContext().packageName + "/" + R.raw.esc
        videoView.setVideoURI(Uri.parse(path))
        videoView.setZOrderOnTop(true)
        videoView.seekTo(100)
        videoView.start()

        val videoView2 = view.findViewById<VideoView>(R.id.hit)
        val path2 ="android.resource://" + requireContext().packageName + "/" + R.raw.hit
        videoView2.setVideoURI(Uri.parse(path2))
        videoView2.setZOrderOnTop(true)
        videoView2.seekTo(100)
        videoView2.start()
        return view
    }

}