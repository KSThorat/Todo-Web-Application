package komalthorat.todoapp.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import komalthorat.todoapp.dao.UserDao;
import komalthorat.todoapp.model.User;

@WebServlet("/register")
public class UserController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
    
	public void init(){
		userDao = new UserDao();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("register/register.jsp");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		register(request,response);
	}
	
	
	private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User employee = new User();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setUsername(username);
		employee.setPassword(password);
		
		try {
			int result = userDao.registerEmployee(employee);
			if(result ==1)
			{
				request.setAttribute("NOTIFICATION", "User Registered Successfully!");
			}else {
				request.setAttribute("NOTIFICATION", "User Registration Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("NOTIFICATION", "An error occured during user registration.");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("register/register.jsp");
		rd.forward(request, response);
	}

}
