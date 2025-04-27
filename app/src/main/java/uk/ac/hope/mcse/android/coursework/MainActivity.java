package uk.ac.hope.mcse.android.coursework;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import uk.ac.hope.mcse.android.coursework.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        // Hide the FAB if the current fragment is not FragmentOne or Fragment to since it
        // has no function
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.FirstFragment || destination.getId() == R.id.SecondFragment) {
                binding.fab.setVisibility(View.VISIBLE);
            } else {
                binding.fab.setVisibility(View.GONE);
            }
        });

        binding.fab.setOnClickListener(v -> {
            NavDestination currentDestination = navController.getCurrentDestination();

                // Is the FAB clicked on the first fragment? Then:
            if (currentDestination.getId() == R.id.FirstFragment) {
                navController.navigate(R.id.action_FirstFragment_to_CreateResidentFragment);
            }

            // Is the FAB clicked on the second fragment? Then:
            else if (currentDestination.getId() == R.id.SecondFragment) {
                navController.navigate(R.id.action_SecondFragment_to_CreateResidentFragment);
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}