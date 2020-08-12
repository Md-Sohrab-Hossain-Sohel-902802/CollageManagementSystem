package com.example.collagemanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collagemanagementsystem.Classes.SideWork;
import com.example.collagemanagementsystem.ui.gallery.ProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;



    private  FirebaseAuth mAuth;
    private  FirebaseUser currentUser;
    private  SideWork sideWork;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
         sideWork=new SideWork();

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Home");


        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {




            }
        });



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);





        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
/*
        if (navigationView != null) {

                    navigationView.setNavigationItemSelectedListener(this);

       }
*/




        View headerView=navigationView.getHeaderView(0);
        TextView userNameTExtview=headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView=headerView.findViewById(R.id.user_profile_Image);



       String image= sideWork.getImage(this);
       String name= sideWork.getName(this);

       if(image.equals("null")){
           setProfileData(userNameTExtview,profileImageView);
       }else{
           Picasso.get().load(image).placeholder(R.drawable.profile).into(profileImageView);

       }

       if(name.equals("null")){
           setProfileData(userNameTExtview,profileImageView);
       }else{
           userNameTExtview.setText(name);

       }








    }

    private void setProfileData(final TextView userNameTExtview, final CircleImageView profileImageView) {

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("collage").child("Users");


        databaseReference.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.hasChild("image")){
                        String image1=snapshot.child("image").getValue().toString();
                        Picasso.get().load(image1).placeholder(R.drawable.profile).into(profileImageView);


                        sideWork.saveImage(MainActivity.this,image1);

                    }else{

                        Picasso.get().load(R.drawable.profile).into(profileImageView);


                    }

                    String fname=snapshot.child("fName").getValue().toString();
                    String lName=snapshot.child("lName").getValue().toString();
                    sideWork.saveName(MainActivity.this,fname+"("+lName+")");

                    userNameTExtview.setText(fname+"("+lName+")");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


        currentUser=mAuth.getCurrentUser();
        if(currentUser==null){
            startActivity(new Intent(MainActivity.this,StartActivity.class));
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.action_Logout){
            mAuth.signOut();
            SideWork sideWork=new SideWork();
            sideWork.saveName(MainActivity.this,"null");
            sideWork.saveImage(MainActivity.this,"null");








            startActivity(new Intent(MainActivity.this,StartActivity.class));
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {



/*
        if(menuItem.getItemId()==R.id.nav_profile){
            startActivity(new Intent(MainActivity.this,UserProfileActivity.class));
        }

*/



        return false;
    }

}