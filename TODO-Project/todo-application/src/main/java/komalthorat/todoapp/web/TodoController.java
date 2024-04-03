package komalthorat.todoapp.web;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import komalthorat.todoapp.dao.TodoDao;
import komalthorat.todoapp.dao.TodoDaoImpl;
import komalthorat.todoapp.model.Todo;
import komalthorat.todoapp.utils.JDBCUtils;

/*
 * ControllerServlet.java This servlet acts as a page controller for the
 * application, handling all requests from the todo.
 */

@WebServlet("/")
public class TodoController extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    private TodoDao todoDAO;
    
   public void init() {
	   todoDAO = new TodoDaoImpl();
   }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getServletPath();
		
		try {
			switch (action) 
			{
				case "/new":
					showNewForm(request,response);
					break;
					
				case "/insert":
					insertTodo(request,response);
					break;
					
				case "/delete":
					deleteTodo(request,response);
					break;
					
				case "/edit":
					showEditForm(request,response);
					break;
					
				case "/update":
					updateTodo(request,response);
					break;
					
				case "/list":
					listTodo(request,response);
					break;
					
				default:
					RequestDispatcher rd = request.getRequestDispatcher("login/login.jsp");
					rd.forward(request, response);
					break;
			}
		}catch(SQLException e) {
			throw new ServletException(e);
		}
	}

	private void listTodo(HttpServletRequest request, HttpServletResponse response) throws  IOException,SQLException, ServletException {
		
		List<Todo> listTodo = todoDAO.selectAllTodos();
		request.setAttribute("listTodo", listTodo);
		RequestDispatcher rd = request.getRequestDispatcher("login/todo-list.jsp");
		rd.forward(request, response);
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,SQLException {
		RequestDispatcher rd = request.getRequestDispatcher("login/todo-form.jsp");
		rd.forward(request, response);		
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws  IOException,SQLException, ServletException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		Todo existingTodo  =  todoDAO.selectTodo(id);
		RequestDispatcher rd = request.getRequestDispatcher("login/todo-form.jsp");
		request.setAttribute("todo", existingTodo);
		rd.forward(request, response);
	}


	private void insertTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		
		String title = request.getParameter("title");
//		String username = (String) request.getSession().getAttribute("username");
		String username = request.getParameter("username");
		String description = request.getParameter("description");
		//LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"));
		boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
		
		Todo newTodo = new Todo(title,username,description,LocalDate.now(),isDone);
		
		todoDAO.insertTodo(newTodo);
		response.sendRedirect("list");
//		try{
//			todoDao.insertTodo(newTodo);
//			response.sendRedirect("list");
//		}catch (SQLException e) {
//			e.printStackTrace();
//			JDBCUtils.printSQLException(e);
//			response.sendRedirect("new");
//		}
	}

	
	private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException ,SQLException{
		int id = Integer.parseInt(request.getParameter("id"));
		
		String title = request.getParameter("title");
		String username = request.getParameter("username");
		String description = request.getParameter("description");
		LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"));
		
		boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
		Todo updateTodo = new Todo(id,title,username,description,targetDate,isDone);
		
		todoDAO.updateTodo(updateTodo);
		response.sendRedirect("list");
		
	}

	

	private void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		todoDAO.deleteTodo(id);
		response.sendRedirect("list");
	}



}
