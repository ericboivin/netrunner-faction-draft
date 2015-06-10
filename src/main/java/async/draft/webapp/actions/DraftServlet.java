package async.draft.webapp.actions;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import async.draft.webapp.Draft;
import async.draft.webapp.DraftManager;
import async.draft.webapp.DraftPick;
import async.draft.webapp.IdentityManager;
import async.draft.webapp.IdentityMap;

/**
 * Servlet implementation class CCC
 */
@WebServlet("/identitydraft")
public class DraftServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IdentityMap idList;

	public void init() {
		idList = IdentityManager.getInstance().getAllIds();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("token") != null
				&& !request.getParameter("token").isEmpty() && request.getParameter("draft") != null
						&& !request.getParameter("draft").isEmpty()) {
			Draft draft = DraftManager.getInstance().getDraft(request.getParameter("draft"));
			DraftPick pick = DraftManager.getInstance().getPick(
					draft, request.getParameter("token"));
			if (pick == null){
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			}else {
				if (pick.getPick() == null){
					request.getSession().setAttribute("pick",
							pick);
					request.getSession().setAttribute("draft",
							draft);
					request.setAttribute("pick", pick);
					request.setAttribute(
							"idList",
							DraftManager.getInstance().filterTaken(draft,
									idList.getAllSideIdentities(pick.getSide())));
				}else{
					request.setAttribute("message", "Sorry, you've already made your pick");
					request.setAttribute("draft", DraftManager.getInstance().getDraft(request.getParameter("code")));
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/showdraft.jsp");
					dispatcher.forward(request, response);
				}
			}
		} else {
			//Show nothing
		}
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/draft.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
