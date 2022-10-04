/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.mlkit.vision.demo.java;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.mlkit.vision.demo.BuildConfig;
import com.google.mlkit.vision.demo.R;
import com.google.mlkit.vision.demo.java.IS.DatabaseManager;
import com.google.mlkit.vision.demo.java.IS.Jump;
import com.google.mlkit.vision.demo.java.IS.JumpGraph;
import com.google.mlkit.vision.demo.java.IS.MainActivity;

/**
 * Demo app chooser which allows you pick from all available testing Activities.
 */
public final class ChooserActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener{
    private static final String TAG = "ChooserActivity";
    private String userID;

    @SuppressWarnings("NewApi") // CameraX is only available on API 21+
    private static final Class<?>[] CLASSES =
            VERSION.SDK_INT < VERSION_CODES.LOLLIPOP
                    ? new Class<?>[]{
                    LivePreviewActivity.class,
                    JumpGraph.class
            }
                    : new Class<?>[]{
                    LivePreviewActivity.class,
                    JumpGraph.class
            };

    private static final int[] DESCRIPTION_IDS =
            VERSION.SDK_INT < VERSION_CODES.LOLLIPOP
                    ? new int[]{
                    R.string.desc_camera_source_activity,
                    R.string.jump_graph_activity
            }
                    : new int[]{
                    R.string.desc_camera_source_activity,
                    R.string.jump_graph_activity
            };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        if(BuildConfig.DEBUG){
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(
                    new StrictMode.VmPolicy.Builder()
                            .detectLeakedSqlLiteObjects()
                            .detectLeakedClosableObjects()
                            .penaltyLog()
                            .build());
        }
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_chooser);

        // Set up ListView and Adapter
        ListView listView = findViewById(R.id.test_activity_list_view);

        MyArrayAdapter adapter = new MyArrayAdapter(this, android.R.layout.simple_list_item_2, CLASSES);
        adapter.setDescriptionIds(DESCRIPTION_IDS);
        userID = getIntent().getStringExtra("authID");
        DatabaseManager.getInstance().getJumps(getIntent().getStringExtra("authID"));

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Class<?> clicked = CLASSES[position];
        startActivity(new Intent(this, clicked).putExtra("authID", userID));
    }

    private static class MyArrayAdapter extends ArrayAdapter<Class<?>>{

        private final Context context;
        private final Class<?>[] classes;
        private int[] descriptionIds;

        MyArrayAdapter(Context context, int resource, Class<?>[] objects){
            super(context, resource, objects);

            this.context = context;
            classes = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View view = convertView;

            if(convertView == null){
                LayoutInflater inflater =
                        (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(android.R.layout.simple_list_item_2, null);
            }

            ((TextView) view.findViewById(android.R.id.text1)).setText(classes[position].getSimpleName());
            ((TextView) view.findViewById(android.R.id.text2)).setText(descriptionIds[position]);

            return view;
        }

        void setDescriptionIds(int[] descriptionIds){
            this.descriptionIds = descriptionIds;
        }
    }
}
