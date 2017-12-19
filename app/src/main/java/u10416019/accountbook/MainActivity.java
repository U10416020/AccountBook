package u10416019.accountbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Intent intent = new Intent();
    private Bundle bundle = new Bundle();
    private TextView textView4, textView5, textView6;
    private String money, content;
    private int[] radioButtonCheck = new int[4];
    int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);

        button.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        intent.setClass(MainActivity.this, Edit.class);
                        startActivityForResult(intent,REQUEST_CODE);
                    }
                });

/*
        intent = this.getIntent();
        bundle = this.getIntent().getExtras();


        money = intent.getStringExtra("money");
        content = intent.getStringExtra("content");
        radioButtonCheck = bundle.getIntArray("radioButtonCheck");

        textView4.setText(money);
        textView5.setText(content);
        textView6.setText(radioButtonCheck[0]+radioButtonCheck[1]+radioButtonCheck[2]+radioButtonCheck[3]);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                bundle = data.getExtras();

                money = data.getStringExtra("money");
                content = data.getStringExtra("content");
                radioButtonCheck = bundle.getIntArray("radioButtonCheck");

                textView4.setText(money);
                textView5.setText(content);
                textView6.setText(String.valueOf(radioButtonCheck[0])+String.valueOf(radioButtonCheck[1])+String.valueOf(radioButtonCheck[2])+String.valueOf(radioButtonCheck[3]));
            }
        }
    }
}
