package u10416019.accountbook;

/**
 * Created by wenqi on 2017/12/13.
 */

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Edit extends AppCompatActivity {

    private final String DATABASE_NAME="accountBook";
    public final String TABLE_NAME = "ACCOUNT";

    private EditText editTextMoney, editTextContent;
    private RadioButton radioButtonIncome, radioButtonExpense, radioButtonNecessity, radioButtonNonNecessity;
    private Spinner spinnerType, spinnerFrequency;
    private Button buttonOK;

    private Intent intent = new Intent();
    private Bundle bundle = new Bundle();
    private int[] radioButtonCheck = new int[4];


    //private int resultCode = 1;

    private String money, content,date;

    private String frequencyItem = "";

    private String[] item = {"無","每週一次","每兩週一次","每月一次"};
    private List<String> allType;
    private String[] getType;
    private String type = "早餐,午餐,晚餐,交通,新增";
    private ArrayAdapter<String> typeAdapter;
    private String typeItem;
    private boolean editData = false;
    private int layoutId;

    private MyDataBase helper;
    public static SQLiteDatabase database;

    public long id=0;

    GlobalVariable gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        intent=this.getIntent();

        gv = (GlobalVariable)getApplicationContext();
        //helper = new MyDataBase(this,DATABASE_NAME,null,1);
        helper = MyDataBase.getInstance(this);
        database=helper.getWritableDatabase();
        //gv = new GlobalVariable();
        //getIntent = this.getIntent();


        //gv = (GlobalVariable)getApplicationContext();
        //gv = new GlobalVariable();


        editTextMoney = (EditText)findViewById(R.id.editTextMoney);
        editTextContent = (EditText)findViewById(R.id.editTextContent);
        radioButtonIncome = (RadioButton)findViewById(R.id.radioButtonIncome);
        radioButtonExpense = (RadioButton)findViewById(R.id.radioButtonExpense);
        radioButtonNecessity = (RadioButton)findViewById(R.id.radioButtonNecessity);
        radioButtonNonNecessity = (RadioButton)findViewById(R.id.radioButtonNonNecessity);
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        spinnerFrequency = (Spinner) findViewById(R.id.spinnerFrequency);
        buttonOK = (Button)findViewById(R.id.buttonOK);

        radioButtonNecessity.setVisibility(View.GONE);
        radioButtonNonNecessity.setVisibility(View.GONE);

/*
        if(intent.hasExtra("bundle")){
            Bundle getBundle = intent.getExtras();
            String money = getBundle.getString("money");
            String content = getBundle.getString("content");
            String typeItem = getBundle.getString("typeItem");
            layoutId = getBundle.getInt("layoutId");
            editTextMoney.setText(money);
            editTextContent.setText(content);
            //spinnerType.setSelection(allType.indexOf(typeItem));
            editData=true;
        }
        //if(bundle!=null){
        //}
*/
        //ArrayAdapter<CharSequence> arrAdapter = ArrayAdapter.createFromResource(Edit.this,item,Spinner.MODE_DIALOG);
        spinnerFrequency.setOnItemSelectedListener(itemselect);
        //editTextContent.setText(money);

        //The items of spinnerType.
        allType = new ArrayList<String>();


        SharedPreferences prefGet = getSharedPreferences("typeItem",MODE_PRIVATE);
        String typeString = prefGet.getString("item",type);
        getType = typeString.split(",");

        if(typeString!=null){
            for(String string : getType){
                allType.add(string);
            }
        }

        //Set  spinnerType adapter
        typeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, allType);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);
        //spinnerType.setOnItemSelectedListener(itemselect);

        spinnerType.setOnItemSelectedListener(typeItemSelect);

        if(intent.hasExtra("id")){
            id = intent.getLongExtra("id",-1);
            String cmd = "select * from "+TABLE_NAME+" where _id = "+id+" ;";
            Cursor result = database.rawQuery(cmd,null);
            if( result != null && result.moveToFirst() ){
                id=result.getLong(0);
                date = result.getString(1);
                money = result.getString(2);
                radioButtonCheck[0] = result.getInt(3);
                radioButtonCheck[2]=result.getInt(4);
                typeItem=result.getString(5);
                content=result.getString(6);
                frequencyItem=result.getString(7);

                editTextMoney.setText(money);
                spinnerType.setSelection(allType.indexOf(typeItem));
                spinnerFrequency.setSelection(Arrays.asList(item).indexOf(frequencyItem));
                editTextContent.setText(content);

                result.close();
            }
            if(radioButtonCheck[0]==1)
                radioButtonCheck[1]=0;
            else if(radioButtonCheck[0]==0)
                radioButtonCheck[1]=1;
            if(radioButtonCheck[2]==1)
                radioButtonCheck[3]=0;
            if(radioButtonCheck[3]==1)
                radioButtonCheck[2]=0;
            setRadioButton();
        }




        getGlobalVariable();
        //setNumber();


        buttonOK.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {

/*
                        intent.setClass(Edit.this, MainActivity.class);
                        checkRadioButton();
                        //intent.putExtra("money",editTextMoney.getText().toString());
                        //intent.putExtra("content",editTextContent.getText().toString());
                        bundle.putIntArray("radioButtonCheck",radioButtonCheck);
                        bundle.putString("content",editTextContent.getText().toString());
                        bundle.putString("money",editTextMoney.getText().toString());
                        bundle.putString("typeItem",typeItem);
                        /*
                        if(editData==false){
                            intent.putExtras(bundle);
                            setResult(RESULT_OK, intent);
                        }
                        else{
                            bundle.putInt("layoutId",layoutId);
                            intent.putExtras(bundle);
                            setResult(3,intent);
                        }
*/

                        intent.setClass(Edit.this, MainActivity.class);
                        setGlobalVariable();
                        sendData();
                        startActivity(intent);

                    }
                });

        radioButtonExpense.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        radioButtonNecessity.setVisibility(View.VISIBLE);
                        radioButtonNonNecessity.setVisibility(View.VISIBLE);
                    }
                });

        radioButtonIncome.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        radioButtonNecessity.setVisibility(View.GONE);
                        radioButtonNonNecessity.setVisibility(View.GONE);
                    }
                });
    }

    //Send data to DataBase named ACCOUNTBOOK
    public void sendData(){
        int income=0,necessary=0;
        if(radioButtonCheck[0]==1&&radioButtonCheck[1]==0)
            income=1;
        else if(radioButtonCheck[0]==0&&radioButtonCheck[1]==1)
            income=0;
        if(radioButtonCheck[2]==1&&radioButtonCheck[1]==0)
            necessary=1;
        else if(radioButtonCheck[2]==0&&radioButtonCheck[3]==1)
            necessary=0;

        //if(id==0){
        long id = helper.insert(date,money,income,necessary,typeItem,content,frequencyItem);
        Log.d("ADD", id+"");
        intent.putExtra("ID",id);
        //}
        //else{
        helper.update(id);
        //}

    }
    //Set Radio Button
    public void checkRadioButton(){
        if(radioButtonIncome.isChecked() == true) radioButtonCheck[0] = 1;
        else radioButtonCheck[0] = 0;
        if(radioButtonExpense.isChecked() == true) radioButtonCheck[1] = 1;
        else radioButtonCheck[1] = 0;
        if(radioButtonNecessity.isChecked() == true) radioButtonCheck[2] = 1;
        else radioButtonCheck[2] = 0;
        if(radioButtonNonNecessity.isChecked() == true) radioButtonCheck[3] = 1;
        else radioButtonCheck[3] = 0;
    }

    public void setRadioButton(){
        if(radioButtonCheck[0] == 1) {
            radioButtonIncome.setChecked(true);
            radioButtonNecessity.setVisibility(View.GONE);
            radioButtonNonNecessity.setVisibility(View.GONE);
        }
        else radioButtonIncome.setChecked(false);
        if(radioButtonCheck[1] == 1) {
            radioButtonExpense.setChecked(true);
            radioButtonNecessity.setVisibility(View.VISIBLE);
            radioButtonNonNecessity.setVisibility(View.VISIBLE);
        }
        else radioButtonExpense.setChecked(false);
        if(radioButtonCheck[2] == 1) radioButtonNecessity.setChecked(true);
        else radioButtonNecessity.setChecked(false);
        if(radioButtonCheck[3] == 1) radioButtonNonNecessity.setChecked(true);
        else radioButtonNonNecessity.setChecked(false);
    }

    public void setGlobalVariable(){
        checkRadioButton();
        money = editTextMoney.getText().toString();
        content = editTextContent.getText().toString();

        gv.setMoney(money);
        gv.setContent(content);
        gv.setRadioButtonCheck(radioButtonCheck);
        gv.setTypeItem(typeItem);
        gv.setFrequencyItem(frequencyItem);
        gv.setLayoutId(layoutId);

    }

    public void getGlobalVariable(){
        money = gv.getMoney();
        content = gv.getContent();
        radioButtonCheck = gv.getRadioButtonCheck();
        typeItem = gv.getTypeItem();
        frequencyItem = gv.getFrequencyItem();
        layoutId = gv.getLayoutId();

        date = gv.getDate();
    }

    public void setNumber(){
        editTextMoney.setText(money);
        editTextContent.setText(content);
        setRadioButton();
        spinnerType.setSelection(allType.indexOf(typeItem));
        spinnerFrequency.setSelection(Arrays.asList(item).indexOf(frequencyItem));
        if(radioButtonCheck[1] == 1){
            radioButtonNecessity.setVisibility(View.VISIBLE);
            radioButtonNonNecessity.setVisibility(View.VISIBLE);
        }
        else{
            radioButtonNecessity.setVisibility(View.GONE);
            radioButtonNonNecessity.setVisibility(View.GONE);
        }
    }

    //SpinnerFrequency item select listener.
    private AdapterView.OnItemSelectedListener itemselect = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            frequencyItem = item[position];
            //Toast.makeText(Edit.this, "選擇項目:"+position,
            //Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    //SpinnerType item select listener.
    private AdapterView.OnItemSelectedListener typeItemSelect = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            final View new_type = LayoutInflater.from(Edit.this).inflate(R.layout.new_type, null);
            if(position==(allType.size()-1)){

                new AlertDialog.Builder(Edit.this)
                        .setTitle("輸入新增項目:")//設定視窗標題
                        //.setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                        //.setMessage("這是一個對話視窗")//設定顯示的文字
                        .setView(new_type)

                        .setNegativeButton("確定",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText editNew = (EditText) new_type.findViewById(R.id.editText);
                                String newItem = editNew.getText().toString();
                                allType.add(0,newItem);
                                //spinnerType.setSelection(allType.size()-4);
                                spinnerType.setSelection(allType.indexOf(newItem));
                                typeItem=newItem;

                            }
                        })//設定結束的子視窗確定
                        .setPositiveButton("取消",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();//呈現對話視窗
            }
            else{
                typeItem=allType.get(position).toString();

                //Toast.makeText(Edit.this, "選擇項目:"+typeItem,
                //Toast.LENGTH_LONG).show();

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    //Write the type items to xml.
    @Override
    protected void onPause() {
        super.onPause();
        StringBuilder sb = new StringBuilder();
        for(String string : allType){
            sb.append(string).append(",");
        }
        SharedPreferences pref = getSharedPreferences("typeItem",MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = pref.edit();
        prefEdit.putString("item",sb.toString());
        prefEdit.commit();
    }
}