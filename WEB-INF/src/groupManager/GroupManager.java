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
    System.out.printf("GroupManager.onSetGroup - Old Group: %s\n", oldGroup);
    if(oldGroup != null)
    {
      groupToUserListMap.get(oldGroup).remove(user);
    }
    System.out.printf("GroupManager.onSetGroup - .get(%s) is null: %s\n", group, groupToUserListMap.get(group) == null ? "YES" : "NO");
    if(groupToUserListMap.get(group) == null)
    {
      System.out.println("New group: WHY ARE YOU HERE?\n");
      groupToUserListMap.put(group, new ArrayList<>());
    }
    System.out.printf("Adding user [%s] to group [%s]\n", user.getId(), group);
    groupToUserListMap.get(group).add(user);
    System.out.printf("GroupManager.onSetGroup - Group list should not be null: %s\n", groupToUserListMap.get(group) == null ? "IS NULL" : "IS NOT NULL");
    System.out.printf("GroupManager.onSetGroup - Number of users in group [%s]: %d\n", group, groupToUserListMap.get(group).size());
    for(Session userInGroup : groupToUserListMap.get(group))
    {
      System.out.printf("Printing user [%s] from group [%s]\n", userInGroup.getId(), group);
    }
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
