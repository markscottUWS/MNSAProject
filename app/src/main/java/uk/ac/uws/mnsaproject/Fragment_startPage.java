package uk.ac.uws.mnsaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment_startPage extends Fragment implements View.OnClickListener{

    private TextView mTv1, mTv2;
    private Button mButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_welcome_first, container, false);

        mTv1 = view.findViewById(R.id.main_welcome_textview);
        mTv2 = view.findViewById(R.id.sub_welcome_textview);
        mButton = view.findViewById(R.id.get_started_button);
        mButton.setOnClickListener(this);
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public void onClick(View v) {
        Intent detailsIntent = new Intent(getActivity(), DetailsActivity.class);
        startActivity(detailsIntent);
    }
}
