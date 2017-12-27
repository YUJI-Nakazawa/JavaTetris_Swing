/***********************************************************
TetrisBlock
	このクラスは、テトリスのブロックに関するクラスです。
***********************************************************/

import java.math.*;

public class TetrisBlock {

	public int 			x;
	public int 			y;
	public int[][] 		block;
	public int[][] 		block2;

	protected int	FieldRows;
	protected int	FieldCols;
	
	//protected TetrisBlock CopyBlk;

	//コンストラクタ（通常版）
	public TetrisBlock(){
		//newBlock();
		FieldRows	= TetrisIni.FieldRows;
		FieldCols	= TetrisIni.FieldCols;
		//CopyBlk = new TetrisBlock();
	}//end of TetrisBlock()
	
	
	//コンストラクタ（block配列サイズのコピー版）
	public TetrisBlock(TetrisBlock fromBlk){
		FieldRows	= TetrisIni.FieldRows;
		FieldCols	= TetrisIni.FieldCols;
		//x = b.x;
		//y = b.y;
		block	= new int[fromBlk.block.length][fromBlk.block.length];
		block2	= new int[fromBlk.block2.length][fromBlk.block2.length];
		//block = b.copy();

	}
	
	
	public static void valueCopy(TetrisBlock fromBlk, TetrisBlock toBlk){
		//b.x = this.x;
		//b.y = this.y;
		
		toBlk.x = fromBlk.x;
		toBlk.y = fromBlk.y;
		
		//b.block = new int[this.block.length][this.block.length];
		//this.copy(b.block = new int[this.block.length][this.block.length]);
		//b.block = this.copy();
		blockArrayCopy(fromBlk.block, toBlk.block);
		blockArrayCopy(fromBlk.block2, toBlk.block2);
	}
	
	
	//新規ブロック
	public void newBlock(){
		//１～７の乱数を発生させて、ブロックの種類を決めます
		int n = (int)(7 * Math.random() + 1.0);
		
		switch(n){
			case 1 :
				block = new int[][] { {0, n, 0},
									  {n, n, n},
									  {0, 0, 0} };
				x=(int)((FieldCols - block.length)/2);
				y=0;
				break;
				
			case 2 :
				block = new int[][] { {n, n, 0},
									  {0, n, n},
									  {0, 0, 0} };
				x=4;
				y=0;
				break;
				
			case 3 :
				block = new int[][] { {0, n, n},
									  {n, n, 0},
									  {0, 0, 0} };
				x=4;
				y=0;
				break;
				
			case 4 :
				block = new int[][] { {0, 0, n},
									  {n, n, n},
									  {0, 0, 0} };
				x=4;
				y=0;
				break;
				
			case 5 :
				block = new int[][] { {n, 0, 0},
									  {n, n, n},
									  {0, 0, 0} };
				x=4;
				y=0;
				break;
				
			case 6 :
				block = new int[][] { {n, n},
									  {n, n} };
				x=4;
				y=0;
				break;
				
			case 7 :
				block = new int[][] { {0, 0, 0, 0},
									  {n, n, n, n},
									  {0, 0, 0, 0},
									  {0, 0, 0, 0} };
				x=3;
				y=0;
				break;
		
		}//end of switch()
		block2 = new int[block.length][block.length];
		
		x=(int)((FieldCols - block.length)/2);
		y=0;
	}//end of new()

	
	//下移動
	public void moveDown(){
			y++;
	}
	
	//右移動
	public void moveRight(){
			x++;
	}
	
	//左移動
	public void moveLeft(){
			x--;
	}
	
	//右回転
	public void turnRight(){
		//int[][] block2 = new int[block.length][block.length];

		//copy(block2 = new int[block.length][block.length]);
		//block2 = copy();
		blockArrayCopy(block, block2);
		
		for(int row = 0; row < block.length; row++){
		for(int col = 0; col < block.length; col++){
			block[col][block.length - 1 - row] = block2[row][col];	//右回転：定式
		}
		}
	}
	
	//左回転
	public void turnLeft(){
		//int[][] block2 = new int[block.length][block.length];
		
		//copy(block2 = new int[block.length][block.length]);
		//block2 = copy();
		blockArrayCopy(block, block2);
		
		for(int row = 0; row < block.length; row++){
		for(int col = 0; col < block.length; col++){
			block[block.length - 1 - col][row] = block2[row][col];	//左回転：定式
		}
		}
	}

	//blockをクリア
	public void clear(){
		//block = new int[][] {{0}};
		block = new int[0][0];
	}
	
	//blockをコピー
	public static void blockArrayCopy(int[][] from_block, int[][] to_block){
		//int[][] block2 = new int[block.length][block.length];
		//int [][] tmp_block = new int[block.length][block.length];
		
		for(int row=0; row < from_block.length; row++){
		for(int col=0; col < from_block.length; col++){
			to_block[row][col] = from_block[row][col];
		}
		}
		
		return;
	}
	/*
	public void blockArrayCopyFrom(TetrisBlock fromBlock){
		//int[][] block2 = new int[block.length][block.length];
		//int [][] tmp_block = new int[block.length][block.length];
		
		for(int row=0; row < fromBlock.block.length; row++){
		for(int col=0; col < fromBlock.block.length; col++){
			block[row][col] = fromBlock.block[row][col];
		}
		}
		
		return;
	}
	*/

	/*
	protected void finalize(){
		try{
			super.finalize();
			System.out.println("finalize:" + this);
		}
		catch(Throwable ex){
		}
	}
	*/

}//end class TetrisBlock


