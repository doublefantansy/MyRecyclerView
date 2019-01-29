package hzkj.cc.my_recyclerview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SuperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private LinearLayoutManager layoutManager;
    Context context;
    public static final int PULLUP_LOAD_MORE = 0;
    public static final int LOADING_MORE = 1;
    public static final int NO_LOAD_MORE = 2;
    public static final int FAIL = 3;
    private int mLoadMoreStatus = PULLUP_LOAD_MORE;
    RecyclerView.Adapter adpter;

    public SuperAdapter(Context context, RecyclerView.Adapter adpter) {
        this.context = context;
        this.adpter = adpter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == TYPE_ITEM) {
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_refresh_recylerview, viewGroup, false);
            return new ItemViewHolder(itemView);
        } else if (i == TYPE_FOOTER) {
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.load_more_footview_layout, viewGroup, false);
            return new FooterViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof SuperAdapter.ItemViewHolder) {
            SuperAdapter.ItemViewHolder itemViewHolder = (SuperAdapter.ItemViewHolder) viewHolder;
        } else if (viewHolder instanceof SuperAdapter.FooterViewHolder) {
            SuperAdapter.FooterViewHolder footerViewHolder = (SuperAdapter.FooterViewHolder) viewHolder;
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(footerViewHolder.mPbLoad, "rotation", 0f, 360f);
            objectAnimator.setDuration(3000);
            LinearInterpolator linearInterpolator = new LinearInterpolator();
            objectAnimator.setInterpolator(linearInterpolator);
            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator.start();
            switch (mLoadMoreStatus) {
                case PULLUP_LOAD_MORE:
                    footerViewHolder.mTvLoadText.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footerViewHolder.mTvLoadText.setText("正加载更多...");
                    break;
                case NO_LOAD_MORE:
                    footerViewHolder.mLoadLayout.setVisibility(View.GONE);
                    break;
                case FAIL:
                    footerViewHolder.mTvLoadText.setText("加载失败");
                    break;
            }
        }
    }

    public void refresh() {
        adpter.notifyDataSetChanged();
    }

    public void scoll(int n) {

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recylerView);
            layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adpter);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        ImageView mPbLoad;
        TextView mTvLoadText;
        LinearLayout mLoadLayout;

        public FooterViewHolder(View itemView) {
            super(itemView);
            mPbLoad = itemView.findViewById(R.id.pbLoad);
            mTvLoadText = itemView.findViewById(R.id.tvLoadText);
            mLoadLayout = itemView.findViewById(R.id.loadLayout);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void changeMoreStatus(int status) {
        mLoadMoreStatus = status;
        notifyDataSetChanged();
    }
}

