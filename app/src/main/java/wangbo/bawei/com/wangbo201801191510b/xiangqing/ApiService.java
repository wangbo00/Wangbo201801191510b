package wangbo.bawei.com.wangbo201801191510b.xiangqing;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.DatasBean;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.GoodsBean;
import wangbo.bawei.com.wangbo201801191510b.xiangqing.bean.MessageBean;

/**
 * author:Created by Wangbo on 2018/1/19.
 */

public interface ApiService {
    @GET("product/getCarts")
    Flowable<MessageBean<List<DatasBean>>> getDatas(@Query("uid") String uid);

    //    @GET("product/deleteCart")
//    Flowable<MessageBean> deleteData(@Query("uid") String uid, @Query("pid") String pid);
    @GET("product/{user}")
    Flowable<MessageBean> addData(@Path("user") String user, @Query("uid") String uid, @Query("pid") String pid);

    //    http://120.27.23.105/product/getProductDetail?pid=1&source=android
    @GET("product/getProductDetail")
    Flowable<GoodsBean> selectData(@Query("pid") String pid);

}
