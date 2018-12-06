package utec.dbp.mychat_mobile;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by usuario on 6/12/2018.
 */

public class CreateSalaActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sala);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public Activity getActivity(){
        return this;
    }

    public void onClickBtnCreateSala(View v) {
        EditText txtSalaName = (EditText) findViewById(R.id.txtNameSala);

        String url = "http://ec2-3-16-158-225.us-east-2.compute.amazonaws.com:8080/sala_mobile";
        RequestQueue queue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap();
        params.put("name", txtSalaName.getText().toString());


        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO
                        try {
                            boolean ok = response.getBoolean("response");
                            if(ok) {
                                Intent intent = new Intent(getActivity(), MessageActivity.class);
                                intent.putExtra("pin", response.getString("pin"));
                                intent.putExtra("nameSala", response.getString("name"));
                                startActivity(intent);
                            } else {
                                showMessage("Something went wrong");
                            }

                            showMessage(response.toString());
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                showMessage(error.getMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }


}
