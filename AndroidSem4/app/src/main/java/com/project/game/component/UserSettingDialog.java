package com.project.game.component;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.project.game.MainActivity;
import com.project.game.R;
import com.project.game.common.Contants;
import com.project.game.datamanager.repository.UserRepository;
import com.project.game.entity.User;

public class UserSettingDialog  extends Dialog {
    private int userAvatar;
    private String userName;

    public UserSettingDialog(@NonNull Context context, boolean isUpdate) {
        super(context);
        setContentView(R.layout.user_setting_dialog);
        userAvatar = 1;
        if(isUpdate){
            findViewById(R.id.userSettingClose).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            findViewById(R.id.userSettingClose).setVisibility(View.INVISIBLE);
        }

        findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = ((EditText)findViewById(R.id.txtUserName)).getText().toString();
                if(userName.length() > 30){
                    ((TextView)findViewById(R.id.txtUserName_error)).setText("Ten qua dai(30)");
                    findViewById(R.id.txtUserName_error).setVisibility(View.VISIBLE);
                } else if(userName.replaceAll("\\s+","").length() == 0){
                    ((TextView)findViewById(R.id.txtUserName_error)).setText("Ten phai co ky tu khac khoang trang");
                    findViewById(R.id.txtUserName_error).setVisibility(View.VISIBLE);
                }else {
                    User user = new User(0,userName,"",userAvatar);
                    if(Contants.IsNetworkConnected(context)){

                    }
                    UserRepository repository = new UserRepository(context);
                    if(repository.add(user)){
                        Log.e("Sussecc","Done");
                        Contants.User = user;
                        MainActivity.userName.setText(Contants.User.getName());
                        MainActivity.userAvatar.setImageResource(Contants.getAvatarResource());
                        dismiss();
                    }
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        return false;
    }
}
