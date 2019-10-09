package com.example.information;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import java.io.IOException;


public class PlayvideotwoActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    Button bt1,bt2;
    MediaPlayer mediaPlayer;
    SurfaceHolder surfaceHolder;
    TextView tv_timing,tv_total;
    SeekBar seekBar;
    String videourlink;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playvideotwo);
        videourlink=getIntent().getBundleExtra("bundle").getString("videourl");

        initView();
        function_control();
        getdata();
    }
    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            bt1.setVisibility(View.VISIBLE);

        }
    };
    MediaPlayer.OnErrorListener onErrorListener=new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            return false;
        }
    };
    private void initView(){
        bt1=(Button)findViewById(R.id.videoplay__start);
        tv_timing=(TextView)findViewById(R.id.video_timing);
        tv_total=(TextView)findViewById(R.id.video_totaltime);
        seekBar=(SeekBar)findViewById(R.id.video_seekbar);

        back=(ImageView)findViewById(R.id.videoplay_back);
        surfaceView=(SurfaceView)findViewById(R.id.videoplay_surface);

        mediaPlayer=new MediaPlayer();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {
//                    surfaceHolder.setKeepScreenOn(true);
//                    surfaceHolder.setFixedSize(320,320);
                //    surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                //    mediaPlayer.reset();
                    mediaPlayer.setDataSource(videourlink);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置播放声音类型
                    mediaPlayer.prepare();
                    mediaPlayer.setDisplay(surfaceView.getHolder());
//                    mediaPlayer.start();
//                    handlervideo.post(runnable);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d("changge", "surfaceChanged=" + System.currentTimeMillis());
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d("destory", "surfaceDestroyed=" + System.currentTimeMillis());
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d("prepared", "onPrepared=" + System.currentTimeMillis());
                mediaPlayer.start();
                handlervideo.post(runnable);
            }
        });

    }
    private void function_control(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                mediaPlayer.release();
                handlervideo.removeCallbacks(runnable);
                Bundle bundle=getIntent().getBundleExtra("bundle");
                Intent intent=new Intent(PlayvideotwoActivity.this,PlayvideooneActivity.class);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b)
                    mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(seekBar.getProgress()*1000);
                mediaPlayer.start();
                handlervideo.post(runnable);

                //获取进度条的进度
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                bt1.setVisibility(View.INVISIBLE);

            }
        });
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    bt1.setVisibility(View.VISIBLE);
                }
                else{
                    bt1.setVisibility(View.INVISIBLE);
                    mediaPlayer.start();
                }
            }
        });
        mediaPlayer.setOnCompletionListener(onCompletionListener);
        mediaPlayer.setOnErrorListener(onErrorListener);
    }
    //加载数据
    private void getdata(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path=videourlink;
                Message msg=new Message();
                msg.obj=path;
                handler.sendMessage(msg);
                // url = new URL("http://music.baidu.com/data/music/fmlink?rate=320&songIds=588259036&type=&callback=&_t=&format=json");//605563712

            }
        }).start();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            play(videourlink);
        }
    };
    //
    private void play(String link){



           // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaPlayer.setDataSource(link);
//            mediaPlayer.prepare();




    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(mediaPlayer.getCurrentPosition()/1000);

            setTime();
            handlervideo.postDelayed(runnable,200);

        }
    };
    Handler handlervideo=new Handler();
    private void setTime(){
        // seekBar.setMax(mediaPlayer.getDuration());
        int now=mediaPlayer.getCurrentPosition();//获取当前的播放进度
        int count=0;
        count=mediaPlayer.getDuration()/1000;
        int seccond=now/1000;//当前有多少秒
        int csecound=count;//总共有多少秒

        seekBar.setMax(count);

        tv_timing.setText(String.valueOf(seccond/60)+":"+String.valueOf(seccond%60));
        tv_total.setText(String.valueOf(csecound/60)+":"+String.valueOf(csecound%60));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
        mediaPlayer.release();
        handlervideo.removeCallbacks(runnable);
    }
}
