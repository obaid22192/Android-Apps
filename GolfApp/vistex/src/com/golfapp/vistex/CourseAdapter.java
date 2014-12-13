package com.golfapp.vistex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CourseAdapter extends BaseAdapter {


	Context context ;
	String distance = " ";
	private ArrayList<GolfCourse> listcourses;
	ArrayList<DistanceCalculaor> DistanceList;
    public CourseAdapter(Context c, ArrayList<GolfCourse> golfcourses) {
		// TODO Auto-generated constructor stub
    	context = c;
    	listcourses = golfcourses;
	}
    public CourseAdapter(Context c, ArrayList<DistanceCalculaor> DC , int dummy){
    	context = c;
    	DistanceList = DC;
    	listcourses = new ArrayList<GolfCourse>();
    	Collections.sort(DistanceList, new Comparator<DistanceCalculaor>() {
          @Override
			public int compare(DistanceCalculaor lhs, DistanceCalculaor rhs) {
				// TODO Auto-generated method stub
				return Float.toString(lhs.getDistance()).compareTo(Float.toString(rhs.getDistance()));
			}
		});
    	ListCourses();
    	
    	
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listcourses.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listcourses.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		LayoutInflater inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		if(row == null) // optimization of dataadapter
		{
		 row =inflater.inflate( R.layout.showallcoursesinlist,parent,false);
		}
		ImageView imageview = (ImageView) row.findViewById(R.id.imageViewflag);
		TextView tvcoursename = (TextView) row.findViewById(R.id.tvcoursename);
		TextView tvcountry = (TextView) row.findViewById(R.id.tvcountryname);
		TextView tvcity = (TextView) row.findViewById(R.id.tvcity);
		TextView tvhole = (TextView) row.findViewById(R.id.tvholes);
		GolfCourse tempcourse = listcourses.get(position);
		String  HoleCount= Integer.toString(tempcourse.getHolecount()) ;
		tvcoursename.setText(tempcourse.getName());
		tvcountry .setText(tempcourse.getCountry());
		tvcity.setText(tempcourse.getCity()+" "+ distance);
		tvhole.setText(HoleCount);
		return row;
	}
	public void ListCourses()
	{
		for(DistanceCalculaor d: DistanceList )
		{
			d.getGcourse().setdistence(d.getDistance());
			GolfCourse gtemp = d.getGcourse();
			listcourses.add(gtemp);
			
		}
		
	}
}
