//package com.example.evenyan.businesscard;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//}

package com.example.evenyan.businesscard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * 主Activity
 *
 * @author wwj_748
 *
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

    // 三个tab布局
    private RelativeLayout homeLayout, cardLayout, meLayout;

    // 底部标签切换的Fragment
    private Fragment homeFragment, cardFragment, meFragment, currentFragment;
    // 底部标签图片
    private ImageView homeImg, cardImg, meImg;
    // 底部标签的文本
//    private TextView homeTv, cardTv, meTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_main);

        initUI();
        initTab();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        homeLayout = (RelativeLayout) findViewById(R.id.rl_know);
        cardLayout = (RelativeLayout) findViewById(R.id.rl_want_know);
        meLayout = (RelativeLayout) findViewById(R.id.rl_me);
        homeLayout.setOnClickListener(this);
        cardLayout.setOnClickListener(this);
        meLayout.setOnClickListener(this);

        homeImg = (ImageView) findViewById(R.id.iv_know);
        cardImg = (ImageView) findViewById(R.id.iv_i_want_know);
        meImg = (ImageView) findViewById(R.id.iv_me);
//        homeTv = (TextView) findViewById(R.id.tv_know);
//        cardTv = (TextView) findViewById(R.id.tv_i_want_know);
//        meTv = (TextView) findViewById(R.id.tv_me);

    }

    /**
     * 初始化底部标签
     */
    private void initTab() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }

        if (!homeFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_layout, homeFragment).commit();

            // 记录当前Fragment
            currentFragment = homeFragment;
            // 设置图片文本的变化
            homeImg.setImageResource(R.drawable.home_pre);
//            homeTv.setTextColor(getResources()
//                    .getColor(R.color.bottomtab_press));
            cardImg.setImageResource(R.drawable.card_nor);
//            cardTv.setTextColor(getResources().getColor(
//                    R.color.bottomtab_normal));
            meImg.setImageResource(R.drawable.me_nor);
//            meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_know: // 知道
                clickTab1Layout();
                break;
            case R.id.rl_want_know: // 我想知道
                clickTab2Layout();
                break;
            case R.id.rl_me: // 我的
                clickTab3Layout();
                break;
            default:
                break;
        }
    }

    /**
     * 点击第一个tab
     */
    private void clickTab1Layout() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), homeFragment);

        // 设置底部tab变化
        homeImg.setImageResource(R.drawable.home_pre);
//        homeTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
        cardImg.setImageResource(R.drawable.card_nor);
//        cardTv.setTextColor(getResources().getColor(
//                R.color.bottomtab_normal));
        meImg.setImageResource(R.drawable.me_nor);
//        meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
    }

    /**
     * 点击第二个tab
     */
    private void clickTab2Layout() {
        if (cardFragment == null) {
            cardFragment = new CardActivityFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), cardFragment);

        homeImg.setImageResource(R.drawable.home_nor);
//        homeTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        cardImg.setImageResource(R.drawable.card_pre);
//        cardTv.setTextColor(getResources().getColor(
//                R.color.bottomtab_press));
        meImg.setImageResource(R.drawable.me_nor);
//        meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));

    }

    /**
     * 点击第三个tab
     */
    private void clickTab3Layout() {
        if (meFragment == null) {
            meFragment = new MeFragment();
        }

        addOrShowFragment(getSupportFragmentManager().beginTransaction(), meFragment);
        homeImg.setImageResource(R.drawable.home_nor);
//        homeTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        cardImg.setImageResource(R.drawable.card_nor);
//        cardTv.setTextColor(getResources().getColor(
//                R.color.bottomtab_normal));
        meImg.setImageResource(R.drawable.me_pre);
//        meTv.setTextColor(getResources().getColor(R.color.bottomtab_press));

    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.content_layout, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }

}
