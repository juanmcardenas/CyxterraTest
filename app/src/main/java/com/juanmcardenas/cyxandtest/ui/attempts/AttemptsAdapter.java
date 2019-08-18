package com.juanmcardenas.cyxandtest.ui.attempts;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.juanmcardenas.auth.db.models.Attempt;
import com.juanmcardenas.cyxandtest.databinding.ItemAttemptBinding;

import java.util.Date;
import java.util.List;

/**
 * Created by Martin Cardenas on 2019-08-18.
 */
public class AttemptsAdapter extends RecyclerView.Adapter<AttemptsAdapter.AttemptViewHolder> {

    private List<Attempt> attemptList;

    public AttemptsAdapter(List<Attempt> attemptList) {
        this.attemptList = attemptList;
    }

    @NonNull
    @Override
    public AttemptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AttemptViewHolder(ItemAttemptBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull AttemptViewHolder holder, int position) {
        // Null checks
        if (attemptList == null || attemptList.size() <= position ) {
            return;
        }
        holder.bind(attemptList.get(position));
    }

    @Override
    public int getItemCount() {
        return attemptList == null ? 0 : attemptList.size();
    }

    public static class AttemptViewHolder extends RecyclerView.ViewHolder {

        ItemAttemptBinding binding;

        public AttemptViewHolder(ItemAttemptBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Attempt attempt) {
            binding.dateTv.setText(new Date(attempt.getDate()).toString());
            binding.resultTv.setText(attempt.getResult());
        }
    }

}
