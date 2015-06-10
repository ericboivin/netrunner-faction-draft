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
import async.draft.webapp.IdentityMap;
import async.draft.webapp.IdentityManager;

/**
 * Servlet implementation class SelectIdentity
 */
@WebServlet("/selectIdentity")
public class SelectIdentity extends HttpServlet {
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
		String code = request.getParameter("code");
		if (request.getParameter("confirm") != null && request.getParameter("confirm").equals("ok")) {
			DraftPick pick = (DraftPick) request.getSession().getAttribute("pick");
			Draft draft = (Draft) request.getSession().getAttribute("draft");
			
			if (DraftManager.getInstance().isTaken(draft, code)){
				request.setAttribute("message", "This identity has already been claimed");
				request.setAttribute("draft", draft);
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/showdraft.jsp");
				dispatcher.forward(request, response);
			}else{
			

				System.out.println(draft.isIdentityTaken(code));
				
				pick.setPick(idList.get(request.getParameter("code")).getCode());
				
				DraftManager.getInstance().savePick(pick, draft);
	
				request.setAttribute("message", "Pick registered");
				request.setAttribute("draft", draft);
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/showdraft.jsp");
				dispatcher.forward(request, response);
			}
		} else {

			request.setAttribute("identity", idList.get(code));
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/confirm.jsp");
			dispatcher.forward(request, response);
		}
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
