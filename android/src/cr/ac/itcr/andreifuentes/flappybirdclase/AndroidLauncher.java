package cr.ac.itcr.andreifuentes.flappybirdclase;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import cr.ac.itcr.andreifuentes.flappybirdclase.FlappyBird;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		Intent intent= getIntent();
		initialize(new FlappyBird(intent.getIntExtra("up",10),intent.getIntExtra("gap",500),intent.getFloatExtra("gravity",0.3f)), config);
	}
}
