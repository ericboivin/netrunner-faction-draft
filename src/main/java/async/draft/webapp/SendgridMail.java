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
		email.setHtml("<html><body>Hi "+player.getName()+"! It's your turn to select your pick for the Netrunner draft. Click the following link to get to the page and do your pick.<br /><br /><a href=\"https://netrunner-faction-draft.herokuapp.com/identitydraft?draft="+draft+"&token="+token+"\">Do your pick</a><br /><br /><a href=\"https://netrunner-faction-draft.herokuapp.com/showdraft?draft="+draft+"\">Click here to see all picks and the draft order</a></body></html>");

		try {
			SendGrid.Response response = sendgrid.send(email);
		} catch (SendGridException e) {
			System.out.println(e);
		}
	}
}
