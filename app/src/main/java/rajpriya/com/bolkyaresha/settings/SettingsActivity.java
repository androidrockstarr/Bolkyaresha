package rajpriya.com.bolkyaresha.settings;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import rajpriya.com.bolkyaresha.R;

public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        ImageView header = new ImageView(this);
        header.setImageDrawable(getResources().getDrawable(R.drawable.actionbar_banner));
        getSupportActionBar().setBackgroundDrawable(header.getDrawable());
    }



}
