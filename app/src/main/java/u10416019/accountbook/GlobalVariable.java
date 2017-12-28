package u10416019.accountbook;

import android.app.Application;

/**
 * Created by wenqi on 2017/12/19.
 */

public class GlobalVariable extends Application {
    private String money = "";
    private String content = "";
    private int[] radioButtonCheck = {0,0,0,0};
    private String typeItem = "";
    private String frequencyItem = "";
    private int layoutId = -1;

    public void setMoney(String money){
        this.money = money;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setRadioButtonCheck(int[] radioButtonCheck){
        this.radioButtonCheck = radioButtonCheck;
    }
    public void setTypeItem(String typeItem){
        this.typeItem = typeItem;
    }
    public void setFrequencyItem(String frequencyItem){
        this.frequencyItem = frequencyItem;
    }
    public void setLayoutId(int layoutId){
        this.layoutId = layoutId;
    }


    public String getMoney() {
        return money;
    }
    public String getContent() {
        return content;
    }
    public int[] getRadioButtonCheck(){
        return radioButtonCheck;
    }
    public String getTypeItem() {
        return typeItem;
    }
    public String getFrequencyItem(){
        return frequencyItem;
    }
    public int getLayoutId(){
        return layoutId;
    }
}
