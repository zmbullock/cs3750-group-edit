package database;

public class DatabaseManager
{
  DBinterface db = new DBinterface();
  public DatabaseManager()
  {

  }

  public boolean updateText(String groupName, int textAreaNumber, String text)
  {
    db.update(groupName + textAreaNumber, text);

    // add error checking
    return true;
  }

  public int getNumOfTextAreas(String groupName)
  {
    // return 4 until dynamic content added
    return 4;

  }

  public String getText(String groupName, int textAreaNumber)
  {
    String groupTextArea = groupName + textAreaNumber;
    return db.get(groupTextArea).get(groupTextArea);
  }
}
