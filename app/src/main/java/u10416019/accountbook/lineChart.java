package u10416019.accountbook;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.SimpleDateFormat;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.line_chart, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        linLayout = (LinearLayout)this.getView().findViewById(R.id.linearLayout);
        lineView();
    }

    public void lineView() {
        // 同樣是需要數據dataset和視圖渲染器renderer
        mDataset = new XYMultipleSeriesDataset();
        series = new XYSeries("Frist");
        series.add(1, 6);
        series.add(2, 5);
        series.add(3, 7);
        series.add(4, 4);

        mDataset.addSeries(series);

        mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(getResources().getColor(R.color.mintcream));
        mRenderer.setMarginsColor(getResources().getColor(R.color.white));
        mRenderer
                .setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        // 設置圖表的X軸的當前方向
        mRenderer.setXTitle("X軸");// 設置為X軸的標題
        mRenderer.setYTitle("Y軸");// 設置y軸的標題
        mRenderer.setAxisTitleTextSize(20);// 設置軸標題文本大小
        mRenderer.setChartTitle("ChartTest");// 設置圖表標題
        mRenderer.setChartTitleTextSize(30);// 設置圖表標題文字的大小
        mRenderer.setLabelsTextSize(50);// 設置標簽的文字大小
        mRenderer.setLegendTextSize(50);// 設置圖例文本大小
        mRenderer.setPointSize(20f);// 設置點的大小
        mRenderer.setYAxisMin(0);// 設置y軸最小值是0
        mRenderer.setYAxisMax(15);
        mRenderer.setYLabels(10);// 設置Y軸刻度個數（貌似不太準確）
        mRenderer.setXAxisMin(0);
        mRenderer.setXAxisMax(5);//設置X軸坐標個數

        mRenderer.setLabelsColor(getResources().getColor(R.color.green));
        mRenderer.setXLabelsColor(getResources().getColor(R.color.colorAccent));

        // 將x標簽欄目顯示如：1,2,3,4替換為顯示1月，2月，3月，4月
        mRenderer.addXTextLabel(1, "1月");
        mRenderer.addXTextLabel(2, "2月");
        mRenderer.addXTextLabel(3, "3月");
        mRenderer.addXTextLabel(4, "4月");
        mRenderer.setXLabels(0);// 設置只顯示如1月，2月等替換後的東西，不顯示1,2,3等
        //mRenderer.setMargins(new int[] { 20, 30, 15, 20 });// 設置視圖位置
        mRenderer.setMargins(new int[] { 50, 50, 50, 50 });// 設置視圖位置
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

    private String nowtime() {
        //求當前系統的時分秒
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());

    }
}
