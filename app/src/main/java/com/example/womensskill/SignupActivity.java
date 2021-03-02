package com.example.womensskill;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.library.NavigationBar;
import com.library.NvTab;

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity implements NavigationBar.OnTabSelected, NavigationBar.OnTabClick {
    private static final String TAG = "SignupActivity";
    CardView btnFirst,btnSecond,btnFinish;
    LinearLayout layoutFirst,layoutSecond,layoutFinal;
   // TextInputLayout userName, userDob;
    String gender = "Male";
    ProgressDialog progressDialog;
    private NavigationBar bar;
    private int position = 0;
    EditText mDisplayDate,userName,password,rePassword,fullName,userEmail,userPhoneno,userAddress;
    Spinner userProvince,userDistrict;
    Button btnMale,btnFemale,btnOther;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference reference = database.getReference("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        bar = (NavigationBar) findViewById(R.id.navBar);
        btnFirst = (CardView) findViewById(R.id.btn_firstStep);
        btnSecond = (CardView) findViewById(R.id.btn_secondStep);
        btnFinish = (CardView) findViewById(R.id.btn_finalStep);
        layoutFirst =(LinearLayout) findViewById(R.id.firststep);
        layoutSecond =(LinearLayout) findViewById(R.id.secondstep);
        layoutFinal =(LinearLayout) findViewById(R.id.finalstep);
        bar.setOnTabSelected(this);
        bar.setOnTabClick(this);
        setup(true, 3);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering..... ");
        //StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
        // stateProgressBar.setStateDescriptionData(descriptionData);    }
        password = (EditText) findViewById(R.id.txtPassword);
        userName = (EditText) findViewById(R.id.txtName);
        rePassword =(EditText) findViewById(R.id.txtRePassword);
        mDisplayDate = (EditText) findViewById(R.id.datepicker);
        fullName =(EditText) findViewById(R.id.txtFullName);
        userEmail = (EditText) findViewById(R.id.txtEmail);
        userPhoneno = (EditText) findViewById(R.id.txtPhoneNumber);
        userAddress = (EditText) findViewById(R.id.txtAddress);
        //userProvince = (EditText) findViewById(R.id.);
        userDistrict = (Spinner) findViewById(R.id.spinner_distric);
        userProvince = (Spinner) findViewById(R.id.spinner_province);

        //Button
        btnMale =(Button)findViewById(R.id.btnMale);
        btnFemale =(Button)findViewById(R.id.btnFeMale);
        btnOther =(Button)findViewById(R.id.btnOther);
        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "Male";
            }
        });
        btnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "Female";
            }
        });
        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "Other";
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = userName.getText().toString();
                String Fullname = fullName.getText().toString();
                String Email = userEmail.getText().toString();
                String Phone = userPhoneno.getText().toString();
                String Address = userAddress.getText().toString();
                String Password = password.getText().toString();
                String Province =userProvince.getSelectedItem().toString();
                String Distric = userDistrict.getSelectedItem().toString();
                String Password2 = rePassword.getText().toString();
                String Date = mDisplayDate.getText().toString();
                if (Username.isEmpty())
                    userName.setError("Field can not be Empty");
                else if (Fullname.isEmpty())
                    fullName.setError("Field can not be Empty");
                else if (!Password.equals(Password2))
                    rePassword.setError("Password must be same!");
                else if (Email.isEmpty())
                    userEmail.setError("Field can not be Empty");
                else if (Phone.isEmpty())
                    userPhoneno.setError("Field can not be Empty");
                else if (Address.isEmpty())
                    userAddress.setError("Field can not be Empty");
                else if (Date.isEmpty())
                    mDisplayDate.setError("Field can not be Empty");
                else if (Province.isEmpty())
                    mDisplayDate.setError("Field can not be Empty");
                else if (Distric.isEmpty())
                    mDisplayDate.setError("Field can not be Empty");
                else
                {
                    progressDialog.show();
                    RegisterUser(Username, Fullname, Email, Phone,Address, Password, Date , gender,Distric,Province );
                }

            }

        });


        //Set Date

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SignupActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };




    }

    private void RegisterUser(String username, String fullname, String email, String phone,String Province,String District, String address, String password, String date, String gender) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            UserAttr userAttr = new UserAttr();
                            userAttr.setEmail(email);
                            userAttr.setFullname(fullname);
                            userAttr.setAddress(address);
                            userAttr.setUsername(username);
                            userAttr.setId(uid);
                            userAttr.setPhone(phone);
                            userAttr.setGender(gender);
                            userAttr.setDistric(District);
                            userAttr.setProvince(Province);
                            userAttr.setDate(date);
                            reference.child(uid).setValue(userAttr);
                            Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
                            // getApplicationContext().finish();
                            //save session
                            //saving value true for session
                            //Save.save(getApplicationContext(),"session","true");
                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.btn_firstStep:
                layoutFirst.setVisibility(View.GONE);
                layoutSecond.setVisibility(View.VISIBLE);
                layoutFinal.setVisibility(View.GONE);
                bar.setCurrentPosition(++position);
                break;
            case R.id.btn_secondStep:
                layoutFinal.setVisibility(View.VISIBLE);
                layoutSecond.setVisibility(View.GONE);
                layoutFirst.setVisibility(View.GONE);
                bar.setCurrentPosition(++position);
                break;
            case R.id.btn_finalStep:
                layoutFirst.setVisibility(View.VISIBLE);
                layoutSecond.setVisibility(View.GONE);
                layoutFinal.setVisibility(View.GONE);
                setup(true, 3);
                startActivity(new Intent(SignupActivity.this,MainActivity.class));
                finish();
                break;

        }
    }
    private void setup(boolean reset, int count) {
        if (reset)
            bar.resetItems();
        bar.setTabCount(count);
        bar.animateView(3000);
        bar.setCurrentPosition(position <= 0 ? 0 : position);
    }

    @Override
    public void onTabClick(int touchPosition, NvTab prev, NvTab nvTab) {

    }

    @Override
    public void onTabSelected(int touchPosition, NvTab prev, NvTab nvTab) {

    }

//    public void ShowHidePass(View view){
//
//        if(view.getId()==R.id.show_pass_btn){
//
//            if(edit_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
//                ((new ImageView(view)).setImageResource(R.drawable.eye_24px));
//
//                //Show Password
//                edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            }
//            else{
//                ((ImageView)(view)).setImageResource(R.drawable.hide_24px);
//
//                //Hide Password
//                edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//
//            }
//        }
//    }
}