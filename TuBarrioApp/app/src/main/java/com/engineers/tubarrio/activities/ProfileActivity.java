package com.engineers.tubarrio.activities;

import android.app.Activity;
import android.os.Bundle;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.adapters.PublicationsAdapter;
import com.engineers.tubarrio.entities.Publication;

import java.util.List;

public class ProfileActivity extends Activity {


    List<Publication> mPublications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}
