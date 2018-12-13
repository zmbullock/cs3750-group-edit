package database;

public class DatabaseManager
{
  DBinterface db = new DBinterface();
  public DatabaseManager()
  {

  }

  public boolean updateText(String groupName, int textAreaNumber, String text)
  {
    System.out.printf("updateText - groupName: [%s]\n", groupName);
    System.out.printf("updateText - textAreaNumber: [%d]\n", textAreaNumber);
    System.out.printf("updateText - text: [%s]\n", text);
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
    System.out.printf("getText - groupName: [%s]\n", groupName);
    System.out.printf("getText - textAreaNumber: [%d]\n", textAreaNumber);
    String rString = db.get(groupTextArea).get(groupTextArea);
    System.out.printf("getText - rString: [%s]\n", rString);
    return rString;
  }
}
