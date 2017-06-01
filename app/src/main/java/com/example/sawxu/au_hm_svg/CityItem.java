package com.example.sawxu.au_hm_svg;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

/**
 * Created by SawXu on 2017/6/2.
 */

public class CityItem {
    private Path path;

    private int fillColor;

    private int strokeColor;

    private float strokeWidth;

    public CityItem() {

    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public CityItem(Path path) {
        this.path = path;
    }

    public void draw(Canvas canvas, Paint paint, boolean isSelect) {
        if (!isSelect) {
            //绘制内容
            paint.clearShadowLayer();
            paint.setStrokeWidth(2);
            paint.setColor(fillColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path, paint);

            //绘制边界线
            paint.clearShadowLayer();
            paint.setStrokeWidth(2);
            paint.setColor(0xFF2c23ab);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, paint);
        } else {
            //绘制阴影
            paint.clearShadowLayer();
            paint.setStrokeWidth(3);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setShadowLayer(16, 0, 0, 0xFFFFFFFF);
            canvas.drawPath(path, paint);

            //绘制内容
            paint.clearShadowLayer();
            paint.setColor(fillColor);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(3);
            canvas.drawPath(path, paint);

        }
    }

    public boolean isTouch(float x, float y) {
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        Region region = new Region();
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));


        return region.contains((int)x, (int)y);
    }

    @Override
    public String toString() {
        return "CityItem{" +
                "path=" + path +
                ", fillColor=" + fillColor +
                ", strokeColor=" + strokeColor +
                ", strokeWidth=" + strokeWidth +
                '}';
    }
}
