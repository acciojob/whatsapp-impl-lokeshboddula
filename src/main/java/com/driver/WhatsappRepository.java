/* package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class WhatsappRepository {
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String createUser(String name, String mobile) throws Exception {

        if(userMobile.contains(mobile)){
            throw new Exception("User already exists");
        }
        userMobile.add(mobile);
        User user = new User(name, mobile);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users){

        if(users.size()==2){
            Group group = new Group(users.get(1).getName(), 2);
            adminMap.put(group, users.get(0));
            groupUserMap.put(group, users);
            groupMessageMap.put(group, new ArrayList<Message>());
            return group;
        }
        this.customGroupCount += 1;
        Group group = new Group(new String("Group "+this.customGroupCount), users.size());
        adminMap.put(group, users.get(0));
        groupUserMap.put(group, users);
        groupMessageMap.put(group, new ArrayList<Message>());
        return group;
    }

    public int createMessage(String content){

        this.messageId += 1;
        Message message = new Message(messageId, content);
        return message.getId();
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{

        if(adminMap.containsKey(group)){
            List<User> users = groupUserMap.get(group);
            Boolean userFound = false;
            for(User user: users){
                if(user.equals(sender)){
                    userFound = true;
                    break;
                }
            }
            if(userFound){
                senderMap.put(message, sender);
                List<Message> messages = groupMessageMap.get(group);
                messages.add(message);
                groupMessageMap.put(group, messages);
                return messages.size();
            }
            throw new Exception("You are not allowed to send message");
        }
        throw new Exception("Group does not exist");
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        if(adminMap.containsKey(group)){
            if(adminMap.get(group).equals(approver)){
                List<User> participants = groupUserMap.get(group);
                Boolean userFound = false;
                for(User participant: participants){
                    if(participant.equals(user)){
                        userFound = true;
                        break;
                    }
                }
                if(userFound){
                    adminMap.put(group, user);
                    return "SUCCESS";
                }
                throw new Exception("User is not a participant");
            }
            throw new Exception("Approver does not have rights");
        }
        throw new Exception("Group does not exist");
    }

    public int removeUser(User user) throws Exception{
        Boolean userFound = false;
        Group userGroup = null;
        for(Group group: groupUserMap.keySet()){
            List<User> participants = groupUserMap.get(group);
            for(User participant: participants){
                if(participant.equals(user)){
                    if(adminMap.get(group).equals(user)){
                        throw new Exception("Cannot remove admin");
                    }
                    userGroup = group;
                    userFound = true;
                    break;
                }
            }
            if(userFound){
                break;
            }
        }
        if(userFound){
            List<User> users = groupUserMap.get(userGroup);
            List<User> updatedUsers = new ArrayList<>();
            for(User participant: users){
                if(participant.equals(user))
                    continue;
                updatedUsers.add(participant);
            }
            groupUserMap.put(userGroup, updatedUsers);

            List<Message> messages = groupMessageMap.get(userGroup);
            List<Message> updatedMessages = new ArrayList<>();
            for(Message message: messages){
                if(senderMap.get(message).equals(user))
                    continue;
                updatedMessages.add(message);
            }
            groupMessageMap.put(userGroup, updatedMessages);

            HashMap<Message, User> updatedSenderMap = new HashMap<>();
            for(Message message: senderMap.keySet()){
                if(senderMap.get(message).equals(user))
                    continue;
                updatedSenderMap.put(message, senderMap.get(message));
            }
            senderMap = updatedSenderMap;
            return updatedUsers.size()+updatedMessages.size()+updatedSenderMap.size();
        }
        throw new Exception("User not found");
    }

    public String findMessage(Date start, Date end, int K) throws Exception{
        List<Message> messages = new ArrayList<>();
        for(Group group: groupMessageMap.keySet()){
            messages.addAll(groupMessageMap.get(group));
        }
        List<Message> filteredMessages = new ArrayList<>();
        for(Message message: messages){
            if(message.getTimestamp().after(start) && message.getTimestamp().before(end)){
                filteredMessages.add(message);
            }
        }
        if(filteredMessages.size() < K){
            throw new Exception("K is greater than the number of messages");
        }
        Collections.sort(filteredMessages, new Comparator<Message>(){
            public int compare(Message m1, Message m2){
                return m2.getTimestamp().compareTo(m1.getTimestamp());
            }
        });
        return filteredMessages.get(K-1).getContent();
    }
} */

