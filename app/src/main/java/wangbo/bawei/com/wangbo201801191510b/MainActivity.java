package wangbo.bawei.com.wangbo201801191510b;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import wangbo.bawei.com.wangbo201801191510b.xiangqing.XqActivity;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pro;
    private TextView tv;
    private DownLoadFile downLoadFile ;
    private Button jr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        jr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, XqActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        pro = (ProgressBar) findViewById(R.id.pro);
        tv = (TextView) findViewById(R.id.tv_progress);
        jr= (Button) findViewById(R.id.jinru);

        File f = new File(Environment.getExternalStorageDirectory()+"/wenjian/");
        if(!f.exists()){
            f.mkdir();
        }
        //存储地址
        String path =  Environment.getExternalStorageDirectory()+"/wenjian/mm.mp4";
        //设置最大度
        pro.setMax(100);
        //实例化
        downLoadFile = new DownLoadFile(this,
                "http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4"
                , path,10, new DownLoadFile.DownLoadListener() {
            @Override
            public void getProgress(int progress) {
                tv.setText("当前进度：" + progress + "%");
                pro.setProgress(progress);
            }

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            }
        }
        );
    }
    public void xiazai(View view){
        downLoadFile.downLoad();
    }
    public void zanting(View view){
        downLoadFile.onPause();
    }
    public void jixu(View view){
        downLoadFile.onStart();
    }
    public void quxiao(View view){
        String path =  Environment.getExternalStorageDirectory()+"/wenjian/mm.mp4";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downLoadFile.onDestroy();
    }
}
