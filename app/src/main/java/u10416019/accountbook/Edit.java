package u10416019.accountbook;

/**
 * Created by wenqi on 2017/12/13.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private EditText editTextMoney, editTextContent;
    private RadioButton radioButtonIncome, radioButtonExpense, radioButtonNecessity, radioButtonNonNecessity;
    private Spinner spinnerType, spinnerFrequency;
    private Button buttonOK;

    private Intent intent = new Intent();
    private Bundle bundle = new Bundle();
    private int[] radioButtonCheck = new int[4];
    private String money, content;

    private String frequencyItem = "";
    private String[] item = {"無","每週一次","每兩週一次","每月一次"};
    private List<String> allType;
    private String[] getType;
    private String type = "早餐,午餐,晚餐,交通,新增";
    private ArrayAdapter<String> typeAdapter;
    private String typeItem;
    private boolean editData = false;
    private int layoutId;

    GlobalVariable gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        intent=this.getIntent();

        gv = (GlobalVariable)getApplicationContext();
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


        //ArrayAdapter<CharSequence> arrAdapter = ArrayAdapter.createFromResource(Edit.this,item,Spinner.MODE_DIALOG);
        spinnerFrequency.setOnItemSelectedListener(itemselect);
        //editTextContent.setText(money);

        allType = new ArrayList<String>();


        SharedPreferences prefGet = getSharedPreferences("typeItem",MODE_PRIVATE);
        String typeString = prefGet.getString("item",type);
        getType = typeString.split(",");

        if(typeString!=null){
            for(String string : getType){
                allType.add(string);
            }
        }

        typeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, allType);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);
        //spinnerType.setOnItemSelectedListener(itemselect);

        spinnerType.setOnItemSelectedListener(typeItemSelect);


        getGlobalVariable();
        setNumber();

        buttonOK.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
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
                        startActivity(intent);
                    }
                });

    }

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
        if(radioButtonCheck[0] == 1) radioButtonIncome.setChecked(true);
        else radioButtonIncome.setChecked(false);
        if(radioButtonCheck[1] == 1) radioButtonExpense.setChecked(true);
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
    }

    public void setNumber(){
        editTextMoney.setText(money);
        editTextContent.setText(content);
        setRadioButton();
        spinnerType.setSelection(allType.indexOf(typeItem));
        spinnerFrequency.setSelection(Arrays.asList(item).indexOf(frequencyItem));
    }

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
                Toast.makeText(Edit.this, "選擇項目:"+typeItem,
                        Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

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
