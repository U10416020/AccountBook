package u10416019.accountbook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.GraphicalView;

/**
 * Created by Eva on 2018/1/3.
 */

public class pieChart extends Fragment {
    private GraphicalView mChartView;//顯示PieChart

    private LinearLayout mLinear;//布局方式

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Record mainActivity = (Record) activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pie_chart, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLinear = (LinearLayout) this.getView().findViewById(R.id.linearLayout);

    }
}
