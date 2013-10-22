package com.collage.goddessofskin.prototype.fragment.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import com.collage.goddessofskin.prototype.R;

import android.R.color;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;

public class FragScheduleGraph extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.frag_schedule_graph, null);

		init(view);

		return view;
	}

	private void init(View view) {
		// TODO Auto-generated method stub

		// 표시할 수치값
		List<double[]> values = new ArrayList<double[]>();
		values.add(new double[] { 5, 6, 7, 8, 7, 6, 5 });

		// 그래프 출력을 위한 그래픽 속성 지정객체
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

		// 상단 표시 제목과 글자 크기
		renderer.setChartTitle("일주일 동안의 수면시간");
		renderer.setChartTitleTextSize(20);

		// 분류에 대한 이름
		String[] titles = new String[] { "당일 수면시간" };

		// 항목을 표시하는데 사용 될 색상 값
		int[] colors = new int[] { Color.CYAN };

		// 분류명 글자 크기 및 각 색상 지정
		renderer.setLegendTextSize(12);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}

		// X,Y축 항목이름과 글자크기
		renderer.setXTitle("요일");
		renderer.setYTitle("시간");
		renderer.setAxisTitleTextSize(12);

		// 수치값 글자 크기 /X축 최소,최대 값/Y축 최소,최대 값
		renderer.setLabelsTextSize(10);
		renderer.setXAxisMin(0.5); // 요일로 바꿀 것
		renderer.setXAxisMax(12.5);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(8);

		// X,Y축 라인 색상
		renderer.setAxesColor(Color.WHITE);

		// 상단 제목,X.Y축 제목,수치값의 글자 색상
		renderer.setLabelsColor(Color.CYAN);

		// X축의 표시간격
		renderer.setXLabels(12);
		// Y축의 표시간격
		renderer.setYLabels(5);

		// X,Y축 정렬방향
		renderer.setXLabelsAlign(Align.LEFT);
		renderer.setYLabelsAlign(Align.LEFT);

		// X,Y축 스크롤 여부 ON/OFF
		renderer.setPanEnabled(false, false);

		// ZOOM기능 ON/OFF
		renderer.setZoomEnabled(false, false);

		// ZOOM비율
		renderer.setZoomRate(1.0f);

		// 막대 간 간격
		renderer.setBarSpacing(0.5f);

		// 설정 정보 설정
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		for (int i = 0; i < titles.length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());

		}

		// 그래프 객체 생성
		GraphicalView gv = ChartFactory.getBarChartView(getActivity(), dataset,
				renderer, Type.STACKED);

		// 그래프를 LinearLayout에 추가
		LinearLayout Body = (LinearLayout) view.findViewById(R.id.Body01);
		Body.addView(gv);

	}

	

}
