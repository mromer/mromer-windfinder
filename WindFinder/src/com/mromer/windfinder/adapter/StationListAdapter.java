package com.mromer.windfinder.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mromer.windfinder.R;
import com.mromer.windfinder.bean.DataType;
import com.mromer.windfinder.utils.SharedPreferencesUtil;

public class StationListAdapter extends GeneralListAdapter {	
	
	private Map<String, String> stationsSelected;

	@SuppressWarnings("unchecked")
	public StationListAdapter(Context context, List<? extends DataType> listStation) {
		
		super(context, (List<DataType>)listStation);		

		stationsSelected = SharedPreferencesUtil.getStationsSelected(context);
		
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		DataType station = getListDataFiltered().get(position);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, null);
		}

		TextView name = (TextView) convertView.findViewById(R.id.name);
		//due to italic text style				
		name.setText(station.getName() + " ");  		

		if (stationsSelected.get(station.getId()) != null) {
			// It is selected			
			convertView.setBackgroundColor(getContext().getResources().getColor(R.color.orange_light));			
		} else {			
			convertView.setBackgroundResource(android.R.drawable.list_selector_background);
		}		

		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {		

		stationsSelected = SharedPreferencesUtil.getStationsSelected(getContext());

		super.notifyDataSetChanged();
	}
	
}
