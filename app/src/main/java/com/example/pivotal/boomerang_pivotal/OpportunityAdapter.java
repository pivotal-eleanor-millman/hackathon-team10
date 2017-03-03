package com.example.pivotal.boomerang_pivotal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pivotal.boomerang_pivotal.model.Opportunity;

import java.util.List;

public class OpportunityAdapter extends ArrayAdapter<Opportunity>{

    public OpportunityAdapter(Context context, List<Opportunity> opportunities) {
        super(context, 0, opportunities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Opportunity opportunity = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_opportunity, parent, false);
        }
        TextView nameTextView = (TextView) convertView.findViewById(R.id.requestTitle);
        TextView descriptionTextView = (TextView) convertView.findViewById(R.id.requestDescription);
        nameTextView.setText(opportunity.getTitle());
        descriptionTextView.setText(opportunity.getDescription());
        return convertView;
    }
}
