package async.draft.webapp;

public interface IDraftDAO 
{
	public void saveDraft(Draft draft);
	public Draft getDraft(String code);
}
