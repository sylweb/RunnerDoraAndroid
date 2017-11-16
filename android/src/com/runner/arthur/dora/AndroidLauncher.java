package com.runner.arthur.dora;

import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.runner.arthur.dora.RunnerDora;

public class AndroidLauncher extends AndroidApplication implements TalkToTheLauncher {
	private RunnerDora game;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		ApplicationListener myRunningGame = new RunnerDora(this);
		this.game = game;
		initialize(myRunningGame, config);
	}

	public void onBackPressed() {
		System.exit(0);
	}

	public void dataTransfert(String dataCode) {
	}
}
