package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testedittext.R;
import com.example.testedittext.entities.Group;

import java.util.List;

public class GroupListRVAdapter extends RecyclerView.Adapter<GroupListRVAdapter.ViewHolder> {

    private final List<Group> groupList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        final Button button;

        public ViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.tvRecView1);
            button = (Button) view.findViewById(R.id.buttonRv1);
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

        Group group = groupList.get(position);
        viewHolder.textView.setText(group.getName());



        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                notifyDataSetChanged();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return groupList != null ? groupList.size() :  0;
    }
}
