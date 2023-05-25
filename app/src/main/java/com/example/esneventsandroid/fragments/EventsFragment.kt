package com.example.esneventsandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esneventsandroid.MyAdapter
import com.example.esneventsandroid.R
import com.example.esneventsandroid.events
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*


class EventsFragment : Fragment() {
    private lateinit var dbref : DatabaseReference
    private lateinit var eventsRecyclerview : RecyclerView
    private lateinit var eventsArrayList : ArrayList<events>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        eventsRecyclerview = view.findViewById(R.id.eventsList)
        eventsRecyclerview.layoutManager = LinearLayoutManager(activity)
        eventsRecyclerview.setHasFixedSize(true)
        eventsArrayList = arrayListOf<events>()
        geteventsData()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility=View.VISIBLE
        return view
    }

    private fun geteventsData() {
        dbref = FirebaseDatabase.getInstance().getReference("events")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (eventsSnapshot in snapshot.children){
                        val events = eventsSnapshot.getValue(events::class.java)
                        eventsArrayList.add(events!!)
                    }
                    eventsRecyclerview.adapter = MyAdapter(eventsArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}