package com.mromer.windfinder.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mromer.windfinder.R;
import com.mromer.windfinder.bean.Station;
import com.mromer.windfinder.utils.SharedPreferencesUtil;

public class StationListAdapter extends BaseAdapter implements Filterable {

	private Context context;

	private List<Station> listStation;
	private List<Station> listStationFiltered;

	private Map<String, String> stationsSelected;

	public StationListAdapter(Context context, List<Station> listStation) {
		this.context = context;
		this.listStation = listStation;

		stationsSelected = SharedPreferencesUtil.getStationsSelected(context);
		
		listStationFiltered = listStation;
	}


	@Override
	public int getCount() {
		return listStationFiltered.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listStationFiltered.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Station station = listStationFiltered.get(position);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, null);
		}

		TextView name = (TextView) convertView.findViewById(R.id.name);
		//due to italic text style				
		name.setText(station.getName() + " ");  		

		if (stationsSelected.get(station.getId()) != null) {
			// It is selected			
			convertView.setBackgroundColor(context.getResources().getColor(R.color.orange_light));			
		} else {			
			convertView.setBackgroundResource(android.R.drawable.list_selector_background);
		}		

		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {		

		stationsSelected = SharedPreferencesUtil.getStationsSelected(context);

		super.notifyDataSetChanged();
	}


	@Override
	public Filter getFilter() {

		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {

				listStationFiltered = (List<Station>) results.values;
				notifyDataSetChanged();
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {

				FilterResults results = new FilterResults();
				ArrayList<Station> filteredStations = new ArrayList<Station>();

				// perform your search here using the searchConstraint String.

				constraint = constraint.toString().toLowerCase(Locale.getDefault());

				for (int i = 0; i < listStation.size(); i++) {
					Station station = listStation.get(i);
					if (station.getName().toLowerCase().contains(constraint.toString()))  {
						filteredStations.add(station);
					}
				}

				results.count = filteredStations.size();
				results.values = filteredStations;

				return results;				
			}			
		};

		return filter;
	}



}
