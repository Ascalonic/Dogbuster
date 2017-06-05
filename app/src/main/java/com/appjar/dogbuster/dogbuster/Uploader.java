package com.appjar.dogbuster.dogbuster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Uploader extends AppCompatActivity {

    TextView textLat,textLong; EditText txtrange;
    RadioButton radiolow,radiomed,radiohigh;
    Button cmdSubmit,cmdCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploader);

        textLat=(TextView)findViewById(R.id.txtSelLat);
        textLong=(TextView)findViewById(R.id.txtSelLong);

        textLat.setText("Selected Latitide:" + String.valueOf(DogbusterCentral.selectedLat));
        textLong.setText("Selected Longitude:" +String.valueOf(DogbusterCentral.selectedLong));

        txtrange=(EditText)findViewById(R.id.editRange);
        txtrange.setText("500");

        radiolow=(RadioButton)findViewById(R.id.radioLow);
        radiomed=(RadioButton)findViewById(R.id.radioMedium);
        radiohigh=(RadioButton)findViewById(R.id.radioHigh);

        cmdSubmit=(Button)findViewById(R.id.btnSubmit);
        cmdCancel=(Button)findViewById(R.id.btnCancel);

        cmdSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)  {

                cmdSubmit.setEnabled(false);

                if (txtrange.getText().toString().isEmpty())
                {
                    Toast.makeText(getBaseContext(), "Please fill the range field",
                            Toast.LENGTH_LONG).show();
                    cmdSubmit.setEnabled(true);
                }
                else if (Integer.parseInt(txtrange.getText().toString()) > 1500)
                {
                    Toast.makeText(getBaseContext(), "Please enter a valid range!",
                            Toast.LENGTH_LONG).show();
                    cmdSubmit.setEnabled(true);
                }
                else {
                    String dlevel = "";

                    if (radiolow.isChecked())
                        dlevel = "LOW";
                    else if (radiomed.isChecked())
                        dlevel = "MEDIUM";
                    else
                        dlevel = "HIGH";

                    String range = txtrange.getText().toString();

                    String res = "";
                    ServerInterface SI = new ServerInterface(getBaseContext(), new AsyncResponse() {
                        @Override
                        public void processFinish(String output) {

                            Toast.makeText(getBaseContext(), output,
                                    Toast.LENGTH_LONG).show();

                            if(output.contains("created successfully"))
                                finish();

                        }
                    }, 4);

                    SI.execute(String.valueOf(DogbusterCentral.selectedLat),String.valueOf(DogbusterCentral.selectedLong),range,dlevel);
                }
            }
            });



        cmdCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


            }
        });
    }
}
