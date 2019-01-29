package hzkj.cc.myrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.FooterViewHolder> {
    Context context;
    List<String1> string1s;
    public TextAdapter(Context context, List<String1> string1s) {
        this.context = context;
        this.string1s=string1s;
    }

    @NonNull
    @Override
    public FooterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.load_more_footview_layout1, viewGroup, false);
        return new TextAdapter.FooterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FooterViewHolder footerViewHolder, int i) {
        footerViewHolder.a.setText(string1s.get(i).a);
        footerViewHolder.a.setText(string1s.get(i).b);
    }

    @Override
    public int getItemCount() {
        return string1s.size();
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView a;
        TextView b;

        public FooterViewHolder(View itemView) {
            super(itemView);
            a = itemView.findViewById(R.id.tvLoadText);
            b = itemView.findViewById(R.id.tvLoadText1);
        }
    }
}

