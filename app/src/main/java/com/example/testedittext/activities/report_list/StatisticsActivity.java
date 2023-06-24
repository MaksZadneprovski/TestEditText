package com.example.testedittext.activities.report_list;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.server.Server;
import com.example.testedittext.entities.Efficiency;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

// Тут колбэком вызывается метод drawGraphic() и сразу efficiencyEntities присваивается список статистики всех логинов

public class StatisticsActivity extends AppCompatActivity implements ResponseEfficiencyListFromServerCallback {

    LineChart lineChart;
    private Button selectDateButton;
    private Button selectTimeButton;
    private Button selectPeople;
    private Button selectLineWidthButton ;
    Button showButton;
    private SimpleDateFormat dateFormat;

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
        selectDateButton = findViewById(R.id.selectDateButton);
        selectTimeButton = findViewById(R.id.selectTimeButton);
        showButton = findViewById(R.id.showButton);
        selectPeople = findViewById(R.id.selectPeople);
        selectLineWidthButton = findViewById(R.id.selectLineWidth);

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
    }


    private void setData() {

        LineData lineData = new LineData(getILineDataSet());
        lineChart.setData(lineData);

        // Customize the appearance of the chart
        lineChart.getDescription().setText("График количества линий");
        //lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        lineChart.getLegend().setEnabled(true);
        // Refresh the chart
        lineChart.invalidate();
    }

    // Метод возвращает список линий графика, разделенные по логинам и названиям отчетов
    public List<ILineDataSet> getILineDataSet() {

        // Его возвращаем в итоге
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        Map<String, Map<String, List<Efficiency>>> groupedEfficiency = new HashMap<>();

        for (Efficiency data : efficiencyEntities) {
            String login = data.getLogin();
            String reportname = data.getReportName();

            if (groupedEfficiency.containsKey(login)) {
                Map<String, List<Efficiency>> innerMap = groupedEfficiency.get(login);
                if (innerMap.containsKey(reportname)) {
                    innerMap.get(reportname).add(data);
                } else {
                    List<Efficiency> newList = new ArrayList<>();
                    newList.add(data);
                    innerMap.put(reportname, newList);
                }
            } else {
                List<Efficiency> newList = new ArrayList<>();
                newList.add(data);
                Map<String, List<Efficiency>> innerMap = new HashMap<>();
                innerMap.put(reportname, newList);
                groupedEfficiency.put(login, innerMap);
            }
        }

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

                List<Efficiency> efficiencyList = entryInner.getValue();

                List<Entry> entries = new ArrayList<>();

                try {

                    for (int i = 0; i < efficiencyList.size(); i++) {

                        String timestampString = efficiencyList.get(i).getTimestamp();

                        Date timestamp = dateFormat.parse(timestampString);

                        // Filter data based on the selected date range
                        if (timestamp.after(startDate) && timestamp.before(endDate)) {
                            float xValue = timestamp.getHours(); // x-axis value as timestamp in milliseconds
                            int countLine = efficiencyList.get(i).getCountLine();
                            entries.add(new Entry(xValue, countLine));
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                LineDataSet lineDataSet = new LineDataSet(entries,login +" "+reportCount);
                int colorIndex = index % colors.size(); // Индекс цвета из списка
                int color = colors.get(colorIndex);
                lineDataSet.setColor(color);
                lineDataSet.setValueTextColor(Color.BLACK);
                // Установите толщину линии
                lineDataSet.setLineWidth(lineWidth); // Здесь 2f - это значение толщины линии (в пикселях)
                dataSets.add(lineDataSet);
                index +=1;
                reportCount +=1;
            }
        }
        logins = logins.stream().sorted().collect(Collectors.toList());
        return dataSets;
    }

    public void drawGraphic() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
        startDate = new Date();
        endDate = new Date();

        // Set the time of the start date to 00:00
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        startCalendar.set(Calendar.YEAR, 1900);
        startDate = startCalendar.getTime();

        // Set the time of the end date to 00:00
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        startCalendar.set(Calendar.YEAR, 2040);
        endDate = endCalendar.getTime();

        setData();

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateRangePicker();
            }
        });

        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeRangePicker();
            }
        });

        // Обработчик нажатия на кнопку selectPeople
        selectPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPeopleSelectionDialog();
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


    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }


    private void showButtons() {
        // Показать кнопки с помощью анимации
        selectDateButton.setVisibility(View.VISIBLE);
        selectTimeButton.setVisibility(View.VISIBLE);
        selectLineWidthButton.setVisibility(View.VISIBLE);
        selectPeople.setVisibility(View.VISIBLE);
        selectDateButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
        selectTimeButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
        selectLineWidthButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
        selectPeople.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
        showButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out));
        showButton.setVisibility(View.GONE);

    }

    private void hideButtons() {
        // Скрыть кнопки с помощью анимации
        selectTimeButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out));
        selectDateButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out));
        selectLineWidthButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out));
        selectPeople.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out));
        selectDateButton.setVisibility(View.GONE);
        selectTimeButton.setVisibility(View.GONE);
        selectLineWidthButton.setVisibility(View.GONE);
        selectPeople.setVisibility(View.GONE);
        showButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
        showButton.setVisibility(View.VISIBLE);

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

        builder.setTitle("Выберите аккаунты для отображения на графике")
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
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Обработка нажатия кнопки "Отмена"
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}