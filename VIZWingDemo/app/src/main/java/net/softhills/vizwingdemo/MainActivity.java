package net.softhills.vizwingdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.softhills.data.Viz;
import net.softhills.vizcore.VIZCore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static VIZCore vizcore;
    private static final int REQUEST_PERMISSION_CODE = 101;

    private static String[] PERMISSIONS_APP = {
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    ListView listView;
    private VizAdapter adapter;
    ArrayList<Viz> listVizFile = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkPermission() == false){
            Toast.makeText(this, "파일 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
            else{
                ActivityCompat.requestPermissions(this, PERMISSIONS_APP, REQUEST_PERMISSION_CODE);
            }
        }

        vizcore = new VIZCore(this);
        //vizcore.License.licenseCode("F3A023EB-4A17-4174-8D07-0CDB2CAB2622");
        vizcore.License.licenseTerm();


        listView = (ListView) findViewById(R.id.lv_viz);
        listVizFile = new ArrayList<>();
        listVizFile = vizcore.getVizList();

        adapter = new VizAdapter(this, listVizFile);
        listView.setAdapter(adapter);

        // ListView의 항목 클릭 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 클릭된 항목의 Viz 객체 가져오기
                Viz item = listVizFile.get(position);

                Intent intent = new Intent(getBaseContext(), LocalViewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("@id/viz_name", item.FileName); //파일 이름
                intent.putExtra("@id/viz_path", item.FilePath); //파일 경로
                intent.putExtra("@id/viz_fullpath", item.FileFullPath); //파일 경로
                startActivity(intent);
            }
        });
    }

    //권한 설정 확인하기
    public boolean checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //Android is 11(R) or above
            return Environment.isExternalStorageManager();
        }
        else{
            //Android is below 11(R)
            int write = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

            return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
        }
    }

    //권한 요청 결과 콜백 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(checkPermission()){
            listVizFile = new ArrayList<>();
            listVizFile = vizcore.getVizList();

            adapter = new VizAdapter(this, listVizFile);
            listView.setAdapter(adapter);
        } else{
            Toast.makeText(this, "파일 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public static class VizAdapter extends ArrayAdapter<Viz> {
        public VizAdapter(Context context, ArrayList<Viz> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 현재 항목 가져오기
            Viz viz = getItem(position);

            // 뷰가 재활용되지 않는 경우 레이아웃을 인플레이트함
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            // 텍스트뷰 참조 가져오기
            TextView textView = (TextView) convertView.findViewById(android.R.id.text1);

            // 텍스트뷰에 파일 이름 설정
            if (viz != null) {
                textView.setText(viz.FileName);
            }

            return convertView;
        }
    }
}