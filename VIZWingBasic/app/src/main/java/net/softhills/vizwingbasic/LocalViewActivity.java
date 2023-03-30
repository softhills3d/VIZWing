package net.softhills.vizwingbasic;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import net.softhills.vizcore.VIZCore;

import java.nio.ByteBuffer;

public class LocalViewActivity extends AppCompatActivity implements VIZCore.VIZCoreCallback{
    FrameLayout frameView;
    VIZCore vizwing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_view);

        frameView = findViewById(R.id.frame_view);
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

    @Override
    public void OnNodePick(int nNodeID) {
        //노드 선택 시 호출
    }

    @Override
    public void OnReadFileFinished() {
        //모델 읽기 완료 시 호출
    }

    @Override
    public void OnCreateReviewItem(int i) {
        //리뷰 생성 시 호출
    }

    @Override
    public void OnScreenCaptured(Bitmap bitmap) {
        //화면 캡처 시 호줄
    }

    @Override
    public void OnWalkThroughModeFinished() {
        //탐색모드 종료 시 호줄
    }

    @Override
    public void OnGeometryMeasure(float[] floats) {
        //표면점 좌표 반환 시 호출
    }
}