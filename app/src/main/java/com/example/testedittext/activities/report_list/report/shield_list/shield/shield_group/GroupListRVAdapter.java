package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testedittext.R;
import com.example.testedittext.entities.Group;
import com.example.testedittext.utils.CopyClick;

import java.util.List;

public class GroupListRVAdapter extends RecyclerView.Adapter<GroupListRVAdapter.ViewHolder> {

    private final List<Group> groupList;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final EditText editText1, editText2;
        final TextView button1, button2 ;

        public ViewHolder(View view) {
            super(view);

            editText1 = (EditText) view.findViewById(R.id.tvRecView1);
            button1 = (TextView) view.findViewById(R.id.buttonRv1);
            editText2 = (EditText) view.findViewById(R.id.tvRecView2);
            button2 = (TextView) view.findViewById(R.id.buttonRv2);
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

        viewHolder.button1.setOnClickListener(copyClick);
        viewHolder.button1.setOnLongClickListener(copyClick);

        viewHolder.button2.setOnClickListener(copyClick);
        viewHolder.button2.setOnLongClickListener(copyClick);

        viewHolder.editText2.setText(groupList.get(position).getAddress());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return groupList != null ? groupList.size() :  0;
    }
}
