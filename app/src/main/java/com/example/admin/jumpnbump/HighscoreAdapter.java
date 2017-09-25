package com.example.admin.jumpnbump;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HighscoreAdapter extends ArrayAdapter<Highscore> {
    private SimpleDateFormat sdf;
    private List<Highscore> highscoreList;
    Context context;

    private static class ViewHolder {
        TextView score;
        TextView date;
    }

    public HighscoreAdapter(Context context, List<Highscore> highscoreList) {
        super(context, R.layout.simple_list_item, highscoreList);
        this.highscoreList = highscoreList;
        this.context = context;
        sdf = new SimpleDateFormat("HH:mm dd.MM.yy");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Highscore highscore = getItem(position);
        final View result;
        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.simple_list_item, parent, false);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.score = (TextView) convertView.findViewById(R.id.score);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        int score = highscore.getScore();
        Date date = highscore.getDate();
        viewHolder.score.setText(String.format("%s - %s", context.getString(R.string.score),String.valueOf(score)));
        viewHolder.date.setText(String.format("%s - %s", context.getString(R.string.date), sdf.format(date)));

        ImageButton deleteButton = (ImageButton) result.findViewById(R.id.deleteScoreButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               remove(highscore);
                notifyDataSetChanged();
                //TODO: uncomment -> SaveFileUtils.writeScoresToFile(context, highscoreList);
            }
        });

        return result;
    }


}
