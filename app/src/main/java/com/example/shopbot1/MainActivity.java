package com.example.shopbot1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.shopbot1.ui.gallery.GalleryFragment;
import com.example.shopbot1.ui.home.HomeFragment;
import com.example.shopbot1.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        firebaseAuth=FirebaseAuth.getInstance();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_signout)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment1 = null;

                switch (id) {
                    case R.id.nav_home:
                        //navigationView.findViewById(R.id.nav_home);
                         fragment1 = new HomeFragment();
                        moveToFragment(fragment1);
                        break;
                    case R.id.nav_gallery:
                        fragment1 = new GalleryFragment();
                        moveToFragment(fragment1);
                        break;
                    case  R.id.nav_slideshow:
                        //navigationView.findViewById(R.id.nav_slideshow);
                        fragment1 = new SlideshowFragment();
                        moveToFragment(fragment1);
                        break;
                    case R.id.nav_signout:
                        firebaseAuth.signOut();
                        Intent intent = new Intent(MainActivity.this, login.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        break;
                    }
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
            }
        });

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_userName);
        navUsername.setText(firebaseAuth.getCurrentUser().getDisplayName());
        TextView navEmailId = (TextView) headerView.findViewById(R.id.nav_userEmailId);
        navEmailId.setText(firebaseAuth.getCurrentUser().getEmail());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void moveToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.cl, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

    }
}