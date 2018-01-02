package u10416019.accountbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.R.drawable.btn_dialog;
import static android.R.drawable.btn_star_big_off;
import static android.R.drawable.btn_star_big_on;

/**
 * Created by Eva on 2017/12/19.
 */

public class addLinLayout extends LinearLayout {
    LinearLayout newLayout,thisLayout;
    ImageView star;
    TextView viewKind,viewContent,viewMoney;
    Context getContext;
    ImageButton imageButton;

    String money,content,typeItem;
    long id;
    int income, necessary;
    MyDataBase myDBHelper;
    Bundle bundle = new Bundle();

    public addLinLayout(Context context,String money,String content,String typeItem,long id,int income, int necessary,MyDataBase myDBHelper) {
        super(context);
        getContext=context;
        this.money = money;
        this.content = content;
        this.typeItem = typeItem;
        this.id=id;
        this.income=income;
        this.necessary=necessary;
        this.myDBHelper=myDBHelper;
        this.setOrientation(LinearLayout.VERTICAL);
        this.addView(onInit(context));
    }

    public LinearLayout onInit(Context context){
        newLayout = new LinearLayout(context);
        newLayout.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        //newLayout.setPadding(8,10,5,10);
        newLayout.setOrientation(LinearLayout.HORIZONTAL);

        star = new ImageView(context);
        if(necessary==0)
            star.setImageResource(btn_star_big_off);
        else if (necessary==1)
            star.setImageResource(btn_star_big_on);
        star.setMaxHeight(100);
        star.setMaxWidth(100);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 80,0.3f);
        lp.setMargins(20,0,5,0);
        star.setLayoutParams(lp);


        viewKind = new TextView(context);
        viewKind.setTextSize(20);
        viewKind.setText(typeItem);
        viewKind.setGravity(1);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(75, LinearLayout.LayoutParams.WRAP_CONTENT,0.44f);
        lp1.setMargins(15,0,8,0);
        viewKind.setLayoutParams(lp1);

        viewContent = new TextView(context);
        viewContent.setTextSize(20);
        viewContent.setText(content);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(150, LinearLayout.LayoutParams.WRAP_CONTENT,0.70f);
        lp2.setMargins(15,0,8,0);
        viewContent.setLayoutParams(lp2);

        viewMoney = new TextView(context);
        viewMoney.setTextSize(20);

        if(income==0){
            viewMoney.setText("-"+money);
            viewMoney.setTextColor(getResources().getColor(R.color.red));
        }
        else if(income==1){
            viewMoney.setText("+"+money);
            viewMoney.setTextColor(getResources().getColor(R.color.green));
        }

        viewMoney.setGravity(1);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT,0.56f);
        lp3.setMargins(15,0,8,0);
        viewMoney.setLayoutParams(lp3);

        imageButton = new ImageButton(context);
        imageButton.setImageResource(btn_dialog);
        LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(60, 60,0.1f);
        lp4.setMargins(0,0,8,0);
        imageButton.setLayoutParams(lp4);
        imageButton.setOnClickListener(imgBtn);

        newLayout.addView(star);
        newLayout.addView(viewKind);
        newLayout.addView(viewContent);
        newLayout.addView(viewMoney);
        newLayout.addView(imageButton);
        newLayout.setOnClickListener(linClick);
        return newLayout;
    }

    //Delete the data from database and delete the layout.
    private ImageButton.OnClickListener imgBtn = new ImageButton.OnClickListener(){
        public void onClick(View V){
            myDBHelper.delete(id);
            removeAllViews();
        }
    };

    //Click the layout to edit the data.
    private LinearLayout.OnClickListener linClick = new LinearLayout.OnClickListener(){
        public void onClick(View V){
            Intent intent = new Intent();
            intent.setClass(getContext,Edit.class);
            intent.putExtra("id",id);

            getContext.startActivity(intent);
        }
    };


}
