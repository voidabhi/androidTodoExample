package com.example.todoapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class TodoActivity extends Activity {

	
	ArrayList<String> items= null;
	ArrayAdapter<String> itemsAdapter = null;
	ListView lvItems=null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        
        lvItems = (ListView)this.findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        setUpViewListener();
    }
    
    // Adding Listener to the item collections
    
    private void setUpViewListener()
    {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(), "Item Deleted!", Toast.LENGTH_SHORT).show();
				saveItems();
				return true;
			}
    		
    	});
    }
    
    //Adding new todo item to the collection
    
    public void addTodoItem(View v){
    	
    	EditText et=(EditText)this.findViewById(R.id.editText1);
    	itemsAdapter.add(et.getText().toString());
    	et.setText("");
    	saveItems();
    	Toast.makeText(getApplicationContext(), "Item Added!",Toast.LENGTH_SHORT).show();
    	
    }
    
    // reading todo items from the file
    
    private void readItems()
    {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir,"todos.txt");
    	try{
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	}catch(IOException e)
    	{
    		items = new ArrayList<String>();
    		e.printStackTrace();
    	}
    }
    
    // saving todo items from the file
    
    private void saveItems()
    {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir,"todos.txt");
    	try{
    		FileUtils.writeLines(todoFile, items);
    	}catch(IOException e)
    	{
    		items = new ArrayList<String>();
    		e.printStackTrace();
    	}
    }    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }
    
}
