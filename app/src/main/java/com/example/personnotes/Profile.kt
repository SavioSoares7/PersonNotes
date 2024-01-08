package com.example.personnotes

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Profile : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var btnExit : TextView

    private var emailUserScreen : TextView? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        auth = Firebase.auth



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailUserScreen = view.findViewById(R.id.user_email)
        emailUserScreen?.text = FirebaseAuth.getInstance().currentUser?.email


        btnExit = view.findViewById(R.id.btn_exit)
        btnExit.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
                requireActivity().finish()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}