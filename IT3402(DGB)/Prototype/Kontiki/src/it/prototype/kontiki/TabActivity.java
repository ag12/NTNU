package it.prototype.kontiki;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TabActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tab, menu);
        return true;
    }
}
