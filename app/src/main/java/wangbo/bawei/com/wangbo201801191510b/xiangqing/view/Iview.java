package wangbo.bawei.com.wangbo201801191510b.xiangqing.view;

import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.MessageBean;

/**
 * author:Created by Wangbo on 2018/1/19.
 */

public interface Iview {
    void onSuccess(Object o);
    void onFailed(Exception e);

    void delSuccess(MessageBean listMessageBean);
}
