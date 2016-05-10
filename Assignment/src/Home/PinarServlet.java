package Home;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class PinarServlet
 */
@WebServlet("/PinarServlet")
public class PinarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PinarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    Connection conn=null;
   	Statement stm=null;
   	ResultSet rs=null;
   	ResultSet rs2=null;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//PinarQuery myQuery=new PinarQuery();
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		out.println("<html><body>");
		out.println("<form method='post' action='Hadi'><input type='text' name='searchTerm'><input type='submit' name='submitSearch' value='search'><input type='submit' name='saveButton' value='Save'>");
		out.println("<table border=\"1\" style=\"width:100%\">");
		try {
			conn=(Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/db1", "root", "penguen");
			stm=(Statement) conn.createStatement();
			rs = stm.executeQuery("select * from cats");
			int index=1;
			while(rs.next()){
				out.print("<tr>");
				String name = rs.getObject(1).toString();
			    String gender = rs.getObject(2).toString();
				out.print("<td>"+ gender+"</td>");
				out.print("<td>"+ name+"</td>");
				out.print("<td><input type='checkbox' name='dat' value='"+index+"'></td>");
				out.print("</tr>");
				index++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		out.print("</form>");
		out.println("</table>");
			out.print("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out= response.getWriter();
		String term;
		if(request.getParameter("submitSearch") != null){
			term = request.getParameter("searchTerm");
			out.println("<html><body>");
			out.println("<table border=\"1\" style=\"width:100%\">");
			try {
				//conn=(Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/db1", "root", "penguen");
				stm=(Statement) conn.createStatement();
				rs = stm.executeQuery("select * from cats where item like '%" + term +"%'" );
				int index = 1;
				out.print("<form method='post' action='Hadi'>" +
						"<input type='submit' value='Go Back' name='mainPage'>"+
						"<input type='submit' value='Save' name='saveButton'>");
				while(rs.next()){
					out.print("<tr>");
					String name = rs.getObject(1).toString();
				    String gender = rs.getObject(2).toString();
					out.print("<td>"+ gender+"</td>");
					out.print("<td>"+ name+"</td>");
					out.print("<td><input type='checkbox' name='dat' value='"+index+"'></td>");
					out.print("</tr>");
					index++;
				}
				out.print("</form>");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			out.println("</table>");
				out.print("</body></html>");
		
		}
		else if(request.getParameter("mainPage") != null){
			doGet(request, response);
		}else if(request.getParameter("saveButton") != null){
			HttpSession ses = request.getSession();
			
			try {
				String[] selecteds=request.getParameterValues("dat");
				for(int i=0; i<selecteds.length; i++){
					int index=Integer.parseInt(selecteds[i]);
					rs.absolute(index);
					String item1 = rs.getObject(1).toString();
				    String item2 = rs.getObject(2).toString();
					out.println(item1+" "+item2);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}

}
