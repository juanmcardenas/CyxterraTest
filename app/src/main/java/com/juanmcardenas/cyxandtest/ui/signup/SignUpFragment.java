package com.juanmcardenas.cyxandtest.ui.signup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juanmcardenas.cyxandtest.R;
import com.juanmcardenas.cyxandtest.databinding.FragmentSignUpBinding;
import com.juanmcardenas.cyxandtest.ui.SecurityQuestionPicker;

/**
 * Registration screen
 */
public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private MutableLiveData<String> securityQuestionLiveData;


    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        securityQuestionLiveData = new MutableLiveData<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sign_up, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Select security question
        binding.selectQuestionBtn.setOnClickListener(v -> new SecurityQuestionPicker().show(getContext(), securityQuestionLiveData));
        // Observe security question
        securityQuestionLiveData.observe(this, s -> {
            if (!TextUtils.isEmpty(s)) {
                binding.selectQuestionBtn.setText(s);
                binding.answerEt.setVisibility(View.VISIBLE);
            }
        });

    }
}
