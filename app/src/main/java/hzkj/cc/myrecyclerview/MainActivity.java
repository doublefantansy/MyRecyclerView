package hzkj.cc.myrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import hzkj.cc.my_recyclerview.MyRecyclerView;
import hzkj.cc.my_recyclerview.OnLoadListenner;
import hzkj.cc.my_recyclerview.OnRreshListenner;

public class MainActivity extends AppCompatActivity {
    List<String1> mDatas = new ArrayList<>();
    MyRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        for (int i = 0; i < 10; i++) {
            String1 s = new String1();
            s.setA("a" + i);
            s.setB("b" + i);
            mDatas.add(s);
        }
        recyclerView.setInsideAdapter(new TextAdapter(this, mDatas), new OnRreshListenner() {
            @Override
            public void refresh() {
                mDatas.clear();
                recyclerView.refreshSuccess();
            }
        }, new OnLoadListenner() {
            @Override
            public void loadMore() {

                recyclerView.loadMoreFail();
            }
        });
    }
}
