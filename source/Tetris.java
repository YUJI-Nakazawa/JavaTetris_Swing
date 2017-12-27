/***********************************************************
Tetris
	このクラスは、テトリス全体を取りまとめる外殻的クラスです。
	また、テトリスの開始クラスでもあります。
***********************************************************/
import java.io.*;

public class Tetris implements Runnable /* extends Thread */ {

	//関連するクラスの宣言
	public TetrisIni		ini;
	public TetrisField		fld;
	public TetrisBlock		blk;
	public TetrisKey		key;
	public TetrisGUI		gui;
	public TetrisProc		prc;
	
	
	/********************************************
	コンストラクタ
	********************************************/
	public Tetris() {
		
		ini		= new TetrisIni();			//iniファイル
		ini.iniRead("tetris.ini");			//iniファイル読込
		
		fld		= new TetrisField();		//フィールド
		blk		= new TetrisBlock();		//ブロック
		key 	= new TetrisKey();			//キー入力
		gui		= new TetrisGUI(this);		//ＧＵＩ
		prc		= new TetrisProc(this);		//プロシージャ(処理)
		//gui		= new TetrisGUI(fld, blk, key);			//ＧＵＩ
		//prc		= new TetrisProc(fld, blk, key, gui);	//プロシージャ(処理)

		//ウィンドウ表示
		gui.windowShow();

		//sleep0.5秒
		try {
			Thread.sleep(500);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		prc.init();
		//prc.start();
		
		/*
		//ループ(AWT, Swing仕様)
	    while (true){
			//実行
			prc.tetrisExec();
	    }
		*/
		
		/*
		//ループ(SWT仕様)
	    while (!gui.shell.isDisposed()){
			if(!gui.display.readAndDispatch()){
				//実行
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
		//ループ(AWT, Swing仕様)
		try{
		    while (true){
				prc.tetrisExec();	//実行
				Thread.sleep(10);	//スリープ
		    }
		}catch(Exception ex){
			//エラー処理
			ex.printStackTrace();
		}
	}//end of run()
	
}//end of class Tetris
