package com.example.testedittext.activities.report_list;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.server.Server;
import com.example.testedittext.entities.Efficiency;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

// Тут колбэком вызывается метод drawGraphic() и сразу efficiencyEntities присваивается список статистики всех логинов

public class StatisticsActivity extends AppCompatActivity implements ResponseEfficiencyListFromServerCallback, SwipeRefreshLayout.OnRefreshListener  {

    LineChart lineChart;
    BarChart barChart;

    private Button selectDateButton;
    private Button selectTimeButton;
    private Button selectPeople;
    private Button selectLineWidthButton ;
    private Button selectGraphicPlus;
    private Button showButton;
    private Button selectGraphicTotal;
    private TextView title;
    private SimpleDateFormat dateFormat;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GestureDetector gestureDetector;

    private boolean isLineGraphic = true;
    private boolean isTotalBarData = true;

    private int durationOfAnim = 500;

    ResponseEfficiencyListFromServerCallback responseEfficiencyListFromServerCallback;

    List<Efficiency> efficiencyEntities;
    List<String> logins;

    Date startDate;
    Date endDate;
    float lineWidth = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);

        responseEfficiencyListFromServerCallback = this;
        lineChart = findViewById(R.id.lineChart);
        barChart = findViewById(R.id.barChart);
        selectDateButton = findViewById(R.id.selectDateButton);
        selectTimeButton = findViewById(R.id.selectTimeButton);
        showButton = findViewById(R.id.showButton);
        selectGraphicPlus = findViewById(R.id.selectGraphicPlus);
        selectPeople = findViewById(R.id.selectPeople);
        selectLineWidthButton = findViewById(R.id.selectLineWidth);
        selectGraphicTotal = findViewById(R.id.selectGraphicTotal);
        title = findViewById(R.id.title);

        //Для обновления при свайпе
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        gestureDetector = new GestureDetector(this, new SwipeGestureListener());

        logins = new ArrayList<>();

        new Server().getEfficiency(this);

        selectLineWidthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Изменение значения переменной int от 1 до 5
                if (lineWidth < 5) {
                    lineWidth++;
                    setData();
                } else {
                    lineWidth = 1;
                    setData();
                }
            }
        });

        // For Animation
        // Установите обработчик событий для кнопки, при нажатии на которую кнопки будут появляться или исчезать
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectDateButton.getVisibility() == View.VISIBLE) {
                    hideButtons();
                } else {
                    showButtons();
                }
            }
        });

        // Установите обработчик событий для корневого макета, чтобы кнопки исчезали при нажатии на любое место экрана
        lineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectDateButton.getVisibility() == View.VISIBLE) {
                    hideButtons();
                }
            }
        });

        // Установите обработчик событий для корневого макета, чтобы кнопки исчезали при нажатии на любое место экрана
        barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectDateButton.getVisibility() == View.VISIBLE) {
                    hideButtons();
                }
            }
        });

    }


    private void setData() {
        // Создаем форматтер даты и времени
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        // Получаем отформатированную строку с датой и временем
        String startDateString = dateFormat.format(startDate);
        String endDateString = dateFormat.format(endDate);

        if (isLineGraphic) {
            title.setText("Количество групп в отчёте\nc "+ startDateString + "   по   " + endDateString);
            lineChart.setVisibility(View.VISIBLE);
            barChart.setVisibility(View.GONE);
            LineData lineData = new LineData(getILineDataSet());
            lineChart.setData(lineData);

            // Customize the appearance of the chart
            lineChart.getDescription().setText(" ");
            lineChart.getDescription().setTextSize(13);
            lineChart.getDescription().setXOffset(4);
            lineChart.getDescription().setYOffset(4);
            lineChart.getDescription().setTextColor(Color.BLACK);
            //lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
            lineChart.setDrawBorders(true);
            lineChart.setBorderColor(Color.BLACK);
            lineChart.setBorderWidth(1);


            // Получите ссылку на легенду вашего графика
            Legend legend = lineChart.getLegend();
            // Включите перенос текста в легенде на новую строку
            legend.setWordWrapEnabled(true);
            legend.setEnabled(true);
            legend.setForm(Legend.LegendForm.CIRCLE);


            // Получите ссылку на ось X вашего графика
            XAxis xAxis = lineChart.getXAxis();


            // Находим максимум и минимум по Х
            float axisMaximum = Float.NEGATIVE_INFINITY;
            float axisMinimum = Float.POSITIVE_INFINITY;
            List<ILineDataSet> dataSets = lineData.getDataSets();

            for (ILineDataSet dataSet : dataSets) {
                for (int j = 0; j < dataSet.getEntryCount(); j++) {
                    Entry entry = dataSet.getEntryForIndex(j);
                    float x = entry.getX();
                    if (x > axisMaximum) {
                        axisMaximum = x;
                    }
                    if (x < axisMinimum) {
                        axisMinimum = x;
                    }
                }
            }

            // Установите максимальное значение оси X на значение последней точки плюс некоторое смещение
             axisMaximum += 0.5f; // Пример: смещение на 0.5
             axisMinimum -= 0.5f; // Пример: смещение на 0.5

            xAxis.setAxisMaximum(axisMaximum);
            xAxis.setAxisMinimum(axisMinimum);

            // Refresh the chart
            lineChart.invalidate();

        }else {
            title.setText("Количество внесенных групп\nc "+ startDateString + "   по   " + endDateString);
            barChart.setVisibility(View.VISIBLE);
            lineChart.setVisibility(View.GONE);
            BarData barData = null;

            if (isTotalBarData){
                barData = new BarData(getTotalIBarDataSet());
            }else {
                barData = new BarData(getIBarDataSet());
            }

            barChart.getDescription().setText(" ");
            barChart.setData(barData);
            barChart.getLegend().setEnabled(true);
            barChart.setDrawBorders(true);
            barChart.setBorderColor(Color.BLACK);
            barChart.setBorderWidth(1);
            barChart.animateY(durationOfAnim);

            Legend legend = barChart.getLegend();
            // Включите перенос текста в легенде на новую строку
            legend.setWordWrapEnabled(true);
            legend.setEnabled(true);
            legend.setForm(Legend.LegendForm.CIRCLE);

            barChart.invalidate();
        }
    }

    public Map<String, Map<String, List<Efficiency>>> getGroupedEfficiency(){
        // Map<Логин, Map< Название отчета, List<Efficiency>>>
        Map<String, Map<String, List<Efficiency>>> groupedEfficiency = new LinkedHashMap<>();

        for (Efficiency data : efficiencyEntities) {
            String login = data.getLogin();
            String reportname = data.getReportName();

            if (groupedEfficiency.containsKey(login)) {
                Map<String, List<Efficiency>> innerMap = groupedEfficiency.get(login);

                if (innerMap != null && innerMap.containsKey(reportname)) {
                    Objects.requireNonNull(innerMap.get(reportname)).add(data);
                } else {
                    List<Efficiency> newList = new ArrayList<>();
                    newList.add(data);
                    if (innerMap != null) {
                        innerMap.put(reportname, newList);
                    }
                }
            } else {
                List<Efficiency> newList = new ArrayList<>();
                newList.add(data);
                Map<String, List<Efficiency>> innerMap = new HashMap<>();
                innerMap.put(reportname, newList);
                groupedEfficiency.put(login, innerMap);
            }
        }
        return groupedEfficiency;
    }

    public List<IBarDataSet> getTotalIBarDataSet() {
        List<IBarDataSet> barDataSets = new ArrayList<>();
        Map<String, Integer> loginCountMap = new HashMap<>();
        Map<String, Map<String, List<Efficiency>>> groupedEfficiency = getGroupedEfficiency();
        // Создайте список разных цветов для каждого DataSet
        List<Integer> colors = generateRandomColors(efficiencyEntities.size());
        // Для рандомного цвета
        int index = 0;

        for (Map.Entry<String, Map<String, List<Efficiency>>> entry : groupedEfficiency.entrySet()) {
            String login = entry.getKey();
            Map<String, List<Efficiency>> reportMap = entry.getValue();

            int totalLoginCount = 0; // Общее суммарное значение countLine для каждого логина

            for (Map.Entry<String, List<Efficiency>> reportEntry : reportMap.entrySet()) {
                String reportName = reportEntry.getKey();
                List<Efficiency> efficiencyList = reportEntry.getValue();

                int maxPreviousCountLine = 0;
                int maxCurrentCountLine = 0;
                for (Efficiency efficiency : efficiencyList) {
                    String timestampString = efficiency.getTimestamp();

                    Date timestamp = null;
                    try {
                        timestamp = dateFormat.parse(timestampString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Проверяем, попадает ли в заданный период
                    if (!timestamp.before(startDate) && !timestamp.after(endDate)) {
                        int currentCountLine = efficiency.getCountLine();
                        if (currentCountLine>maxCurrentCountLine) maxCurrentCountLine = currentCountLine;

                    }
                    if (timestamp.before(startDate)){
                        int previousCountLine = efficiency.getCountLine();
                        if (previousCountLine>maxPreviousCountLine) maxPreviousCountLine = previousCountLine;
                    }
                }

                // Вычисляем прирост как разницу между текущим и предыдущим значением countLine
                if (maxCurrentCountLine> maxPreviousCountLine) {
                    int growth = maxCurrentCountLine - maxPreviousCountLine;
                    totalLoginCount += growth; // Добавляем прирост к общему суммарному значению для логина
                }
            }
            loginCountMap.put(login, totalLoginCount); // Добавляем суммарное значение countLine для текущего логина в Map
        }

        // Создаем объекты BarEntry и добавляем их в список IBarDataSet для каждого логина
        int reportCount  = 0;
        for (Map.Entry<String, Integer> loginEntry : loginCountMap.entrySet()) {
            String login = loginEntry.getKey();
            int countLine = loginEntry.getValue();

            BarEntry barEntry = new BarEntry(reportCount, countLine);
            BarDataSet barDataSet = new BarDataSet(Collections.singletonList(barEntry), login);
            int colorIndex = index % colors.size(); // Индекс цвета из списка
            int color = colors.get(colorIndex);
            barDataSet.setColor(color);
            barDataSets.add(barDataSet);
            reportCount +=1;
            index +=1;
        }

        return barDataSets;
    }



    public List<IBarDataSet> getIBarDataSet() {
        // Его возвращаем в итоге
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        Map<String, Map<String, List<Efficiency>>> groupedEfficiency = getGroupedEfficiency();

        // Создайте список разных цветов для каждого DataSet
        List<Integer> colors = generateRandomColors(efficiencyEntities.size());

        // Для рандомного цвета
        int index = 0;

        int reportCount = 1;

        // Map<Логин, Map< Название отчета, List<Efficiency>>>
        // Проход по логинам
        for (Map.Entry<String, Map<String, List<Efficiency>>> entry : groupedEfficiency.entrySet()) {
            String login = entry.getKey();

            // Добавляем логин в список, чтобы потом использовать в фильтре
            if (!logins.contains(login)) {
                logins.add(login);
            }



            // Проход по отчетам
            for (Map.Entry<String, List <Efficiency>> entryInner : entry.getValue().entrySet()) {
                boolean isReportCountIterate = false;
                String reportName = entryInner.getKey();
                List<Efficiency> efficiencyList = entryInner.getValue();

                List<BarEntry> entries = new ArrayList<>();

                try {
                    // Для хранения максимального количества строк в отчете нужной даты
                    int maxCurrentCountLine = 0;
                    // Для хранения макс. кол-во строк отчета даты, до указанной, чтобы потом вычесть это число
                    int maxCountLineBeforeDate = 0;

                    int countLineBeforeDate = 0;

                    // Проход по объектам эффективности
                    for (int i = 0; i < efficiencyList.size(); i++) {

                        String timestampString = efficiencyList.get(i).getTimestamp();

                        Date timestamp = dateFormat.parse(timestampString);
                        int currentCountLine = efficiencyList.get(i).getCountLine();
                        // Filter data based on the selected date range
                        if (timestamp != null && timestamp.before(endDate)) {
                            if (currentCountLine > maxCurrentCountLine) maxCurrentCountLine = currentCountLine;
                        }
                        if (timestamp != null && timestamp.before(startDate)){
                            countLineBeforeDate = efficiencyList.get(i).getCountLine();
                            if (countLineBeforeDate > maxCountLineBeforeDate){
                                maxCountLineBeforeDate = countLineBeforeDate;
                            }
                        }

                    }

                    if (maxCurrentCountLine - maxCountLineBeforeDate > 0) {
                        // Добавляем в ентриес количество линий за выбранную дату - количество линий до этой даты
                        entries.add(new BarEntry(reportCount, maxCurrentCountLine - maxCountLineBeforeDate));
                        isReportCountIterate = true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                BarDataSet barDataSet = new BarDataSet(entries,login +" "+reportCount);
                int colorIndex = index % colors.size(); // Индекс цвета из списка
                int color = colors.get(colorIndex);
                barDataSet.setColor(color);

                dataSets.add(barDataSet);
                index +=1;
                if (isReportCountIterate) {
                    reportCount +=1;
                }
            }

        }
        logins = logins.stream().sorted().collect(Collectors.toList());

        return dataSets;
    }

    // Метод возвращает список линий графика, разделенные по логинам и названиям отчетов
    public List<ILineDataSet> getILineDataSet() {

        // Его возвращаем в итоге
        List <ILineDataSet> dataSets = new ArrayList<>();

        Map<String, Map<String, List<Efficiency>>> groupedEfficiency = getGroupedEfficiency();

        // Создайте список разных цветов для каждого DataSet
        List<Integer> colors = generateRandomColors(efficiencyEntities.size());

        // Для рандомного цвета
        int index = 0;

        // Проход по элементам HashMap с использованием entrySet()
        // Проходим по HashMap по каждому логину и формируем List<Entry> entries для каждого логина (Это набор точек x, y координат)
        // После этого каждый List<Entry> формируем в LineDataSet lineDataSet, а каждый lineDataSet добавляем в  ArrayList<ILineDataSet> dataSets
        // Плюс тут они сразу фильтруются по дате и времени
        for (Map.Entry<String, Map<String, List<Efficiency>>> entry : groupedEfficiency.entrySet()) {
            String login = entry.getKey();

            // Добавляем логин в список, чтобы потом использовать в фильтре
            if (!logins.contains(login)) {
                logins.add(login);
            }

            int reportCount = 1;

            for (Map.Entry<String, List<Efficiency>> entryInner : entry.getValue().entrySet()) {
                boolean isReportCountIterate = false;
                List<Efficiency> efficiencyList = entryInner.getValue();

                List<Entry> entries = new ArrayList<>();

                // Находим наименьшую дату, для того чтобы организовать переполнение часов
                Optional<Long> minMilliSecondsSinceStartOptional = efficiencyList.stream()
                        .map(e -> {
                            try {
                                Date timestamp = dateFormat.parse(e.getTimestamp());
                                return timestamp.after(startDate) ? timestamp.getTime() : Long.MAX_VALUE;
                            } catch (ParseException ex) {
                                ex.printStackTrace();
                                return null;
                            }
                        })
                        .min(Comparator.naturalOrder());

                long minMilliSecondsSinceStart = minMilliSecondsSinceStartOptional.orElseThrow(() -> new RuntimeException("minHoursSinceStartOptional is empty"));

                for (Efficiency efficiency : efficiencyList) {
                    String timestampString = efficiency.getTimestamp();
                    try {
                        Date timestamp = dateFormat.parse(timestampString);
                        if (timestamp != null && timestamp.after(startDate) && timestamp.before(endDate)) {

                            int hoursSinceMinimalTime = (int)(((int)(timestamp.getTime() - minMilliSecondsSinceStart)) / (1000 * 60 * 60));
                            int countLine = efficiency.getCountLine();
                            entries.add(new Entry((float)(hoursSinceMinimalTime) , countLine));
                            isReportCountIterate = true;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                LineDataSet lineDataSet = new LineDataSet(entries,login +" "+reportCount);
                int colorIndex = index % colors.size(); // Индекс цвета из списка
                int color = colors.get(colorIndex);
                lineDataSet.setColor(color);

                lineDataSet.setDrawCircleHole(false);
                lineDataSet.setDrawCircles(false);
                lineDataSet.setDrawValues(false);

                lineDataSet.setValueTextColor(Color.BLACK);

                // Установите толщину линии
                lineDataSet.setLineWidth(lineWidth); // Здесь 2f - это значение толщины линии (в пикселях)
                dataSets.add(lineDataSet);
                index +=1;
                if (isReportCountIterate) reportCount +=1;
            }
        }
        logins = logins.stream().sorted().collect(Collectors.toList());
        return dataSets;
    }

    public void drawGraphic() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());

        if (startDate == null && endDate == null ){
            startDate = new Date();
            endDate = new Date();
            System.out.println(startDate.toString());

            // Set the time of the start date to 00:00
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            startCalendar.set(Calendar.HOUR_OF_DAY, 0);
            startCalendar.set(Calendar.MINUTE, 0);
            startDate = startCalendar.getTime();

            // Set the time of the end date to 00:00
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            startCalendar.set(Calendar.HOUR_OF_DAY, 23);
            startCalendar.set(Calendar.MINUTE, 59);
            endDate = endCalendar.getTime();
        }

        setData();

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateRangePicker();
                hideButtons();
            }
        });

        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeRangePicker();
                hideButtons();
            }
        });

        // Обработчик нажатия на кнопку selectPeople
        selectPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPeopleSelectionDialog();
            }
        });

        // Обработчик нажатия на кнопку selectGraphic
        selectGraphicPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGraphic();
            }
        });

        // Обработчик нажатия на кнопку selectGraphic
        selectGraphicTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGraphicTotalOrNot();
                hideButtons();
            }
        });


    }

    @Override
    public void callbackCall(List<Efficiency> efficiencyEntities) {
        this.efficiencyEntities = efficiencyEntities;
        drawGraphic();
    }

    private void showDateRangePicker() {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        builder.setCalendarConstraints(constraintsBuilder.build());
        MaterialDatePicker<Pair<Long, Long>> picker = builder.build();

        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                startDate = new Date(selection.first);
                endDate = new Date(selection.second);

                // Set the time of the start date to 00:00
                Calendar startCalendar = Calendar.getInstance();
                startCalendar.setTime(startDate);
                startCalendar.set(Calendar.HOUR_OF_DAY, 0);
                startCalendar.set(Calendar.MINUTE, 0);
                startCalendar.set(Calendar.SECOND, 0);
                startDate = startCalendar.getTime();

                // Set the time of the end date to 00:00
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(endDate);
                endCalendar.set(Calendar.HOUR_OF_DAY, 23);
                endCalendar.set(Calendar.MINUTE, 59);
                endCalendar.set(Calendar.SECOND, 59);
                endDate = endCalendar.getTime();

                // Update chart data based on the selected date range
                setData();
            }
        });

        picker.show(getSupportFragmentManager(), picker.toString());
    }



    private void showTimeRangePicker() {
        TimePickerDialog.OnTimeSetListener startTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Update the start time and show the end time picker
                Calendar startCalendar = Calendar.getInstance();
                startCalendar.setTime(startDate);
                startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                startCalendar.set(Calendar.MINUTE, minute);
                startDate = startCalendar.getTime();

                showEndTimePicker();
            }
        };

        // Show the start time picker
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        int startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
        int startMinute = startCalendar.get(Calendar.MINUTE);
        TimePickerDialog startTimePicker = new TimePickerDialog(this, startTimeListener, startHour, startMinute, true);
        startTimePicker.show();
    }

    private void showEndTimePicker() {
        TimePickerDialog.OnTimeSetListener endTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Update the end time and update the chart data
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(endDate);
                endCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                endCalendar.set(Calendar.MINUTE, minute);
                endDate = endCalendar.getTime();

                setData();
            }
        };

        // Show the end time picker
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        int endHour = endCalendar.get(Calendar.HOUR_OF_DAY);
        int endMinute = endCalendar.get(Calendar.MINUTE);
        TimePickerDialog endTimePicker = new TimePickerDialog(this, endTimeListener, endHour, endMinute, true);
        endTimePicker.show();
    }

    private List<Integer> generateRandomColors(int count) {
        List<Integer> colors = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
            colors.add(color);
        }

        return colors;
    }


    private void showButtons() {
        // Показать кнопки с помощью анимации
        if (isTotalBarData) selectGraphicTotal.setText("По отчетам");
        else selectGraphicTotal.setText("Суммарно");
        selectDateButton.setVisibility(View.VISIBLE);
        selectTimeButton.setVisibility(View.VISIBLE);
        if (isLineGraphic) selectLineWidthButton.setVisibility(View.VISIBLE);
        else selectLineWidthButton.setVisibility(View.GONE);
        if (!isLineGraphic) selectGraphicTotal.setVisibility(View.VISIBLE);
        else selectGraphicTotal.setVisibility(View.GONE);
        selectPeople.setVisibility(View.VISIBLE);
        selectDateButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
        selectTimeButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
        if (isLineGraphic) selectLineWidthButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
        if (!isLineGraphic) selectGraphicTotal.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
        selectPeople.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
        showButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out));
        showButton.setVisibility(View.INVISIBLE);

    }

    private void hideButtons() {
        // Скрыть кнопки с помощью анимации
        showButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
        showButton.setVisibility(View.VISIBLE);
        selectTimeButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out));
        selectDateButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out));
        selectLineWidthButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out));
        selectPeople.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out));
        selectDateButton.setVisibility(View.GONE);
        selectTimeButton.setVisibility(View.GONE);
        selectLineWidthButton.setVisibility(View.GONE);
        selectPeople.setVisibility(View.GONE);
        selectGraphicTotal.setVisibility(View.GONE);


    }

    // Метод для отображения всплывающего окна с выбором списка людей
    private void showPeopleSelectionDialog() {
        // Здесь нужно использовать ваш список людей или другой источник данных
        String[] peopleList = null;
        // Создаем массив строк такого же размеры, что и входной ArrayList
        peopleList = logins.toArray(new String[logins.size()]);

        // Индексы выбранных элементов списка
        final ArrayList<Integer> selectedItems = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] finalPeopleList = peopleList;

        builder.setTitle("Выберите аккаунты,статистика которых будет отображена на графике")
                .setMultiChoiceItems(peopleList, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    selectedItems.add(which);
                                } else if (selectedItems.contains(which)) {
                                    selectedItems.remove(Integer.valueOf(which));
                                }
                            }
                        })
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Обработка нажатия кнопки "Ок"
                        // Возвращение выбранных элементов списка
                        ArrayList<String> selectedPeople = new ArrayList<>();
                        for (int index : selectedItems) {
                            selectedPeople.add(finalPeopleList[index]);
                        }
                        // Выполнение нужных действий с выбранными элементами списка
                        new Server().getEfficiencyByLoginIn(responseEfficiencyListFromServerCallback, selectedPeople);
                        hideButtons();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Обработка нажатия кнопки "Отмена"
                        dialog.dismiss();
                        hideButtons();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void changeGraphic(){
        isLineGraphic = !isLineGraphic;
        setData();
    }

    public void changeGraphicTotalOrNot(){
        isTotalBarData = !isTotalBarData;
        setData();
    }


    // Для обновления при свайпе

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onRefresh() {
        // Метод, который будет вызываться при свайпе вниз
        setData();
        swipeRefreshLayout.setRefreshing(false);
    }

    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            boolean result = false;
            float diffY = event2.getY() - event1.getY();
            float diffX = event2.getX() - event1.getX();
            if (Math.abs(diffX) < Math.abs(diffY) && event1.getY() < swipeRefreshLayout.getTop()) {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        // Свайп вниз
                        onRefresh();
                    }
                    result = true;
                }
            }
            return result;
        }
    }

}