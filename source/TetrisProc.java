/***********************************************************
TetrisProc
	���̃N���X�́A�e�g���X�̐S�����ł��B�e�g���X���̂̒��j�I
	�ȏ�����S�����܂��B
	TetrisField, TetrisBlock, TetrisKey, TetrisGUI�̃N���X��
	�A�g���Ă��܂��B
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

	
	//�Q�[�����[�h
	public static int	gameMode;
	public static final int GAME_PLAY			= 1;
	public static final int GAME_PLAY_dropBlock	= 2;
	public static final int GAME_PLAY_addBlock	= 3;
	public static final int GAME_PLAY_deleteLine= 4;
	public static final int GAME_PLAY_newBlock	= 5;
	public static final int GAME_PAUSE			= 6;
	public static final int GAME_OVER			= 7;
	public static final int STATE_UP			= 0;
	
	//�Փ˃^�C�v
	private static final int MOVE_DOWN	= 0;
	private static final int MOVE_RIGHT	= 1;
	private static final int MOVE_LEFT	= 2;
	private static final int TURN_RIGHT	= 3;
	private static final int TURN_LEFT	= 4;
	private static final int BLOCK_NEW	= 5;
	
	//�u���b�N��������
	private long waitTime = 1000;


	/********************************************
	�R���X�g���N�^
	********************************************/
	//TetrisProc(TetrisField fld, TetrisBlock blk, TetrisKey key, TetrisGUI gui){
	TetrisProc(Tetris tetris){
		this.fld	= tetris.fld;
		this.blk	= tetris.blk;
		this.key	= tetris.key;
		this.gui	= tetris.gui;
		//this.blk_dummy = new TetrisBlock();

		
		//�^�C�}�[�X���b�h�J�n
		timer		= new TetrisTimer();
		//timer		= new Thread(new TetrisTimer());
		//timer.setPriority(8);
		timer.setName("ProcTimer");
		timer.start();
		timer.timerStop();

		//init();

	}//end TetrisProc()

	
	/********************************************
	�Q�[��������
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
				
		//�^�C�}�[������
		//timer.timerStop();
		timer.timerStart();
		//timer.setPriority(8);

	}
	
	/********************************************
	�e�g���X���s
	********************************************/
	public void tetrisExec(){
		//�Q�[�����[�h�ݒ�
		gameMode = getMode();
		//�L�[���͏���
		keyBoard(gameMode);
		//�Q�[���i�s����
		gameRun(gameMode);
		//�`�揈��
		//screenDraw(gameMode);
		//�X���[�v
		//Thread.sleep(10);
		//mySleep(10);
	}


	
	/********************************************
	�L�[���͏���
	********************************************/
	//int t1, t2, t3, t4, t5, t6;
	private boolean isFirstCheck_DOWN;
	private boolean isFirstCheck_LEFT;
	private boolean isFirstCheck_RIGHT;

	private void keyBoard(int mode){
		//long tmp_time;

		switch(mode){
		case GAME_PLAY :
			/* ��L�[ */
			if(key.UP && !isCollision(TURN_LEFT)){
				blockTurnLeft();
				mySleep(160);
			}
			/* ���L�[ */
			if(key.DOWN){
				//tmp_time = key.timerDown.getTime();
				//System.out.println(key.DownCheck);
				//if(key.DownCheck == true && tmp_time < 300){
				if(isFirstCheck_DOWN || key.timerDown.getTime() > 280){
					isFirstCheck_DOWN = false;
					if(!isCollision(MOVE_DOWN)){	//if �����n
						blockMoveDown();			//��������
						timer.timerReset();			//�^�C�}�[���Z�b�g
						mySleep(10);
					}else{							//if ���n
						setMode(GAME_PLAY_addBlock);//���[�h�ύX
						timer.timerReset();			//�^�C�}�[���Z�b�g
						timer.timerStop();			//�^�C�}�[�X�g�b�v
					}
				}
			}else{
				isFirstCheck_DOWN = true;
			}
			/* �E�L�[ */
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
			/* ���L�[ */
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
			/* �E��]�L�[ */
			if(key.TURN_R && !isCollision(TURN_RIGHT)){
				blockTurnRight();
				mySleep(160);
			}
			/* ����]�L�[ */
			if(key.TURN_L && !isCollision(TURN_LEFT)){
				blockTurnLeft();
				mySleep(160);
			}
			/* �ꎞ��~/�ĊJ */
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
	�Q�[���i�s����
	********************************************/
	//private long nothingTime;

	private void gameRun(int mode){
		switch(mode){
		//�Q�[���v���C
		case GAME_PLAY :
		 	if(timer.getTime() >= waitTime){
				//timer.setPriority(1);
				timer.timerStop();
				timer.timerReset();
				setMode(GAME_PLAY_dropBlock);
		 	}
			break;

		//�u���b�N��i����
		case GAME_PLAY_dropBlock :
	 		if(!isCollision(MOVE_DOWN)){
				blockMoveDown();
				setMode(GAME_PLAY);
				timer.timerStart();
			}else{
				setMode(GAME_PLAY_addBlock);
	 		}
			break;
		
		//�u���b�N���t�B�[���h�ɒǉ�
		case GAME_PLAY_addBlock :
			addBlock();					//�u���b�N���t�B�[���h�ɉ�����
			gui.blockErase();
			blk.clear();				//�u���b�N�̃N���A
			if(fld.checkLine() > 0){	//�����Ă��郉�C���̗L�����`�F�b�N
				setMode(GAME_PLAY_deleteLine);
			}else{
				setMode(GAME_PLAY_newBlock);
			}
			gui.fieldDraw();
			gui.BGtoFG();
			break;
				
		//���C��������
		case GAME_PLAY_deleteLine :
			mySleep(300);
			fld.deleteLine();//���C��������
			gui.fieldDraw();
			gui.BGtoFG();
			mySleep(600);
			fld.killLine();	//���������C�����l�߂�
			gui.fieldDraw();
			gui.BGtoFG();
			setMode(GAME_PLAY_newBlock);
			break;

		//�V�����u���b�N����
		case GAME_PLAY_newBlock :
			mySleep(800);
			blk.newBlock();			//�V�K�u���b�N
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
		
		//�ꎞ���f
		case GAME_PAUSE :
		 	break;
		
		//�Q�[���I�[�o�[
		case GAME_OVER :
			mySleep(1000);
			init();
			break;
		}//end switch

	}//end gameRun()
	

	
	/********************************************
	�u���b�N�ɂ��Ă̊e����
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
	�Փ˃`�F�b�N����
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
		
		//�Փ˔���
		for(int BlkRow=0; BlkRow < blk_dummy.block.length; BlkRow++){
			if(blk_dummy.y + BlkRow < 0        )continue;
			if(blk_dummy.y + BlkRow > FldRows-1)break;
			
			for(int BlkCol=0; BlkCol < blk_dummy.block.length; BlkCol++){
				if(blk_dummy.x + BlkCol < 0        )continue;
				if(blk_dummy.x + BlkCol > FldCols-1)break;

				//�����̃u���b�N�����݂���ӏ��ŁA����
				//�t�B�[���h�u���b�N�����݂���Ȃ�Փ�
				if(blk_dummy.block[BlkRow][BlkCol] != 0
				&& fld.field[blk_dummy.y + BlkRow][blk_dummy.x + BlkCol] != 0){
					return true;
				}
			}
		}
		return false;
	}//end isCollision()

	
	
	/********************************************
	�u���b�N���t�B�[���h�ɉ����鏈��
	********************************************/
	public void addBlock(){
		//block �� field �R�s�[
		for(int row=0; row < blk.block.length; row++){
		for(int col=0; col < blk.block.length; col++){
			if(blk.block[row][col] != 0){
				fld.field[blk.y + row][blk.x + col] = blk.block[row][col];
			}
		}
		}
	}//end addBlock()

	
	/********************************************
	GAMEOVER���菈��
	********************************************/
	public boolean isGameOver(){
		if(isCollision(BLOCK_NEW)){
			return true;
		}else{
			return false;
		}
	}//end isGameOver()
	
	
	
	/********************************************
	�X���[�v
	********************************************/
	private void mySleep(long ms){
		try {
			Thread.sleep(ms);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	
	/********************************************
	�Q�[�����[�h�Ɋւ��鏈��
	�@�@�Q�[�����[�h�ɕύX������������ MODE[]�z��
	�@�@�Ɏ���̃��[�h���i�[�����B
	********************************************/
	private int[]	MODE;		//���[�h�i�[�z��
	private int		modeSP; 	//���񃂁[�h�̊i�[�ʒu�������X�^�b�N�|�C���^
	private int		saveMode;	//���[�h�ꎞ�ۑ��p
	//private int		state = 0;
	//private int		saveState;
	//private boolean modeChangeFLG;
	
	//MODE�i�[
	private void setMode(int mode){
		//STATE_UP�Ȃ�Astate�����̂�
		/*
		if(mode == STATE_UP){
			state++;
			return;
		}
		*/
		//�i�[�z�񂪖��t�Ȃ�A�������Ȃ�
		if(modeSP >= MODE.length-1) return;

		//���񃂁[�h���i�[
		MODE[++modeSP] = mode;
	}
	
	//MODE��o
	private int getMode(){
		int returnNextMode;
		
		//���񃂁[�h��������΁A���݃��[�h�p��
		if(modeSP < 0) return gameMode;
		
		//���[�h����o
		returnNextMode = MODE[0];
		System.arraycopy(MODE, 1, MODE, 0, MODE.length-1);
		modeSP--;

		//GAME_PAUSE�̏ꍇ�͈ȑO�̃��[�h���L�����Ă���
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
