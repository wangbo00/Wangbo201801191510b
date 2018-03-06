package wangbo.bawei.com.wangbo201801191510b.xiangqing.presenter;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.DatasBean;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.GoodsBean;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.MessageBean;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.model.NewsModel;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.view.Iview;

/**
 * author:Created by Wangbo on 2018/1/19.
 */

public class NewsPresenter implements BasePresenter{
    private Iview iv;
    private DisposableSubscriber subscriber;

    public void attachView(Iview iv) {
        this.iv = iv;
    }

    public void detachView() {
        if (iv != null) {
            iv = null;
        }
        if (!subscriber.isDisposed()){
            subscriber.dispose();
        }

    }

    @Override
    public void getData(String user,String uid,String pid) {
        NewsModel model = new NewsModel(this);
        model.getData(user,uid,pid);
    }

    public void getNews(Flowable<MessageBean<List<DatasBean>>> flowable) {
        subscriber = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<MessageBean<List<DatasBean>>>() {
                    @Override
                    public void onNext(MessageBean<List<DatasBean>> listMessageBean) {
                        if (listMessageBean != null) {
                            List<DatasBean> list = listMessageBean.getData();
                            if (list != null) {
                                iv.onSuccess(list);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        iv.onFailed((Exception) t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getGoods(Flowable<GoodsBean> flowable) {
        subscriber = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<GoodsBean>() {
                    @Override
                    public void onNext(GoodsBean listMessageBean) {
                        if (listMessageBean != null) {
                            iv.onSuccess(listMessageBean);

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        iv.onFailed((Exception) t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
