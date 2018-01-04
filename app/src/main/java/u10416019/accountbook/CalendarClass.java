package u10416019.accountbook;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static u10416019.accountbook.R.layout.calendar_layout;

/**
 * Created by wenqi on 2018/1/2.
 */

public class CalendarClass extends AppCompatActivity {

    private Button buttonDate;
    private Intent intent = new Intent();
    private CalendarView calendarView;
    private TextView date;
    final java.util.Calendar calendar = java.util.Calendar.getInstance();
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
    public GlobalVariable gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(calendar_layout);

        gv = (GlobalVariable)getApplicationContext();

        buttonDate = (Button)findViewById(R.id.buttonDate);
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        date = (TextView)findViewById(R.id.date);

        date.setText(dateFormat.format(calendar.getTime()));
        gv.setDate(dateFormat.format(calendar.getTime()));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CalendarClass.this,listener,calendar.get(java.util.Calendar.YEAR),calendar.get(java.util.Calendar.MONTH),calendar.get(java.util.Calendar.DAY_OF_MONTH)).show();
                //calendarView.setDate();
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(CalendarClass.this,
                        "您的生日是"+year+"年"+(month+1)+"月"+dayOfMonth+"日", Toast.LENGTH_LONG).show();
            }
        });

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(CalendarClass.this, MainActivity.class);
                startActivity(intent);
            }
        });


        MyDataBase myDBHelper = MyDataBase.getInstance(this);
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        /*
        String cmd = "SELECT * FROM "+TABLE_NAME+";";
        Cursor result = db.query(TABLE_NAME,null,null,null,null,null,null,null);
        for(int i = 0;i<result.getCount();i++){
            result.moveToPosition(i);
            long id = result.getLong(0);
            String date = result.getString(1);
            String money = result.getString(2);
            int income = result.getInt(3);
            int necessary = result.getInt(4);
            String type = result.getString(5);
            String content = result.getString(6);
            String frequency = result.getString(7);
            //linLay.addView(new addLinLayout(CalendarClass.this,money,content,type,id,income,necessary,myDBHelper));
        }
        */
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(java.util.Calendar.YEAR,year);
            calendar.set(java.util.Calendar.MONTH,month);
            calendar.set(java.util.Calendar.DAY_OF_MONTH,dayOfMonth);
            updateDate();
        }
    };

    private void updateDate(){

        date.setText(dateFormat.format(calendar.getTime()));
        gv.setDate(dateFormat.format(calendar.getTime()));
    }

}
