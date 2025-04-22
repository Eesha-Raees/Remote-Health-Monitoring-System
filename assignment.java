import java.util.Date;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

// Interface
interface Notifiable {
    void sendNotification(String message);
}

// Emergency Alert System
class EmergencyAlert {
    private String patientId;
    private String alertMessage;
    private Date alertTime;
    private boolean isCritical;

    public EmergencyAlert(String patientId, String alertMessage, boolean isCritical) {
        this.patientId = patientId;
        this.alertMessage = alertMessage;
        this.isCritical = isCritical;
        this.alertTime = new Date();
    }

    public String getPatientId() { return patientId; }
    public String getAlertMessage() { return alertMessage; }
    public Date getAlertTime() { return alertTime; }
    public boolean isCritical() { return isCritical; }
}

class NotificationService {
    private Notifiable notifiable;

    public NotificationService(Notifiable notifiable) {
        this.notifiable = notifiable;
    }

    public void sendAlert(EmergencyAlert alert) {
        String message = "ALERT for Patient " + alert.getPatientId() + 
                        ": " + alert.getAlertMessage() + 
                        " at " + alert.getAlertTime();
        notifiable.sendNotification(message);
    }
}

class PanicButton {
    private String patientId;
    private NotificationService notificationService;

    public PanicButton(String patientId, NotificationService notificationService) {
        this.patientId = patientId;
        this.notificationService = notificationService;
    }

    public void triggerEmergency() {
        EmergencyAlert alert = new EmergencyAlert(patientId, 
                                "Patient triggered panic button!", true);
        notificationService.sendAlert(alert);
    }
}

// Chat & Video Consultation
class ChatServer {
    private List<String> messages = new ArrayList<>();

    public void sendMessage(String sender, String recipient, String message) {
        String formattedMsg = sender + " to " + recipient + ": " + message;
        messages.add(formattedMsg);
        System.out.println("Message stored: " + formattedMsg);
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }
}

class ChatClient {
    private String userId;
    private ChatServer chatServer;

    public ChatClient(String userId, ChatServer chatServer) {
        this.userId = userId;
        this.chatServer = chatServer;
    }

    public void sendMessage(String recipient, String message) {
        chatServer.sendMessage(userId, recipient, message);
    }
}

class VideoCall {
    private String patientId;
    private String doctorId;
    private String meetingLink;

    public VideoCall(String patientId, String doctorId) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.meetingLink = generateMeetingLink();
    }

    private String generateMeetingLink() {
        return "https://meet.google.com/" + 
               UUID.randomUUID().toString().substring(0, 8);
    }

    public void startCall() {
        System.out.println("Starting video call between " + patientId + 
                          " and " + doctorId);
        System.out.println("Join at: " + meetingLink);
    }
}

// Notifications & Reminders
class EmailNotification implements Notifiable {
    private String emailAddress;

    public EmailNotification(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("Sending email to " + emailAddress + ": " + message);
    }
}

class SMSNotification implements Notifiable {
    private String phoneNumber;

    public SMSNotification(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }
}

class ReminderService {
    private Notifiable notifiable;

    public ReminderService(Notifiable notifiable) {
        this.notifiable = notifiable;
    }

    public void sendReminder(String recipient, String reminderMessage, Date time) {
        String message = "REMINDER: " + reminderMessage + " at " + time;
        notifiable.sendNotification(message);
    }
}

// Main Application
public class Main {
    public static void main(String[] args) {
        // Testing Emergency Alert System
        System.out.println("=== Testing Emergency Alert System ===");
        Notifiable emailNotifier = new EmailNotification("doctor@hospital.com");
        NotificationService notificationService = new NotificationService(emailNotifier);
        
        EmergencyAlert alert = new EmergencyAlert("PAT123", "High blood pressure detected!", true);
        notificationService.sendAlert(alert);
        
        PanicButton panicButton = new PanicButton("PAT123", notificationService);
        panicButton.triggerEmergency();
        
        // Testing Chat & Video Consultation
        System.out.println("\n=== Testing Chat & Video Consultation ===");
        ChatServer chatServer = new ChatServer();
        ChatClient patientChat = new ChatClient("PAT123", chatServer);
        ChatClient doctorChat = new ChatClient("DOC456", chatServer);
        
        patientChat.sendMessage("DOC456", "I'm not feeling well today");
        doctorChat.sendMessage("PAT123", "Please schedule a video call");
        
        VideoCall videoCall = new VideoCall("PAT123", "DOC456");
        videoCall.startCall();
        
        // Testing Notifications & Reminders
        System.out.println("\n=== Testing Notifications & Reminders ===");
        Notifiable smsNotifier = new SMSNotification("+1234567890");
        ReminderService reminderService = new ReminderService(smsNotifier);
        
        reminderService.sendReminder("PAT123", "Take your medication", 
                                   new Date(System.currentTimeMillis() + 3600000));
    }
}