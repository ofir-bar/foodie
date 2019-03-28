package com.foodis.app.AddDish;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodis.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ByGramsOrByPiecesFragment extends Fragment {

    FrameLayout btnByGrams, btnByPieces;
    TextView textByGram, textByPieces;
    LinearLayout linByGrams;
    LinearLayout lin_pieces;
    int  btnChooseState = 0;

    public ByGramsOrByPiecesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_by_grams_or_pieces, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnByGrams = view.findViewById(R.id.btnByGrams);
        btnByPieces = view.findViewById(R.id.btnByPieces);
        textByGram = view.findViewById(R.id.textByGram);
        textByPieces = view.findViewById(R.id.textByPieces);
        linByGrams = view.findViewById(R.id.lin_grams);
        lin_pieces = view.findViewById(R.id.lin_pieces);

        btnByGrams.setOnClickListener(v -> {
            set_by_grams();
        });

        btnByPieces.setOnClickListener(v -> {
            set_by_pieces();
        });

    }

    public void set_by_grams(){
        btnChooseState = 1;
        btnByGrams.setBackgroundResource(R.drawable.rec_background_orange_with_border);
        btnByPieces.setBackgroundResource(R.drawable.rec_background_with_border);
        textByGram.setTextColor(getResources().getColor(R.color.white));
        textByPieces.setTextColor(getResources().getColor(R.color.Orange));
        textByGram.setTypeface(null, Typeface.BOLD);
        textByPieces.setTypeface(null, Typeface.NORMAL);
        linByGrams.setVisibility(View.VISIBLE);
        lin_pieces.setVisibility(View.GONE);
    }

    public void set_by_pieces(){
        btnChooseState = 2;
        btnByPieces.setBackgroundResource(R.drawable.rec_background_orange_with_border);
        btnByGrams.setBackgroundResource(R.drawable.rec_background_with_border);
        textByGram.setTextColor(getResources().getColor(R.color.Orange));
        textByPieces.setTextColor(getResources().getColor(R.color.white));
        textByGram.setTypeface(null, Typeface.NORMAL);
        textByPieces.setTypeface(null, Typeface.BOLD);
        linByGrams.setVisibility(View.GONE);
        lin_pieces.setVisibility(View.VISIBLE);

    }
}

