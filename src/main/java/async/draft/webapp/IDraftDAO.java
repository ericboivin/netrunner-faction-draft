package async.draft.webapp;

import java.util.List;

public interface IDraftDAO 
{
	public void create(Draft draft);
	public Draft getDraftByCode(String code);
	public DraftPick getPickByToken(String token);
	public void saveNewPick(DraftPick pick);
	public List<DraftPick> getPicksForDraft(String draftCode);
}
