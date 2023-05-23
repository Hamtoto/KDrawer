package kdr.net;

class Message
{
    private String who;
    private String message;

    Message(String who, String message) {
        this.who = who;
        this.message = message;
    }
    String getWho() {
        return who;
    }
    String getMessage() {
        return message;
    }
}