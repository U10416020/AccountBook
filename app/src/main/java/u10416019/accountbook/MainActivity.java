package u10416019.accountbook;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnadd;
    private LinearLayout linLay;
    private TextView date;
    private Intent intent = new Intent();
    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
    public String abc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnadd =(ImageButton)findViewById(R.id.add);
        linLay = (LinearLayout)findViewById(R.id.linLayout);
        btnadd.setOnClickListener(addView);
        date = (TextView)findViewById(R.id.date);

        date.setText(dateFormat.format(calendar.getTime()));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(MainActivity.this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private ImageButton.OnClickListener addView= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,Edit.class);
            //startActivity(intent);
            startActivityForResult(intent,1);
            //linLay.addView(new addLinLayout(MainActivity.this));
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundleResult = data.getExtras();
            Toast.makeText(MainActivity.this, "選擇項目:"+bundleResult.getString("typeItem"),
                    Toast.LENGTH_LONG).show();

            String money = bundleResult.getString("money");
            String content = bundleResult.getString("content");
            String typeItem = bundleResult.getString("typeItem");
            linLay.addView(new addLinLayout(MainActivity.this,money,content,typeItem));
        }
        else if(resultCode==3){
            Bundle bundleResult = data.getExtras();
            Toast.makeText(MainActivity.this, "layoutID:"+bundleResult.getInt("layoutId"),
                    Toast.LENGTH_LONG).show();

        }
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateDate();
        }
    };

    private void updateDate(){
        date.setText(dateFormat.format(calendar.getTime()));
    }

    private Button.OnClickListener changeCalendar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();

        }
    };
}
