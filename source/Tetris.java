/***********************************************************
Tetris
	���̃N���X�́A�e�g���X�S�̂����܂Ƃ߂�O�k�I�N���X�ł��B
	�܂��A�e�g���X�̊J�n�N���X�ł�����܂��B
***********************************************************/
import java.io.*;

public class Tetris implements Runnable /* extends Thread */ {

	//�֘A����N���X�̐錾
	public TetrisIni		ini;
	public TetrisField		fld;
	public TetrisBlock		blk;
	public TetrisKey		key;
	public TetrisGUI		gui;
	public TetrisProc		prc;
	
	
	/********************************************
	�R���X�g���N�^
	********************************************/
	public Tetris() {
		
		ini		= new TetrisIni();			//ini�t�@�C��
		ini.iniRead("tetris.ini");			//ini�t�@�C���Ǎ�
		
		fld		= new TetrisField();		//�t�B�[���h
		blk		= new TetrisBlock();		//�u���b�N
		key 	= new TetrisKey();			//�L�[����
		gui		= new TetrisGUI(this);		//�f�t�h
		prc		= new TetrisProc(this);		//�v���V�[�W��(����)
		//gui		= new TetrisGUI(fld, blk, key);			//�f�t�h
		//prc		= new TetrisProc(fld, blk, key, gui);	//�v���V�[�W��(����)

		//�E�B���h�E�\��
		gui.windowShow();

		//sleep0.5�b
		try {
			Thread.sleep(500);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		prc.init();
		//prc.start();
		
		/*
		//���[�v(AWT, Swing�d�l)
	    while (true){
			//���s
			prc.tetrisExec();
	    }
		*/
		
		/*
		//���[�v(SWT�d�l)
	    while (!gui.shell.isDisposed()){
			if(!gui.display.readAndDispatch()){
				//���s
				prc.tetrisExec();
			}
	    }
		*/

	}//end of Tetris()
	
	
	/********************************************
	main()
	********************************************/
	public static void main(String[] args){
		//Tetris tetris = new Tetris();
		Thread tetris = new Thread(new Tetris());
		tetris.start();
	}

	
	/********************************************
	run()
	********************************************/
	public void run(){
		//���[�v(AWT, Swing�d�l)
		try{
		    while (true){
				prc.tetrisExec();	//���s
				Thread.sleep(10);	//�X���[�v
		    }
		}catch(Exception ex){
			//�G���[����
			ex.printStackTrace();
		}
	}//end of run()
	
}//end of class Tetris
