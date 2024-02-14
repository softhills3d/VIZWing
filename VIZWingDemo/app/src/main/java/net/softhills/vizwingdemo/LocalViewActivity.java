package net.softhills.vizwingdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.FrameLayout;

import net.softhills.vizcore.VIZCore;

public class LocalViewActivity extends AppCompatActivity implements VIZCore.VIZCoreCallback{
    VIZCore vizcore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_view);

        vizcore = MainActivity.vizcore;
    }

    @Override
    public void OnNodePick(int id) {

    }

    @Override
    public void OnReadFileFinished() {

    }

    @Override
    public void OnCreateReviewItem(int id) {

    }

    @Override
    public void OnScreenCaptured(Bitmap bitmap) {

    }

    @Override
    public void OnWalkThroughModeFinished() {

    }

    @Override
    public void OnGeometryMeasure(float[] floats) {

    }

    @Override
    public void OnRefresh() {

    }

    @Override
    public void OnSetting() {

    }
}