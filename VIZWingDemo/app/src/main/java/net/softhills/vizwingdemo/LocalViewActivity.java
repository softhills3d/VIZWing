package net.softhills.vizwingdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.FrameLayout;

import net.softhills.vizcore.VIZCore;

public class LocalViewActivity extends AppCompatActivity implements VIZCore.VIZCoreCallback{
    VIZCore vizcore;
    String fileName;
    String filePath;
    String fileFullPath;
    FrameLayout frameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_view);

        vizcore = MainActivity.vizcore;

        filePath = getIntent().getExtras().getString("@id/viz_path");
        fileName = getIntent().getExtras().getString("@id/viz_name");
        fileFullPath = getIntent().getExtras().getString("@id/viz_fullpath");

        frameView = findViewById(R.id.frame_view);

        vizcore.setFrameLayout(frameView); //viewer화면
        vizcore.Model.open(fileFullPath); //모델 열기
        vizcore.ModelTree.setModeltreeDrawerMode(true); //모델 트리 설정
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