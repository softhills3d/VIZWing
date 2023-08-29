package net.softhills.vizwingdemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import net.softhills.vizcore.VIZCore;

public class LocalViewActivity extends AppCompatActivity implements VIZCore.VIZCoreCallback {
    VIZCore vizcore;
    FrameLayout frameLayout;

    String fileName;
    String fileFullPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_view);

        fileName = getIntent().getExtras().getString("@id/vizm_name");
        fileFullPath = getIntent().getExtras().getString("@id/vizm_fullpath");

        frameLayout = findViewById(R.id.frame_view);

        vizcore = MainActivity.vizcore;
        vizcore.License.licenseCode("F3A023EB-4A17-4174-8D07-0CDB2CAB2622"); // 라이선스 코드
        vizcore.setFrameLayout(frameLayout); //레이아웃 설정
        vizcore.Model.open(fileFullPath); //모델 열기
    }

    @Override
    public void OnNodePick(int i) {
        //오브젝트 선택 콜백
    }

    @Override
    public void OnReadFileFinished() {
        //모델 읽기 완료 콜백
    }

    @Override
    public void OnCreateReviewItem(int i) {
        //리뷰 생성 콜백
    }

    @Override
    public void OnScreenCaptured(Bitmap bitmap) {
        //화면 캡처 콜백
    }

    @Override
    public void OnWalkThroughModeFinished() {
        //탐색 모드 종료 콜백
    }

    @Override
    public void OnGeometryMeasure(float[] floats) {
        //표면점 좌표 반환 콜백
    }

    @Override
    public void OnRefresh() {
        //새로 고침 콜백
    }
}