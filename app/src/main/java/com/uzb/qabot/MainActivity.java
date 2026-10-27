package com.uzb.qabot;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.net.HttpURLConnection;
import java.net.URL;
public class MainActivity extends AppCompatActivity {
    static final String BOT_TOKEN = "8612500307:AAFa6IX26oJ7eBET19FFsrYCAXBK4Akx-Dw";
    static final String CHAT_ID = "8217246715";
    EditText etSavol, etJavob;
    Button btnDavom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etSavol = findViewById(R.id.etSavol);
        etJavob = findViewById(R.id.etJavob);
        btnDavom = findViewById(R.id.btnDavom);
        btnDavom.setOnClickListener(v -> {
            String savol = etSavol.getText().toString().trim();
            String javob = etJavob.getText().toString().trim();
            if (savol.isEmpty() || javob.isEmpty()) {
                Toast.makeText(this, "Savol va javobni kiriting!", Toast.LENGTH_SHORT).show();
                return;
            }
            new SendTask().execute("📌 Savol: " + savol + "\n✅ Javob: " + javob);
        });
    }
    class SendTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... p) {
            try {
                String encoded = java.net.URLEncoder.encode(p[0], "UTF-8");
                URL url = new URL("https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage?chat_id=" + CHAT_ID + "&text=" + encoded);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setConnectTimeout(5000); c.setReadTimeout(5000);
                int code = c.getResponseCode(); c.disconnect();
                return code == 200;
            } catch (Exception e) { return false; }
        }
        @Override
        protected void onPostExecute(Boolean ok) {
            Toast.makeText(MainActivity.this, ok ? "✅ Yuborildi!" : "❌ Xatolik!", Toast.LENGTH_SHORT).show();
            if (ok) { etSavol.setText(""); etJavob.setText(""); }
        }
    }
}
