package net.softhills.vizwingbasic;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvVizm;
    private VizmAdapter VizmAdapter;
    private ArrayList<VizmItem> listVizm = new ArrayList<>(); //.vizm 파일 리스트

    private static final int MY_PERMISSIONS_REQUEST_APP_CONTACTS = 0;
    private static String[] PERMISSIONS_APP = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvVizm = (ListView) findViewById(R.id.lv_vizm);


        //권한 요청.
        ActivityCompat.requestPermissions(this,
                PERMISSIONS_APP,
                MY_PERMISSIONS_REQUEST_APP_CONTACTS);

        setVizmList(); //.vizm 파일 목록 바인딩
    }

    // 권한 요청 결과 콜백 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한이 필요합니다.", Toast.LENGTH_LONG).show();
                    break;
                }
                setVizmList(); //.vizm 파일 목록 바인딩
            }
        }
    }

    /**
     * 전체 경로에서 VIZM 파일 찾기
     * @param strFilePath  VIZM 파일을 찾을 경로
     */
    private void GetVizmItemList(String strFilePath) {
        File root = new File(strFilePath);
        File[] fileList = root.listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                if (file.isDirectory()) {
                    //디렉토리
                    GetVizmItemList(file.getPath());
                } else if (file.isFile()) {
                    //파일
                    //확장자 체크
                    if (file.getName().endsWith(".vizm")) {
                        VizmItem vo = new VizmItem();
                        vo.Name = file.getName();
                        vo.Path = file.getPath();
                        listVizm.add(vo);
                    }
                }
            }
        }
    }

    /**
     * .vizm 파일 목록 바인딩
     */
    private void setVizmList() {
        String dafaultPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        GetVizmItemList(dafaultPath);
        VizmAdapter = new VizmAdapter(this, R.layout.item_vizm, listVizm);
        lvVizm.setAdapter(VizmAdapter);
    }

    public static class VizmItem {
        public String Name;
        public String Path;
    }

    public class VizmAdapter extends ArrayAdapter<VizmItem> {
        Context context;
        int layoutResourceId;
        ArrayList<VizmItem> data = new ArrayList<>();

        public VizmAdapter(Context context, int layoutResourceId, ArrayList<VizmItem> data){
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)  {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(layoutResourceId, parent, false);
            }

            VizmItem item = data.get(position);
            TextView tvVizm = (TextView) view.findViewById(R.id.tv_vizm);
            tvVizm.setText(item.Name);

            view.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    VizmItem item = data.get(position);
                    Intent intent = new Intent(context, LocalViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("@id/vizm_path", item.Path); //파일 경로
                    context.startActivity(intent);
                }
            });
            return view;
        }
    }
}