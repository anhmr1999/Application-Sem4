package com.project.game.component;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.project.game.MainActivity;
import com.project.game.R;
import com.project.game.common.ApiProviderImpl;
import com.project.game.common.CallApiResult;
import com.project.game.common.Contants;
import com.project.game.datamanager.repository.UserRepository;
import com.project.game.entity.User;

import java.util.List;

public class UserSettingDialog  extends Dialog {
    private int userAvatar;
    private String userName;

    public UserSettingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.user_setting_dialog);
        userAvatar = 1;
        if(Contants.User != null){
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
                    if(Contants.User == null){
                        Contants.User = new User(0,userName,"",userAvatar);
                    } else {
                        Contants.User.setName(userName);
                        Contants.User.setAvatar(userAvatar);
                    }

                    MainActivity.UpdateUser(context);
                    dismiss();
                }
            }
        });

        findViewById(R.id.userAvatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAvatar+=1;
                if(userAvatar > 5){
                    userAvatar = 1;
                }
                setImg();
            }
        });
    }

    private void setImg(){
        switch (userAvatar){
            case 1:
                ((ImageView)findViewById(R.id.userAvatar)).setImageResource(R.drawable.bird);
                userAvatar = 1;
                break;
            case 2:
                ((ImageView)findViewById(R.id.userAvatar)).setImageResource(R.drawable.user1);
                userAvatar = 2;
                break;
            case 3:
                ((ImageView)findViewById(R.id.userAvatar)).setImageResource(R.drawable.user2);
                userAvatar = 3;
                break;
            case 4:
                ((ImageView)findViewById(R.id.userAvatar)).setImageResource(R.drawable.user3);
                userAvatar = 4;
                break;
            case 5:
                ((ImageView)findViewById(R.id.userAvatar)).setImageResource(R.drawable.user4);
                userAvatar = 5;
                break;
            default:
                ((ImageView)findViewById(R.id.userAvatar)).setImageResource(R.drawable.bird);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        return false;
    }
}
