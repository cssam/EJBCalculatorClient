package ca.bcit.comp4655.ejb.stateless.client.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.bcit.comp4655.ejb.statelss.calculator.Calculator;
import ca.bcit.comp4655.ejb.statelss.calculator.CalculatorRemote;



/**
 * 
 * CalcServlet creates a web client for <code>CalculatorBean</code>.
 */
public class CalcServlet extends HttpServlet
{
	//Using dependency injection to obtain a reference to CalculatorBean
	@EJB( mappedName="CalculatorEJB", beanInterface=CalculatorRemote.class)
	Calculator calc;
	
	private static final long serialVersionUID = -1936970755491232742L;
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException 
	{
		
		/*
		 * In addition to dependency injection using @EJB, We can also use JNDI binding for the remote and local interfaces.
		 *  By default, JBoss will use "ejbName/local" and "ejbName/remote" for local and remote interfaces, respectively.
		 
		Calculator calc = null;
		try {
			calc = getCalculatorBean();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		response.setContentType( "text/html;charset=UTF-8" );

        PrintWriter out = response.getWriter();
        // Output the results
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Calculator</title>");
        out.println("</head>");
        out.println("<body>");
        out.println(
                "<h1>Servlet Add Calculator at " + request.getContextPath()
                + "</h1>");

        try {
	            String operandOne = request.getParameter( "operandOne" );
	            String operandTwo = request.getParameter( "operandTwo" );
	            
	            if ( operandOne!=null && 
	            	 operandTwo!=null &&
	            	 operandOne.length()>0 &&
	            	 operandTwo.length()>0 )
	            {
	            	int operand1 = Integer.parseInt( operandOne );
		            int operand2 = Integer.parseInt( operandTwo );
		            out.println(
	                        "<p> Add Result = " + calc.add( operand1, operand2 )+ "</p>");
		            
	            }
	            else
	            {
	            	 out.println( "<p>Enter two numbers to add:</p>");
	            	 out.println( "<form method=\"get\">" );
	            	 out.println( "<p><input type=\"text\" name=\"operandOne\" size=\"25\"></p>" );
	            	 out.println( "<p><input type=\"text\" name=\"operandTwo\" size=\"25\"></p>" );
	            	 out.println( "<br/>" );
	                 out.println(
	                         "<input type=\"submit\" value=\"Add\">"
	                         + "<input type=\"reset\" value=\"Reset\">" );
	                 out.println( "</form>" );
	            }
        }
        catch ( NumberFormatException nfe )
        {
        	 out.println("<p>Invalid Amounts! try again</p>");
        } 
        finally 
        {
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
	}
	
	@Override
	protected void doPost( HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}
	
	private Calculator getCalculatorBean() throws NamingException
	{
		InitialContext context = new InitialContext();
		Calculator calc = ( Calculator) context.lookup( "CalculatorEJB" );
		
		return calc;
	}
}
