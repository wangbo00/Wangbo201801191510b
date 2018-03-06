package wangbo.bawei.com.wangbo201801191510b.xiangqing.model;

import java.util.List;

import io.reactivex.Flowable;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.DatasBean;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.GoodsBean;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.MessageBean;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.presenter.NewsPresenter;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.utils.RetrofitUtils;

/**
 * author:Created by Wangbo on 2018/1/19.
 */

public class NewsModel implements IModel{
    private NewsPresenter presenter;

    public NewsModel(NewsPresenter presenter){
        this.presenter = (NewsPresenter) presenter;

    }
    @Override
    public void getData(String user,String uid,String pid) {
        Flowable<MessageBean<List<DatasBean>>> flowable = RetrofitUtils.getInstance().getApiService().getDatas(uid);
        presenter.getNews(flowable);
        if(uid=="1"){
            Flowable<GoodsBean> flowable2 = RetrofitUtils.getInstance().getApiService().selectData(pid);
            presenter.getGoods(flowable2);
        }

    }
}
