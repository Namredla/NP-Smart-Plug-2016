package firebase.smartplugtestapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TextView powerDisplay;
    double averageTotalPower = 0;
    int childCount = 0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Test to set testbox value at app startup
        powerDisplay = (TextView)findViewById(R.id.powerDataDisplay);
        powerDisplay.setText("Initialized!");
    }
    /*powerRef.addChildEventListener(new ChildEventListener(){
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            childCount++;
            double childValue = dataSnapshot.getValue(double.class);
            averageTotalPower = averageTotalPower + ((childValue - averageTotalPower) / childCount);
            String stringDouble = Double.toString(averageTotalPower);
            powerDisplay.setText(stringDouble);
        }
    });*/
        // Child event listener which gets the value placed in the new child created, and adds it to the average
        ChildEventListener powerDataListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                childCount++;
                double childValue = dataSnapshot.getValue(double.class);
                averageTotalPower = averageTotalPower + ((childValue - averageTotalPower) / childCount);
                String stringDouble = Double.toString(averageTotalPower);
                powerDisplay.setText(stringDouble);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    //Should attach listener described above to the Plug Data reference, but does not work
    DatabaseReference powerRef = database.getReference("PlugData");
    powerRef.addChildEventListener(powerDataListener);

    public void toggleCommand(View view) {
        DatabaseReference toggleRef = database.getReference("ToggleInput");
        toggleRef.setValue(1);
    }
}
