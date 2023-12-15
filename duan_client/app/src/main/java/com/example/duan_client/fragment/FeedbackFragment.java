package com.example.duan_client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.duan_client.R;
import com.example.duan_client.adapter.FeedbackAdapter;
import com.example.duan_client.model.FeedBack;

public class FeedbackFragment extends Fragment {
    RecyclerView recyclerViewFeedback;
    EditText edtFeedbackContent;
    Button btnSendFeedback;
    FeedbackAdapter adapter;
    List<FeedBack> feedBacks = new ArrayList<>();
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    // Appearance
    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewFeedback = view.findViewById(R.id.recyclerViewFeedback);
        showListFeedback();
    }

    public void showListFeedback() {
        adapter = new FeedbackAdapter(getContext(), feedBacks);
        getAllFeedbacks();
        recyclerViewFeedback.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerViewFeedback.setAdapter(adapter);
    }

    public void getAllFeedbacks() {
        myRef.child("feedbacks").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FeedBack feedBack = snapshot.getValue(FeedBack.class);
                if (feedBack != null) {
                    feedBacks.add(feedBack);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FeedBack feedBack = snapshot.getValue(FeedBack.class);
                if (feedBack == null || feedBacks == null || feedBacks.isEmpty()) {
                    return;
                }
                for (int i = 0; i < feedBacks.size(); i++) {
                    if (feedBack.getId().equals(feedBacks.get(i).getId())) {
                        feedBacks.set(i, feedBack);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                FeedBack feedBack = snapshot.getValue(FeedBack.class);
                if (feedBack == null || feedBacks == null || feedBacks.isEmpty()) {
                    return;
                }
                for (int i = 0; i < feedBacks.size(); i++) {
                    if (feedBack.getId().equals(feedBacks.get(i).getId())) {
                        feedBacks.remove(feedBacks.get(i));
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}