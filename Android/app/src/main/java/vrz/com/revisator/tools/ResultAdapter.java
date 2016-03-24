package vrz.com.revisator.tools;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vrz.com.revisator.R;

/**
 * ListView peronalisée pour l'affichage des résultats
 * Created by Victor on 24/03/2016.
 */
public class ResultAdapter extends ArrayAdapter<String[]>{

    public ResultAdapter(Context context, ArrayList<String[]> resource) {
        super(context, R.layout.resultlistview_rows, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater resultInflater = LayoutInflater.from(getContext());
        View resultView = resultInflater.inflate(R.layout.resultlistview_rows, parent, false);

        TextView questionNumberTextView = (TextView) resultView.findViewById(R.id.questionNumber);
        TextView questionTitleTextView = (TextView) resultView.findViewById(R.id.questionTitle);
        TextView userAnswerTextView = (TextView) resultView.findViewById(R.id.userAnswer);
        TextView correctAnswerTextView = (TextView) resultView.findViewById(R.id.userCorrect);

        String singleQuestionNumberText = getItem(position)[4];
        String singleQuestionTitle = getItem(position)[0];
        String singleUserAnswer = getItem(position)[1];
        String singleCorrectAnswer = getItem(position)[2];

        questionNumberTextView.setText(questionNumberTextView.getText() + " " + singleQuestionNumberText);
        questionTitleTextView.setText(singleQuestionTitle);
        userAnswerTextView.setText(singleUserAnswer);
        correctAnswerTextView.setText(singleCorrectAnswer);

        if(getItem(position)[3].equals("True"))
            questionNumberTextView.setTextColor(Color.rgb(40, 144, 32));
        else
            questionNumberTextView.setTextColor(Color.RED);

        return resultView;

    }
}
