package com.to_do_list;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper DB_helper;
    ArrayAdapter<String> adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB_helper = new DatabaseHelper(this);
        list = findViewById(R.id.listview);
        
        show_task_list();
    }

    private void show_task_list() {
        ArrayList list_task = DB_helper. getTaskList();
        if(adapter == null)
        {
            adapter = new ArrayAdapter<String>(this,R.layout.item_menu,R.id.textView,list_task);
            list.setAdapter(adapter);
        }
        else
        {
            adapter.clear();
            adapter.addAll(list_task);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case R.id.list_item:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add new Task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = taskEditText.getText().toString();
                                DB_helper.insert_new_task(task);
                                show_task_list();
                            }
                        })
                        .setNegativeButton("CANCEL",null)
                        .create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View view){
        View parent = (View) view.getParent();
        TextView taskText = parent.findViewById(R.id.textView);
        String task = taskText.getText().toString();
        DB_helper.delete_task(task);
        show_task_list();
    }
}
