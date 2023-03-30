package net.softhills.vizwingar;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import net.softhills.data.Data;
import net.softhills.math.Vector3D;
import net.softhills.vizcore.VIZCore;

import java.nio.ByteBuffer;

public class LocalViewActivity extends AppCompatActivity implements VIZCore.VIZCoreCallback{
    FrameLayout frameView;
    FrameLayout frameViewMenu;
    VIZCore vizwing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_view);

        frameView = findViewById(R.id.frame_view);
        frameViewMenu = findViewById(R.id.frame_view_menu);
        frameViewMenu.bringToFront();
        vizwing = new VIZCore(frameView);

        String ip = "192.168.0.215"; //라이선스 서버 ip
        int port = 8901; //라이선스 서버 port

        vizwing.License.licenseServer(ip, port); // 라이선스 서버 확인

        if (vizwing.License.isAuthentication()){ //라이선스 확인
            try {
                String vizmPath = getIntent().getExtras().getString("@id/vizm_path"); //.vizm 경로
                vizwing.Model.open(vizmPath); //모델 열기
            } catch (Exception e) {
                Log.e("Error : ", "Model open failed");
                Log.e("Error : ", e.getMessage());
            }
        } else {
            Log.e("Error : ", "License check failed");
        }
    }

    //오브젝트 선택 콜백
    @Override
    public void OnNodePick(int nNodeID) {

    }

    //모델 읽기 완료 콜백
    @Override
    public void OnReadFileFinished() {

    }

    //리뷰 생성 콜백
    @Override
    public void OnCreateReviewItem(int reviewItemID) {

    }

    //스크린샷 콜백
    @Override
    public void OnScreenCaptured(Bitmap bitmap) {
    }

    //탐색 모드 종료 콜백
    @Override
    public void OnWalkThroughModeFinished() {

    }

    public void OnGeometryMeasure(float[] measureData) {
        vizwing.AR.close();
    }

    public void OnMenuARStart(View v) {
        Vector3D start = new Vector3D(23867, -23, 2800);
        Vector3D view = new Vector3D(18224, -45, 2800);
        vizwing.AR.ARStartPos = start;
        vizwing.AR.ARViewPos = view;
        vizwing.AR.ARCoreProcessType = 0;
        vizwing.AR.start();
    }
    public void OnMenuARClose(View v) {
        vizwing.AR.close();
    }
}