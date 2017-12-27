/***********************************************************
TetrisGUI
	���̃N���X�́A�e�g���X�̂f�t�h��S������N���X�ł��B
	�X�N���[���ւ̕`����s���܂��B
	TetrisField, TetrisBlock, TetrisKey�̊e�N���X�ƘA�g����
	���܂��B
	(���̃\�[�X�ł́A�f�t�h�� Swing ���̗p)
***********************************************************/

import java.awt.*; 
import javax.swing.*;

public class TetrisGUI {
	
	private	JFrame		frame;
	private JPanel		panel;
	private Image		image;
	
	private TetrisField	fld;
	private TetrisBlock	blk;
	private TetrisKey	key;
	
	private int aSIZE;
	private int	ScreenX;
	private int	ScreenY;
	private int	ScreenW;
	private int	ScreenH;
	private int	FldRows;
	private int	FldCols;
	
	private Color[] color = new Color[4];
	
	private Graphics fg;
	private Graphics bg;
	

	/********************************************
	�R���X�g���N�^
	********************************************/
	//TetrisGUI(TetrisField fld, TetrisBlock blk, TetrisKey key){
	TetrisGUI(Tetris tetris){
		this.key 	= tetris.key;
		this.fld 	= tetris.fld;
		this.blk 	= tetris.blk;
		
		aSIZE	= TetrisIni.BlockSize;
		ScreenX	= TetrisIni.ScreenX;
		ScreenY	= TetrisIni.ScreenY;
		ScreenW	= TetrisIni.ScreenW;
		ScreenH	= TetrisIni.ScreenH;
		FldRows	= TetrisIni.FieldRows;
		FldCols	= TetrisIni.FieldCols;
		
		//�F
		color[0] = new Color(TetrisIni.color[0][0], TetrisIni.color[0][1], TetrisIni.color[0][2]);	//��
		color[1] = new Color(TetrisIni.color[1][0], TetrisIni.color[1][1], TetrisIni.color[1][2]);	//�u���b�N[��]
		color[2] = new Color(TetrisIni.color[2][0], TetrisIni.color[2][1], TetrisIni.color[2][2]);	//�u���b�N[�R]
		color[3] = new Color(TetrisIni.color[3][0], TetrisIni.color[3][1], TetrisIni.color[3][2]);	//�u���b�N[����]
		
		//�t���[��
		frame 	= new JFrame("Tetris");
		frame.setBounds(ScreenX, ScreenY, ScreenW, ScreenH);
		frame.setResizable(false);
		frame.addKeyListener(key);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�R���e���c�y�C��
		Container cont = frame.getContentPane();
		cont.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));

		//�p�l��
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(ScreenW, ScreenH));
		//panel.setBackground(Color.black);
		cont.add(panel);

		frame.pack();
		
		
		image = panel.createImage(ScreenW, ScreenH);

		fg = panel.getGraphics();
		bg = image.getGraphics();
		bg.setColor(Color.BLACK);
		bg.fillRect(0, 0, ScreenW, ScreenH);
		
	}

	
	/********************************************
	��ʂ�\��
	********************************************/
	public void windowShow(){
		frame.show();
	}
	
	
	/********************************************
	�t�B�[���h��`��:�����(bg)
	********************************************/
	public void fieldDraw(){
	
		for(int row=0; row <= FldRows-1; row++){
		for(int col=0; col <= FldCols-1; col++){
			if(fld.field[row][col] == 0){
				//�u���b�N�Ȃ�
				bg.setColor(color[0]);
				bg.fillRect(aSIZE*col+1, aSIZE*row+1, aSIZE-2, aSIZE-2);
				//bg.fillRoundRectangle(aSIZE*i+1, aSIZE*j+1, aSIZE-1, aSIZE-1, 8, 8);
				//bg.fillOval(aSIZE*i, aSIZE*j, aSIZE, aSIZE);
				
			}
			else if(fld.field[row][col] == 9){
				//�u���b�N(��)
				bg.setColor(color[1]);
				bg.fillRect(aSIZE*col+1, aSIZE*row+1, aSIZE-2, aSIZE-2);
				//bg.fillRoundRectangle(aSIZE*i+1, aSIZE*j+1, aSIZE-1, aSIZE-1, 8, 8);
				//bg.fillOval(aSIZE*i, aSIZE*j, aSIZE, aSIZE);
			}
			else{
				//�u���b�N(�R)
				bg.setColor(color[2]);
				bg.fillRect(aSIZE*col+1, aSIZE*row+1, aSIZE-2, aSIZE-2);
				//bg.fillRoundRectangle(aSIZE*i+1, aSIZE*j+1, aSIZE-1, aSIZE-1, 8, 8);
				//bg.fillOval(aSIZE*i, aSIZE*j, aSIZE, aSIZE);
			}
		}
		}
	}//end fieldDraw()
	

	/********************************************
	�u���b�N��`��:�����(bg)
	********************************************/	
	public void blockDraw(){
		
		for(int row=0; row < blk.block.length; row++){
		for(int col=0; col < blk.block.length; col++){
			if(blk.block[row][col] != 0){
				//�u���b�N����
				bg.setColor(color[3]);
				bg.fillRect(aSIZE*(col+blk.x)+1, aSIZE*(row+blk.y)+1, aSIZE-2, aSIZE-2);
				//bg.fillRoundRectangle(aSIZE*(col+blk.x)+1, aSIZE*(row+blk.y)+1, aSIZE-1, aSIZE-1, 8, 8);
				//bg.fillOval(aSIZE*(col+blk.x), aSIZE*(row+blk.y), aSIZE, aSIZE);
				
			}
		}
		}
	}//end blockDraw()
	
	
	/********************************************
	�u���b�N������:�����(bg)
	********************************************/	
	public void blockErase(){
		
		for(int row=0; row < blk.block.length; row++){
		for(int col=0; col < blk.block.length; col++){
			if(blk.block[row][col] != 0){
				//�u���b�N����
				bg.setColor(color[0]);
				bg.fillRect(aSIZE*(col+blk.x)+1, aSIZE*(row+blk.y)+1, aSIZE-2, aSIZE-2);
				//bg.fillRoundRectangle(aSIZE*(col+blk.x)+1, aSIZE*(row+blk.y)+1, aSIZE-1, aSIZE-1, 8, 8);
				//bg.fillOval(aSIZE*(col+blk.x), aSIZE*(row+blk.y), aSIZE, aSIZE);
				
			}
		}
		}
	}//end blockDraw()
	
	/********************************************
	�����(bg) �� �\���(fg)
	********************************************/	
	public void BGtoFG(){
	    /*
		GC gc = new GC(canvas);
		gc.drawImage(image, 0, 0);
		gc.dispose();
		*/
		fg.drawImage(image, 0, 0, panel);

		//frame.setTitle(Thread.currentThread().getName());
	}//end BGtoFG()
	
}//end class TetrisGUI
