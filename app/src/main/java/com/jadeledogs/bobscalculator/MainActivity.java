package com.jadeledogs.bobscalculator;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    enum States { CLEAR, DIGIT, OPERATOR, EQUALS }

    private static final String PLUS = "\u002b";
    private static final String MINUS = "\u002d";
    private static final String MULTIPLY = "\u00d7";
    private static final String DIVIDE = "\u00f7";
    private static final String DECIMAL = "\u002e";

    private double savedValue = 0.0;
    private String savedOperator = null;
    private States currentState = States.CLEAR;

    private TextView display = null;

    private DecimalFormat formatter = new DecimalFormat("###.#####");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.display = (TextView)findViewById(R.id.display_view);
    }

    public void buttonPressed(View view)
    {
    }

    public void clearButtonPressed(View view)
    {
        this.savedValue = 0;
        this.savedOperator = null;
        this.display.setText(" ");
        this.currentState = States.CLEAR;
    }

    public void plusMinusButtonPressed(View view)
    {
        String value = null;

        switch (this.currentState) {
            case CLEAR:
                break;

            case DIGIT:
                value = (String)this.display.getText();
                if (value != null) {
                    if (value.startsWith("-")) {
                        this.display.setText(value.substring(1));
                    }
                    else {
                        this.display.setText("-" + value);
                    }
                    this.currentState = States.DIGIT;
                }
                break;

            case OPERATOR:
                break;

            case EQUALS:
                value = (String)this.display.getText();
                if (value != null) {
                    if (value.startsWith("-")) {
                        this.display.setText(value.substring(1));
                    }
                    else {
                        this.display.setText("-" + value);
                    }
                    this.currentState = States.EQUALS;
                }
                break;

            default:
                break;
        }
    }

    /**
     +, -, ×, ÷	CLEAR	do nothing	CLEAR
     +, -, ×, ÷	DIGIT	if the current operator is not null
     {
     process calculation
     display result
     }
     set current operator to button value	OPERATOR
     +, -, ×, ÷	OPERATOR	set current operator to button value	OPERATOR
     +, -, ×, ÷	EQUALS	set current operator to button value	OPERATOR

     * @param view
     */
    public void divideButtonPressed(View view)
    {
        String value = null;

        switch (this.currentState) {
            case CLEAR:
                break;

            case DIGIT:
                if (this.savedOperator != null)
                {
                    value = (String)this.display.getText();
                    this.savedValue = this.processCalculation(value);
                    this.display.setText(this.formatter.format(this.savedValue));
                }
                this.savedOperator = DIVIDE;
                this.currentState = States.OPERATOR;
                break;

            case OPERATOR:
                this.savedOperator = DIVIDE;
                this.currentState = States.OPERATOR;
                break;

            case EQUALS:
                this.savedOperator = DIVIDE;
                this.currentState = States.OPERATOR;
                break;

            default:
                break;
        }
    }

    public void multiplyButtonPressed(View view)
    {
        String value = null;

        switch (this.currentState) {
            case CLEAR:
                break;

            case DIGIT:
                if (this.savedOperator != null)
                {
                    value = (String)this.display.getText();
                    this.savedValue = this.processCalculation(value);
                    this.display.setText(this.formatter.format(this.savedValue));
                }
                this.savedOperator = MULTIPLY;
                this.currentState = States.OPERATOR;
                break;

            case OPERATOR:
                this.savedOperator = MULTIPLY;
                this.currentState = States.OPERATOR;
                break;

            case EQUALS:
                this.savedOperator = MULTIPLY;
                this.currentState = States.OPERATOR;
                break;

            default:
                break;
        }
    }

    public void minusButtonPressed(View view)
    {
        String value = null;

        switch (this.currentState) {
            case CLEAR:
                break;

            case DIGIT:
                if (this.savedOperator != null)
                {
                    value = (String)this.display.getText();
                    this.savedValue = this.processCalculation(value);
                    this.display.setText(this.formatter.format(this.savedValue));
                }
                this.savedOperator = MINUS;
                this.currentState = States.OPERATOR;
                break;

            case OPERATOR:
                this.savedOperator = MINUS;
                this.currentState = States.OPERATOR;
                break;

            case EQUALS:
                this.savedOperator = MINUS;
                this.currentState = States.OPERATOR;
                break;

            default:
                break;
        }
    }

    public void plusButtonPressed(View view)
    {
        String value = null;

        switch (this.currentState) {
            case CLEAR:
                break;

            case DIGIT:
                if (this.savedOperator != null)
                {
                    value = (String)this.display.getText();
                    this.savedValue = this.processCalculation(value);
                    this.display.setText(this.formatter.format(this.savedValue));
                }
                this.savedOperator = PLUS;
                this.currentState = States.OPERATOR;
                break;

            case OPERATOR:
                this.savedOperator = PLUS;
                this.currentState = States.OPERATOR;
                break;

            case EQUALS:
                this.savedOperator = PLUS;
                this.currentState = States.OPERATOR;
                break;

            default:
                break;
        }
    }

    /*
0-9	CLEAR	display button value	DIGIT
0-9	DIGIT	append button value to display	DIGIT
0-9	OPERATOR	save current display value
display button value	DIGIT
0-9	EQUALS	display button value	DIGIT
  */
    public void digitButtonPressed(View view)
    {
        String value = null;
        String text = ((Button)view).getText().toString();

        switch (this.currentState) {
            case CLEAR:
                this.display.setText(text);
                this.currentState = States.DIGIT;
                break;

            case DIGIT:
                value = (String)this.display.getText();
                value += text;
                this.display.setText(String.valueOf(value));
                this.currentState = States.DIGIT;
                break;

            case OPERATOR:
                value = (String)this.display.getText();
                this.savedValue = Double.valueOf(value);
                this.display.setText(text);
                this.currentState = States.DIGIT;
                break;

            case EQUALS:
                this.display.setText(text);
                this.currentState = States.DIGIT;
                break;

            default:
                break;
        }
    }

    /*
.	CLEAR	display “0.”	DIGIT
.	DIGIT	if the current display value does not contain “.”
{
append “.” to the current display value
}	DIGIT
.	OPERATOR	save current display value
display “0.”	DIGIT
.	EQUALS	clear memory and display
display “0.”	DIGIT
 */
    public void decimalButtonPressed(View view)
    {
        String value = null;

        switch (this.currentState) {
            case CLEAR:
                this.display.setText("0.");
                this.currentState = States.DIGIT;
                break;

            case DIGIT:
                value = (String)this.display.getText();
                if (!value.contains("."))
                {
                    value += ".";
                    this.display.setText(String.valueOf(value));
                }
                this.currentState = States.DIGIT;
                break;

            case OPERATOR:
                value = (String)this.display.getText();
                this.savedValue = Double.valueOf(value);
                this.display.setText("0.");
                this.currentState = States.DIGIT;
                break;

            case EQUALS:
                this.savedValue = 0.0;
                this.savedOperator = null;
                this.display.setText("0.");
                this.currentState = States.DIGIT;
                break;

            default:
                break;
        }
    }

    /*
=	CLEAR	do nothing	CLEAR
=	DIGIT	if the current operator is not null
{
process calculation
display result
}	EQUALS
=	OPERATOR	if the current operator is not null
{
process calculation
display result
}	EQUALS
=	EQUALS	if the current operator is not null
{
process calculation
display result
}	EQUALS
     */
    public void equalsButtonPressed(View view)
    {
        String value = null;

        switch (this.currentState) {
            case CLEAR:
                 break;

            case DIGIT:
                if (this.savedOperator != null) {
                    value = (String) this.display.getText();
                    this.savedValue = this.processCalculation(value);
                    this.display.setText(this.formatter.format(this.savedValue));
                    this.currentState = States.EQUALS;
                }
                break;

            case OPERATOR:
                if (this.savedOperator != null) {
                    value = (String) this.display.getText();
                    this.savedValue = this.processCalculation(value);
                    this.display.setText(this.formatter.format(this.savedValue));
                    this.currentState = States.EQUALS;
                }
                break;

            case EQUALS:
                if (this.savedOperator != null) {
                    value = (String) this.display.getText();
                    this.savedValue = this.processCalculation(value);
                    this.display.setText(this.formatter.format(this.savedValue));
                    this.currentState = States.EQUALS;
                }
                break;

            default:
                break;
        }
    }

    private double processCalculation(String value)
    {
        return this.processCalculation(Double.valueOf(value));
    }

    private double processCalculation(double value)
    {
        double number = 0;

        switch (this.savedOperator) {
            case PLUS:
                number = this.savedValue + value;
                break;

            case MINUS:
                number = this.savedValue - value;
                break;

            case MULTIPLY:
                number = this.savedValue * value;
                break;

            case DIVIDE:
                number = this.savedValue / value;
                break;

            default:
                break;
        }

        return number;
    }

    //  getters and setters

    public double getSavedValue() {
        return savedValue;
    }

    public void setSavedValue(double savedValue) {
        this.savedValue = savedValue;
    }

    public String getSavedOperator() {
        return savedOperator;
    }

    public void setSavedOperator(String savedOperator) {
        this.savedOperator = savedOperator;
    }

    public States getCurrentState() {
        return currentState;
    }

    public void setCurrentState(States currentState) {
        this.currentState = currentState;
    }
}
