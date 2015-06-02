package async.draft.webapp;

public interface IDraftDAO 
{
	public void create(Draft draft);
	public Draft getDraftByID(int id);
}
