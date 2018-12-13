package groupManager;

import java.util.*;

import javax.websocket.Session;

public class GroupManager
{
  private class GroupTextArea
  {
    public GroupTextArea(String g, int n)
    {
      group = g;
      textAreaNumber = n;
    }
    public String group;
    public int textAreaNumber;
  }

  Map<Session, String> userToGroupMap;
  Map<String, List<Session>> groupToUserListMap;
  Map<String, Session> textAreaToIsLockedMap;
  public GroupManager()
  {
    userToGroupMap = new HashMap<>();
    groupToUserListMap = new HashMap<>();
    groupToUserListMap.put("ungrouped", new ArrayList<>());
    textAreaToIsLockedMap = new HashMap<>();
  }

  public void onOpen(Session user)
  {
    userToGroupMap.put(user, "ungrouped");
    groupToUserListMap.get("ungrouped").add(user);
  }

  public void onClose(Session user)
  {
    userToGroupMap.put(user, null);
    List<String> removeGroupList = new ArrayList<>();
    List<String> removeTextAreaList = new ArrayList<>();
    for(Map.Entry<String, List<Session>> entry : groupToUserListMap.entrySet())
    {
//      job.add(entry.getKey(), entry.getValue());
        if(entry.getValue().contains(user))
        {
          removeGroupList.add(entry.getKey());
        }
    }
    for(Map.Entry<String, Session> entry : textAreaToIsLockedMap.entrySet())
    {
//      job.add(entry.getKey(), entry.getValue());
        if(entry.getValue() == user)
        {
          removeTextAreaList.add(entry.getKey());
        }
    }
    for(String group : removeGroupList)
    {
      groupToUserListMap.get(group).remove(user);
    }
    for(String textArea : removeTextAreaList)
    {
      textAreaToIsLockedMap.remove(textArea);
    }
  }
  
  public void onSetGroup(Session user, String group)
  {
    String oldGroup = userToGroupMap.get(user);
    userToGroupMap.put(user, group);
    if(oldGroup != null)
    {
      groupToUserListMap.get(oldGroup).remove(user);
    }
    if(groupToUserListMap.get(group) == null)
    {
      groupToUserListMap.put(group, new ArrayList<>());
    }
    groupToUserListMap.get(group).add(user);
  }

  public boolean onLockTextArea(Session user, int textAreaNumber)
  {
    String group = userToGroupMap.get(user);
    String groupTextArea = group+"_"+textAreaNumber;
    boolean isLocked = textAreaToIsLockedMap.get(groupTextArea) != null;
    if(isLocked)
    {
      return false;
    }
    textAreaToIsLockedMap.put(groupTextArea, user);
    return true;
  }
  
  public boolean onUnlockTextArea(Session user, int textAreaNumber)
  {
    String group = userToGroupMap.get(user);
    String groupTextArea = group+"_"+textAreaNumber;
    boolean userHasLock = textAreaToIsLockedMap.get(groupTextArea) == user;
    if(userHasLock)
    {
      textAreaToIsLockedMap.put(groupTextArea, null);
      return true;
    }
    return false;
  }

  public boolean hasLock(Session user, String group, int textAreaNumber)
  {
    String groupTextArea = group+"_"+textAreaNumber;
    return textAreaToIsLockedMap.get(groupTextArea) == user;
  }
  
  public boolean isLocked(String group, int textAreaNumber)
  {
    String groupTextArea = group+"_"+textAreaNumber;
    return textAreaToIsLockedMap.get(groupTextArea) != null;
  }

  public List<Session> getUsersFromGroup(String group)
  {
    return groupToUserListMap.get(group);
  }
}
