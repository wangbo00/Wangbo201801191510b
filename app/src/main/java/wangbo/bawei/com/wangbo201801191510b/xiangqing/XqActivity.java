package wangbo.bawei.com.wangbo201801191510b.xiangqing;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wangbo.bawei.com.wangbo201801191510b.R;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.GoodsBean;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.MessageBean;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.presenter.DelPresenter;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.presenter.NewsPresenter;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.view.Iview;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.view.Main2Activity;

/**
 * author:Created by Wangbo on 2018/1/19.
 */

public class XqActivity extends AppCompatActivity implements Iview{

    @BindView(R.id.goods_img)
    SimpleDraweeView mGoodsImg;
    @BindView(R.id.goods_title)
    TextView mGoodsTitle;
    @BindView(R.id.goods_prices)
    TextView mGoodsPrices;
    @BindView(R.id.goods_name)
    TextView mGoodsName;
    @BindView(R.id.add_btn)
    Button mAddBtn;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xq);
        ButterKnife.bind(this);
        NewsPresenter presenter = new NewsPresenter();
        presenter.attachView(this);
        presenter.getData("","1", "1");
        videoView = (VideoView) findViewById(R.id.vv);

        String videoUrl2= Utils.videoUrl;

        Uri uri = Uri.parse(videoUrl2);

        //设置视频播放
        videoView.setMediaController(new MediaController(this));
        //播放完成回调
        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());
        //设置视频路径 
        videoView.setVideoURI(uri);
        //开始播放视频
        videoView.start();

    }
    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( XqActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }
    //回调方法
    @Override
    public void onSuccess(Object o) {
        GoodsBean bean = (GoodsBean) o;
        if (bean != null) {
            GoodsBean.DataBean data = bean.getData();
            String images = data.getImages();
            String[] split = images.split("\\|");
            //给控件赋值
            mGoodsImg.setImageURI(split[0]);
            mGoodsName.setText(bean.getSeller().getDescription());
            mGoodsPrices.setText("￥"+data.getBargainPrice());
            mGoodsTitle.setText(data.getTitle());
        }
    }

    @Override
    public void onFailed(Exception e) {

    }

    @Override
    public void delSuccess(MessageBean listMessageBean) {
        if(listMessageBean!=null){
            String msg = listMessageBean.getMsg();
//            Log.e("zxz",msg);
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
            if(msg.equals("加购成功")){
                Intent intent = new Intent(XqActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        }
    }

    @OnClick(R.id.add_btn)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.add_btn:
                DelPresenter delPresenter = new DelPresenter();
                delPresenter.attachView(this);
                delPresenter.getData("addCart","3027","1");
                break;
        }
    }
}
