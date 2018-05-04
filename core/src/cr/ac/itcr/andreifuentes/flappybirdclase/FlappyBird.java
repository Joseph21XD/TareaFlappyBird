package cr.ac.itcr.andreifuentes.flappybirdclase;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Sound sound[];
	Texture background;
	Texture topTube;
	Texture bottomTube;
	Texture[] birds;
	Sprite[] birdos;
	Texture gameOver;
	Sprite sprite;
	int birdState;
	float gap;
	float birdY;
	float velocity;
	float gravity;
	int numberOfPipes = 4;
	float pipeX[] = new float[numberOfPipes];
	float pipeYOffset[] = new float[numberOfPipes];
	float distance;
	float pipeVelocity = 5;
	Random random;
	float maxLine;
	float minLine;
	int score;
	int pipeActivo;
	BitmapFont font;
	int game_state;
	int birdSize;
	int change;
	int up;
	Circle birdCircle;
	Rectangle[] topPipes;
	Rectangle[] bottomPipes;

	public FlappyBird(int up1, int dist, float grav1){
			change= dist;
			gravity= grav1;
			up= up1;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		sound = new Sound[4];
		sound[0] = Gdx.audio.newSound( Gdx.files.getFileHandle("wing.mp3", Files.FileType.Internal) );
		sound[1] = Gdx.audio.newSound( Gdx.files.getFileHandle("point.mp3", Files.FileType.Internal) );
		sound[2] = Gdx.audio.newSound( Gdx.files.getFileHandle("hit.mp3", Files.FileType.Internal) );
		sound[3] = Gdx.audio.newSound( Gdx.files.getFileHandle("die.mp3", Files.FileType.Internal) );
		background = new Texture("bg.png");
		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		gameOver = new Texture("gameOverOriginal.png");
		birdos = new Sprite[2];
		birdos[0] = new Sprite(birds[0]);
		birdos[0].setSize(birds[0].getWidth(),birds[0].getHeight());
		birdos[1] = new Sprite(birds[1]);
		birdos[1].setSize(birds[1].getWidth(),birds[1].getHeight());
		birdCircle = new Circle();
		topPipes = new Rectangle[numberOfPipes];
		bottomPipes = new Rectangle[numberOfPipes];
		birdState = 0;
		game_state = 0;
		velocity = 0;
		random = new Random();
		distance = Gdx.graphics.getWidth() * 4/5;
		maxLine = Gdx.graphics.getHeight()* 3/4;
		minLine = Gdx.graphics.getHeight()* 1/4;
		score = 0;
		pipeActivo = 0;
		birdSize = 0;
		gap= change+ random.nextInt(150);
		change= 5;
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);

		startGame();
	}

	public void startGame(){
		birdY = Gdx.graphics.getHeight()/2 - birds[birdState].getHeight()/2;
		for (int i = 0; i<numberOfPipes; i++){
			pipeYOffset[i] = (random.nextFloat()*(maxLine-minLine)+minLine);
			pipeX[i] = Gdx.graphics.getWidth()/2 - topTube.getWidth() + Gdx.graphics.getWidth() + distance*i;

			// inicializamos cada uno de los Shapes
			topPipes[i] = new Rectangle();
			bottomPipes[i] = new Rectangle();
		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


		// no iniciado
		if (game_state == 0){
			if (Gdx.input.justTouched()){
				game_state = 1;
			}
		}
		// jugando
		else if (game_state == 1){
			if (pipeX[pipeActivo] < Gdx.graphics.getWidth()/2 - topTube.getWidth()){
				score++;
				sound[1].play();

				if (pipeActivo < numberOfPipes - 1){
					pipeActivo++;
				}
				else {
					pipeActivo = 0;
				}

				Gdx.app.log("score", Integer.toString(score));
			}


			birdCircle.set(Gdx.graphics.getWidth()/2, birdY + birds[birdState].getHeight()/2, birds[birdState].getWidth()/2);

//			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//			shapeRenderer.setColor(Color.MAGENTA);
//			shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
//

			// Posicionamiento de los pipes
			for (int i = 0; i<numberOfPipes; i++) {

				if (pipeX[i] < -topTube.getWidth()){
					pipeYOffset[i] = (random.nextFloat()*(maxLine-minLine)+minLine);
					pipeX[i] += distance*(numberOfPipes);
				}
				else {
					pipeX[i] = pipeX[i] - pipeVelocity;
				}

				batch.draw(topTube,
						pipeX[i],
						pipeYOffset[i]+gap/2,
						topTube.getWidth(),
						topTube.getHeight());
				batch.draw(bottomTube,
						pipeX[i],
						pipeYOffset[i]-bottomTube.getHeight()-gap/2,
						bottomTube.getWidth(),
						bottomTube.getHeight());

				topPipes[i] = new Rectangle(pipeX[i],
						pipeYOffset[i]+gap/2,
						topTube.getWidth(),
						topTube.getHeight());
				bottomPipes[i] = new Rectangle(pipeX[i],
						pipeYOffset[i]-bottomTube.getHeight()-gap/2,
						bottomTube.getWidth(),
						bottomTube.getHeight());

//				shapeRenderer.rect(topPipes[i].x, topPipes[i].y, topTube.getWidth(),
//						topTube.getHeight());
//				shapeRenderer.rect(bottomPipes[i].x, bottomPipes[i].y, bottomTube.getWidth(),
//						bottomTube.getHeight());

				if (Intersector.overlaps(birdCircle, topPipes[i])){
					Gdx.app.log("Intersector", "top pipe overlap");
					game_state = 2;
					sound[2].play();
				}
				else if (Intersector.overlaps(birdCircle, bottomPipes[i])){
					Gdx.app.log("Intersector", "bottom pipe overlap");
					game_state = 2;
					sound[2].play();
				}
			}

			if (Gdx.input.justTouched()){
				velocity = velocity - up;
				sound[0].play();
			}


			if(change==0){
			birdState = birdState == 0 ? 1 : 0;
			change=5;}
			else{
				change--;
			}



			velocity = velocity + gravity;


			if (birdY < 0){
				game_state = 2;
				sound[3].play();
			}
			else {

				birdY = birdY - velocity;
			}

//			shapeRenderer.end();


		}
		// game over
		else if (game_state == 2){
			batch.draw(gameOver, Gdx.graphics.getWidth()/2 - gameOver.getWidth()/2, Gdx.graphics.getHeight()/2 - gameOver.getHeight()/2);
			if (Gdx.input.justTouched()){
				game_state = 1;
				score = 0;
				pipeActivo = 0;
				velocity = 0;
				startGame();
			}
		}
		//batch.draw(birds[birdState], Gdx.graphics.getWidth() / 2 - birds[birdState].getWidth()/2,  birdY,  birds[birdState].getWidth() + birdSize, birds[birdState].getHeight() + birdSize);
		font.draw(batch, Integer.toString(score), Gdx.graphics.getWidth()*1/8, Gdx.graphics.getHeight()*9/10);
		birdos[birdState].setPosition(Gdx.graphics.getWidth() / 2 - birds[birdState].getWidth()/2,  birdY);
		if(velocity<0){
			float k=(velocity*-1)+10;
			if(k>90){k=90;}
			birdos[birdState].setRotation(k);
		}
		else if(velocity==0){
			birdos[birdState].setRotation(0);
		}
		else{
			float k= velocity;
			if(velocity>10){
				k+=20;
			}
			birdos[birdState].setRotation(360-k);
		}
		birdos[birdState].draw(batch);
		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
