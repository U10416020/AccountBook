package u10416019.accountbook;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
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
    public int layoutId;

    public final String TABLE_NAME = "ACCOUNT";

    GlobalVariable gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //intent=this.getIntent();
        gv = (GlobalVariable)getApplicationContext();
        //gv = new GlobalVariable();
        layoutId = gv.getLayoutId();


/*
        Cursor result = db.rawQuery(cmd,null);
        result.moveToNext();
        for(int i = 0;i<result.getColumnCount();i++){
            String data = result.getString(i);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(data);
            builder.show();
        }
        */
        btnadd =(ImageButton)findViewById(R.id.add);
        linLay = (LinearLayout)findViewById(R.id.linLayout);
        btnadd.setOnClickListener(addView);
        date = (TextView)findViewById(R.id.date);

        date.setText(dateFormat.format(calendar.getTime()));
        gv.setDate(dateFormat.format(calendar.getTime()));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(MainActivity.this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //if(layoutId == 0){
            //linLay.addView(new addLinLayout(MainActivity.this,gv.getMoney(),gv.getContent(),gv.getTypeItem()));
       // }

        //MyDatabase myDBHelper = new MyDatabase(this, DATABASE_NAME, null, 3);
        MyDataBase myDBHelper = MyDataBase.getInstance(this);
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
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
            linLay.addView(new addLinLayout(MainActivity.this,money,content,type,id,income,necessary,myDBHelper));
        }

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
            reset();
            startActivity(intent);
            //startActivityForResult(intent,1);
            //linLay.addView(new addLinLayout(MainActivity.this,"1111","asdf","1"));
        }
    };
/*
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
*/
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
