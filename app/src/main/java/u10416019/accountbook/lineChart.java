package u10416019.accountbook;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Eva on 2018/1/3.
 */

public class lineChart extends Fragment {
    XYMultipleSeriesDataset mDataset;
    // 多個系列的數據集合,即多條線的數據集合
    XYSeries series;
    // 一個系列的數據，即一條線的數據集合

    XYMultipleSeriesRenderer mRenderer;
    // 多個系列的環境渲染，即整個畫折線的區域
    XYSeriesRenderer r;
    // 一個系列的環境渲染，即一條線的環境渲染
    GraphicalView view;
    // 整個view
    int i = 5;

    LinearLayout linLayout;
    TextView date;
    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy");
    private View v;
    int getTotal[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.line_chart, container, false);
        date = (TextView) v.findViewById(R.id.date);
        updateDate();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(),listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        linLayout = (LinearLayout)this.getView().findViewById(R.id.linearLayout);
        setDateForLineChart();
    }
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateDate();
            setDateForLineChart();
        }
    };

    private void updateDate(){
        date.setText(dateFormat.format(calendar.getTime()));
    }

    public void setDateForLineChart(){
        MyDataBase dataBase = MyDataBase.getInstance(v.getContext());
        getTotal = dataBase.getMonthTotalOfYear(date.getText().toString());
        lineView(getTotal);
    }

    public void lineView(int[] getTotal) {
        linLayout.removeView(view);
        // 同樣是需要數據dataset和視圖渲染器renderer
        mDataset = new XYMultipleSeriesDataset();
        series = new XYSeries("");
        for(int i=0;i<12;i++){
            series.add(i+1,getTotal[i]);
        }
        mDataset.addSeries(series);

        mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(getResources().getColor(R.color.mintcream));
        mRenderer.setMarginsColor(getResources().getColor(R.color.white));
        mRenderer
                .setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        // 設置圖表的X軸的當前方向
        mRenderer.setXTitle("月份");// 設置為X軸的標題
        mRenderer.setYTitle("金額");// 設置y軸的標題
        mRenderer.setAxisTitleTextSize(50);// 設置軸標題文本大小
        mRenderer.setChartTitle("ChartTest");// 設置圖表標題
        mRenderer.setChartTitleTextSize(30);// 設置圖表標題文字的大小
        mRenderer.setLabelsTextSize(50);// 設置標簽的文字大小
        mRenderer.setLegendTextSize(50);// 設置圖例文本大小
        mRenderer.setPointSize(20f);// 設置點的大小
        mRenderer.setYAxisMin(0);// 設置y軸最小值是0
        mRenderer.setYAxisMax(getMaxY(getTotal));
        mRenderer.setYLabels(10);// 設置Y軸刻度個數（貌似不太準確）
        mRenderer.setXAxisMin(0);
        mRenderer.setXAxisMax(5);//設置X軸坐標個數

        mRenderer.setLabelsColor(getResources().getColor(R.color.green));
        mRenderer.setXLabelsColor(getResources().getColor(R.color.colorAccent));

        // 將x標簽欄目顯示如：1,2,3,4替換為顯示1月，2月，3月，4月
        for(int i =1;i<13;i++){
            mRenderer.addXTextLabel(i,i+"月");
        }
        mRenderer.setXLabels(0);// 設置只顯示如1月，2月等替換後的東西，不顯示1,2,3等
        //mRenderer.setMargins(new int[] { 20, 30, 15, 20 });// 設置視圖位置
        mRenderer.setMargins(new int[] { 50, 100, 50, 50 });// 設置視圖位置
        mRenderer.setPanEnabled(true, false);
        // 第一個參數設置X軸是否可滑動，第二個參數設置Y軸是夠可滑動
        r = new XYSeriesRenderer();
        r.setColor(Color.BLUE);// 設置顏色
        r.setPointStyle(PointStyle.CIRCLE);// 設置點的樣式
        r.setFillPoints(true);// 填充點（顯示的點是空心還是實心）
        r.setDisplayChartValues(true);// 將點的值顯示出來
        r.setChartValuesSpacing(20);// 顯示的點的值與圖的距離
        r.setChartValuesTextSize(50);// 點的值的文字大小

        // r.setFillBelowLine(true);//是否填充折線圖的下方
        // r.setFillBelowLineColor(Color.GREEN);//填充的顏色，如果不設置就默認與線的
        //顏色一致
        r.setLineWidth(3);// 設置線寬
        mRenderer.addSeriesRenderer(r);

        view = ChartFactory.getLineChartView(this.getContext(), mDataset, mRenderer);
        //view.setBackgroundColor(Color.BLACK);
        linLayout.addView(view);
        //將畫好折線的view添加到xml中的一個布局裏
    }

    public int getMaxY(int[] getTotal){
        int maxY = 0;
        for(int i=0;i<12;i++){
            if(getTotal[i]>maxY)
                maxY=getTotal[i];
        }
        return maxY+1000;
    }

}
