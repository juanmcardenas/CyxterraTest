package com.juanmcardenas.cyxandtest.ui.signup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.juanmcardenas.auth.AuthManager;
import com.juanmcardenas.auth.db.models.User;
import com.juanmcardenas.auth.util.DialogUtil;
import com.juanmcardenas.cyxandtest.R;
import com.juanmcardenas.cyxandtest.databinding.FragmentSignUpBinding;
import com.juanmcardenas.cyxandtest.ui.SecurityQuestionPicker;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        // Register button
        binding.registerBtn.setOnClickListener(v -> {
            AuthManager authManager = new AuthManager();
            authManager.register(getActivity(), binding.usernameEt.getText().toString(),
                    binding.passwordEt.getText().toString(),
                    binding.confirmPasswordEt.getText().toString(),
                    binding.selectQuestionBtn.getText().toString(),
                    binding.answerEt.getText().toString())
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<User>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(User user) {
                            if (user != null) {
                                Toast.makeText(getContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(v).navigateUp();
                            } else {
                                DialogUtil.showErrorDialog(getContext(), AuthManager.RESULT_ERROR_INVALID_CREDENTIALS);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            DialogUtil.showErrorDialog(getContext(), e.getMessage());
                        }
                    });
        });

    }
}
