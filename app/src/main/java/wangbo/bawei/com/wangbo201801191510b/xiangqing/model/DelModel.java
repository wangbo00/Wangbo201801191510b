package wangbo.bawei.com.wangbo201801191510b.xiangqing.model;

import io.reactivex.Flowable;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.MessageBean;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.presenter.DelPresenter;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.utils.RetrofitUtils;

/**
 * author:Created by Wangbo on 2018/1/19.
 */

public class DelModel implements IModel{
    private DelPresenter presenter;

    public DelModel(DelPresenter presenter){
        this.presenter =  presenter;

    }
    @Override
    public void getData(String user,String uid,String pid) {

        Flowable<MessageBean> delFlowable = RetrofitUtils.getInstance().getApiService().addData(user,uid,pid);
        presenter.delData(delFlowable);
    }
}
