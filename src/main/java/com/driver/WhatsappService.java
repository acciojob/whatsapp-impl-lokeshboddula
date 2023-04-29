package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WhatsappService {
    HashMap<String,User> userHashMap=new HashMap<>(); //key as mobile
    HashMap<Group, List<User>> groupHashMap=new HashMap<>(); //group Name as key
    HashMap<Group,List<Message>> messagesInGroup=new HashMap<>();
    List<Message> messageList=new ArrayList<>();
    HashMap<User,List<Message>> userMessageList=new HashMap<>();

    private int groupCount=0;
    private int messageCount=0;

    public String  createUser(String name,String mobile)throws Exception{

        if(userHashMap.containsKey(mobile))
        {
            throw new Exception("User already exists");
        }

        User user=new User(name, mobile);
        userHashMap.put(mobile,user);

        return "SUCCESS";
    }
    public Group createGroup(List<User> users){

        if(users.size()==2)
        {
            Group group=new Group(users.get(1).getName(),2);
            groupHashMap.put(group,users);
            return group;
        }
        Group group=new Group("Group "+ ++groupCount,users.size());
        groupHashMap.put(group,users);
        return group;
    }
    public int createMessage(String content)
    {
        Message message=new Message(++messageCount,content);
        message.setTimestamp(new Date());
        messageList.add(message);
        return messageCount;
    }
    public int sendMessage(Message message,User sender,Group group)throws Exception{

        if(!groupHashMap.containsKey(group))
        {
            throw new Exception("Group does not exist");
        }
        boolean senderExist=false;
        for(User user:groupHashMap.get(group))
        {
            if(user.equals(sender))
            {
                senderExist=true;   break;
            }
        }
        if(!senderExist)
        {
            throw new Exception("You are not allowed to send message");
        }

        if(messagesInGroup.containsKey(group))
        {
            messagesInGroup.get(group).add(message);
        }
        else
        {
            List<Message> messages=new ArrayList<>();
            messages.add(message);
            messagesInGroup.put(group,messages);
        }

        if(userMessageList.containsKey(sender))
        {
            userMessageList.get(sender).add(message);
        }
        else
        {
            List<Message> messages=new ArrayList<>();
            messages.add(message);
            userMessageList.put(sender,messages);
        }

        return messagesInGroup.get(group).size();
    }
    public String changeAdmin(User approver, User user, Group group)throws Exception{

        if(!groupHashMap.containsKey(group))
        {
            throw new Exception("Group does not exist");
        }

        User pastAdmin=groupHashMap.get(group).get(0);

        if(!approver.equals(pastAdmin))
        {
            throw new Exception("Approver does not have rights");
        }

        boolean check=false;
        for(User user1:groupHashMap.get(group))
        {
            if(user1.equals(user))   check=true;
        }

        if(!check)
        {
            throw new Exception("User is not a participant");
        }

        User newAdmin=null;

        Iterator<User> userIterator = groupHashMap.get(group).iterator();

        while(userIterator.hasNext())
        {
            User u= userIterator.next();
            if(u.equals(user))
            {
                newAdmin = u;
                userIterator.remove();
            }
        }

        groupHashMap.get(group).add(0,newAdmin);
        return  "SUCCESS";

    }
    public int removeUser(User user)throws Exception {
        //A user belongs to exactly one group
        //If user is not found in any group, throw "User not found" exception
        //If user is found in a group & it is the admin, throw "Cannot remove admin" exception
        //If user is not the admin, remove the user from the group, remove all its messages from all the databases, and update relevant attributes accordingly.
        //If user is removed successfully, return (the updated number of users in the group + the updated number of messages in group + the updated number of overall messages)

        boolean userFound = false;
        int groupSize = 0;
        int messageCount = 0;
        int overallMessageCount = messageList.size();
        Group groupToRemoveFrom = null;
        for (Map.Entry<Group, List<User>> entry : groupHashMap.entrySet()) {
            List<User> groupUsers = entry.getValue();
            if (groupUsers.contains(user))
            {
                userFound = true;
                groupToRemoveFrom = entry.getKey();
                if (groupUsers.get(0).equals(user))
                {
                    throw new Exception("Cannot remove admin");
                }
                groupUsers.remove(user);
                groupSize = groupUsers.size();
                break;
            }
        }
        if (!userFound)
        {
            throw new Exception("User not found");
        }

        if (userMessageList.containsKey(user))
        {
            messageCount = userMessageList.get(user).size() - 2;
            userMessageList.remove(user);
        }


        return groupSize + messageCount + overallMessageCount;

    }
    public String findMessage(Date start, Date end, int k) {

        return "Will try for sure";
    }


}
