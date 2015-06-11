package async.draft.webapp;

import com.sendgrid.*;

public class SendgridMail {

	public static void sendMail(Player player, String token, String draft){
	
		SendGrid sendgrid = new SendGrid(System.getenv("SENDGRID_USERNAME"),
				System.getenv("SENDGRID_PASSWORD"));

		SendGrid.Email email = new SendGrid.Email();
		email.addTo(player.getEmail());
		email.setFrom("ericboivin@gmail.com");
		email.setSubject("Netrunner Identity Draft");
		StringBuffer html = new StringBuffer("<html><body>Hi ");
		html.append(player.getName());
		html.append("! It's your turn to select your pick for the Netrunner draft. Click the following link to get to the page and do your pick.<br /><br /><a href=\"https://netrunner-faction-draft.herokuapp.com/identitydraft?draft=");
		html.append(draft);
		html.append("&token=");
		html.append(token);
		html.append("\">Do your pick</a><br /><br /><a href=\"https://netrunner-faction-draft.herokuapp.com/showdraft?draft=");
		html.append(draft);
		html.append("\">Click here to see all picks and the draft order</a><br /><br /><small>Problems clicking the link? Use this instead: https://netrunner-faction-draft.herokuapp.com/identitydraft?draft=");
		html.append(draft);
		html.append("&token=");
		html.append(token);
		html.append("</small>");
		html.append("</body></html>");
		
		email.setHtml(html.toString());

		try {
			SendGrid.Response response = sendgrid.send(email);
		} catch (SendGridException e) {
			System.out.println(e);
		}
	}
}
