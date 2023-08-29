package net.softhills.vizwingdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.softhills.data.Vizm;
import net.softhills.vizcore.VIZCore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static VIZCore vizcore;

    private static final int MY_PERMISSIONS_REQUEST_APP_CONTACTS = 0;
    private static String[] PERMISSIONS_APP = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    ListView lvVizm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //권한 요청.
        ActivityCompat.requestPermissions(this,
                PERMISSIONS_APP,
                MY_PERMISSIONS_REQUEST_APP_CONTACTS);

        vizcore = new VIZCore(this);

        //vizm파일 목록 가져오기
        setVizmList();
    }

    //권한 요청 결과 콜백 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한이 필요합니다.", Toast.LENGTH_LONG).show();
                    break;
                }
                return;
            }
            //vizm파일 목록 가져오기
            setVizmList();
        }
    }

     //.vizm 파일 목록 바인딩
    private void setVizmList() {
        lvVizm = (ListView) findViewById(R.id.lv_vizm);
        ArrayList<Vizm> vizms = vizcore.getVizmList();
        AdapterVizm adapter = new AdapterVizm(this, R.layout.item_vizm, vizms);
        lvVizm.setAdapter(adapter);
    }

    //.vizm 어댑터
    public class AdapterVizm extends ArrayAdapter<Vizm> {
        Context context;
        int layoutResourceId;
        ArrayList<Vizm> data = new ArrayList<>();

        public AdapterVizm(Context context, int layoutResourceId, ArrayList<Vizm> data){
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
            Vizm item = data.get(position);

            TextView txtVizmName = (TextView) view.findViewById(R.id.text_vizm_name);

            txtVizmName.setText(item.FileName);

            view.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    Vizm item = data.get(position);
                    Intent intent = new Intent(context, LocalViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("@id/vizm_name", item.FileName); //파일 이름
                    intent.putExtra("@id/vizm_fullpath", item.FileFullPath); //파일 경로
                    context.startActivity(intent);
                }
            });
            return view;
        }
    }
}