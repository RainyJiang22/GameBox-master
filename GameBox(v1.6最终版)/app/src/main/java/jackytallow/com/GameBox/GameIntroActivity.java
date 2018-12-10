package jackytallow.com.GameBox;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import yescorp.com.tuixiangzi.R;

public class GameIntroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_game_intro);
        ListView lv_game_intro = (ListView) findViewById(R.id.lv_game_intro);
        GameIntroAdapter gameIntroAdapter = new GameIntroAdapter(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.game_intro));
        lv_game_intro.setAdapter(gameIntroAdapter);

    }

    private class GameIntroAdapter extends ArrayAdapter<String>{

        private String [] mIntroStatements;
        private Context mContext;
        public GameIntroAdapter(Context context, int resource, String[] statements) {
            super(context, resource, statements);
            mIntroStatements = statements;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mIntroStatements.length;
        }

        @Override
        public String getItem(int position) {
            return mIntroStatements[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv_statement;
            if (convertView == null)
                tv_statement = new TextView(mContext);
            else
                tv_statement = (TextView) convertView;
            if (position % 2 == 0)
                tv_statement.setTextColor(Color.BLACK);
            else
                tv_statement.setTextColor(Color.BLUE);
            tv_statement.setText(mIntroStatements[position]);
            return tv_statement;
        }
    }


}
