package lt.kentai.bachelorgame.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LoginFailureDialog extends Dialog {

	public LoginFailureDialog(String title, String failureMsg, Skin skin) {
		super(title, skin);
		
		text(failureMsg);
		button("Close");
	}

}
