package net.softhills.vizwingview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import net.softhills.data.Data;
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

        vizwing.License.LicenseServer(ip, port); // 라이선스 서버 확인

        if (vizwing.License.IsAuthentication()){ //라이선스 확인
            try {
                String vizmPath = getIntent().getExtras().getString("@id/vizm_path"); //.vizm 경로
                vizwing.Model.Open(vizmPath); //모델 열기
            } catch (Exception e) {
                Log.e("Error : ", "Model open failed");
                Log.e("Error : ", e.getMessage());
            }
        } else {
            Log.e("Error : ", "License check failed");
        }
    }

    @Override
    public void OnNodePick(int nNodeID) {
        //노드 선택 시 호출
    }

    @Override
    public void OnReadFileFinished() {
        //모델 읽기 완료 시 호출
    }

    @Override
    public void OnCreateReviewItem(int reviewItemID, float[] subData) {
        //Review item 생성 시 호출
    }

    @Override
    public void OnScreenCaptured(int width, int height, ByteBuffer pixelBuffer) {
        //화면 캡처 시 호출
    }

    @Override
    public void OnWalkThroughModeFinished() {
        //탐색모드 종료 시 호줄
    }

    //카메라 방향 전환 ISO+
    public void OnMenuViewISOPlus(View v) {
        vizwing.View.MoveCamera(Data.CameraDirection.ISO_PLUS);
    }

    //카메라 방향 전환 ISO-
    public void OnMenuViewISOMinus(View v) {
        vizwing.View.MoveCamera(Data.CameraDirection.ISO_MINUS);
    }

    //카메라 방향 전환 X+
    public void OnMenuViewXPlus(View v) {
        vizwing.View.MoveCamera(Data.CameraDirection.X_PLUS);
    }

    //카메라 방향 전환 X-
    public void OnMenuViewXMinus(View v) {
        vizwing.View.MoveCamera(Data.CameraDirection.X_MINUS);
    }

    //카메라 방향 전환 Y-
    public void OnMenuViewYPlus(View v) {
        vizwing.View.MoveCamera(Data.CameraDirection.Y_PLUS);
    }

    //카메라 방향 전환 Y-
    public void OnMenuViewYMinus(View v) {
        vizwing.View.MoveCamera(Data.CameraDirection.Y_MINUS);
    }

    //카메라 방향 전환 Z+
    public void OnMenuViewZPlus(View v) {
        vizwing.View.MoveCamera(Data.CameraDirection.Z_PLUS);
    }

    //카메라 방향 전환 Z-
    public void OnMenuViewZMinus(View v) {
        vizwing.View.MoveCamera(Data.CameraDirection.Z_MINUS);
    }
}