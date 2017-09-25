package com.example.admin.jumpnbump;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Ball ball;
    private Obstacle obstacle;
    private SensorManager sensorManager;
    private Sensor rotationVectorSensor;

    public GameSurface(Context context)  {
        super(context);
        setFocusable(true);
        getHolder().addCallback(this);
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    public void update()  {
        ball.update();
        this.obstacle.update();
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        ball.draw(canvas);
        this.obstacle.draw(canvas);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap ballBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        ball = new Ball(this, ballBitmap, 0);

        Bitmap spikeBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.green_obstacle);
        this.obstacle = new Obstacle(this,spikeBitmap,1000, 600);

        gameThread = new GameThread(this,holder);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.setRunning(false);
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ball.jump();
        return true;
    }
}
