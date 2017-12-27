/***********************************************************
TetrisKey
	このクラスは、キー入力を検知するためのクラスです。
	TetrisGUIに結びつけられます。
	検知したキー入力の情報は、TetrisProc が参照します。
***********************************************************/

import java.awt.event.*;

public class TetrisKey implements KeyListener {

	public boolean	UP;
	public boolean	DOWN;
	public boolean	LEFT;
	public boolean	RIGHT;
	public boolean	TURN_L;
	public boolean	TURN_R;
	public boolean	START;
		
	public TetrisTimer	timerDown;
	public TetrisTimer	timerLeft;
	public TetrisTimer	timerRight;
	//public Thread	timerDown;

	TetrisKey(){
		timerDown	= new TetrisTimer();
		timerLeft	= new TetrisTimer();
		timerRight	= new TetrisTimer();
		timerDown.start();
		timerLeft.start();
		timerRight.start();
	}
	
	
	//keyTyped
	public void keyTyped(KeyEvent e){
	}
	
	int n = 0;
	
	//キーボードが押されたときの処理
	public void keyPressed(KeyEvent e){
		int key_code;
		//int n = 0;
		key_code = e.getKeyCode();
		
		if(key_code == TetrisIni.KeyDownValue){
			if(!DOWN){
				DOWN = true;
				timerDown.timerStart();
				//System.out.println("KEY_DOWN:" + n++);
			}
			return;
		}
		if(key_code == TetrisIni.KeyUpValue){
			UP = true;
			return;
		}
		if(key_code == TetrisIni.KeyLeftValue){
			if(!LEFT){
				LEFT = true;
				timerLeft.timerStart();
			}
			return;
		}
		if(key_code == TetrisIni.KeyRightValue){
			if(!RIGHT){
				RIGHT = true;
				timerRight.timerStart();
			}
			return;
		}
		if(key_code == TetrisIni.KeyLeftTurnValue){
			TURN_L = true;
			return;
		}
		if(key_code == TetrisIni.KeyRightTurnValue){
			TURN_R = true;
			return;
		}
		if(key_code == TetrisIni.KeyStartValue){
			START = true;
			return;
		}
		
		/*
		switch(e.getKeyCode()){
			case KeyEvent.VK_Z ://TetrisIni.KeyLeftValue:
				TURN_L = true;
				break;
			case KeyEvent.VK_X :
				TURN_R = true;
				break;
			case KeyEvent.VK_UP :
				UP = true;
				break;
			case KeyEvent.VK_DOWN :
				DOWN = true;
				//timerDown = new TetrisTimer();
				timerDown.timerStart();
				//timerDown.start();
				break;
			case KeyEvent.VK_LEFT :
				LEFT = true;
				break;
			case KeyEvent.VK_RIGHT :
				RIGHT = true;
				break;
			case KeyEvent.VK_ENTER :	//Enter
			case KeyEvent.VK_SPACE :	//Space
				START = true;
				break;

		}
		*/
		
		/*
		switch(e.getKeyChar()){
			case 'z' :
			case 'Z' :
				TURN_L = true;
				break;
			case 'x' :
			case 'X' :
				TURN_R = true;
				break;
		}
		*/
		//System.out.println((int)e.character+" : "+e.keyCode);
	}//end keyPressed


	//キーボードが離されたときの処理
	public void keyReleased(KeyEvent e){
		int key_code;
		key_code = e.getKeyCode();
		
		if(key_code == TetrisIni.KeyDownValue){
			DOWN = false;
			timerDown.timerReset();
			timerDown.timerStop();
			return;
		}
		if(key_code == TetrisIni.KeyUpValue){
			UP = false;
			return;
		}
		if(key_code == TetrisIni.KeyLeftValue){
			LEFT = false;
			timerLeft.timerReset();
			timerLeft.timerStop();
			return;
		}
		if(key_code == TetrisIni.KeyRightValue){
			RIGHT = false;
			timerRight.timerReset();
			timerRight.timerStop();
			return;
		}
		if(key_code == TetrisIni.KeyLeftTurnValue){
			TURN_L = false;
			return;
		}
		if(key_code == TetrisIni.KeyRightTurnValue){
			TURN_R = false;
			return;
		}
		if(key_code == TetrisIni.KeyStartValue){
			START = false;
			return;
		}

	
		/*
		switch(e.getKeyCode()){
			case KeyEvent.VK_Z :
				TURN_L = false;
				break;
			case KeyEvent.VK_X :
				TURN_R = false;
				break;
			case KeyEvent.VK_UP :
				UP = false;
				break;
			case KeyEvent.VK_DOWN :
				DOWN = false;
				DownCheck = false;
				//timerDown = new TetrisTimer();
				timerDown.timerStop();
				//timerDown.start();
				break;
			case KeyEvent.VK_LEFT :
				LEFT = false;
				break;
			case KeyEvent.VK_RIGHT :
				RIGHT = false;
				break;
			case KeyEvent.VK_ENTER :	//Enter
			case KeyEvent.VK_SPACE :	//Space
				START = false;
				break;

		}
		*/

		/*
		switch(e.getKeyChar()){
			case 'z' :
			case 'Z' :
				TURN_L = false;
				break;
			case 'x' :
			case 'X' :
				TURN_R = false;
				break;
		}
		*/
	}//end keyReleased()

}