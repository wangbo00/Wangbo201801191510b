package wangbo.bawei.com.wangbo201801191510b.xiangqing.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import wangbo.bawei.com.wangbo201801191510b.R;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.adapter.MyAdapter;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.DatasBean;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.MessageBean;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.MessageEven;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.PriceAndCountEvent;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.SomeId;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.presenter.DelPresenter;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.presenter.NewsPresenter;

/**
 * author:Created by Wangbo on 2018/1/19.
 */

public class Main2Activity extends AppCompatActivity implements Iview{

    private String uid = "3027";
    private NewsPresenter presenter;
    private CheckBox mCheckbox2;
    private ExpandableListView mElv;

    /**
     * 0
     */
    private TextView mTvPrice;
    /**
     * 结算(0)
     */
    private TextView mTvNum;
    private MyAdapter adapter;
    private List<DatasBean> groupList = new ArrayList<>();
    private List<List<DatasBean.ListBean>> childList = new ArrayList<>();
    private String pid;
    private DelPresenter delPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        //EventBus注册
        EventBus.getDefault().register(this);
        initView();
        presenter = new NewsPresenter();
        presenter.attachView(this);
        delPresenter = new DelPresenter();
        delPresenter.attachView(this);
        adapter = new MyAdapter(this, groupList, childList);
        mElv.setAdapter(adapter);
        //监听,调用适配器中方法改变复选框状态
        mCheckbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.changeAllListCbState(mCheckbox2.isChecked());
            }
        });
    }

    private void initView() {
        mElv = (ExpandableListView) findViewById(R.id.elv);
        mCheckbox2 = (CheckBox) findViewById(R.id.checkbox2);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvNum = (TextView) findViewById(R.id.tv_num);
    }
    //实现接口中的方法
    @Override
    public void onSuccess(Object o) {
        if (o!=null){
            List<DatasBean> list= (List<DatasBean>)o;
            if (list!=null){
                groupList.addAll(list);
                for (int i=0;i<list.size();i++){
                    List<DatasBean.ListBean> datas= list.get(i).getList();
                    childList.add(datas);
                }
                adapter.notifyDataSetChanged();
                mCheckbox2.setChecked(true);
                adapter.changeAllListCbState(true);
                mElv.setGroupIndicator(null);
                for (int i=0;i<groupList.size();i++){
                    mElv.expandGroup(i);
                }
            }
        }

    }
    @Override
    public void onFailed(Exception e) {

    }

    //重写删除接口中的方法
    @Override
    public void delSuccess(MessageBean listMessageBean) {
        Toast.makeText(this,listMessageBean.getMsg(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getData("",uid,pid);
    }

    @Subscribe
    public void onMessageEvent(MessageEven event) {
        mCheckbox2.setChecked(event.isChecked());
    }

    @Subscribe
    public void onMessageEvent(PriceAndCountEvent event) {
        mTvNum.setText("结算(" + event.getCount() + ")");
        mTvPrice.setText("￥"+event.getPrice() );
    }
    @Subscribe
    public void onMessageEvent(SomeId event) {
        pid = event.getPid();
        delPresenter.getData("deleteCart",uid,pid);


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (presenter!=null){
            presenter.detachView();
        }
    }
}
