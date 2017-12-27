/***********************************************************
TetrisIni
	このクラスは、設定ファイル（iniファイル）から設定情報を
	読み込むクラスです。
***********************************************************/
import java.io.*;
import java.awt.event.*;
import java.util.HashMap;

class TetrisIni {
	
	//テトリス表示サイズ
	public static int BlockSize;	//ブロック一つのドットサイズ
	public static int FieldCols;	//フィールドの横マス数(左右は壁)
	public static int FieldRows;	//フィールドの縦マス数(下は底壁)

	//ウィンドウ表示サイズ（テトリス表示サイズで決まる）
	public static int ScreenW;		//ウィンドウ幅
	public static int ScreenH;		//ウィンドウ高さ

	//ウィンドウ表示位置
	public static int ScreenX;		//ウィンドウ表示位置左上座標Ｘ
	public static int ScreenY;		//ウィンドウ表示位置左上座標Ｙ

	//色　[空白,壁,山ブロック,落下ブロック][R,G,B]
	public static int[][] color = new int[4][3];

	//キー設定
	public static int KeyUpValue;
	public static int KeyDownValue;
	public static int KeyLeftValue;
	public static int KeyRightValue;
	public static int KeyLeftTurnValue;
	public static int KeyRightTurnValue;
	public static int KeyStartValue;
	
	//INI項目マップ
	private HashMap	IniItemMap;
	//KeyCodeマップ
	private HashMap	KeyCodeMap;

	TetrisIni(){
	/* INI項目マップ */
		//INI項目マップ生成
		IniItemMap = new HashMap();
		//INI項目マップに登録 put(キー, 処理オブジェクト)
		IniItemMap.put("BLOCKSIZE"    , new INI_BLOCKSIZE());
		IniItemMap.put("FIELDCOLS"    , new INI_FIELDCOLS());
		IniItemMap.put("FIELDROWS"    , new INI_FIELDROWS());
		IniItemMap.put("SCREENX"      , new INI_SCREENX());
		IniItemMap.put("SCREENY"      , new INI_SCREENY());
		IniItemMap.put("COLOR0"       , new INI_COLOR0());
		IniItemMap.put("COLOR1"       , new INI_COLOR1());
		IniItemMap.put("COLOR2"       , new INI_COLOR2());
		IniItemMap.put("COLOR3"       , new INI_COLOR3());
		IniItemMap.put("KEY_UP"       , new INI_KEY_UP());
		IniItemMap.put("KEY_DOWN"     , new INI_KEY_DOWN());
		IniItemMap.put("KEY_LEFT"     , new INI_KEY_LEFT());
		IniItemMap.put("KEY_RIGHT"    , new INI_KEY_RIGHT());
		IniItemMap.put("KEY_LEFTTURN" ,	new INI_KEY_LEFTTURN());
		IniItemMap.put("KEY_RIGHTTURN",	new INI_KEY_RIGHTTURN());
		IniItemMap.put("KEY_START"    ,	new INI_KEY_START());
		
	/* KeyCodeマップ */
		String[] strKey = {
			"VK_0",
			"VK_1",
			"VK_2",
			"VK_3",
			"VK_4",
			"VK_5",
			"VK_6",
			"VK_7",
			"VK_8",
			"VK_9",
			"VK_A",
			"VK_ACCEPT",
			"VK_ADD",
			"VK_AGAIN",
			"VK_ALL_CANDIDATES",
			"VK_ALPHANUMERIC",
			"VK_ALT",
			"VK_ALT_GRAPH",
			"VK_AMPERSAND",
			"VK_ASTERISK",
			"VK_AT",
			"VK_B",
			"VK_BACK_QUOTE",
			"VK_BACK_SLASH",
			"VK_BACK_SPACE",
			"VK_BRACELEFT",
			"VK_BRACERIGHT",
			"VK_C",
			"VK_CANCEL",
			"VK_CAPS_LOCK",
			"VK_CIRCUMFLEX",
			"VK_CLEAR",
			"VK_CLOSE_BRACKET",
			"VK_CODE_INPUT",
			"VK_COLON",
			"VK_COMMA",
			"VK_COMPOSE",
			"VK_CONTROL",
			"VK_CONVERT",
			"VK_COPY",
			"VK_CUT",
			"VK_D",
			"VK_DEAD_ABOVEDOT",
			"VK_DEAD_ABOVERING",
			"VK_DEAD_ACUTE",
			"VK_DEAD_BREVE",
			"VK_DEAD_CARON",
			"VK_DEAD_CEDILLA",
			"VK_DEAD_CIRCUMFLEX",
			"VK_DEAD_DIAERESIS",
			"VK_DEAD_DOUBLEACUTE",
			"VK_DEAD_GRAVE",
			"VK_DEAD_IOTA",
			"VK_DEAD_MACRON",
			"VK_DEAD_OGONEK",
			"VK_DEAD_SEMIVOICED_SOUND",
			"VK_DEAD_TILDE",
			"VK_DEAD_VOICED_SOUND",
			"VK_DECIMAL",
			"VK_DELETE",
			"VK_DIVIDE",
			"VK_DOLLAR",
			"VK_DOWN",
			"VK_E",
			"VK_END",
			"VK_ENTER",
			"VK_EQUALS",
			"VK_ESCAPE",
			"VK_EURO_SIGN",
			"VK_EXCLAMATION_MARK",
			"VK_F",
			"VK_F1",
			"VK_F10",
			"VK_F11",
			"VK_F12",
			"VK_F13",
			"VK_F14",
			"VK_F15",
			"VK_F16",
			"VK_F17",
			"VK_F18",
			"VK_F19",
			"VK_F2",
			"VK_F20",
			"VK_F21",
			"VK_F22",
			"VK_F23",
			"VK_F24",
			"VK_F3",
			"VK_F4",
			"VK_F5",
			"VK_F6",
			"VK_F7",
			"VK_F8",
			"VK_F9",
			"VK_FINAL",
			"VK_FIND",
			"VK_FULL_WIDTH",
			"VK_G",
			"VK_GREATER",
			"VK_H",
			"VK_HALF_WIDTH",
			"VK_HELP",
			"VK_HIRAGANA",
			"VK_HOME",
			"VK_I",
			"VK_INPUT_METHOD_ON_OFF",
			"VK_INSERT",
			"VK_INVERTED_EXCLAMATION_MARK",
			"VK_J",
			"VK_JAPANESE_HIRAGANA",
			"VK_JAPANESE_KATAKANA",
			"VK_JAPANESE_ROMAN",
			"VK_K",
			"VK_KANA",
			"VK_KANA_LOCK",
			"VK_KANJI",
			"VK_KATAKANA",
			"VK_KP_DOWN",
			"VK_KP_LEFT",
			"VK_KP_RIGHT",
			"VK_KP_UP",
			"VK_L",
			"VK_LEFT",
			"VK_LEFT_PARENTHESIS",
			"VK_LESS",
			"VK_M",
			"VK_META",
			"VK_MINUS",
			"VK_MODECHANGE",
			"VK_MULTIPLY",
			"VK_N",
			"VK_NONCONVERT",
			"VK_NUM_LOCK",
			"VK_NUMBER_SIGN",
			"VK_NUMPAD0",
			"VK_NUMPAD1",
			"VK_NUMPAD2",
			"VK_NUMPAD3",
			"VK_NUMPAD4",
			"VK_NUMPAD5",
			"VK_NUMPAD6",
			"VK_NUMPAD7",
			"VK_NUMPAD8",
			"VK_NUMPAD9",
			"VK_O",
			"VK_OPEN_BRACKET",
			"VK_P",
			"VK_PAGE_DOWN",
			"VK_PAGE_UP",
			"VK_PASTE",
			"VK_PAUSE",
			"VK_PERIOD",
			"VK_PLUS",
			"VK_PREVIOUS_CANDIDATE",
			"VK_PRINTSCREEN",
			"VK_PROPS",
			"VK_Q",
			"VK_QUOTE",
			"VK_QUOTEDBL",
			"VK_R",
			"VK_RIGHT",
			"VK_RIGHT_PARENTHESIS",
			"VK_ROMAN_CHARACTERS",
			"VK_S",
			"VK_SCROLL_LOCK",
			"VK_SEMICOLON",
			"VK_SEPARATER",
			"VK_SEPARATOR",
			"VK_SHIFT",
			"VK_SLASH",
			"VK_SPACE",
			"VK_STOP",
			"VK_SUBTRACT",
			"VK_T",
			"VK_TAB",
			"VK_U",
			"VK_UNDEFINED",
			"VK_UNDERSCORE",
			"VK_UNDO",
			"VK_UP",
			"VK_V",
			"VK_W",
			"VK_X",
			"VK_Y",
			"VK_Z"
		};
		
		String[] strKeyEventValue = {
			Integer.toString(KeyEvent.VK_0),
			Integer.toString(KeyEvent.VK_1),
			Integer.toString(KeyEvent.VK_2),
			Integer.toString(KeyEvent.VK_3),
			Integer.toString(KeyEvent.VK_4),
			Integer.toString(KeyEvent.VK_5),
			Integer.toString(KeyEvent.VK_6),
			Integer.toString(KeyEvent.VK_7),
			Integer.toString(KeyEvent.VK_8),
			Integer.toString(KeyEvent.VK_9),
			Integer.toString(KeyEvent.VK_A),
			Integer.toString(KeyEvent.VK_ACCEPT),
			Integer.toString(KeyEvent.VK_ADD),
			Integer.toString(KeyEvent.VK_AGAIN),
			Integer.toString(KeyEvent.VK_ALL_CANDIDATES),
			Integer.toString(KeyEvent.VK_ALPHANUMERIC),
			Integer.toString(KeyEvent.VK_ALT),
			Integer.toString(KeyEvent.VK_ALT_GRAPH),
			Integer.toString(KeyEvent.VK_AMPERSAND),
			Integer.toString(KeyEvent.VK_ASTERISK),
			Integer.toString(KeyEvent.VK_AT),
			Integer.toString(KeyEvent.VK_B),
			Integer.toString(KeyEvent.VK_BACK_QUOTE),
			Integer.toString(KeyEvent.VK_BACK_SLASH),
			Integer.toString(KeyEvent.VK_BACK_SPACE),
			Integer.toString(KeyEvent.VK_BRACELEFT),
			Integer.toString(KeyEvent.VK_BRACERIGHT),
			Integer.toString(KeyEvent.VK_C),
			Integer.toString(KeyEvent.VK_CANCEL),
			Integer.toString(KeyEvent.VK_CAPS_LOCK),
			Integer.toString(KeyEvent.VK_CIRCUMFLEX),
			Integer.toString(KeyEvent.VK_CLEAR),
			Integer.toString(KeyEvent.VK_CLOSE_BRACKET),
			Integer.toString(KeyEvent.VK_CODE_INPUT),
			Integer.toString(KeyEvent.VK_COLON),
			Integer.toString(KeyEvent.VK_COMMA),
			Integer.toString(KeyEvent.VK_COMPOSE),
			Integer.toString(KeyEvent.VK_CONTROL),
			Integer.toString(KeyEvent.VK_CONVERT),
			Integer.toString(KeyEvent.VK_COPY),
			Integer.toString(KeyEvent.VK_CUT),
			Integer.toString(KeyEvent.VK_D),
			Integer.toString(KeyEvent.VK_DEAD_ABOVEDOT),
			Integer.toString(KeyEvent.VK_DEAD_ABOVERING),
			Integer.toString(KeyEvent.VK_DEAD_ACUTE),
			Integer.toString(KeyEvent.VK_DEAD_BREVE),
			Integer.toString(KeyEvent.VK_DEAD_CARON),
			Integer.toString(KeyEvent.VK_DEAD_CEDILLA),
			Integer.toString(KeyEvent.VK_DEAD_CIRCUMFLEX),
			Integer.toString(KeyEvent.VK_DEAD_DIAERESIS),
			Integer.toString(KeyEvent.VK_DEAD_DOUBLEACUTE),
			Integer.toString(KeyEvent.VK_DEAD_GRAVE),
			Integer.toString(KeyEvent.VK_DEAD_IOTA),
			Integer.toString(KeyEvent.VK_DEAD_MACRON),
			Integer.toString(KeyEvent.VK_DEAD_OGONEK),
			Integer.toString(KeyEvent.VK_DEAD_SEMIVOICED_SOUND),
			Integer.toString(KeyEvent.VK_DEAD_TILDE),
			Integer.toString(KeyEvent.VK_DEAD_VOICED_SOUND),
			Integer.toString(KeyEvent.VK_DECIMAL),
			Integer.toString(KeyEvent.VK_DELETE),
			Integer.toString(KeyEvent.VK_DIVIDE),
			Integer.toString(KeyEvent.VK_DOLLAR),
			Integer.toString(KeyEvent.VK_DOWN),
			Integer.toString(KeyEvent.VK_E),
			Integer.toString(KeyEvent.VK_END),
			Integer.toString(KeyEvent.VK_ENTER),
			Integer.toString(KeyEvent.VK_EQUALS),
			Integer.toString(KeyEvent.VK_ESCAPE),
			Integer.toString(KeyEvent.VK_EURO_SIGN),
			Integer.toString(KeyEvent.VK_EXCLAMATION_MARK),
			Integer.toString(KeyEvent.VK_F),
			Integer.toString(KeyEvent.VK_F1),
			Integer.toString(KeyEvent.VK_F10),
			Integer.toString(KeyEvent.VK_F11),
			Integer.toString(KeyEvent.VK_F12),
			Integer.toString(KeyEvent.VK_F13),
			Integer.toString(KeyEvent.VK_F14),
			Integer.toString(KeyEvent.VK_F15),
			Integer.toString(KeyEvent.VK_F16),
			Integer.toString(KeyEvent.VK_F17),
			Integer.toString(KeyEvent.VK_F18),
			Integer.toString(KeyEvent.VK_F19),
			Integer.toString(KeyEvent.VK_F2),
			Integer.toString(KeyEvent.VK_F20),
			Integer.toString(KeyEvent.VK_F21),
			Integer.toString(KeyEvent.VK_F22),
			Integer.toString(KeyEvent.VK_F23),
			Integer.toString(KeyEvent.VK_F24),
			Integer.toString(KeyEvent.VK_F3),
			Integer.toString(KeyEvent.VK_F4),
			Integer.toString(KeyEvent.VK_F5),
			Integer.toString(KeyEvent.VK_F6),
			Integer.toString(KeyEvent.VK_F7),
			Integer.toString(KeyEvent.VK_F8),
			Integer.toString(KeyEvent.VK_F9),
			Integer.toString(KeyEvent.VK_FINAL),
			Integer.toString(KeyEvent.VK_FIND),
			Integer.toString(KeyEvent.VK_FULL_WIDTH),
			Integer.toString(KeyEvent.VK_G),
			Integer.toString(KeyEvent.VK_GREATER),
			Integer.toString(KeyEvent.VK_H),
			Integer.toString(KeyEvent.VK_HALF_WIDTH),
			Integer.toString(KeyEvent.VK_HELP),
			Integer.toString(KeyEvent.VK_HIRAGANA),
			Integer.toString(KeyEvent.VK_HOME),
			Integer.toString(KeyEvent.VK_I),
			Integer.toString(KeyEvent.VK_INPUT_METHOD_ON_OFF),
			Integer.toString(KeyEvent.VK_INSERT),
			Integer.toString(KeyEvent.VK_INVERTED_EXCLAMATION_MARK),
			Integer.toString(KeyEvent.VK_J),
			Integer.toString(KeyEvent.VK_JAPANESE_HIRAGANA),
			Integer.toString(KeyEvent.VK_JAPANESE_KATAKANA),
			Integer.toString(KeyEvent.VK_JAPANESE_ROMAN),
			Integer.toString(KeyEvent.VK_K),
			Integer.toString(KeyEvent.VK_KANA),
			Integer.toString(KeyEvent.VK_KANA_LOCK),
			Integer.toString(KeyEvent.VK_KANJI),
			Integer.toString(KeyEvent.VK_KATAKANA),
			Integer.toString(KeyEvent.VK_KP_DOWN),
			Integer.toString(KeyEvent.VK_KP_LEFT),
			Integer.toString(KeyEvent.VK_KP_RIGHT),
			Integer.toString(KeyEvent.VK_KP_UP),
			Integer.toString(KeyEvent.VK_L),
			Integer.toString(KeyEvent.VK_LEFT),
			Integer.toString(KeyEvent.VK_LEFT_PARENTHESIS),
			Integer.toString(KeyEvent.VK_LESS),
			Integer.toString(KeyEvent.VK_M),
			Integer.toString(KeyEvent.VK_META),
			Integer.toString(KeyEvent.VK_MINUS),
			Integer.toString(KeyEvent.VK_MODECHANGE),
			Integer.toString(KeyEvent.VK_MULTIPLY),
			Integer.toString(KeyEvent.VK_N),
			Integer.toString(KeyEvent.VK_NONCONVERT),
			Integer.toString(KeyEvent.VK_NUM_LOCK),
			Integer.toString(KeyEvent.VK_NUMBER_SIGN),
			Integer.toString(KeyEvent.VK_NUMPAD0),
			Integer.toString(KeyEvent.VK_NUMPAD1),
			Integer.toString(KeyEvent.VK_NUMPAD2),
			Integer.toString(KeyEvent.VK_NUMPAD3),
			Integer.toString(KeyEvent.VK_NUMPAD4),
			Integer.toString(KeyEvent.VK_NUMPAD5),
			Integer.toString(KeyEvent.VK_NUMPAD6),
			Integer.toString(KeyEvent.VK_NUMPAD7),
			Integer.toString(KeyEvent.VK_NUMPAD8),
			Integer.toString(KeyEvent.VK_NUMPAD9),
			Integer.toString(KeyEvent.VK_O),
			Integer.toString(KeyEvent.VK_OPEN_BRACKET),
			Integer.toString(KeyEvent.VK_P),
			Integer.toString(KeyEvent.VK_PAGE_DOWN),
			Integer.toString(KeyEvent.VK_PAGE_UP),
			Integer.toString(KeyEvent.VK_PASTE),
			Integer.toString(KeyEvent.VK_PAUSE),
			Integer.toString(KeyEvent.VK_PERIOD),
			Integer.toString(KeyEvent.VK_PLUS),
			Integer.toString(KeyEvent.VK_PREVIOUS_CANDIDATE),
			Integer.toString(KeyEvent.VK_PRINTSCREEN),
			Integer.toString(KeyEvent.VK_PROPS),
			Integer.toString(KeyEvent.VK_Q),
			Integer.toString(KeyEvent.VK_QUOTE),
			Integer.toString(KeyEvent.VK_QUOTEDBL),
			Integer.toString(KeyEvent.VK_R),
			Integer.toString(KeyEvent.VK_RIGHT),
			Integer.toString(KeyEvent.VK_RIGHT_PARENTHESIS),
			Integer.toString(KeyEvent.VK_ROMAN_CHARACTERS),
			Integer.toString(KeyEvent.VK_S),
			Integer.toString(KeyEvent.VK_SCROLL_LOCK),
			Integer.toString(KeyEvent.VK_SEMICOLON),
			Integer.toString(KeyEvent.VK_SEPARATER),
			Integer.toString(KeyEvent.VK_SEPARATOR),
			Integer.toString(KeyEvent.VK_SHIFT),
			Integer.toString(KeyEvent.VK_SLASH),
			Integer.toString(KeyEvent.VK_SPACE),
			Integer.toString(KeyEvent.VK_STOP),
			Integer.toString(KeyEvent.VK_SUBTRACT),
			Integer.toString(KeyEvent.VK_T),
			Integer.toString(KeyEvent.VK_TAB),
			Integer.toString(KeyEvent.VK_U),
			Integer.toString(KeyEvent.VK_UNDEFINED),
			Integer.toString(KeyEvent.VK_UNDERSCORE),
			Integer.toString(KeyEvent.VK_UNDO),
			Integer.toString(KeyEvent.VK_UP),
			Integer.toString(KeyEvent.VK_V),
			Integer.toString(KeyEvent.VK_W),
			Integer.toString(KeyEvent.VK_X),
			Integer.toString(KeyEvent.VK_Y),
			Integer.toString(KeyEvent.VK_Z)
		};
		
		//KeyCodeマップ生成
		KeyCodeMap = new HashMap();
		//KeyCodeマップに登録 put(キー, 仮想キーコード)
		for(int i = 0; i < strKey.length; i++){
			KeyCodeMap.put(strKey[i], strKeyEventValue[i]);
		}
		//配列解放
		//strKey           = new String[0];
		//strKeyEventValue = new String[0];
		strKey           = null;
		strKeyEventValue = null;
		
		//iniRead(strFile);
		//ScreenW = BlockSize * FieldCols;	//ウィンドウ横サイズ
		//ScreenH = BlockSize * FieldRows;	//ウィンドウ縦サイズ

	}//end of TetrisIni()
	
	/********************************************
	 INI項目に応じた処理クラス列挙
	********************************************/
	abstract class IniItem {
		public abstract void exec(String strValue);
	}
	class INI_BLOCKSIZE extends IniItem{
		public void exec(String strValue){
			/* 明示的なアクセスの書き方（BlockSizeが非staticでも通用する）
			TetrisIni.this.BlockSize = Integer.parseInt(strValue);
			System.out.println("BlockSize:" + TetrisIni.this.BlockSize);
			*/
			BlockSize = Integer.parseInt(strValue);
			System.out.println("BlockSize:" + strValue);
		}
	}
	class INI_FIELDCOLS extends IniItem{
		public void exec(String strValue){
			FieldCols = Integer.parseInt(strValue) + 2;
			System.out.println("FieldCols:" + strValue);
		}
	}
	class INI_FIELDROWS extends IniItem{
		public void exec(String strValue){
			FieldRows = Integer.parseInt(strValue) + 1;
			System.out.println("FieldRows:" + strValue);
		}
	}
	class INI_SCREENX extends IniItem{
		public void exec(String strValue){
			ScreenX   = Integer.parseInt(strValue);
			System.out.println("ScreenX:" + strValue);
		}
	}
	class INI_SCREENY extends IniItem{
		public void exec(String strValue){
			ScreenY   = Integer.parseInt(strValue);
			System.out.println("ScreenY:" + strValue);
		}
	}
	class INI_COLOR0 extends IniItem{
		public void exec(String strValue){
			colorSet(0, strValue);
			System.out.println("Color0:" + strValue);
		}
	}
	class INI_COLOR1 extends IniItem{
		public void exec(String strValue){
			colorSet(1, strValue);
			System.out.println("Color1:" + strValue);
		}
	}
	class INI_COLOR2 extends IniItem{
		public void exec(String strValue){
			colorSet(2, strValue);
			System.out.println("Color2:" + strValue);
		}
	}
	class INI_COLOR3 extends IniItem{
		public void exec(String strValue){
			colorSet(3, strValue);
			System.out.println("Color3:" + strValue);
		}
	}
	class INI_KEY_UP extends IniItem{
		public void exec(String strValue){
			KeyUpValue = Integer.parseInt((String)KeyCodeMap.get(strValue));
			System.out.println("KEY_UP:" + KeyUpValue);
		}
	}
	class INI_KEY_DOWN extends IniItem{
		public void exec(String strValue){
			KeyDownValue = Integer.parseInt((String)KeyCodeMap.get(strValue));
			System.out.println("KEY_DOWN:" + KeyDownValue);
		}
	}
	class INI_KEY_LEFT extends IniItem{
		public void exec(String strValue){
			KeyLeftValue = Integer.parseInt((String)KeyCodeMap.get(strValue));
			System.out.println("KEY_LEFT:" + KeyLeftValue);
		}
	}
	class INI_KEY_RIGHT extends IniItem{
		public void exec(String strValue){
			KeyRightValue = Integer.parseInt((String)KeyCodeMap.get(strValue));
			System.out.println("KEY_RIGHT:" + KeyRightValue);
		}
	}
	class INI_KEY_LEFTTURN extends IniItem{
		public void exec(String strValue){
			KeyLeftTurnValue = Integer.parseInt((String)KeyCodeMap.get(strValue));
			System.out.println("KEY_LEFTTURN:" + KeyLeftTurnValue);
		}
	}
	class INI_KEY_RIGHTTURN extends IniItem{
		public void exec(String strValue){
			KeyRightTurnValue = Integer.parseInt((String)KeyCodeMap.get(strValue));
			System.out.println("KEY_RIGHTTURN:" + KeyRightTurnValue);
		}
	}
	class INI_KEY_START extends IniItem{
		public void exec(String strValue){
			KeyStartValue = Integer.parseInt((String)KeyCodeMap.get(strValue));
			System.out.println("KEY_START:" + KeyStartValue);
		}
	}
	
	/********************************************
	iniファイル読込
	********************************************/
	public void iniRead(String strFile){
		String	strLine, item, value;
		int		pEqual;
		
		try{
			FileReader		fr	= new FileReader(strFile);
			BufferedReader	buf	= new BufferedReader(fr);
            while((strLine = buf.readLine()) != null) {
				//System.out.println(strLine.indexOf("[", 0));
				if(strLine.indexOf("[", 0) == 0)continue;				//"["が先頭にあればスキップ
				if(strLine.indexOf("#", 0) == 0)continue;				//"#"が先頭にあればスキップ
				//pEqual = strLine.indexOf("=", 0);		//pEqual:"="の位置
				if((pEqual = strLine.indexOf("=", 0)) == -1)continue;	//"="が見つからないならスキップ
                item  = strLine.substring(0, pEqual);	//"item=value"
				value = strLine.substring(pEqual + 1);	//"item=value"
				
				//INI項目名から処理オブジェクト取得
				((IniItem)IniItemMap.get(item)).exec(value);
				
            }//end of while
            buf.close();
			
		}catch(Exception ex){	//iniファイルに不備があるとき
		
			BlockSize = 16;
			FieldCols = 10+2;
			FieldRows = 20+1;
			ScreenX   = 300;
			ScreenY   = 100;
			color = new int[][] {
				{90,90,90},		//空白
				{0,0,255},		//ブロック[壁]
				{0,255,0},		//ブロック[山]
				{255,0,0},		//ブロック[落下]
			};
			KeyUpValue			= KeyEvent.VK_UP;
			KeyDownValue		= KeyEvent.VK_DOWN;
			KeyLeftValue		= KeyEvent.VK_LEFT;
			KeyRightValue		= KeyEvent.VK_RIGHT;
			KeyLeftTurnValue	= KeyEvent.VK_Z;
			KeyRightTurnValue	= KeyEvent.VK_X;
			KeyStartValue		= KeyEvent.VK_SPACE;
		}
		
		ScreenW = BlockSize * FieldCols;	//ウィンドウ横サイズ
		ScreenH = BlockSize * FieldRows;	//ウィンドウ縦サイズ

	}//end of iniRead()
	
	
	/********************************************
	色のセット
	********************************************/
	//public void colorSet(String item, String value){
	public void colorSet(int colorNum, String strValue){
		//int colorNum = Integer.parseInt(item.substring(item.length()-1));
		int	pComma1, pComma2;
		
		pComma1 = strValue.indexOf(",", 0);				//p1:１つ目の","の位置
		pComma2 = strValue.indexOf(",", pComma1 + 1);	//p2:２つ目の","の位置
		color[colorNum][0] = Integer.parseInt(strValue.substring(0,pComma1));			//Red
		color[colorNum][1] = Integer.parseInt(strValue.substring(pComma1 + 1,pComma2));	//Green
		color[colorNum][2] = Integer.parseInt(strValue.substring(pComma2 + 1));			//Blue
	}//end of colorSet()
	
	
	//デバッグ用
	/*
	public static void main(String[] args){
		
		new TetrisIni("tetris.ini");
	}
	*/
}//end of class TetrisIni
