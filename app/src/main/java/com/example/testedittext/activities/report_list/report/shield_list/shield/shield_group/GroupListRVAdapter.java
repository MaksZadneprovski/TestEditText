package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testedittext.R;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.enums.Phases;
import com.example.testedittext.utils.CopyClick;
import com.example.testedittext.visual.InstantAutoComplete;

import java.util.List;

public class GroupListRVAdapter extends RecyclerView.Adapter<GroupListRVAdapter.ViewHolder> {

    private final List<Group> groupList;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final EditText tvRecView1, tvRecView2, tvRecView4, tvRecView5, tvRecView6, tvRecView7, tvRecView8, tvRecView9, tvRecView10, tvRecView11, tvRecView12;
        final TextView buttonRv1, buttonRv2, buttonRv3, buttonRv4, buttonRv5, buttonRv6, buttonRv7, buttonRv8, buttonRv9, buttonRv10, buttonRv11, buttonRv12 ;
        final InstantAutoComplete tvRecView3;

        public ViewHolder(View view) {
            super(view);

            tvRecView1 = (EditText) view.findViewById(R.id.tvRecView1);
            tvRecView2 = (EditText) view.findViewById(R.id.tvRecView2);
            tvRecView3 = (InstantAutoComplete) view.findViewById(R.id.tvRecView3);
            tvRecView4 = (EditText) view.findViewById(R.id.tvRecView4);
            tvRecView5 = (EditText) view.findViewById(R.id.tvRecView5);
            tvRecView6 = (EditText) view.findViewById(R.id.tvRecView6);
            tvRecView7 = (EditText) view.findViewById(R.id.tvRecView7);
            tvRecView8 = (EditText) view.findViewById(R.id.tvRecView8);
            tvRecView9 = (EditText) view.findViewById(R.id.tvRecView9);
            tvRecView10 = (EditText) view.findViewById(R.id.tvRecView10);
            tvRecView11 = (EditText) view.findViewById(R.id.tvRecView11);
            tvRecView12 = (EditText) view.findViewById(R.id.tvRecView12);

            buttonRv1 = (TextView) view.findViewById(R.id.buttonRv1);
            buttonRv2 = (TextView) view.findViewById(R.id.buttonRv2);
            buttonRv3 = (TextView) view.findViewById(R.id.buttonRv3);
            buttonRv4 = (TextView) view.findViewById(R.id.buttonRv4);
            buttonRv5 = (TextView) view.findViewById(R.id.buttonRv5);
            buttonRv6 = (TextView) view.findViewById(R.id.buttonRv6);
            buttonRv7 = (TextView) view.findViewById(R.id.buttonRv7);
            buttonRv8 = (TextView) view.findViewById(R.id.buttonRv8);
            buttonRv9 = (TextView) view.findViewById(R.id.buttonRv9);
            buttonRv10 = (TextView) view.findViewById(R.id.buttonRv10);
            buttonRv11 = (TextView) view.findViewById(R.id.buttonRv11);
            buttonRv12 = (TextView) view.findViewById(R.id.buttonRv12);

            // Получаем массив строк из ресурсов
            String[] phases = view.getResources().getStringArray(R.array.phases);


            // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
            ArrayAdapter<String> adapter = new ArrayAdapter (view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, phases);
            tvRecView3.setAdapter(adapter);

        }

    }


    public GroupListRVAdapter(List<Group> groupList) {
        this.groupList = groupList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.group_rv_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        CopyClick copyClick = new CopyClick(position);

        viewHolder.buttonRv1.setOnClickListener(copyClick);
        viewHolder.buttonRv2.setOnClickListener(copyClick);
        viewHolder.buttonRv3.setOnClickListener(copyClick);
        viewHolder.buttonRv4.setOnClickListener(copyClick);
        viewHolder.buttonRv5.setOnClickListener(copyClick);
        viewHolder.buttonRv6.setOnClickListener(copyClick);
        viewHolder.buttonRv7.setOnClickListener(copyClick);
        viewHolder.buttonRv8.setOnClickListener(copyClick);
        viewHolder.buttonRv9.setOnClickListener(copyClick);
        viewHolder.buttonRv10.setOnClickListener(copyClick);
        viewHolder.buttonRv11.setOnClickListener(copyClick);
        viewHolder.buttonRv12.setOnClickListener(copyClick);

        viewHolder.buttonRv1.setOnLongClickListener(copyClick);
        viewHolder.buttonRv2.setOnLongClickListener(copyClick);
        viewHolder.buttonRv3.setOnLongClickListener(copyClick);
        viewHolder.buttonRv4.setOnLongClickListener(copyClick);
        viewHolder.buttonRv5.setOnLongClickListener(copyClick);
        viewHolder.buttonRv6.setOnLongClickListener(copyClick);
        viewHolder.buttonRv7.setOnLongClickListener(copyClick);
        viewHolder.buttonRv8.setOnLongClickListener(copyClick);
        viewHolder.buttonRv9.setOnLongClickListener(copyClick);
        viewHolder.buttonRv10.setOnLongClickListener(copyClick);
        viewHolder.buttonRv11.setOnLongClickListener(copyClick);
        viewHolder.buttonRv12.setOnLongClickListener(copyClick);

        Group group = groupList.get(position);

        viewHolder.tvRecView1.setText(group.getDesignation());
        viewHolder.tvRecView2.setText(group.getAddress());
        viewHolder.tvRecView3.setText(group.getPhases());
        viewHolder.tvRecView4.setText(group.getCable());
        viewHolder.tvRecView5.setText(group.getNumberOfWireCores());
        viewHolder.tvRecView6.setText(group.getWireThickness());
        viewHolder.tvRecView7.setText(group.getDefenseApparatus());
        viewHolder.tvRecView8.setText(group.getMachineBrand());
        viewHolder.tvRecView9.setText(group.getRatedCurrent());
        viewHolder.tvRecView10.setText(group.getReleaseType());
        viewHolder.tvRecView11.setText(group.getF0Range());
        viewHolder.tvRecView12.setText(group.gettSrabAvt());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return groupList != null ? groupList.size() :  0;
    }
}
