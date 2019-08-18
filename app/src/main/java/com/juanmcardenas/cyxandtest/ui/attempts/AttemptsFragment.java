package com.juanmcardenas.cyxandtest.ui.attempts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juanmcardenas.auth.AuthManager;
import com.juanmcardenas.cyxandtest.R;
import com.juanmcardenas.cyxandtest.databinding.FragmentAttemptsBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Main screen to see attempts
 */
public class AttemptsFragment extends Fragment {

    private FragmentAttemptsBinding binding;

    public AttemptsFragment() {
        // Required empty public constructor
    }

    public static AttemptsFragment newInstance(String param1, String param2) {
        AttemptsFragment fragment = new AttemptsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_attempts, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Fetch list of attempts
        AuthManager authManager = new AuthManager();
        authManager.getAttemptList(getActivity())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(attempts -> {
                    AttemptsAdapter adapter = new AttemptsAdapter(attempts);
                    binding.attemptsListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.attemptsListRv.setAdapter(adapter);
                });

    }
}
