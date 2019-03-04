package com.example.quiz.OnlyQuestion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.quiz.R;

import androidx.recyclerview.widget.RecyclerView;


public class OnlyQuestionAdapter extends RecyclerView.Adapter<OnlyQuestionAdapter.ExampleViewHolder> {

    private String[] mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;
        public TextView mTextView2;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public OnlyQuestionAdapter(String[]  exampleList){
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.example_item, viewGroup, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int i) {
//        OnlyQuestion currentQuestion = mExampleList.get(i);
        String currentQuestion = mExampleList[i];
        holder.mTextView1.setText(String.valueOf(i+1));
        holder.mTextView2.setText(currentQuestion);
//        if(currentQuestion.getCorrect().equals("true")){
//            holder.mTextView1.setBackgroundColor(Color.parseColor("#00ff00"));
//        } else {
//            holder.mTextView1.setBackgroundColor(Color.parseColor("#ff0000"));
//        }

    }

    @Override
    public int getItemCount() {
        return mExampleList.length;
    }

}
