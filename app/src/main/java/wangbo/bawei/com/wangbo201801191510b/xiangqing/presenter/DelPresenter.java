package wangbo.bawei.com.wangbo201801191510b.xiangqing.presenter;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.MessageBean;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.model.DelModel;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.view.Iview;

/**
 * author:Created by Wangbo on 2018/1/19.
 */

public class DelPresenter implements BasePresenter{
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
        DelModel model = new DelModel(this);
        model.getData(user,uid,pid);
    }



    public void delData(Flowable<MessageBean> delFlowable) {
        subscriber = delFlowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<MessageBean>() {
                    @Override
                    public void onNext(MessageBean listMessageBean) {
                        if (listMessageBean != null) {
                            iv.delSuccess(listMessageBean);

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
