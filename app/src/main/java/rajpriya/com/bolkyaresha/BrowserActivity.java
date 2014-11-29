package rajpriya.com.bolkyaresha;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.github.pedrovgs.DraggablePanel;

import java.util.ArrayList;

import rajpriya.com.bolkyaresha.models.FBPagePost;


public class BrowserActivity extends ActionBarActivity {

    public static final String PAGE_DATA = "fb_page_data";

    private GridView mGrid;
    private DraggablePanel mPanel;
    private JokePostFragment mJokeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        final ArrayList<FBPagePost> pageData = getIntent().getExtras().getParcelableArrayList(PAGE_DATA);
        mJokeFragment = JokePostFragment.newInstance(pageData.get(0));
        JokesAdapter adapter = new JokesAdapter(pageData);
        mGrid = (GridView)findViewById(R.id.jokes_grid);
        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPanel.setVisibility(View.VISIBLE);
                mPanel.maximize();
                mJokeFragment.showPost(pageData.get(position));
            }
        });
        mGrid.setAdapter(adapter);
        mPanel = (DraggablePanel)findViewById(R.id.draggable_panel);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
