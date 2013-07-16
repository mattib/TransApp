package com.transapp.extentions.activities;

import com.managment.TasksManager;
import com.transapp.R;
import com.transapp.R.layout;
import com.transapp.extentions.models.TaskItem;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TasksListViewActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FillTasksViewList();
	}
	
	@Override
	  protected void onResume() {
		//m_taskDataSource.open();
	    super.onResume();
	    FillTasksViewList();
	  }
	
//	public void onContentChanged () {
//		
//		FillTasksViewList();
//	}
	

	private void FillTasksViewList() {
		
		int userId = GetUserId();
		
		TasksManager tasksManager= new TasksManager(this);
		
		tasksManager.LoadUsersTasks(userId);
		
		TaskItem[] items = tasksManager.GetTasks(userId);
		
		TasksListAdapter adapter = new TasksListAdapter(this, R.layout.activity_task, items);
		
		this.setListAdapter(adapter);
		
		final ListView lv = getListView();
		
		lv.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view,
	              int position, long id) {
	        	  TaskItem taskItem = (TaskItem) lv.getItemAtPosition(position);

	              Intent intent = new Intent(getApplicationContext(), TaskDetailsActivity.class);

	              intent.putExtra("taskId", taskItem.GetId());
	              startActivity(intent);
	          }
		});
	}
	
	 private int GetUserId(){
		    SharedPreferences sharedPreferences = this.getSharedPreferences("com.transapp.extentions", Context.MODE_PRIVATE);
		    int result = sharedPreferences.getInt("CurrentUserId", 0);
		    return result;
		   }
}


