package cr.ac.itcr.andreifuentes.flappybirdclase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void easy(View view){
        Intent intent= new Intent(MainActivity.this, AndroidLauncher.class);
        intent.putExtra("gap", 600);
        intent.putExtra("gravity", 0.3);
        intent.putExtra("up", 10);
        startActivity(intent);
    }

    public void normal(View view){
        Intent intent= new Intent(MainActivity.this, AndroidLauncher.class);
        intent.putExtra("gap", 400);
        intent.putExtra("gravity", 0.5);
        intent.putExtra("up", 15);
        startActivity(intent);
    }

    public void hard(View view){
        Intent intent= new Intent(MainActivity.this, AndroidLauncher.class);
        intent.putExtra("gap", 300);
        intent.putExtra("gravity", 0.7);
        intent.putExtra("up", 20);
        startActivity(intent);
    }
}
