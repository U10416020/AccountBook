package u10416019.accountbook;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnadd;
    private Button record;
    private LinearLayout linLay;
    private TextView date;
    private Intent intent = new Intent();
    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

    public String abc;
    public int layoutId;

    public final String TABLE_NAME = "ACCOUNT";

    GlobalVariable gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gv = (GlobalVariable)getApplicationContext();
        //gv = new GlobalVariable();
        layoutId = gv.getLayoutId();

        btnadd =(ImageButton)findViewById(R.id.add);
        record = (Button)findViewById(R.id.button2);
        linLay = (LinearLayout)findViewById(R.id.linLayout);
        btnadd.setOnClickListener(addView);
        date = (TextView)findViewById(R.id.date);

        intent=this.getIntent();

        if(intent.hasExtra("date")){
            date.setText(intent.getStringExtra("date"));
            gv.setDate(date.getText().toString());
        }
        else{
            date.setText(dateFormat.format(calendar.getTime()));
            gv.setDate(dateFormat.format(calendar.getTime()));
        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(MainActivity.this,Record.class);
                startActivity(intent);
            }
        });
        changeLinearLayout();
    }

    private ImageButton.OnClickListener addView= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,Edit.class);
            reset();
            startActivity(intent);
        }
    };

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
        gv.setDate(dateFormat.format(calendar.getTime()));
        changeLinearLayout();
    }

    //Clean linear layout and get database to new linear layout. And get total.
    private void changeLinearLayout(){
        linLay.removeAllViews();
        MyDataBase myDBHelper = MyDataBase.getInstance(this);
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        String cmd = "SELECT * FROM "+TABLE_NAME+" WHERE DATE = \""+date.getText().toString()+"\";";
        Cursor result = db.rawQuery(cmd,null);
        result.moveToFirst();
        String dates="";
        if(result.moveToFirst()){
            do{
                long id = result.getLong(0);
                dates = result.getString(1);
                String money = result.getString(2);
                int income = result.getInt(3);
                int necessary = result.getInt(4);
                String type = result.getString(5);
                String content = result.getString(6);
                String frequency = result.getString(7);
                linLay.addView(new addLinLayout(MainActivity.this,money,content,type,id,income,necessary,myDBHelper));
                Log.d("ID",id+"+DATE="+dates);
            }while(result.moveToNext());
        }
        int total = myDBHelper.getMonthTotal(date.getText().toString());
        Log.d("TOTAL",total+"");
    }

    private Button.OnClickListener changeCalendar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
        }
    };

    public void reset(){
        gv.setMoney("");
        gv.setContent("");
        gv.setRadioButtonCheck(new int[4]);
        gv.setTypeItem("");
        gv.setFrequencyItem("");
        gv.setLayoutId(0);
    }
}
