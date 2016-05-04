package Item3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class AssemblyGenListener extends MiniCBaseListener 
{

	ParseTreeProperty<String> newTexts = new ParseTreeProperty<String>();
	Map<String, String> map = new HashMap<String, String>();
	Map<String, String> gmap = new HashMap<String, String>();
	ArrayList<Integer> pre_local= new ArrayList<Integer>();

	String blink = "	";
	int lexical_level = 0;
	int block_level = 0;
	int global_cnt = 0;
	int local_var_cnt = 0;
	int pre_local_var_cnt = 0;
	int param_cnt = 0;
	int index = 0;
	
	boolean isBinaryOperation(MiniCParser.ExprContext ctx) 
	{
		return ctx.getChildCount() == 3 && ctx.getChild(1) != ctx.expr(0);
	}

	@Override public void enterProgram(MiniCParser.ProgramContext ctx)
	{
		block_level++;
		System.out.println();
	}

	@Override public void exitProgram(MiniCParser.ProgramContext ctx) 
	{ 
		String mString = "\n";
		lexical_level = 1;
		String decl = "";
		int check_global = 0;
		int check_fun = 0;
		
		int decl_cnt = ctx.decl().size();
		
		for(int i=0; i<decl_cnt; i++)
		{
			if(check_global == 0 && ctx.decl(i).getChild(0) instanceof MiniCParser.Var_declContext)
			{
				decl += ".section .bss\n"+ newTexts.get(ctx.decl(i));
				check_global++;
			}
			else if (check_fun == 0 && ctx.decl(i).getChild(0) instanceof MiniCParser.Fun_declContext)
			{
				decl += "\n.section .text\n.global .main\n"+ newTexts.get(ctx.decl(i));
				check_fun++;
			}
			else
			{
				decl += newTexts.get(ctx.decl(i));
			}
		}
		
		mString += decl;
		System.out.print(mString);
	}

	@Override public void exitDecl(MiniCParser.DeclContext ctx) 
	{ 
		String var_decl = null, fun_decl = null;
		
		if(ctx.getChild(0).equals(ctx.var_decl()))
		{	
			var_decl = newTexts.get(ctx.var_decl());
			newTexts.put(ctx, var_decl);
		}
		else
		{
			fun_decl = newTexts.get(ctx.fun_decl());
			newTexts.put(ctx, fun_decl);
		}
	}
	
	@Override public void exitVar_decl(MiniCParser.Var_declContext ctx) 
	{ 
		String ident = ctx.IDENT().getText();
		
		if(ctx.getChildCount() == 3)
		{
			newTexts.put(ctx, blink+ident+" resd 1");
			gmap.put(ident, ident);
		} 
		else
		{
			String literal = ctx.LITERAL().getText();
			newTexts.put(ctx, blink+ident+" resd "+literal);
			gmap.put(ident, ident);
		}
	}
	
	@Override public void exitType_spec(MiniCParser.Type_specContext ctx) 
	{ 
		if(ctx.getChild(0) == ctx.VOID())
		{
			newTexts.put(ctx, ctx.VOID().getText());
		}
		else
		{
			newTexts.put(ctx, ctx.INT().getText());
		}
	}

	@Override public void enterFun_decl(MiniCParser.Fun_declContext ctx) 
	{
		block_level++;
		local_var_cnt =0;
		pre_local_var_cnt = 0;
		param_cnt=0;
	}
	
	@Override public void exitFun_decl(MiniCParser.Fun_declContext ctx) 
	{ 
		String ident = null, params ="", compound_stmt = "";			
		
		ident =ctx.IDENT().getText();
		params += newTexts.get(ctx.params());
		compound_stmt = newTexts.get(ctx.compound_stmt());
		block_level--;
		
		if(ident.equals("main"))
		{
			newTexts.put(ctx, ident+":\n"+blink+"pushl	%ebp\n"+blink+"movl	%ebp, %esp\n"+params+compound_stmt+blink+"movl	%esp, %ebp\n"+blink+"pop	%ebp\n"+blink+"ret\n");
		}
		else
		{
			newTexts.put(ctx, ident+":\n"+blink+"pushl	%ebp\n"+blink+"movl	%ebp, %esp\n"+compound_stmt+blink+"pop	%ebp\n"+blink+"ret\n");
		}
		
		param_cnt = 0;
	}

	@Override public void exitParams(MiniCParser.ParamsContext ctx) 
	{
		String param = newTexts.get(ctx.param(0));
		
		int param_cnt = ctx.param().size();
		
		for(int i = 1; i<param_cnt ; i++)
		{
			param += newTexts.get(ctx.param(i));
		}
		
		if (ctx.getChildCount() == 0)
		{
			newTexts.put(ctx, "");
		}
		else if(ctx.getChild(0).getText().equals("void"))
		{
			newTexts.put(ctx, "");
		}
		else 
		{ 
			newTexts.put(ctx, param);
		}
	}
	
	@Override public void enterParam(MiniCParser.ParamContext ctx)
	{
		param_cnt++;
	}

	@Override public void exitParam(MiniCParser.ParamContext ctx) 
	{ 
		String ident = ctx.IDENT().getText();
		
		if(map.get(ident) != null)
		{
			newTexts.put(ctx, blink+"pushl	"+map.get(ident)+"\n");
		}
		
		newTexts.put(ctx, blink+"pushl	"+ident+"\n");
	}
	

	@Override public void exitStmt(MiniCParser.StmtContext ctx) 
	{ 
		String expr_stmt = "",compound_stmt = "", if_stmt= "",while_stmt= "", return_stmt= "";
		
		if(ctx.getChild(0).equals(ctx.expr_stmt()))
		{
			expr_stmt = newTexts.get(ctx.expr_stmt());
			newTexts.put(ctx, expr_stmt);
		}
		else if(ctx.getChild(0).equals(ctx.compound_stmt()) )
		{
			compound_stmt = newTexts.get(ctx.compound_stmt());
			newTexts.put(ctx, compound_stmt);
		}
		else if(ctx.getChild(0).equals(ctx.if_stmt()) )
		{
			if_stmt = newTexts.get(ctx.if_stmt());
			newTexts.put(ctx, if_stmt);
		}
		else if(ctx.getChild(0).equals(ctx.while_stmt()) )
		{
			while_stmt = newTexts.get(ctx.while_stmt());
			newTexts.put(ctx, while_stmt);
		}
		else if(ctx.getChild(0).equals(ctx.return_stmt()))
		{
			return_stmt = newTexts.get(ctx.return_stmt());
			newTexts.put(ctx, return_stmt);
		}
	}

	@Override public void exitExpr_stmt(MiniCParser.Expr_stmtContext ctx) 
	{ 
		String expr = "";
		
		expr += newTexts.get(ctx.expr());
		newTexts.put(ctx, expr);
	}
	
	@Override public void enterWhile_stmt(MiniCParser.While_stmtContext ctx) 
	{
		block_level++;
		param_cnt = 0;
	}

	@Override public void exitWhile_stmt(MiniCParser.While_stmtContext ctx)
	{ 
		String _while = "", expr = "", stmt = "";

		_while = "L"+(++index);
		String mark = _while;
		String mark2 = "L"+(++index);
		
		expr = newTexts.get(ctx.expr());
		stmt = newTexts.get(ctx.stmt());
		
		newTexts.put(ctx, mark+":	"+expr+blink+mark2+"\n"+stmt+blink+"jmp	"+mark+"\n"+mark2+":\n");
		block_level--;
	}
	@Override public void enterCompound_stmt(MiniCParser.Compound_stmtContext ctx) 
	{
		pre_local.add(local_var_cnt);
		pre_local_var_cnt ++;
		local_var_cnt = 0;
		local_var_cnt += param_cnt;
	}

	@Override public void exitCompound_stmt(MiniCParser.Compound_stmtContext ctx) 
	{ 
		lexical_level = 2;
		String local_decl = newTexts.get(ctx.local_decl(0)), stmt = newTexts.get(ctx.stmt(0));
	
		int local_cnt = ctx.local_decl().size(),stmt_cnt = ctx.stmt().size();
		
		for(int i = 1; i<local_cnt ; i++)
		{
			local_decl += newTexts.get(ctx.local_decl(i));
		}
		
		for(int i = 1; i<stmt_cnt ; i++)
		{
			stmt += newTexts.get(ctx.stmt(i));
		}
		
		if(local_cnt == 0 && stmt_cnt == 0)
		{
			newTexts.put(ctx, "");
		}
		else if(local_cnt !=0 && stmt_cnt == 0)
		{
			newTexts.put(ctx, blink+"pushl	%ebp\n	movl	%esp, %ebp\n	subl	$"+(4*local_var_cnt)+", %esp\n"+local_decl+"	movl	%ebp, %esp\n	popl	%ebp\n");
		}
		else if(local_cnt ==0 && stmt_cnt != 0)
		{
			newTexts.put(ctx, stmt);
		}
		else 
		{
			newTexts.put(ctx, blink+"pushl	%ebp\n	movl	%esp, %ebp\n	subl	$"+(4*local_var_cnt)+", %esp\n"+local_decl+stmt+"	movl	%ebp, %esp\n	popl	%ebp\n");
		}
		if(pre_local_var_cnt > 1)
		{
			local_var_cnt = pre_local.get(pre_local_var_cnt-1);
			pre_local_var_cnt --;
		}
	}

	@Override public void enterLocal_decl(MiniCParser.Local_declContext ctx)
	{
		String ident = null;
		ident = ctx.IDENT().getText();
		
		if(ctx.getChildCount() == 3)
		{
			local_var_cnt++;
			String map_val = "-"+(4*local_var_cnt)+"(%esp)";
			map.put(ident, map_val);
		}
		else
		{
			int size = Integer.parseInt(ctx.LITERAL().getText());
			int start ;
			local_var_cnt += size;
			start = local_var_cnt - size+1;
			String map_val = "-"+(4*start)+"(%esp)";
			map.put(ident, map_val);
		}
	}
	
	@Override public void exitLocal_decl(MiniCParser.Local_declContext ctx) 
	{ 
		String ident = null;
		ident = ctx.IDENT().getText();
		
		if(ctx.getChildCount() == 3)
		{
			newTexts.put(ctx, "");
			map.put(ident, "-"+(4*local_var_cnt)+"(%esp)");
		}
		else
		{
			int size = Integer.parseInt(ctx.LITERAL().getText());
			int start = local_var_cnt - size+1;
			
			newTexts.put(ctx, "");
			map.put(ident, "-"+(4*start)+"(%esp)");
		}
	}

	@Override public void enterIf_stmt(MiniCParser.If_stmtContext ctx) 
	{
		block_level++;
		param_cnt=0;
	}

	@Override public void exitIf_stmt(MiniCParser.If_stmtContext ctx) 
	{ 
		
		String s1 = null, s2 = null;
		
		if(ctx.getChildCount() == 5)
		{
			s1 = "L"+(++index);
			s2 = "L"+(++index);
			
			String expr = newTexts.get(ctx.expr());
			String stmt = newTexts.get(ctx.stmt(0));
			
			newTexts.put(ctx,s1+":	"+expr+blink+s2+"\n"+stmt+s2+":\n");
			block_level--;
		}
		else if (ctx.getChildCount() == 7 && ctx.getChild(5) == ctx.ELSE())
		{
			s1 = "L"+(++index);
			s2 = "L"+(++index);
			
			String expr = newTexts.get(ctx.expr());
			String stmt = newTexts.get(ctx.stmt(0));
			String stmt2 = newTexts.get(ctx.stmt(1));
			
			newTexts.put(ctx,s1+":	"+expr+blink+s2+"\n"+stmt+s2+":"+stmt2);
			block_level--;
		}
	}

	@Override public void exitReturn_stmt(MiniCParser.Return_stmtContext ctx) 
	{ 
		String ex1 = newTexts.get(ctx.expr());
		
		if(ctx.getChildCount() == 2)
		{
			newTexts.put(ctx, "");
		}
		else
		{
			newTexts.put(ctx,  ex1);
		}
	}

	@Override public void exitExpr(MiniCParser.ExprContext ctx) 
	{ 
		String expr1 = null, expr2 = null, op = null;
		String ident  = null;
	
		if(isBinaryOperation(ctx))
		{
			if(ctx.getChild(0) == ctx.IDENT())
			{
				ident = map.get(ctx.IDENT().getText());
				expr1 = newTexts.get(ctx.expr(0));
				
				if(ctx.expr(0).getChildCount() == 1)
				{
					if(map.get(expr1) != null)
					{
						if(gmap.get(ctx.IDENT().getText()) != null)
						{
							newTexts.put(ctx, blink+"movl	"+map.get(expr1)+", $"+gmap.get(ctx.IDENT().getText())+"\n");
						}
						else
						{
							newTexts.put(ctx, blink+"movl	"+map.get(expr1)+", "+ident+"\n");
						}
					}
					else 
					{
						if(gmap.get(ctx.IDENT().getText()) != null)
						{
							newTexts.put(ctx, blink+"movl	$"+expr1+", $"+gmap.get(ctx.IDENT().getText())+"\n");
						}
						else
						{
							newTexts.put(ctx, blink+"movl	$"+expr1+", "+ident+"\n");
						}
					}
				}
				else
				{
					if(ctx.expr(0).getChildCount() == 4)
					{
						newTexts.put(ctx, blink+"movl	$"+expr1+", "+ident+"\n");
					}
					else
					{
						newTexts.put(ctx, expr1);
					}
				}
			}
			else 
			{
				expr1 = newTexts.get(ctx.expr(0));
				String expr1_str = map.get(expr1);
				expr2 = newTexts.get(ctx.expr(1));
				String expr2_str = map.get(expr2);
				op = ctx.getChild(1).getText();
				String op_comm = null;
				
				if(op.equals("+"))
				{
					if(map.containsKey(expr1) == true && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,blink+"add	"+expr2_str+", "+expr1_str+"\n");
					}
					else if(map.containsKey(expr1) == false && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,blink+"add	"+expr2_str+", $"+expr1+"\n" );
					}
					else if(map.containsKey(expr1) == true && map.containsKey(expr2) == false){
						
						newTexts.put(ctx,blink+"add	$"+expr2+", "+expr1_str+"\n" );
					}
					else
					{
						newTexts.put(ctx,blink+"add	$"+expr2+", $"+expr1+"\n" );
					}
				}
				else if(op.equals("-"))
				{
					if(map.containsKey(expr1) == true && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,blink+"sub	"+expr2_str+", "+expr1_str+"\n");
					}
					else if(map.containsKey(expr1) == false && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,blink+"sub	"+expr2_str+", $"+expr1+"\n");
					}
					else if(map.containsKey(expr1) == true && map.containsKey(expr2) == false)
					{
						newTexts.put(ctx,blink+"sub	$"+expr2+", "+expr1_str+"\n");
					} 
					else
					{
						newTexts.put(ctx,blink+"sub	$"+expr2+", $"+expr1+"\n");
					}
				}
				else if(op.equals("*"))
				{
					if(map.containsKey(expr1) == true && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,blink+"mul	"+expr2_str+", "+expr1_str+"\n");
					}
					else if(map.containsKey(expr1) == false && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,blink+"mul	"+expr2_str+", $"+expr1+"\n");
					}
					else if(map.containsKey(expr1) == true && map.containsKey(expr2) == false)
					{
						newTexts.put(ctx,blink+"mul	$"+expr2+", "+expr1_str+"\n");
					} 
					else
					{
						newTexts.put(ctx,blink+"mul	$"+expr2+", $"+expr1+"\n");
					}
				}
				else if(op.equals("/"))
				{
					if(map.containsKey(expr1) == true && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,blink+"div	"+expr2_str+", "+expr1_str+"\n");
					}
					else if(map.containsKey(expr1) == false && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,blink+"div	"+expr2_str+", $"+expr1+"\n");
					} 
					else if(map.containsKey(expr1) == true && map.containsKey(expr2) == false)
					{
						newTexts.put(ctx,blink+"div	$"+expr2+", "+expr1_str+"\n");
					} 
					else
					{
						newTexts.put(ctx,blink+"div	$"+expr2+", $"+expr1+"\n");
					}
				} 
				else if(op.equals("%"))
				{
					if(map.containsKey(expr1) == true && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,blink+"mod	"+expr2_str+", "+expr1_str+"\n");
					}
					else if(map.containsKey(expr1) == false && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,blink+"mod	"+expr2_str+", $"+expr1+"\n");
					}
					else if(map.containsKey(expr1) == true && map.containsKey(expr2) == false)
					{
						newTexts.put(ctx,blink+"mod	$"+expr2+", "+expr1_str+"\n");
					} 
					else
					{
						newTexts.put(ctx,blink+"mod	$"+expr2+", $"+expr1+"\n");
					}
				} 
				else if(op.equals("or"))
				{
					if(map.containsKey(expr1) == true && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"or	"+expr2_str+", "+expr1_str+"\n");
					}
					else if(map.containsKey(expr1) == false && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"or	"+expr2_str+", $"+expr1+"\n");
					} 
					else if(map.containsKey(expr1) == true && map.containsKey(expr2) == false)
					{
						newTexts.put(ctx,"or	$"+expr2+", "+expr1_str+"\n");
					}
					else
					{
						newTexts.put(ctx,"or	$"+expr2+", $"+expr1+"\n");
					}
				} 
				else if(op.equals("and"))
				{
					if(map.containsKey(expr1) == true && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"and	"+expr2_str+", "+expr1_str+"\n");
					}
					else if(map.containsKey(expr1) == false && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"and	"+expr2_str+", $"+expr1+"\n");
					} 
					else if(map.containsKey(expr1) == true && map.containsKey(expr2) == false)
					{
						newTexts.put(ctx,"and	$"+expr2+", "+expr1_str+"\n");
					}
					else
					{
						newTexts.put(ctx,"and	$"+expr2+", $"+expr1+"\n");
					}
				} 
				else if(op.equals("<="))
				{
					op_comm = "jle";
					
					if(map.containsKey(expr1) == true && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"cmpl	"+expr2_str+", "+expr1_str+"\n"+blink+op_comm);
					}
					else if(map.containsKey(expr1) == false && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"cmpl	"+expr2_str+", $"+expr1+"\n"+blink+op_comm );
					}
					else if(map.containsKey(expr1) == true && map.containsKey(expr2) == false)
					{
						newTexts.put(ctx,"cmpl	$"+expr2+", "+expr1_str+"\n"+blink+op_comm );
					}
					else
					{
						newTexts.put(ctx,"cmpl	$"+expr2+", $"+expr1+"\n"+blink+op_comm );
					}
				}
				else if(op.equals(">="))
				{
					op_comm = "jge";
					
					if(map.containsKey(expr1) == true && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"cmpl	"+expr2_str+", "+expr1_str+"\n"+blink+op_comm);
					}
					else if(map.containsKey(expr1) == false && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"cmpl	"+expr2_str+", $"+expr1+"\n"+blink+op_comm );
					} 
					else if(map.containsKey(expr1) == true && map.containsKey(expr2) == false)
					{
						newTexts.put(ctx,"cmpl	$"+expr2+", "+expr1_str+"\n"+blink+op_comm );
					} 
					else
					{
						newTexts.put(ctx,"cmpl	$"+expr2+", $"+expr1+"\n"+blink+op_comm );
					}
				} else if(op.equals("=="))
				{
					op_comm = "je";
					
					if(map.containsKey(expr1) == true && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"cmpl	"+expr2_str+", "+expr1_str+"\n"+blink+op_comm);
					}
					else if(map.containsKey(expr1) == false && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"cmpl	"+expr2_str+", $"+expr1+"\n"+blink+op_comm );
					} 
					else if(map.containsKey(expr1) == true && map.containsKey(expr2) == false)
					{
						newTexts.put(ctx,"cmpl	$"+expr2+", "+expr1_str+"\n"+blink+op_comm );
					} 
					else
					{
						newTexts.put(ctx,"cmpl	$"+expr2+", $"+expr1+"\n"+blink+op_comm );
					}
				} 
				else if(op.equals("!="))
				{
					op_comm = "jne";
					
					if(map.containsKey(expr1) == true && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"cmpl	"+expr2_str+", "+expr1_str+"\n"+blink+op_comm);
					}
					else if(map.containsKey(expr1) == false && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"cmpl	"+expr2_str+", $"+expr1+"\n"+blink+op_comm );
					} 
					else if(map.containsKey(expr1) == true && map.containsKey(expr2) == false)
					{
						newTexts.put(ctx,"cmpl	$"+expr2+", "+expr1_str+"\n"+blink+op_comm );
					}
					else
					{
						newTexts.put(ctx,"cmpl	$"+expr2+", $"+expr1+"\n"+blink+op_comm );
					}
				} 
				else if(op.equals("<"))
				{
					op_comm = "jl";
					
					if(map.containsKey(expr1) == true && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"cmpl	"+expr2_str+", "+expr1_str+"\n"+blink+op_comm);
					}
					else if(map.containsKey(expr1) == false && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"cmpl	"+expr2_str+", $"+expr1+"\n"+blink+op_comm );
					}
					else if(map.containsKey(expr1) == true && map.containsKey(expr2) == false)
					{
						newTexts.put(ctx,"cmpl	$"+expr2+", "+expr1_str+"\n"+blink+op_comm );
					} 
					else
					{
						newTexts.put(ctx,"cmpl	$"+expr2+", $"+expr1+"\n"+blink+op_comm );
					}
				} 
				else if(op.equals(">"))
				{
					op_comm = "jg";
					
					if(map.containsKey(expr1) == true && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"cmpl	"+expr2_str+", "+expr1_str+"\n"+blink+op_comm);
					}
					else if(map.containsKey(expr1) == false && map.containsKey(expr2) == true)
					{
						newTexts.put(ctx,"cmpl	"+expr2_str+", $"+expr1+"\n"+blink+op_comm );
					} 
					else if(map.containsKey(expr1) == true && map.containsKey(expr2) == false)
					{
						newTexts.put(ctx,"cmpl	$"+expr2+", "+expr1_str+"\n"+blink+op_comm );
					} 
					else
					{
						newTexts.put(ctx,"cmpl	$"+expr2+", $"+expr1+"\n"+blink+op_comm );
					}
				}
			}
		}
		else if(ctx.getChildCount() == 3 )
		{
			expr1 = newTexts.get(ctx.expr(0));
			newTexts.put(ctx, expr1);
		}
		else if (ctx.getChildCount() == 4 && ctx.getChild(2) instanceof MiniCParser.ExprContext)
		{
			ident = ctx.IDENT().getText();
			String var_val = map.get(ident);
			String mString = "";
			expr1 = newTexts.get(ctx.expr(0));
			int index = Integer.parseInt(expr1);
			
			if(gmap.get(ident) != null)
			{
				mString += "["+ident+"+"+(4*index)+"]";
			}
			else
			{
				mString += "["+var_val+"+"+(4*index)+"]";
			}
			
			newTexts.put(ctx, mString);
		}
		else if (ctx.getChildCount() == 4 && ctx.getChild(2) instanceof MiniCParser.ArgsContext)
		{
			ident = ctx.IDENT().getText();
			String mString = "";
			int cnt = 0;
			
			if(ctx.args().getChildCount() != 0)
			{
				for(int i = 0; i<ctx.args().getChildCount(); i++)
				{
					if(map.get(ctx.args().getChild(i).getText()) != null)
					{
						mString +=blink+"pushl	"+map.get(ctx.args().getChild(i).getText())+"\n";
						cnt++;
					}
					else if(i%2 == 0)
					{
						mString +=blink+"pushl	$"+ctx.args().getChild(i).getText()+"\n";
						cnt++;
					}
						
				}
			}
			
			newTexts.put(ctx, mString+blink+"call	"+ident+"\n"+blink+"add	$"+(4*cnt)+", %esp\n");
		}
		else if(ctx.getChildCount() == 2)
		{
			expr1 = newTexts.get(ctx.expr(0));
			
			if(ctx.getChild(0).getText().equals("!"))
			{
				newTexts.put(ctx, blink+"not	"+expr1+"\n");
			}
			else if(ctx.getChild(0).getText().equals("-"))
			{
				newTexts.put(ctx, blink+"neg	"+expr1+"\n");
			}
			else 
			{
				newTexts.put(ctx, expr1);
			}
		}
		else if(ctx.getChildCount() == 6)
		{
			String mString = "";
			ident = ctx.IDENT().getText();
			expr1 = newTexts.get(ctx.expr(0));
			int index = Integer.parseInt(expr1);
			expr2 = newTexts.get(ctx.expr(1));
			
			if(map.get(expr2) != null)
			{
				if(gmap.get(ident) != null)
				{
					mString +=blink+"movl	"+map.get(expr2)+", "+"["+ident+"+"+(4*index)+"]\n";
				}
				else
				{
					mString +=blink+"movl	"+map.get(expr2)+", "+"["+map.get(ident)+"+"+(4*index)+"]\n";
				}
			}
			else
			{
				if(gmap.get(ident) != null)
				{
					mString +=blink+"movl	$"+expr2+", "+"["+ident+"+"+(4*index)+"]\n";
				}
				else
				{
					mString +=blink+"movl	$"+expr2+", "+"["+map.get(ident)+"+"+(4*index)+"]\n";
				}
			}
			
			newTexts.put(ctx, mString);
		}
		else 
		{
			newTexts.put(ctx, ctx.getChild(0).getText());
		}
	}
	
	@Override public void exitArgs(MiniCParser.ArgsContext ctx) 
	{
		String expr = newTexts.get(ctx.expr(0));
		int expr_cnt = ctx.expr().size();
		
		for(int i=1; i<expr_cnt ; i++)
		{
			expr += newTexts.get(ctx.expr(i));
		}
		
		newTexts.put(ctx, expr);
	}
}