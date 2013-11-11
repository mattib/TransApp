package com.transapp.extentions.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.transapp.R;
import com.transapp.extentions.models.TaskItem;
import com.transapp.extentions.models.TaskStatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TasksListAdapter extends ArrayAdapter<TaskItem> {

	private ArrayList<TaskItem> m_taskItems;
	private Context m_context;
	
	public TasksListAdapter(Context context, int textViewResourceId,
			TaskItem[] items) {
		super(context, textViewResourceId, items);
		m_context = context;
		m_taskItems = new ArrayList<TaskItem>(Arrays.asList(items));
	}
	public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_task, null);
        }
        
        //fix after to right
        
        
        TaskItem task = m_taskItems.get(position);
        if (task!= null) {
        	
            TextView taskNameItemView = (TextView) view.findViewById(R.id.taskName);
            if (taskNameItemView != null) {
            	taskNameItemView.setText(task.GetComment());
            }
            
            TextView addressItemView = (TextView) view.findViewById(R.id.address);
            if (addressItemView != null) {
            	addressItemView.setText("כתובות: " + task.GetReciverAddress());
            }
            
            TextView deliveryNumberView = (TextView) view.findViewById(R.id.deliveryNumber);
            if (deliveryNumberView != null) {
            	deliveryNumberView.setText("מספר תעודת משלוח: " + task.GetDeliveryNumber());
            }
            
            TaskStatus taskStatus = task.GetTaskStatus();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    		
            String dateString = "";
            Date deliveryTime = task.GetDeliveryTime();
//            if(deliveryTime != null)
//            {
//            	if(taskStatus.value() >= 3)
//                {
//            		deliveryTime = task.GetDeliveredAt();
//                }
//            }
            if(deliveryTime == null)
            {
            	deliveryTime = new Date();
            }
            
            StringBuilder dateStringBuilder = new StringBuilder( dateFormat.format( deliveryTime ) );
        	dateString = dateStringBuilder.toString();
            
            TextView dateItemView = (TextView) view.findViewById(R.id.date);
            
//            if(taskStatus.value() >= 3)
//            {
//            	if (dateItemView != null) {
//                	dateItemView.setText("זמן מסירה: " + dateString);
//                }
//            }else
//            {
            	if (dateItemView != null) {
                	dateItemView.setText("זמן יעד מסירה: " + dateString);
                }
//            }
            
            TextView statusItemView = (TextView) view.findViewById(R.id.status);
            if (statusItemView != null) {
            	String statusString = "";
            	switch (taskStatus)
            	{
            		case NotStarted: statusString = "לא התחיל";
            				break;
            		case Started: statusString = "התחיל";
    						break;
            		case Accepted: statusString = "התקבל";
    						break;
            		case Rejected: statusString = "נדחה";
    						break;
            		case Finished: statusString = "הסתיים";
							break;
            		
            	}
            	statusItemView.setText("מצב משימה : " + statusString);
            }
         }

        return view;
    }
	
	public int getTaskId(int position)
	{
		TaskItem task = m_taskItems.get(position);
		return task.GetId();
	}
}