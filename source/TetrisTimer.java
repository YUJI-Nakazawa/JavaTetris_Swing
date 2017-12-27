/***********************************************************
TetrisTimer
	�^�C�}�[�N���X�ł��B���Ԍv����S�����܂��B
	Dummy�N���X���p�����Ă��܂����A���ɈӖ��͂���܂���B
	(�P�Ȃ錟�ؗp�ł�)
***********************************************************/

class Dummy extends Thread{}

public class TetrisTimer /*implements Runnable*/ extends Dummy {
	private long time0;
	private long time1;
	private long pastTime;
	
	private boolean timer_on;
	
	public Thread TetrisTimerThread;
	
	TetrisTimer(){
		time0		= 0;
		time1		= 0;
		pastTime	= 0;
		timer_on	= false;	//������Ԃł͌v�����J�n
		//this.setPriority(8);
		//TetrisTimerThread = new Thread(this);
		//return tetrisTimer;
	}
	
	
	public void timerStart(){
		//System.out.println("timerStart");
		//this.setPriority(8);
		//TetrisTimerThread.setPriority(8);
		timer_on = true;
		//this.notify();
	}
	
	
	public void timerStop(){
		//System.out.println("timerStop");
		//this.setPriority(1);
		//TetrisTimerThread.setPriority(1);
		//this.yield();
		timer_on = false;
		/*
		try {
			this.wait();
			//this.join();
			//System.out.println("join");
			//this.interrupt();
			timer_on = false;
			//this.join();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		*/
		//time0 = 0;
		//pastTime = 0;
	}
	
	public void timerReset(){
		time0 = 0;
		pastTime = 0;
	}
	
	public long getTime(){
		return pastTime;
	}
	

	/********************************************
	run()�X���b�h
	********************************************/	
	public void run(){
		//System.out.println("run");
		//timer_on = true;
		try {
		
			while(true){
				if(timer_on){
					if(time0 == 0){		//�^�C�}�[�J�n����
						time0 = System.currentTimeMillis();
						pastTime = 0;
					}else{
						time1		= System.currentTimeMillis();
						pastTime	= pastTime + (time1 - time0);
						time0 		= time1;
					}
				}else{
					time0 = 0;
					//pastTime = 0;
					//break;
				}

					this.sleep(10);

				//System.out.println(timer_on);
			}//end of while
			//time0 = 0;
			//System.out.println("timer off");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}//end run()
	


}//end class TetrisTimer
