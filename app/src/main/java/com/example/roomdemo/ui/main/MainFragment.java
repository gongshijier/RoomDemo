package com.example.roomdemo.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.roomdemo.R;
import com.example.roomdemo.model.Word;

import java.util.List;

public class MainFragment extends Fragment {
    TextView textView ;
    Button insert ;
    Button delete ;
    Button deleteAll ;
    Button update ;

    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.main_fragment, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
       textView = view.findViewById(R.id.textView);
        insert = view.findViewById(R.id.addButton);
       delete = view.findViewById(R.id.deleteButton);
         deleteAll = view.findViewById(R.id.clearButton);
         update = view.findViewById(R.id.updateButton);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //实时监听数据库的变化
        mViewModel.getAll().observe(requireActivity(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                StringBuilder text = new StringBuilder();
                for(int i=0;i<words.size();i++) {
                    Word word = words.get(i);
                    text.append(word.getId()).append(":").append(word.getEnglish()).append("=").append(word.getMean()).append("\n");
                }
                textView.setText(text.toString());

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("Hello","你好！");
                Word word2 = new Word("World","世界！");
                mViewModel.insert(word1,word2);
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.deleteAll();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Hi","你好啊!");
                word.setId(2);
                mViewModel.update(word);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Hi","你好啊!");
                word.setId(4);
                mViewModel.delete(word);
            }
        });


    }

}
