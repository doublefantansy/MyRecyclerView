package hzkj.cc.my_recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.logging.Handler;

public class MyRecyclerView extends LinearLayout {
    SuperAdapter adapter;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView.Adapter insideAdapter;
    OnRreshListenner onRreshListenner;
    OnLoadListenner onLoadListenner;
    int lastVisibleItem;

    public MyRecyclerView(Context context) {
        super(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setInsideAdapter(RecyclerView.Adapter adapter, OnRreshListenner onRreshListenner, OnLoadListenner onLoadListenner) {
        insideAdapter = adapter;
        this.onRreshListenner = onRreshListenner;
        this.onLoadListenner = onLoadListenner;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.recycler, null);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        this.addView(view);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new SuperAdapter(getContext(), insideAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusableInTouchMode(false);
        initListener();
        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        initPullRefresh();
        initLoadMoreListener();
    }

    private void initPullRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRreshListenner.refresh();
            }
        });
    }

    public void refreshSuccess() {
        adapter.refresh();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void loadMoreSuccess() {
        adapter.changeMoreStatus(SuperAdapter.NO_LOAD_MORE);
        adapter.refresh();
        adapter.changeMoreStatus(SuperAdapter.PULLUP_LOAD_MORE);
    }

    public void loadMoreFail() {
        adapter.changeMoreStatus(SuperAdapter.FAIL);
        recyclerView.scrollToPosition(lastVisibleItem - 1);
//        adapter.refresh();
    }

    private void initLoadMoreListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    adapter.changeMoreStatus(SuperAdapter.LOADING_MORE);
                    onLoadListenner.loadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }
}
