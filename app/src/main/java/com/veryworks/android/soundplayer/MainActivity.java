package com.veryworks.android.soundplayer;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.veryworks.android.soundplayer.domain.Database;
import com.veryworks.android.soundplayer.domain.Sound;
import com.veryworks.android.soundplayer.util.fragment.PagerAdapter;

import java.text.Format;
import java.util.List;

import static com.veryworks.android.soundplayer.R.id.tab;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int REQ_PERMISSION = 100; // 권한요청코드
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("메인액티비티 - 온크리에이트", "======================");

        if (savedInstanceState != null)
            return;

        checkPermission();
    }

    private void init() {

        // 화면의 툴바 가져오기
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setTitleTextColor(Color.BLACK); // 타이틀 텍스트 컬러 변경
        setSupportActionBar(toolbar);

        // 네비 드로워 설정
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 컨텐트영역
        // 1. 탭 레이아웃
        TabLayout tabLayout = (TabLayout) findViewById(tab);
        // tabLayout.setTabMode(TabLayout.MODE_FIXED); // Default
        // tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE); // 가로축 스크롤하기

        // 탭 생성 및 타이틀 입력
        tabLayout.addTab(tabLayout.newTab().setText(
                getResources().getString(R.string.menu_title)) // "Title" -> values/strings.xml > 값을 세팅
        );
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.menu_artist)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.menu_album)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.menu_genre)));
        // 2. 뷰페이저
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        // 아답터 설정 필요
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        // 아답터에 프래그먼트 추가

        final ListFragment listFragment1 = ListFragment.newInstance(1, ListFragment.TYPE_SONG);
        final ListFragment listFragment2 = ListFragment.newInstance(3, ListFragment.TYPE_ARTIST);
        final ListFragment listFragment3 = ListFragment.newInstance(3, ListFragment.TYPE_ALBUM);
        final ListFragment listFragment4 = ListFragment.newInstance(1, ListFragment.TYPE_GENRE);

        adapter.add(listFragment1);
        adapter.add(listFragment2);
        adapter.add(listFragment3);
        adapter.add(listFragment4);

        viewPager.setAdapter(adapter);

        // 1. 페이저 리스너 : 페이저가 변경되었을때 탭을 바꿔주는 리스너
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // 2. 탭 리스너 : 탭이 변경되었을 때 페이지를 바꿔저는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));


        // 플로팅 버튼 설정
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setShuffle();
            }
        });

        FloatingActionButton btnUp = (FloatingActionButton) findViewById(R.id.btnUp);
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        listFragment1.recyclerView.smoothScrollToPosition(listFragment1.position);
                        break;
                    case 1:
                        listFragment2.recyclerView.smoothScrollToPosition(listFragment2.position);
                        break;
                    case 2:
                        listFragment3.recyclerView.smoothScrollToPosition(listFragment3.position);
                        break;
                    case 3:
                        listFragment4.recyclerView.smoothScrollToPosition(listFragment4.position);
                }
            }
        });

    }

    // 리스트 섞기
    public void setShuffle() {
        // TODO 구현합시다
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // 툴바 우측 상단 메뉴 설정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(null);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Sound sound;
                String title = null;
                String artist = null;

                for (int i = 0; i < Database.getSoundListSize(); i++) {
                    sound = Database.getSound(i);
                    title = sound.getTitle();
                    artist = sound.getArtist();

                }
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query", query);
                intent.putExtra("title", title);
                intent.putExtra("artist", artist);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return true;
    }

    // 툴바 우측 상단 메뉴 onClick
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(this, "Setting is selected!!!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_mylist:
                Toast.makeText(this, "My List is selected!!!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_search:
                Toast.makeText(this, "Search is selected!!!", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 네비게이션 드로어 메뉴가 onClick 되면 호출
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_title) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.menu_artist) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.menu_album) {
            viewPager.setCurrentItem(2);
        } else if (id == R.id.menu_genre) {
            viewPager.setCurrentItem(3);
        } else if (id == R.id.nav_mylist) {

        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // 권한관리
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionControl.checkPermission(this, REQ_PERMISSION)) {
                init();
            }
        } else {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_PERMISSION) {
            if (PermissionControl.onCheckResult(grantResults)) {
                init();
            } else {
                Toast.makeText(this, "권한을 허용하지 않으시면 프로그램을 실행할 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        Log.i("메인액티비티 - 온리줌", "======================");

        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.i("메인액티비티 - 온스탑", "======================");

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("메인액티비티 - 온디스트로이", "======================");
        super.onDestroy();
    }

    //    //13글자 이상일경우 ...으로 표기
//    public static String titleFomatter(String title) {
//        String result = String.format("%10s", title);
//        result = result + "...";
//        return result;
//    }


}
