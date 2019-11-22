package com.example.sergio.sistz.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sergio.sistz.BuildConfig;
import com.example.sergio.sistz.R;
import com.example.sergio.sistz.adapter.ReporTableDataAdapter;
import com.example.sergio.sistz.data.TableData;
import com.example.sergio.sistz.mysql.DBUtility;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import org.buraktamturk.loadingview.LoadingView;
import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.util.TypedValue.COMPLEX_UNIT_SP;
import static android.view.View.GONE;

public class ReportElementFragment extends Fragment implements AdapterView.OnItemClickListener{
    public static final int GRAPH_TABLE = 1;
    public static final int GRAPH_LINE = 2;
    public static final int GRAPH_GAUGE = 3;
    public static final int GRAPH_ANYCHART_GAUGE = 4;
    public static final int GRAPH_ANYCHART_LINE = 5;
    public static final int GRAPH_ANYCHART_LINEARGAUGE = 6;
    public static final int GRAPH_ANYCHART_VBARCHART = 7;
    public static final int GRAPH_ANYCHART_GRAYGAUGE = 8;
    public static final int GRAPH_ANYCHART_PIECHART = 9;
    public static final int GRAPH_ANYCHART_LINE_Y_SCALE = 10;
    @BindView(R.id.main_layout) LinearLayout mainLayout;
    @BindView(R.id.ll_table_layout) LinearLayout llTable;
    @BindView(R.id.layout_title) LinearLayout llTitle;
    @BindView(R.id.layout_graph) LinearLayout llGraph;
    @BindView(R.id.rl_fit_chart_small) RelativeLayout rlFitChart;
    @BindView(R.id.txt_fit_chart_small) TextView txtFitChart;
    @BindView(R.id.graph_fit_chart_small) FitChart fitChart;
    @BindView(R.id.rl_fit_chart_big) RelativeLayout rlFitChartB;
    @BindView(R.id.txt_fit_chart_big) TextView txtFitChartB;
    @BindView(R.id.graph_fit_chart_big) FitChart fitChartB;
    @BindView(R.id.txtTitle) TextView txtTitle;
    @BindView(R.id.graph_table) ListView graphList;
    @BindView(R.id.graph_line_chart) LineChart lineChart;
    @BindView(R.id.txt_table_message) TextView tableMessage;
    @BindView(R.id.ll_line_chart) LinearLayout layLineChart;
    private WebView webview;

    private int typeOfGraphic, widthWeb, heightWeb;
    private String title, displayFit, xTitle, yTitle;
    private JSONArray jsonArrayValues;
    private List<TableData> tableData;
    private int percentGauge, greenMin, greenMax, yellowMin, yellowMax, redMin, redMax ;
    private IFragmentActions fragmentActions;
    private List<Entry> yValues;
    private boolean small;
    private DBUtility conn;
    private LoadingView loadingView;
    private String textValue;
    private float valueDecimal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.report_element_fragment_layout, null);
        ButterKnife.bind(this, v);
        setGraphics();
        txtTitle.setText(title);
        return v;
    }

    public void setInformationAnyChartLinearGauge(String title, String textValue, int value, int width, int height, IFragmentActions fragmentActions) {
        this.typeOfGraphic = GRAPH_ANYCHART_LINEARGAUGE;
        this.title = title;
        this.percentGauge = value;
        this.fragmentActions = fragmentActions;
        this.widthWeb = width;
        this.heightWeb = height;
        this.textValue = textValue;

    }
    public void setInformationTable(List<TableData> tableData, String title, IFragmentActions fragmentActions) {
        this.typeOfGraphic = GRAPH_TABLE;
        this.title = title;
        this.tableData = tableData;
        this.fragmentActions = fragmentActions;
    }

    public void setInformationGauge(String title, String display, int percentGauge, int greenMin, int greenMax, int yellowMin, int yellowMax, int redMin, int redMax, boolean small,IFragmentActions fragmentActions) {
        this.typeOfGraphic = GRAPH_GAUGE;
        this.title = title;
        this.percentGauge = percentGauge;
        this.greenMin = greenMin;
        this.greenMax = greenMax;
        this.yellowMin = yellowMin;
        this.yellowMax = yellowMax;
        this.redMin = redMin;
        this.redMax = redMax;
        this.displayFit = display;
        this.fragmentActions = fragmentActions;
        this.small = small;
    }

    public void setInformationLineChart(String title, List<Entry> yValues, IFragmentActions fragmentActions) {
        this.typeOfGraphic = GRAPH_LINE;
        this.title = title;
        this.fragmentActions = fragmentActions;
        this.yValues = yValues;
    }

    public void setInformationAnyChartGauge( String title, int value, int width, int height, IFragmentActions fragmentActions) {
        this.typeOfGraphic = GRAPH_ANYCHART_GAUGE;
        this.title = title;
        this.fragmentActions = fragmentActions;
        this.percentGauge = value;
        this.widthWeb = width;
        this.heightWeb = height;
    }

    public void setInformationAnyChartLine(String title, String xTitle, String yTitle,  JSONArray value,int width, int height, IFragmentActions fragmentActions ) {
        this.typeOfGraphic = GRAPH_ANYCHART_LINE;
        this.title = title;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        this.jsonArrayValues = value;
        this.widthWeb = width;
        this.heightWeb = height;
        this.fragmentActions = fragmentActions;
    }
    public void setInformationAnyChartLineYRange(String title, String xTitle, String yTitle,  JSONArray value,int width, int height, IFragmentActions fragmentActions ) {
        this.typeOfGraphic = GRAPH_ANYCHART_LINE_Y_SCALE;
        this.title = title;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        this.jsonArrayValues = value;
        this.widthWeb = width;
        this.heightWeb = height;
        this.fragmentActions = fragmentActions;
    }
    public void setInformationAnyChartVerticalBarChart(String title, String xTitle, String yTitle, JSONArray value, int width, int height, IFragmentActions fragmentActions ) {
        this.typeOfGraphic = GRAPH_ANYCHART_VBARCHART;
        this.title = title;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        this.jsonArrayValues = value;
        this.widthWeb = width;
        this.heightWeb = height;
    }

    public void setInformationAnyChartPieChart(String title, String xTitle, String yTitle, JSONArray value, int width, int height, IFragmentActions fragmentActions ) {
        this.typeOfGraphic = GRAPH_ANYCHART_PIECHART;
        this.title = title;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        this.jsonArrayValues = value;
        this.widthWeb = width;
        this.heightWeb = height;
    }

    public void setInformationAnyChartGrayGauge(String title, String textValue, float value, int width, int height,IFragmentActions fragmentActions ) {
        this.title = title;
        this.textValue = textValue;
        this.valueDecimal = value;
        this.widthWeb = width;
        this.heightWeb = height;
        this.fragmentActions = fragmentActions;
        this.typeOfGraphic = GRAPH_ANYCHART_GRAYGAUGE;
    }

    public void setGraphics() {
        if (typeOfGraphic == GRAPH_TABLE) {
            llTitle.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.transparent));
            txtTitle.setTextColor(Color.parseColor("#000000"));
            llTable.setVisibility(View.VISIBLE);
            rlFitChart.setVisibility(GONE);
            rlFitChartB.setVisibility(GONE);
            layLineChart.setVisibility(GONE);
            if (title.equals("")) {
                llTitle.setVisibility(GONE);
            }
            if (tableData.size() == 1) {
                graphList.setVisibility(GONE);
                tableMessage.setVisibility(View.VISIBLE);
            } else {
                graphList.setAdapter(new ReporTableDataAdapter(getActivity(), tableData));
                graphList.setDividerHeight(0);
                tableMessage.setVisibility(GONE);
                graphList.setVisibility(View.VISIBLE);
                graphList.setOnItemClickListener(this);
            }
        } else if (typeOfGraphic == GRAPH_GAUGE){
            llTitle.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.transparent));
            llTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            txtTitle.setTextColor(Color.parseColor("#000000"));
            txtTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            txtTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            llTable.setVisibility(GONE);
            txtTitle.setTextSize(COMPLEX_UNIT_SP, 18);
            rlFitChart.setVisibility(View.VISIBLE);
            layLineChart.setVisibility(GONE);
            setGauge();
        } else if (typeOfGraphic == GRAPH_LINE) {
            llTable.setVisibility(GONE);
            rlFitChartB.setVisibility(GONE);
            rlFitChart.setVisibility(GONE);
            layLineChart.setVisibility(View.VISIBLE);
            setLineChart();
        } else if (typeOfGraphic == GRAPH_ANYCHART_GAUGE) {
            txtTitle.setVisibility(GONE);
            llTable.setVisibility(View.GONE);
            rlFitChart.setVisibility(GONE);
            rlFitChartB.setVisibility(GONE);
            layLineChart.setVisibility(GONE);
            setWebGauge(title, percentGauge,widthWeb, heightWeb, this.getActivity());
        } else if (typeOfGraphic == GRAPH_ANYCHART_LINE) {
            txtTitle.setVisibility(GONE);
            llTable.setVisibility(View.GONE);
            rlFitChart.setVisibility(GONE);
            rlFitChartB.setVisibility(GONE);
            layLineChart.setVisibility(GONE);
            setWebLineChart(jsonArrayValues, title, xTitle,yTitle,widthWeb, heightWeb);
        } else if (typeOfGraphic == GRAPH_ANYCHART_LINEARGAUGE) {
            txtTitle.setVisibility(GONE);
            llTable.setVisibility(View.GONE);
            rlFitChart.setVisibility(GONE);
            rlFitChartB.setVisibility(GONE);
            layLineChart.setVisibility(GONE);
            setWebLinearGauge();
        } else if (typeOfGraphic == GRAPH_ANYCHART_VBARCHART){
            txtTitle.setVisibility(GONE);
            llTable.setVisibility(View.GONE);
            rlFitChart.setVisibility(GONE);
            rlFitChartB.setVisibility(GONE);
            layLineChart.setVisibility(GONE);
            setWebVerticalBarChart();
        } else if (typeOfGraphic == GRAPH_ANYCHART_PIECHART){
            txtTitle.setVisibility(GONE);
            llTable.setVisibility(View.GONE);
            rlFitChart.setVisibility(GONE);
            rlFitChartB.setVisibility(GONE);
            layLineChart.setVisibility(GONE);
            setWebPieChart();
        } else if (typeOfGraphic == GRAPH_ANYCHART_GRAYGAUGE){
            txtTitle.setVisibility(GONE);
            llTable.setVisibility(View.GONE);
            rlFitChart.setVisibility(GONE);
            rlFitChartB.setVisibility(GONE);
            layLineChart.setVisibility(GONE);
            setWebGrayGauge();
        } else if(typeOfGraphic == GRAPH_ANYCHART_LINE_Y_SCALE) {
            txtTitle.setVisibility(GONE);
            llTable.setVisibility(View.GONE);
            rlFitChart.setVisibility(GONE);
            rlFitChartB.setVisibility(GONE);
            layLineChart.setVisibility(GONE);
            setWebLineChartYRange(jsonArrayValues, title, xTitle,yTitle,widthWeb, heightWeb);
        }
    }

    private void setGauge() {
        Collection<FitChartValue> values = new ArrayList<>();
        int color = ((int) Color.parseColor("#226100"));
        if (percentGauge >= greenMin && percentGauge <= greenMax) {
            values.add(new FitChartValue(percentGauge - greenMin, Color.parseColor("#226100")));
        } else if (percentGauge > greenMax) {
            values.add(new FitChartValue(greenMax - greenMin, Color.parseColor("#226100")));
        }
        if (percentGauge >= yellowMin && percentGauge <= yellowMax) {
            values.add(new FitChartValue(percentGauge - yellowMin, Color.YELLOW));
            color = Color.YELLOW;
        } else if (percentGauge > yellowMax ) {
            values.add(new FitChartValue(yellowMax - yellowMin, Color.YELLOW));
        }
        if (percentGauge >= redMin && percentGauge <= redMax) {
            values.add(new FitChartValue(percentGauge - redMin, Color.RED));
            color = Color.RED;
        } else if (percentGauge > redMax ) {
            values.add(new FitChartValue(redMax - redMin, Color.RED));
        }
        if (small) {
            rlFitChartB.setVisibility(GONE);
            fitChartB.setVisibility(GONE);
            txtFitChartB.setVisibility(GONE);
            fitChart.setMinValue(greenMin);
            fitChart.setMaxValue(redMax);
            fitChart.setValues(values);
            txtFitChart.setText(displayFit);
            txtFitChart.setTextColor(color);
        } else {
            rlFitChart.setVisibility(GONE);
            fitChart.setVisibility(GONE);
            txtFitChart.setVisibility(GONE);
            fitChartB.setMinValue(greenMin);
            fitChartB.setMaxValue(redMax);
            fitChartB.setValues(values);
            txtFitChartB.setText(displayFit);
            txtFitChartB.setTextColor(color);
        }

    }

    public List<String> getLabels() {
        conn = new DBUtility(getActivity());
        SimpleDateFormat df = new SimpleDateFormat("MMMM", conn.getCurrentLocale());
        List<String> labels = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        for (int i = 0;i < 12; i ++) {
            cal.set(Calendar.MONTH, i);
            labels.add(df.format(cal.getTime()));
        }
        return labels;
    }

    private void setLineChart() {
        Description description = new Description();
        description.setText(title);
        lineChart.setPadding(20,20,20,20);
        lineChart.setNoDataText("No data available");
        lineChart.setHighlightPerTapEnabled(true);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setPinchZoom(true);
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(getLabels()));
        lineChart.setScaleY(1);
        YAxis yaxis = lineChart.getAxisLeft(), yaxis2 = lineChart.getAxisRight();
        yaxis.setAxisMaximum(100);
        yaxis.setAxisMinimum(0);
        yaxis.setTextSize(12);
        yaxis2.setEnabled(false);
        yaxis2.setTextColor(Color.WHITE);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setAxisLineColor(Color.parseColor("#FF0000"));
        lineChart.getXAxis().setGridLineWidth(0f);
        lineChart.getXAxis().setGridColor(Color.parseColor("#00000000"));
        lineChart.getXAxis().setTextSize(12);
        lineChart.getAxisLeft().setGridLineWidth(0f);
        lineChart.getAxisLeft().setGridColor(Color.parseColor("#E4E4E4"));
        lineChart.getAxisLeft().setGridColor(Color.WHITE);
        lineChart.getAxisLeft().setAxisLineColor(Color.parseColor("#000000"));
        lineChart.getXChartMax();
        lineChart.animateY(1000);
        LineData data;

        Legend l = lineChart.getLegend();


        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(getContext().getResources().getColor(R.color.tzColorGreyDark));
        l.setTextSize(17);


        LineDataSet set1 = new LineDataSet(yValues, "Assistance");
        set1.setColor(Color.RED); // LINEA DE GRAFICA Y MARCA DE LEYENDA
        set1.setCircleColor(getContext().getResources().getColor(R.color.tzColorGreyDark));
        set1.setCircleColorHole(Color.WHITE);
        set1.setLineWidth(6.0f);
        set1.setValueTextColor(getContext().getResources().getColor(R.color.tzColorGreyDark));
        set1.setValueTextSize(15.0f);

        set1.setFillAlpha(110);

        data = new LineData(set1);
        data.setValueTextColor(getContext().getResources().getColor(R.color.tzColorGreyDark));

        lineChart.setData(data);
    }

    private void setWebLinearGauge() {
        loadingView = new LoadingView(getActivity());
        loadingView.setLoading(true);
        loadingView.setText(getResources().getString(R.string.loading_message));
        webview = new WebView(this.getActivity());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 550));
        webview.setWebChromeClient(
                new WebChromeClient());

        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(
                    WebView view,
                    String url)
            {
                loadWebLinearGauge();
                loadingView.setVisibility(GONE);
                loadingView.setLoading(false);
                mainLayout.removeView(loadingView);
            }
        });

        // note the mapping from  file:///android_asset
        // to Android-D3jsPieChart/assets or
        // Android-D3jsPieChart/app/src/main/assets
        webview.loadUrl("file:///android_asset/" +
                "html/pichart.html");
        webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mainLayout.addView(loadingView, 0);
        mainLayout.addView(webview,0);
    }

    private  void setWebGrayGauge() {
        loadingView = new LoadingView(getActivity());
        loadingView.setLoading(true);
        loadingView.setText(getResources().getString(R.string.loading_message));
        webview = new WebView(this.getActivity());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        webview.setWebChromeClient(
                new WebChromeClient());

        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(
                    WebView view,
                    String url)
            {
                loadWebGrayGauge();
                webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 350));
                loadingView.setVisibility(GONE);
                loadingView.setLoading(false);
                mainLayout.removeView(loadingView);
            }
        });

        webview.loadUrl("file:///android_asset/" +
                "html/pichart.html");
        webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mainLayout.addView(loadingView, 0);
        mainLayout.addView(webview,0);
    }

    private void setWebVerticalBarChart() {
        loadingView = new LoadingView(getActivity());
        loadingView.setLoading(true);
        loadingView.setText(getResources().getString(R.string.loading_message));
        webview = new WebView(this.getActivity());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 550));
        webview.setWebChromeClient(
                new WebChromeClient());

        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(
                    WebView view,
                    String url)
            {
                loadWebVerticalBarChart();
                loadingView.setVisibility(GONE);
                loadingView.setLoading(false);
                mainLayout.removeView(loadingView);
            }
        });



        // note the mapping from  file:///android_asset
        // to Android-D3jsPieChart/assets or
        // Android-D3jsPieChart/app/src/main/assets
        webview.loadUrl("file:///android_asset/" +
                "html/pichart.html");
        webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mainLayout.addView(loadingView, 0);
        mainLayout.addView(webview,0);
    }

    private void setWebPieChart() {
        loadingView = new LoadingView(getActivity());
        loadingView.setLoading(true);
        loadingView.setText(getResources().getString(R.string.loading_message));
        webview = new WebView(this.getActivity());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 550));
        webview.setWebChromeClient(
                new WebChromeClient());

        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(
                    WebView view,
                    String url)
            {
                loadWebPieChart2();
                loadingView.setVisibility(GONE);
                loadingView.setLoading(false);
                mainLayout.removeView(loadingView);
            }
        });

        webview.loadUrl("file:///android_asset/" +
                "html/pichart.html");
        webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mainLayout.addView(loadingView, 0);
        mainLayout.addView(webview,0);
    }

    private void setWebLineChart (final JSONArray data, final String title, final String xTitle, final String yTitle, final int width, final int height) {
        loadingView = new LoadingView(getActivity());
        loadingView.setLoading(true);
        loadingView.setText(getResources().getString(R.string.loading_message));
        webview = new WebView(getActivity());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 550));
        webview.setWebChromeClient(
                new WebChromeClient());

        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(
                    WebView view,
                    String url)
            {
                loadWebLineChart(data, width, height, title, xTitle, yTitle);
                loadingView.setVisibility(GONE);
                loadingView.setLoading(false);
                mainLayout.removeView(loadingView);
            }
        });

        // note the mapping from  file:///android_asset
        // to Android-D3jsPieChart/assets or
        // Android-D3jsPieChart/app/src/main/assets
        webview.loadUrl("file:///android_asset/" +
                "html/pichart.html");
        webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mainLayout.addView(loadingView, 0);
        mainLayout.addView(webview,0);
    }
    private void setWebLineChartYRange (final JSONArray data, final String title, final String xTitle, final String yTitle, final int width, final int height) {
        loadingView = new LoadingView(getActivity());
        loadingView.setLoading(true);
        loadingView.setText(getResources().getString(R.string.loading_message));
        webview = new WebView(getActivity());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 550));
        webview.setWebChromeClient(
                new WebChromeClient());

        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(
                    WebView view,
                    String url)
            {
                loadWebLineChartYRange(data, width, height, title, xTitle, yTitle);
                loadingView.setVisibility(GONE);
                loadingView.setLoading(false);
                mainLayout.removeView(loadingView);
            }
        });

        // note the mapping from  file:///android_asset
        // to Android-D3jsPieChart/assets or
        // Android-D3jsPieChart/app/src/main/assets
        webview.loadUrl("file:///android_asset/" +
                "html/pichart.html");
        webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mainLayout.addView(loadingView, 0);
        mainLayout.addView(webview,0);
    }

    private void setWebGauge(final String title, final int value, final int width, final int height, Context context) {
        loadingView = new LoadingView(getActivity());
        loadingView.setLoading(true);
        loadingView.setText(getResources().getString(R.string.loading_message));
        webview = new WebView(context);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 550));
        webview.setWebChromeClient(
                new WebChromeClient());

        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(
                    WebView view,
                    String url)
            {
                loadPieChart(value, width, height);
                loadingView.setVisibility(GONE);
                loadingView.setLoading(false);
                mainLayout.removeView(loadingView);
            }
        });

        // note the mapping from  file:///android_asset
        // to Android-D3jsPieChart/assets or
        // Android-D3jsPieChart/app/src/main/assets
        webview.loadUrl("file:///android_asset/" +
                "html/pichart.html");
        webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mainLayout.addView(loadingView, 0);
        mainLayout.addView(webview,0);
    }

    private void loadPieChart(int value, int width, int height) {
        webview.loadUrl("javascript:loadGaugeChart(" + value + ", " + width + ", " + height + ",'<b>" + title + "</b>','" + BuildConfig.AnyChartAPIKey + "')");
    }

    private void loadWebLineChart(JSONArray data, int width, int height, String title, String xTitle, String yTitle) {
        webview.loadUrl("javascript:loadLineChart(" + data.toString() + ", " + width + ", " + height + ",'<b>" + title + "</b>','" + xTitle + "','" + yTitle + "','" + BuildConfig.AnyChartAPIKey + "')");
    }
    private void loadWebLineChartYRange(JSONArray data, int width, int height, String title, String xTitle, String yTitle) {
        webview.loadUrl("javascript:loadLineChartYScale(" + data.toString() + ", " + width + ", " + height + ",'<b>" + title + "</b>','" + xTitle + "','" + yTitle + "','" + BuildConfig.AnyChartAPIKey + "')");
    }

    private void loadWebLinearGauge() {
        webview.loadUrl("javascript:loadLinearGaugeChart(" + percentGauge + ", '" + textValue + "', " + widthWeb + "," + heightWeb + ",'" + title + "','" + BuildConfig.AnyChartAPIKey + "')");
    }

    private void loadWebGrayGauge() {
        webview.loadUrl("javascript:loadGrayGaugeChart(" + valueDecimal + ", '" + textValue + "', " + widthWeb + "," + heightWeb + ",'" + title + "','" + BuildConfig.AnyChartAPIKey + "')");
    }

    private void loadWebVerticalBarChart() {
        webview.loadUrl("javascript:loadBarChart(" + ((jsonArrayValues == null)?"[]":jsonArrayValues.toString()) + "," + widthWeb + "," + heightWeb + ",'" + title + "','" + yTitle + "','" + xTitle + "','" + BuildConfig.AnyChartAPIKey + "')");
    }

    private void loadWebPieChart2() {
        webview.loadUrl("javascript:loadPieChart(" + ((jsonArrayValues == null)?"[]":jsonArrayValues.toString()) + "," + widthWeb + "," + heightWeb + ",'" + title + "','" + yTitle + "','" + xTitle + "','" + BuildConfig.AnyChartAPIKey + "')");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        fragmentActions.onClickListener(((TableData)tableData.get(position)));
    }
}
