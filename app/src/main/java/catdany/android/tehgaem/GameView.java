package catdany.android.tehgaem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import catdany.android.tehgaem.entity.EntityClient;
import catdany.android.tehgaem.entity.WorldClient;
import catdany.android.tehgaem.net.TGClient;
import catdany.android.tehgaem.net.packet.MovementPacket;

/**
 * Created by CatDany on 29.05.2016.
 */
public class GameView extends View {

    public static final double coordWidth = 50D;

    public Paint paint;

    public GameView(Context context, AttributeSet attributes) {
        super(context, attributes);
        this.paint = new Paint(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float viewWidth = getWidth();
        float viewHeight = getHeight();
        double coordHeight = viewHeight/viewWidth*coordWidth;
        for (EntityClient i : WorldClient.instance.entityList) {
            LLog.v("%s Posx:%s CoordWidth:%s ViewWidth:%s >> %s", i.id, i.posX, coordWidth, viewWidth, (float)(i.posX/coordWidth*viewWidth));
            LLog.v("%s Posy:%s CoordHeigth:%s ViewHeight:%s >> %s", i.id, i.posY, coordHeight, viewHeight, (float)(i.posY/coordHeight*viewHeight));
            canvas.drawCircle((float)(i.posX/coordWidth*viewWidth), (float)(i.posY/coordHeight*viewHeight), (float)(i.radius/coordWidth*viewWidth), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float viewX = event.getX();
            float viewY = event.getY();
            LLog.v("ViewX:%s ViewY:%s", viewX, viewY);
            float viewWidth = getWidth();
            float viewHeight = getHeight();
            double coordHeight = viewHeight/viewWidth*coordWidth;
            double coordX = viewX/viewWidth*coordWidth;
            double coordY = viewY/viewHeight*coordHeight;
            LLog.v("CoordX:%s CoordY:%s", coordX, coordY);
            TGClient.instance.sendPacket(new MovementPacket(coordX, coordY));
        }
        return false;
    }

    public static void invalidateOnUI() {
        GamePlaceholderActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GamePlaceholderActivity.instance.viewGame.invalidate();
            }
        });
    }
}
