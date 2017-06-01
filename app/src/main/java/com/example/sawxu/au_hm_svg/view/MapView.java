package com.example.sawxu.au_hm_svg.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.sawxu.au_hm_svg.CityItem;
import com.example.sawxu.au_hm_svg.R;
import com.example.sawxu.au_hm_svg.tool.SaxParseXml;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by SawXu on 2017/6/2.
 */

public class MapView extends View {
    private int colorIndex;
    private float scale = 1.3f;
    List<CityItem> cityItemList;
    private CityItem selectItem;
    private Paint paint;
    private TypedArray colorTypedArray;
    private GestureDetectorCompat gestureDetector;

    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);
        colorTypedArray = getResources().obtainTypedArray(R.array.colorArray);
        gestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                handleTouch(e.getX(), e.getY());
                return super.onDown(e);
            }
        });

        Observable<CityItem> observable = Observable.create(new ObservableOnSubscribe<CityItem>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<CityItem> e) throws Exception {
                SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
                SaxParseXml saxParseXml = new SaxParseXml();
                InputStream inputStream = getResources().openRawResource(R.raw.taiwan);
                saxParser.parse(inputStream, saxParseXml);
                cityItemList = saxParseXml.getList();
                for (CityItem cityItem : cityItemList) {
                    e.onNext(cityItem);
                }

            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Observer<CityItem>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CityItem cityItem) {
                        if (colorIndex > colorTypedArray.length() - 1)
                            colorIndex = 0;
                        cityItem.setFillColor(colorTypedArray.getColor(colorIndex++, 0));
                        Log.d("Saw", cityItem.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        postInvalidate();
                    }
                });

    }

    private void handleTouch(float x, float y) {
        if (cityItemList != null) {
            CityItem temp = null;
            for (CityItem cityItem : cityItemList) {
                if (cityItem.isTouch(x, y)) {
                    temp = cityItem;
                    break;
                }
            }
            if (temp != null) {
                selectItem = temp;
                postInvalidate();
            }

        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (cityItemList != null) {
            canvas.save();
//            canvas.scale(scale, scale);
            for (CityItem cityItem : cityItemList) {
                if (cityItem != selectItem) {
                    cityItem.draw(canvas, paint, false);
                }
            }

            if (selectItem != null) {
                selectItem.draw(canvas, paint, true);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
