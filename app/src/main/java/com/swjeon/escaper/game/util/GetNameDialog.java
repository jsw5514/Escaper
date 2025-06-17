package com.swjeon.escaper.game.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class GetNameDialog extends AlertDialog{
    private static final String TAG = GetNameDialog.class.getSimpleName();
    Context context = GameView.view.getContext();
    private final boolean cancelable;
    EditText nameEntry = new EditText(context);
    public interface OnNameSetListener {
        void onNameSet(String name);
    }
    OnNameSetListener listener;
    public void setOnNameSetListener(OnNameSetListener listener) {
        this.listener = listener;
    }

    public GetNameDialog(Context context, boolean cancelable) {
        super(context);
        this.cancelable = cancelable;
        setCancelable(cancelable);
    }

    @Override
    public void show(){
        setTitle("랭킹에 등록할 이름을 입력하세요.");
        setView(nameEntry);
        setButton(BUTTON_POSITIVE, "등록", (OnClickListener) null);
        setButton(BUTTON_NEGATIVE, "등록 거부", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onNameSet(null);
            }
        });
        setOnShowListener(SetPositiveBtListener);
        if (cancelable){
            setButton(BUTTON_NEUTRAL, "취소", (OnClickListener) null);
        }
        super.show();
    }

    private final OnShowListener SetPositiveBtListener = new DialogInterface.OnShowListener() {
        @Override
        public void onShow(DialogInterface Idialog) {
            Log.i(TAG,"onShow 리스너 호출됨");
            Button positiveBt = getButton(AlertDialog.BUTTON_POSITIVE);
            positiveBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = nameEntry.getText().toString().trim();
                    if (name.isEmpty()) {
                        Toast.makeText(context, "이름을 입력하지 않고 종료하려면 '등록 거부' 버튼을 누르세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.i(TAG,"이름이 입력됨: "+ name);
                        listener.onNameSet(name);
                        dismiss();
                    }
                }
            });
        }
    };
}
