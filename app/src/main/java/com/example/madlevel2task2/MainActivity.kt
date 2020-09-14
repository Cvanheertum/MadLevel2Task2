package com.example.madlevel2task2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel2task2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        binding.rvQuestions.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        binding.rvQuestions.adapter = questionAdapter
        binding.rvQuestions.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        fillQuestionsList()
        createItemTouchHelper().attachToRecyclerView(rvQuestions)
    }

    private fun fillQuestionsList() {
        questions.add(Question("A 'val' and 'var' are the same", false)) //FALSE
        questions.add(Question("Mobile Application Development grants 12 ECTS",false)) //FALSE
        questions.add(Question("A Unit in Kotlin corresponds to a void in Java", true)) //TRUE
        questions.add(Question("In Kotlin 'when' replaces the 'switch' operator in Java",true)) //TRUE
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val currentQuestion = questions[position]
                /*if (direction == ItemTouchHelper.RIGHT) {
                    Snackbar.make(root_layout,"True",Snackbar.LENGTH_SHORT).show()
                }
                if (direction == ItemTouchHelper.LEFT) {
                    Snackbar.make(root_layout,"False",Snackbar.LENGTH_SHORT).show()
                }*/
                if (direction == ItemTouchHelper.RIGHT) {
                    // Answer should be true to be correct
                    // Else it is wrong
                    if(currentQuestion.answer) {
                        Snackbar.make(root_layout,"You're correct!",Snackbar.LENGTH_SHORT).show()
                        viewHolder.itemView.setBackgroundColor(Color.GREEN)
                    } else {
                        Snackbar.make(root_layout,"Wrong answer, please try again.",Snackbar.LENGTH_SHORT).show()
                        viewHolder.itemView.setBackgroundColor(Color.RED)
                    }
                }
                if (direction == ItemTouchHelper.LEFT) {
                    // Answer should be false to be correct
                    // Else it is wrong
                    if(!currentQuestion.answer) {
                        Snackbar.make(root_layout,"You're correct!",Snackbar.LENGTH_SHORT).show()
                        viewHolder.itemView.setBackgroundColor(Color.GREEN)
                    } else {
                        Snackbar.make(root_layout,"Wrong answer, please try again.",Snackbar.LENGTH_SHORT).show()
                        viewHolder.itemView.setBackgroundColor(Color.RED)
                    }
                }
                questionAdapter.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(callback)
    }
}