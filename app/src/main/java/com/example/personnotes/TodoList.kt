package com.example.personnotes

import android.os.Bundle
import android.renderscript.Sampler.Value
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TodoList : Fragment() {

    private var param1: String? = null
    private var param2: String? = null


    private lateinit var inputTask: EditText
    private lateinit var btnTask : TextView
    private lateinit var numberTask : TextView
    private lateinit var recyclerTask : RecyclerView



    private val userId: String? = FirebaseAuth.getInstance().currentUser?.uid
    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("users").child(userId.toString())

    private var tasksReference: DatabaseReference = database.child("Tasks")
    private var userTasks: List<String> = emptyList()



    private fun createTask(editTask: EditText) {
        val task = editTask.text.toString()




        if (task.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Informe sua task", Toast.LENGTH_SHORT).show()
            editTask.setBackgroundResource(R.drawable.inp_black_error)
            return
        }

        val createTask = hashMapOf(
            "Task" to task
        )

        val reference = database.child("Tasks").push()

        reference.setValue(createTask)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Tarefa criada com sucesso", Toast.LENGTH_SHORT).show()
                editTask.text.clear() // Limpar o EditText após salvar
            }
            .addOnFailureListener {
                // Tratar falhas ao salvar os dados
                Toast.makeText(requireContext(), "Erro ao criar a tarefa", Toast.LENGTH_SHORT).show()
            }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnTask = view.findViewById(R.id.btn_create)

        // Agora que a visão está criada, podemos acessar o EditText
        inputTask = view.findViewById(R.id.inp_task)

        // Chamar o método createTask com a instância correta do EditText
        btnTask.setOnClickListener {
            createTask(inputTask)
        }

        numberTask = view.findViewById(R.id.task_create)

        tasksReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val taskCount = snapshot.childrenCount.toInt()
                numberTask.text = taskCount.toString()
                getListOfTask()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Erro ao ler dados do banco de dados", Toast.LENGTH_SHORT).show()
            }
        })
        recyclerTask = view.findViewById(R.id.recycler_task)
        initRecycleView()
    }

    private fun initRecycleView() {
        recyclerTask.layoutManager = LinearLayoutManager(requireContext())
        recyclerTask.setHasFixedSize(true)
        recyclerTask.adapter = Adpater_item_task(userTasks) // Certifique-se de passar dados para o adaptador conforme necessário
    }

    private fun getListOfTask() {
        tasksReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tasksList: MutableList<String> = mutableListOf()

                for (taskSnapshot in snapshot.children) {
                    val task = taskSnapshot.child("Task").getValue(String::class.java)
                    task?.let {
                        tasksList.add(it)
                    }
                }

                userTasks = tasksList

                // Atualizar o RecyclerView após obter as tarefas no thread principal
                requireActivity().runOnUiThread {
                    recyclerTask.adapter = Adpater_item_task(userTasks)
                }
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Erro ao ler dados do banco de dados", Toast.LENGTH_SHORT).show()
            }
        })
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TodoList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}