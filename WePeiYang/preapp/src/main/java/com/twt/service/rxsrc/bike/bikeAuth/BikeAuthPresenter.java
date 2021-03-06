package com.twt.service.rxsrc.bike.bikeAuth;

import android.content.Context;
import android.util.Log;

import com.twt.service.rxsrc.api.BikeApiClient;
import com.twt.service.rxsrc.api.BikeApiSubscriber;
import com.twt.service.rxsrc.api.OnNextListener;
import com.twt.service.rxsrc.common.BikePresenter;
import com.twt.service.rxsrc.model.BikeAuth;
import com.twt.service.rxsrc.model.BikeCard;
import com.twt.service.support.PrefUtils;

import java.util.List;


/**
 * Created by jcy on 2016/8/7.
 */

public class BikeAuthPresenter extends BikePresenter {
    private String TAG="BikeAuth";

    private BikeAuthController mViewController;

    public BikeAuthPresenter(Context context, BikeAuthController viewController) {
        super(context);
        mViewController = viewController;
    }

    public void getBikeToken(){
        String wpy_token = PrefUtils.getTokenForBike();
        BikeApiClient.getInstance().getBikeToken(mContext,new BikeApiSubscriber(mContext,mListener),wpy_token);
    }

    private OnNextListener<BikeAuth> mListener = new OnNextListener<BikeAuth>() {
        @Override
        public void onNext(BikeAuth bikeAuth) {
            PrefUtils.setBikeToken(bikeAuth.token);
            mViewController.onTokenGot(bikeAuth);
            Log.d(TAG,bikeAuth.token);
        }
    };



    public void getBikeCard(String idnum){
        BikeApiClient.getInstance().getBikeCard(mContext,new BikeApiSubscriber(mContext,mCardListener),idnum);
    }

    private OnNextListener<List<BikeCard>> mCardListener = new OnNextListener<List<BikeCard>>() {
        @Override
        public void onNext(List<BikeCard> bikeCards) {
            Log.d(TAG,bikeCards.get(0).toString());
            BikeCard card = bikeCards.get(0);
            PrefUtils.setCardId(card.id);
            PrefUtils.setCardSign(card.sign);
        }
    };

    public void bindBikeCard(){
        BikeApiClient.getInstance().bindBikeCard(mContext,new BikeApiSubscriber(mContext,mBindListener),PrefUtils.getCardId(),PrefUtils.getCardSign());
    }

    protected OnNextListener<String> mBindListener = new OnNextListener<String>() {
        @Override
        public void onNext(String s) {
            Log.d(TAG, "onNext: "+"bindok");
        }
    };
}
