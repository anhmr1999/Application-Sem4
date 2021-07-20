package com.project.game.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.game.R;
import com.project.game.gamecontroll.Game2048;
import com.project.game.gameobj.Box;

import java.util.ArrayList;
import java.util.List;

public class BoxAdapter extends ArrayAdapter<Integer> {
    private Context context;
    private ArrayList<Integer> arr;

    public BoxAdapter(@NonNull Context context, int resource, @NonNull List<Integer> objects) {
        super(context, resource, objects);
        this.context = context;
        arr = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.box_item,null);
        }
        if(arr.size() > 1){
            Box box = (Box)convertView.findViewById(R.id.txtBox);
            box.setTextBox(arr.get(position));
        }
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        arr = Game2048.getDataGame().getArrView();
        super.notifyDataSetChanged();
    }
}