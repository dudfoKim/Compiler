// Generated from MiniC.g4 by ANTLR 4.4
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiniCLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__16=1, T__15=2, T__14=3, T__13=4, T__12=5, T__11=6, T__10=7, T__9=8, 
		T__8=9, T__7=10, T__6=11, T__5=12, T__4=13, T__3=14, T__2=15, T__1=16, 
		T__0=17, VOID=18, INT=19, WHILE=20, IF=21, ELSE=22, RETURN=23, OR=24, 
		AND=25, LE=26, GE=27, EQ=28, NE=29, IDENT=30, LITERAL=31, DecimalConstant=32, 
		OctalConstant=33, HexadecimalConstant=34, WS=35;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'", "'\\u001A'", "'\\u001B'", "'\\u001C'", "'\\u001D'", "'\\u001E'", 
		"'\\u001F'", "' '", "'!'", "'\"'", "'#'"
	};
	public static final String[] ruleNames = {
		"T__16", "T__15", "T__14", "T__13", "T__12", "T__11", "T__10", "T__9", 
		"T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", 
		"VOID", "INT", "WHILE", "IF", "ELSE", "RETURN", "OR", "AND", "LE", "GE", 
		"EQ", "NE", "IDENT", "LITERAL", "DecimalConstant", "OctalConstant", "HexadecimalConstant", 
		"WS"
	};


	public MiniCLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MiniC.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2%\u00c7\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3"+
		"\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3"+
		"\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3"+
		"\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3"+
		"\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3"+
		"\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\36\3"+
		"\37\3\37\7\37\u009f\n\37\f\37\16\37\u00a2\13\37\3 \3 \3 \5 \u00a7\n \3"+
		"!\3!\3!\7!\u00ac\n!\f!\16!\u00af\13!\5!\u00b1\n!\3\"\3\"\7\"\u00b5\n\""+
		"\f\"\16\"\u00b8\13\"\3#\3#\3#\6#\u00bd\n#\r#\16#\u00be\3$\6$\u00c2\n$"+
		"\r$\16$\u00c3\3$\3$\2\2%\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f"+
		"\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63"+
		"\33\65\34\67\359\36;\37= ?!A\"C#E$G%\3\2\n\5\2C\\aac|\6\2\62;C\\aac|\3"+
		"\2\63;\3\2\62;\3\2\629\4\2ZZzz\5\2\62;CHch\5\2\13\f\17\17\"\"\u00ce\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2"+
		"\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2"+
		"\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2"+
		"\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2"+
		"\3I\3\2\2\2\5K\3\2\2\2\7M\3\2\2\2\tO\3\2\2\2\13Q\3\2\2\2\rS\3\2\2\2\17"+
		"U\3\2\2\2\21W\3\2\2\2\23Y\3\2\2\2\25[\3\2\2\2\27]\3\2\2\2\31_\3\2\2\2"+
		"\33a\3\2\2\2\35c\3\2\2\2\37e\3\2\2\2!g\3\2\2\2#i\3\2\2\2%k\3\2\2\2\'p"+
		"\3\2\2\2)t\3\2\2\2+z\3\2\2\2-}\3\2\2\2/\u0082\3\2\2\2\61\u0089\3\2\2\2"+
		"\63\u008c\3\2\2\2\65\u0090\3\2\2\2\67\u0093\3\2\2\29\u0096\3\2\2\2;\u0099"+
		"\3\2\2\2=\u009c\3\2\2\2?\u00a6\3\2\2\2A\u00b0\3\2\2\2C\u00b2\3\2\2\2E"+
		"\u00b9\3\2\2\2G\u00c1\3\2\2\2IJ\7\61\2\2J\4\3\2\2\2KL\7}\2\2L\6\3\2\2"+
		"\2MN\7]\2\2N\b\3\2\2\2OP\7=\2\2P\n\3\2\2\2QR\7>\2\2R\f\3\2\2\2ST\7?\2"+
		"\2T\16\3\2\2\2UV\7\177\2\2V\20\3\2\2\2WX\7_\2\2X\22\3\2\2\2YZ\7@\2\2Z"+
		"\24\3\2\2\2[\\\7#\2\2\\\26\3\2\2\2]^\7\'\2\2^\30\3\2\2\2_`\7*\2\2`\32"+
		"\3\2\2\2ab\7+\2\2b\34\3\2\2\2cd\7,\2\2d\36\3\2\2\2ef\7-\2\2f \3\2\2\2"+
		"gh\7.\2\2h\"\3\2\2\2ij\7/\2\2j$\3\2\2\2kl\7x\2\2lm\7q\2\2mn\7k\2\2no\7"+
		"f\2\2o&\3\2\2\2pq\7k\2\2qr\7p\2\2rs\7v\2\2s(\3\2\2\2tu\7y\2\2uv\7j\2\2"+
		"vw\7k\2\2wx\7n\2\2xy\7g\2\2y*\3\2\2\2z{\7k\2\2{|\7h\2\2|,\3\2\2\2}~\7"+
		"g\2\2~\177\7n\2\2\177\u0080\7u\2\2\u0080\u0081\7g\2\2\u0081.\3\2\2\2\u0082"+
		"\u0083\7t\2\2\u0083\u0084\7g\2\2\u0084\u0085\7v\2\2\u0085\u0086\7w\2\2"+
		"\u0086\u0087\7t\2\2\u0087\u0088\7p\2\2\u0088\60\3\2\2\2\u0089\u008a\7"+
		"q\2\2\u008a\u008b\7t\2\2\u008b\62\3\2\2\2\u008c\u008d\7c\2\2\u008d\u008e"+
		"\7p\2\2\u008e\u008f\7f\2\2\u008f\64\3\2\2\2\u0090\u0091\7>\2\2\u0091\u0092"+
		"\7?\2\2\u0092\66\3\2\2\2\u0093\u0094\7@\2\2\u0094\u0095\7?\2\2\u00958"+
		"\3\2\2\2\u0096\u0097\7?\2\2\u0097\u0098\7?\2\2\u0098:\3\2\2\2\u0099\u009a"+
		"\7#\2\2\u009a\u009b\7?\2\2\u009b<\3\2\2\2\u009c\u00a0\t\2\2\2\u009d\u009f"+
		"\t\3\2\2\u009e\u009d\3\2\2\2\u009f\u00a2\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0"+
		"\u00a1\3\2\2\2\u00a1>\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a3\u00a7\5A!\2\u00a4"+
		"\u00a7\5C\"\2\u00a5\u00a7\5E#\2\u00a6\u00a3\3\2\2\2\u00a6\u00a4\3\2\2"+
		"\2\u00a6\u00a5\3\2\2\2\u00a7@\3\2\2\2\u00a8\u00b1\7\62\2\2\u00a9\u00ad"+
		"\t\4\2\2\u00aa\u00ac\t\5\2\2\u00ab\u00aa\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad"+
		"\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00b1\3\2\2\2\u00af\u00ad\3\2"+
		"\2\2\u00b0\u00a8\3\2\2\2\u00b0\u00a9\3\2\2\2\u00b1B\3\2\2\2\u00b2\u00b6"+
		"\7\62\2\2\u00b3\u00b5\t\6\2\2\u00b4\u00b3\3\2\2\2\u00b5\u00b8\3\2\2\2"+
		"\u00b6\u00b4\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7D\3\2\2\2\u00b8\u00b6\3"+
		"\2\2\2\u00b9\u00ba\7\62\2\2\u00ba\u00bc\t\7\2\2\u00bb\u00bd\t\b\2\2\u00bc"+
		"\u00bb\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2"+
		"\2\2\u00bfF\3\2\2\2\u00c0\u00c2\t\t\2\2\u00c1\u00c0\3\2\2\2\u00c2\u00c3"+
		"\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5"+
		"\u00c6\b$\2\2\u00c6H\3\2\2\2\13\2\u009e\u00a0\u00a6\u00ad\u00b0\u00b6"+
		"\u00be\u00c3\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}