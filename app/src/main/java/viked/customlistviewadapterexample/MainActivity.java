package viked.customlistviewadapterexample;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ImageButton upButton;
    ImageButton downButton;
    TextView txtSelectedFolder;


    Drawable upIcon;
    Drawable downIcon;
    Drawable upIconUn;
    Drawable downIconUn;

    FileAdapter adapter;

    FileNavigator fileNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left_black_24dp);
        downIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_right_black_24dp);
        upIconUn = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left_grey600_24dp);
        downIconUn = ContextCompat.getDrawable(this, R.drawable.ic_arrow_right_grey600_24dp);

        fileNavigator = new FileNavigator();
        listView = (ListView) findViewById(R.id.file_list_view);
        txtSelectedFolder = (TextView) findViewById(R.id.txt_selected_folder);
        upButton = (ImageButton) findViewById(R.id.imageButtonUp);
        downButton = (ImageButton) findViewById(R.id.imageButtonDown);

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateView(fileNavigator.goToUpperFolder());
            }
        });
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateView(fileNavigator.goToLowerFolder());
            }
        });
        adapter = new FileAdapter(this, new FileAdapter.Callback() {
            @Override
            public boolean isFolderOpen(File file) {
                return fileNavigator.isFolderOpen(file);
            }
        }, fileNavigator.getFiles());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateView(fileNavigator.goToFolder(position));
            }
        });
        updateView(true);
    }

    public void updateView(boolean updateNavigationButtons){
        if(updateNavigationButtons){
            upButton.setImageDrawable( fileNavigator.isNavigateUp()? upIcon : upIconUn);
            downButton.setImageDrawable( fileNavigator.isNavigateDown() ? downIcon : downIconUn);
        }
        txtSelectedFolder.setText(fileNavigator.getCurrentPath());
        adapter.notifyDataSetChanged();
    }


}
