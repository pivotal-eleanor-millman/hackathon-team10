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
        TextView statusTextView = (TextView) convertView.findViewById(R.id.requestStatus);
        nameTextView.setText(opportunity.getTitle());
        descriptionTextView.setText(opportunity.getDescription());
        statusTextView.setText(opportunity.isAccepted() ? "Volunteer Found" : "Waiting for a Volunteer");
        statusTextView.setCompoundDrawablesWithIntrinsicBounds(opportunity.isAccepted() ? R.mipmap.ic_face : R.mipmap.ic_timelapse, 0, 0, 0);
        return convertView;
    }
}
