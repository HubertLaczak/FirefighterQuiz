package com.example.quiz.Others;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quiz.R;

import java.util.ArrayList;

public class ExampleAdapterChapter extends RecyclerView.Adapter<ExampleAdapterChapter.ExampleViewHolder> {

    private ArrayList<ExChapter> mExampleList;
    private OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_ChaptersName,tv_ChapterCount, tv_Correct, tv_Incorrect, tv_Remembered;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            tv_ChapterCount = itemView.findViewById(R.id.tv_ChapterCount);
            tv_ChaptersName = itemView.findViewById(R.id.tv_ChaptersName);
            tv_Correct = itemView.findViewById(R.id.tv_Correct);
            tv_Incorrect = itemView.findViewById(R.id.tv_Incorrect);
            tv_Remembered = itemView.findViewById(R.id.tv_Remembered);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                           listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public ExampleAdapterChapter(ArrayList<ExChapter> exampleList){
        this.mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.example_chapter_item, viewGroup, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        ExChapter currentItem = mExampleList.get(i);
        exampleViewHolder.tv_ChapterCount.setText(String.valueOf(currentItem.getChapterNumber()));
        exampleViewHolder.tv_ChaptersName.setText(currentItem.getChapterName());
        exampleViewHolder.tv_Correct.setText(String.valueOf(currentItem.getCorrect()));
        exampleViewHolder.tv_Incorrect.setText(String.valueOf(currentItem.getIncorrect()));
        exampleViewHolder.tv_Remembered.setText(String.valueOf(currentItem.getRemembered()));
    }

    @Override
    public int getItemCount()
    {
        return mExampleList.size();
    }
}
