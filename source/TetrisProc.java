/***********************************************************
TetrisProc
	このクラスは、テトリスの心臓部です。テトリス自体の中核的
	な処理を担当します。
	TetrisField, TetrisBlock, TetrisKey, TetrisGUIのクラスと
	連携しています。
***********************************************************/

public class TetrisProc /* extends Thread */ {

	private TetrisField		fld;
	private TetrisBlock		blk;
	private TetrisBlock		blk_dummy;
	private TetrisKey		key;
	private TetrisGUI		gui;
	private TetrisTimer		timer;
	//private Thread			timer;
	
	private final int		FldRows	= TetrisIni.FieldRows;
	private final int		FldCols	= TetrisIni.FieldCols;

	
	//ゲームモード
	public static int	gameMode;
	public static final int GAME_PLAY			= 1;
	public static final int GAME_PLAY_dropBlock	= 2;
	public static final int GAME_PLAY_addBlock	= 3;
	public static final int GAME_PLAY_deleteLine= 4;
	public static final int GAME_PLAY_newBlock	= 5;
	public static final int GAME_PAUSE			= 6;
	public static final int GAME_OVER			= 7;
	public static final int STATE_UP			= 0;
	
	//衝突タイプ
	private static final int MOVE_DOWN	= 0;
	private static final int MOVE_RIGHT	= 1;
	private static final int MOVE_LEFT	= 2;
	private static final int TURN_RIGHT	= 3;
	private static final int TURN_LEFT	= 4;
	private static final int BLOCK_NEW	= 5;
	
	//ブロック落下時間
	private long waitTime = 1000;


	/********************************************
	コンストラクタ
	********************************************/
	//TetrisProc(TetrisField fld, TetrisBlock blk, TetrisKey key, TetrisGUI gui){
	TetrisProc(Tetris tetris){
		this.fld	= tetris.fld;
		this.blk	= tetris.blk;
		this.key	= tetris.key;
		this.gui	= tetris.gui;
		//this.blk_dummy = new TetrisBlock();

		
		//タイマースレッド開始
		timer		= new TetrisTimer();
		//timer		= new Thread(new TetrisTimer());
		//timer.setPriority(8);
		timer.setName("ProcTimer");
		timer.start();
		timer.timerStop();

		//init();

	}//end TetrisProc()

	
	/********************************************
	ゲーム初期化
	********************************************/
	public void init(){

		fld.newField();
		blk.newBlock();
		blk_dummy = new TetrisBlock(blk);
		//blk_dummy.block = new int[blk.block.length][blk.block.length];
		//blk_dummy.block2= new int[blk.block.length][blk.block.length];
		gui.fieldDraw();
		gui.blockDraw();
		gui.BGtoFG();
		
		MODE   = new int[4];
		modeSP = -1;
		//state  = 0;

		setMode(GAME_PLAY);
				
		//タイマー初期化
		//timer.timerStop();
		timer.timerStart();
		//timer.setPriority(8);

	}
	
	/********************************************
	テトリス実行
	********************************************/
	public void tetrisExec(){
		//ゲームモード設定
		gameMode = getMode();
		//キー入力処理
		keyBoard(gameMode);
		//ゲーム進行処理
		gameRun(gameMode);
		//描画処理
		//screenDraw(gameMode);
		//スリープ
		//Thread.sleep(10);
		//mySleep(10);
	}


	
	/********************************************
	キー入力処理
	********************************************/
	//int t1, t2, t3, t4, t5, t6;
	private boolean isFirstCheck_DOWN;
	private boolean isFirstCheck_LEFT;
	private boolean isFirstCheck_RIGHT;

	private void keyBoard(int mode){
		//long tmp_time;

		switch(mode){
		case GAME_PLAY :
			/* 上キー */
			if(key.UP && !isCollision(TURN_LEFT)){
				blockTurnLeft();
				mySleep(160);
			}
			/* 下キー */
			if(key.DOWN){
				//tmp_time = key.timerDown.getTime();
				//System.out.println(key.DownCheck);
				//if(key.DownCheck == true && tmp_time < 300){
				if(isFirstCheck_DOWN || key.timerDown.getTime() > 280){
					isFirstCheck_DOWN = false;
					if(!isCollision(MOVE_DOWN)){	//if 未着地
						blockMoveDown();			//落下処理
						timer.timerReset();			//タイマーリセット
						mySleep(10);
					}else{							//if 着地
						setMode(GAME_PLAY_addBlock);//モード変更
						timer.timerReset();			//タイマーリセット
						timer.timerStop();			//タイマーストップ
					}
				}
			}else{
				isFirstCheck_DOWN = true;
			}
			/* 右キー */
			if(key.RIGHT){
				if(isFirstCheck_RIGHT || key.timerRight.getTime() > 260){
					isFirstCheck_RIGHT = false;
					if(!isCollision(MOVE_RIGHT)){
						blockMoveRight();
						mySleep(80);
					}
				}
			}else{
				isFirstCheck_RIGHT = true;
			}
			/*
			if(key.RIGHT && !isCollision(MOVE_RIGHT)){
				blockMoveRight();
				mySleep(140);
			}
			*/
			/* 左キー */
			if(key.LEFT){
				if(isFirstCheck_LEFT || key.timerLeft.getTime() > 260){
					isFirstCheck_LEFT = false;
					if(!isCollision(MOVE_LEFT)){
						blockMoveLeft();
						mySleep(80);
					}
				}
			}else{
				isFirstCheck_LEFT = true;
			}
			/*
			if(key.LEFT && !isCollision(MOVE_LEFT)){
				blockMoveLeft();
				mySleep(140);
			}
			*/
			/* 右回転キー */
			if(key.TURN_R && !isCollision(TURN_RIGHT)){
				blockTurnRight();
				mySleep(160);
			}
			/* 左回転キー */
			if(key.TURN_L && !isCollision(TURN_LEFT)){
				blockTurnLeft();
				mySleep(160);
			}
			/* 一時停止/再開 */
			if(key.START){
				setMode(GAME_PAUSE);
				mySleep(200);
			}
			break;
		
		case GAME_PLAY_dropBlock :
		case GAME_PLAY_addBlock :
		case GAME_PLAY_deleteLine :
		case GAME_PLAY_newBlock :
			if(key.START){
				setMode(GAME_PAUSE);
				mySleep(200);
			}
			break;

		case GAME_PAUSE :
			if(key.START){
				setMode(GAME_PAUSE);
				mySleep(200);
			}
			break;
		}//end switch
		
	}//end keyBoard()
	
	
	
	/********************************************
	ゲーム進行処理
	********************************************/
	//private long nothingTime;

	private void gameRun(int mode){
		switch(mode){
		//ゲームプレイ
		case GAME_PLAY :
		 	if(timer.getTime() >= waitTime){
				//timer.setPriority(1);
				timer.timerStop();
				timer.timerReset();
				setMode(GAME_PLAY_dropBlock);
		 	}
			break;

		//ブロック一段落下
		case GAME_PLAY_dropBlock :
	 		if(!isCollision(MOVE_DOWN)){
				blockMoveDown();
				setMode(GAME_PLAY);
				timer.timerStart();
			}else{
				setMode(GAME_PLAY_addBlock);
	 		}
			break;
		
		//ブロックをフィールドに追加
		case GAME_PLAY_addBlock :
			addBlock();					//ブロックをフィールドに加える
			gui.blockErase();
			blk.clear();				//ブロックのクリア
			if(fld.checkLine() > 0){	//揃っているラインの有無をチェック
				setMode(GAME_PLAY_deleteLine);
			}else{
				setMode(GAME_PLAY_newBlock);
			}
			gui.fieldDraw();
			gui.BGtoFG();
			break;
				
		//ラインを消去
		case GAME_PLAY_deleteLine :
			mySleep(300);
			fld.deleteLine();//ラインを消す
			gui.fieldDraw();
			gui.BGtoFG();
			mySleep(600);
			fld.killLine();	//消えたラインを詰める
			gui.fieldDraw();
			gui.BGtoFG();
			setMode(GAME_PLAY_newBlock);
			break;

		//新しいブロック生成
		case GAME_PLAY_newBlock :
			mySleep(800);
			blk.newBlock();			//新規ブロック
			blk_dummy = new TetrisBlock(blk);
			gui.blockDraw();
			gui.BGtoFG();
			//blk_dummy.block = new int[blk.block.length][blk.block.length];
			//blk_dummy.block2= new int[blk.block.length][blk.block.length];
			if(isGameOver()){
				setMode(GAME_OVER);
			}else{
				setMode(GAME_PLAY);
				//timer = new TetrisTimer();
				timer.timerStart();
			}
			break;
		
		//一時中断
		case GAME_PAUSE :
		 	break;
		
		//ゲームオーバー
		case GAME_OVER :
			mySleep(1000);
			init();
			break;
		}//end switch

	}//end gameRun()
	

	
	/********************************************
	ブロックについての各処理
	********************************************/
	private void blockMoveDown(){
		gui.blockErase();
		blk.moveDown();
		gui.blockDraw();
		gui.BGtoFG();
	}
	private void blockMoveLeft(){
		gui.blockErase();
		blk.moveLeft();
		gui.blockDraw();
		gui.BGtoFG();
	}
	private void blockMoveRight(){
		gui.blockErase();
		blk.moveRight();
		gui.blockDraw();
		gui.BGtoFG();
	}
	private void blockTurnLeft(){
		gui.blockErase();
		blk.turnLeft();
		gui.blockDraw();
		gui.BGtoFG();
	}
	private void blockTurnRight(){
		gui.blockErase();
		blk.turnRight();
		gui.blockDraw();
		gui.BGtoFG();
	}
	
	
	
	/********************************************
	衝突チェック処理
	********************************************/
	private boolean isCollision(int type){
		//TetrisBlock copyBlk = new TetrisBlock(blk);
		TetrisBlock.valueCopy(blk, blk_dummy);
		
		switch(type){
		case MOVE_DOWN:
			blk_dummy.moveDown();
			break;
			
		case MOVE_RIGHT:
			blk_dummy.moveRight();
			break;
			
		case MOVE_LEFT:
			blk_dummy.moveLeft();
			break;
			
		case TURN_RIGHT:
			blk_dummy.turnRight();
			break;
			
		case TURN_LEFT:
			blk_dummy.turnLeft();
			break;
			
		case BLOCK_NEW:
			break;
		}
		
		//衝突判定
		for(int BlkRow=0; BlkRow < blk_dummy.block.length; BlkRow++){
			if(blk_dummy.y + BlkRow < 0        )continue;
			if(blk_dummy.y + BlkRow > FldRows-1)break;
			
			for(int BlkCol=0; BlkCol < blk_dummy.block.length; BlkCol++){
				if(blk_dummy.x + BlkCol < 0        )continue;
				if(blk_dummy.x + BlkCol > FldCols-1)break;

				//自分のブロックが存在する箇所で、かつ
				//フィールドブロックが存在するなら衝突
				if(blk_dummy.block[BlkRow][BlkCol] != 0
				&& fld.field[blk_dummy.y + BlkRow][blk_dummy.x + BlkCol] != 0){
					return true;
				}
			}
		}
		return false;
	}//end isCollision()

	
	
	/********************************************
	ブロックをフィールドに加える処理
	********************************************/
	public void addBlock(){
		//block → field コピー
		for(int row=0; row < blk.block.length; row++){
		for(int col=0; col < blk.block.length; col++){
			if(blk.block[row][col] != 0){
				fld.field[blk.y + row][blk.x + col] = blk.block[row][col];
			}
		}
		}
	}//end addBlock()

	
	/********************************************
	GAMEOVER判定処理
	********************************************/
	public boolean isGameOver(){
		if(isCollision(BLOCK_NEW)){
			return true;
		}else{
			return false;
		}
	}//end isGameOver()
	
	
	
	/********************************************
	スリープ
	********************************************/
	private void mySleep(long ms){
		try {
			Thread.sleep(ms);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	
	/********************************************
	ゲームモードに関する処理
	　　ゲームモードに変更があった時は MODE[]配列
	　　に次回のモードが格納される。
	********************************************/
	private int[]	MODE;		//モード格納配列
	private int		modeSP; 	//次回モードの格納位置を示すスタックポインタ
	private int		saveMode;	//モード一時保存用
	//private int		state = 0;
	//private int		saveState;
	//private boolean modeChangeFLG;
	
	//MODE格納
	private void setMode(int mode){
		//STATE_UPなら、state増加のみ
		/*
		if(mode == STATE_UP){
			state++;
			return;
		}
		*/
		//格納配列が満杯なら、何もしない
		if(modeSP >= MODE.length-1) return;

		//次回モードを格納
		MODE[++modeSP] = mode;
	}
	
	//MODE取出
	private int getMode(){
		int returnNextMode;
		
		//次回モードが無ければ、現在モード継続
		if(modeSP < 0) return gameMode;
		
		//モードを取出
		returnNextMode = MODE[0];
		System.arraycopy(MODE, 1, MODE, 0, MODE.length-1);
		modeSP--;

		//GAME_PAUSEの場合は以前のモードを記憶しておく
		if(returnNextMode == GAME_PAUSE){
			if(gameMode != GAME_PAUSE){
				saveMode = gameMode;
				//saveState = state;
			}else{
				//state = saveState;
				return saveMode;
			}
		}
		
		//state = 0;
		return returnNextMode;
	}
	
	
}//End class TetrisProc
