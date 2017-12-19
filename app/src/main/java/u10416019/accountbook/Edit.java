package u10416019.accountbook;

/**
 * Created by wenqi on 2017/12/13.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Edit extends AppCompatActivity {

    private EditText editTextMoney, editTextContent;
    private RadioButton radioButtonIncome, radioButtonExpense, radioButtonNecessity, radioButtonNonNecessity;
    private Spinner spinnerType, spinnerFrequency;
    private Button buttonOK;
    private Intent intent = new Intent();
    private Bundle bundle = new Bundle();
    private int[] radioButtonCheck = new int[4];
    private int resultCode = 1;

    private int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        editTextMoney = (EditText)findViewById(R.id.editTextMoney);
        editTextContent = (EditText)findViewById(R.id.editTextContent);
        radioButtonIncome = (RadioButton)findViewById(R.id.radioButtonIncome);
        radioButtonExpense = (RadioButton)findViewById(R.id.radioButtonExpense);
        radioButtonNecessity = (RadioButton)findViewById(R.id.radioButtonNecessity);
        radioButtonNonNecessity = (RadioButton)findViewById(R.id.radioButtonNonNecessity);
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        spinnerFrequency = (Spinner) findViewById(R.id.spinnerFrequency);
        buttonOK = (Button)findViewById(R.id.buttonOK);

        buttonOK.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        intent.setClass(Edit.this, MainActivity.class);
                        checkRadioButton();
                        intent.putExtra("money",editTextMoney.getText().toString());
                        intent.putExtra("content",editTextContent.getText().toString());
                        bundle.putIntArray("radioButtonCheck",radioButtonCheck);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

    }

    public void checkRadioButton(){
        check = 1;

        if(radioButtonIncome.isChecked() == true) radioButtonCheck[0] = 1;
        else radioButtonCheck[0] = 0;
        if(radioButtonExpense.isChecked() == true) radioButtonCheck[1] = 1;
        else radioButtonCheck[1] = 0;
        if(radioButtonNecessity.isChecked() == true) radioButtonCheck[2] = 1;
        else radioButtonCheck[2] = 0;
        if(radioButtonNonNecessity.isChecked() == true) radioButtonCheck[3] = 1;
        else radioButtonCheck[3] = 0;
    }
}
