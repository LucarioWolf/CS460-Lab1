package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


/**
 * Name: Alan Ruelas-Cordova
 * Due Date: 10/9/26
 * Lab 1
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    //Variables for XML elements
    TextView solutionTV, resultTV;

    MaterialButton buttonC, buttonOpenBrack, buttonCloseBrack;

    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;

    MaterialButton buttonAdd, buttonSub, buttonMul, buttonDiv, buttonEqual;

    MaterialButton buttonAC, buttonDot;

    /**
     * Starts the program and beginning the emulator.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Assign Textview objects
        solutionTV = findViewById(R.id.solution_tv);
        resultTV = findViewById(R.id.result_tv);

        //Assign calculator functions "( ) .  etc
        assignID(buttonC, R.id.button_C);
        assignID(buttonOpenBrack, R.id.button_open_bracket);
        assignID(buttonCloseBrack, R.id.button_close_bracket);
        assignID(buttonAC, R.id.button_ac);
        assignID(buttonDot,R.id.button_dot);

        //Assign Number buttons
        assignID(button0,R.id.button_0);
        assignID(button1,R.id.button_1);
        assignID(button2,R.id.button_2);
        assignID(button3,R.id.button_3);
        assignID(button4,R.id.button_4);
        assignID(button5,R.id.button_5);
        assignID(button6,R.id.button_6);
        assignID(button7,R.id.button_7);
        assignID(button8,R.id.button_8);
        assignID(button9,R.id.button_9);

        //Assign operations buttons
        assignID(buttonAdd,R.id.button_plus);
        assignID(buttonSub,R.id.button_minus);
        assignID(buttonMul,R.id.button_mul);
        assignID(buttonDiv,R.id.button_divide);
        assignID(buttonEqual,R.id.button_equal);

    }

    /**
     * A function to assign the buttons from the XML files to a onject in Java
     * @param btn Java Object that would contain the XML buttons
     * @param id  A pointer to receive an XML object using the ID
     */
    public void assignID(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    /**
     *  A function to give a click event to a object in the view,
     *  it would take the text from the object ot determine the operation needed.
     * @param view That holds the XML elements
     */
    @Override
    public void onClick(View view) {

        //Get the button that was clicked from the view
        MaterialButton button = (MaterialButton) view;

        //Get the text from the button and the solution textview
        String buttonText = button.getText().toString();
        String dataToCal = solutionTV.getText().toString();

        //If the Solution Textview is the Student ID from startup, reset the textview.
        if (dataToCal.equals(getString(R.string.StudentID))){
            dataToCal = "";
        }

        //If the AC button was pressed, reset the calculator
        if(buttonText.equals("AC")){
            solutionTV.setText("");
            resultTV.setText("0");
            return;
        }

        //If the = button was pressed, but the result text to solution textview
        if(buttonText.equals("=")){
            solutionTV.setText(resultTV.getText());
            return;
        }

        //If the C button was pressed, delete the last char in the solution text
        if(buttonText.equals("C")){
            dataToCal = dataToCal.substring(0, dataToCal.length()-1);

        }
        //Add the button text to the solution textview
        else{
            dataToCal = dataToCal + buttonText;
        }

        //Set the updated text to the solution textview
        solutionTV.setText(dataToCal);

        //Get the answer of the equation of the user inputs
        String finalResult = getResults(dataToCal);

        //set the result of the equation in the result textview if valid
        if(!finalResult.equals("ERROR")){
            resultTV.setText(finalResult);
        }

    }

    /**
     * A function that operates an equation set by the user using a package.
     * @param data The user inputted equation
     * @return  Returns either the result of the equation or an error if equation is invalid
     */
    String getResults(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            return context.evaluateString(scriptable, data, "Javascript", 1, null).toString();

        } catch (Exception e) {
            return "ERROR";
        }
    }
}