package u10416019.accountbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by wenqi on 2018/1/2.
 */

public class Calendar extends AppCompatActivity {

    private Button buttonDate;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        buttonDate = (Button)findViewById(R.id.buttonDate);


        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(Calendar.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
