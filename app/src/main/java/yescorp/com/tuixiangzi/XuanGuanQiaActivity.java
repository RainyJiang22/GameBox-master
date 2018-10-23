package yescorp.com.tuixiangzi;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;

public class XuanGuanQiaActivity extends Activity {

    public static final String GUAN_QIA = "GuanQia";
    private Boolean mGameLevels_PassedOrNot[];

    private XuanGuanQiaAdapter xgqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_xuan_guan_qia);
        if (GameInitialData.GameLevels.size() == 0)
            //GameInitialData.addInitGameData();
            try {
                GameInitialData.readInitialData(getResources(), GameInitialData.CONFIG_FILE_NAME);
            } catch (IOException e) {
                Toast.makeText(this, "无法读取配置文件。无法获取关卡数据。退出。", Toast.LENGTH_LONG).show();
                System.exit(-1);
            }
        mGameLevels_PassedOrNot = new Boolean[GameInitialData.GameLevels.size()];
        for (int level = 1; level <= GameInitialData.GameLevels.size(); level++)
                mGameLevels_PassedOrNot[level - 1] = PrfsManager.getPassedLevel(this, level);

        xgqAdapter = new XuanGuanQiaAdapter(this, R.layout.item_guan_qia_gridview, mGameLevels_PassedOrNot);
        GridView gv_GuanQia = (GridView) findViewById(R.id.gv_xuan_guan_qia);
        gv_GuanQia.setAdapter(xgqAdapter);
//        gv_GuanQia.setOnItemSelectedListener(new GuanQia_ItemSelectedListener());
        gv_GuanQia.setOnItemClickListener(new GV_ItemClickListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        xgqAdapter.notifyDataSetChanged();  //有可能，玩家过了一关
    }

    private class GuanQia_ItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int itemIndex, long l) {
            Toast.makeText(XuanGuanQiaActivity.this, "选中了第" + itemIndex + "关", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private class GV_ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
            int level = itemIndex + 1;

            Intent intent = new Intent(XuanGuanQiaActivity.this, GameActivity.class);
            intent.putExtra(GUAN_QIA, level);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_xuan_guanqia, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itm_xgq_back:
                finish();
                break;
            case R.id.itm_xgq_exit:
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
