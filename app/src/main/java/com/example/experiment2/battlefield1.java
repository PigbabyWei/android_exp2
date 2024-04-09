package com.example.experiment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.media.MediaPlayer;

public class battlefield1 extends AppCompatActivity {

    private static int eliminateCount = 0;

    /*使用SharedPreferences类存储计数器中的击杀敌人数.
    SharedPreferences是Android平台上一个轻量级的存储类，
    适合用于保存一些简单的使用数据，不会生成复杂的文件结构，
    而且它支持保存bool, float, int, long, 和 string五种数据类型。*/
    SharedPreferences sharedPreferences;

    /*public static SharedPreferences.Editor getEditor() {
        return editor;
    }*/

    //static
    /*不要为了生成getter而把SharedPreferences.Editor设为static,因为这种方式在处理并发时可能会出现问题,
    并且也不符合封装原则.事实上,这样会在运行时出现闪退问题.logcat中的错误日志显示,这样会导致空指针异常.

    解决办法? 不要偷懒,在MainActivity中再创建一个新的SharedPreferences和Editor实例来完成清零的操作,不要在
    battlefield里创建过多getter.
    */
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG","battlefield_onCreate");
        setContentView(R.layout.activity_battlefield);
        Button backToBase = (Button) findViewById(R.id.backToBase);
        backToBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(battlefield1.this, MainActivity.class);
                intent.putExtra("counter",eliminateCount);
                intent.setClass(battlefield1.this,MainActivity.class);
                startActivity(intent);
            }
        });



        int[] japImageIds = new int[]{
              R.id.imageView1,
              R.id.imageView2,
              R.id.imageView3,
              R.id.imageView4,
              R.id.imageView5,
              R.id.imageView6,
              R.id.imageView7,
              R.id.imageView8,
        };

        for(final int japImageId : japImageIds){
            final ImageView japImage = findViewById(japImageId);
            japImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //击杀敌人时播放狙击枪的声音.
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.sniperrifle1);
                    mediaPlayer.start();

                    japImage.setVisibility(View.GONE);
                    eliminateCount++;

                    //不需要播放音频时可以调用mediaPlayer.release()方法来释放资源.
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                        public void onCompletion(MediaPlayer mp){
                            mp.release();
                        }
                    });
                }
            });
        }

        //用于在Activity间进行数据传输.
        SharedPreferences sharedPreferences = getSharedPreferences("counter",MODE_PRIVATE);
        eliminateCount = sharedPreferences.getInt("eliminateCount",0);
    }

    @Override
    /*
    在GameActivity的onPause()或onStop()方法中保存计数值就行了，
    因为这两个方法都表示Activity不再在前台与用户交互（可能是因为用户按了Home键，或者用户打开了另一款应用，等等）。
    特别是当系统内存不足，需要关闭一些应用时，一般会选择那些不在前台的应用关闭,所以需要用SharedPreferences对数据进行持久化存储.
    */
    public void onPause(){
        super.onPause();
        //初始化SharedPreferences类的对象.
        sharedPreferences = getSharedPreferences("counter",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("eliminateCount",eliminateCount);
        editor.commit();
    }
    public static int getEliminateCount() {
            return eliminateCount;
    }

}
