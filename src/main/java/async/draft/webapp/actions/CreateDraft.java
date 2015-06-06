package async.draft.webapp.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import async.draft.webapp.Draft;
import async.draft.webapp.DraftManager;
import async.draft.webapp.Player;

/**
 * Servlet implementation class CreateDraft
 */
@WebServlet(urlPatterns = { "/createdraft" })
public class CreateDraft extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateDraft() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/createdraft.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Player> players = new ArrayList<Player>();

		String[] names = request.getParameterValues("name[]");
		String[] emails = request.getParameterValues("email[]");

		String error = null;

		for (int i = 0; i < names.length; i++) {
			Player p = new Player();
			p.setName(names[i]);
			p.setEmail(emails[i]);
			players.add(p);
			if (emails[i] == null
					|| (names[i].equals("") && !emails[i].equals(""))
					|| (!names[i].equals("") && emails[i].equals(""))) {
				error = "Every field is mandatory";
			} else {
				if (!isValidEmailAddress(emails[i])) {
					error = "Invalid email address";
				}
			}
		}

		if (error == null){
			Draft draft = DraftManager.getInstance().createDraft(players);
			
			request.setAttribute("message", "Draft created");
			request.setAttribute("draft", draft); 
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/showdraft.jsp");
			dispatcher.forward(request, response);
			
		}else{
			request.setAttribute("error", error);
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/createdraft.jsp");
			dispatcher.forward(request, response);
		}
	}

	private boolean isValidEmailAddress(String email) {
		Pattern pattern = Pattern.compile("^.+@.+\\..+$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
