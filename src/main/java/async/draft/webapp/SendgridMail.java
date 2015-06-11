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
		html.append("! It's your turn to select your pick for the Netrunner draft.<br /><br />Click the following link to get to the page and do your pick.<br />https://netrunner-faction-draft.herokuapp.com/identitydraft?draft=");
		html.append(draft);
		html.append("&token=");
		html.append(token);
		html.append("<br /><br />");
		html.append("Click here to see all picks and the draft order: <br />https://netrunner-faction-draft.herokuapp.com/showdraft?draft=");
		html.append(draft);
		html.append("</body></html>");
		
		email.setHtml(html.toString());

		try {
			sendgrid.send(email);
		} catch (SendGridException e) {
			System.out.println(e);
		}
	}
}
