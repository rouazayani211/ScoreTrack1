package service;

import Models.Utilisateur;
import utils.MyDB;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ServiceUtilisateur implements IService<Utilisateur> {
    private Connection connection;

    public ServiceUtilisateur() {
        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public void Ajouter(Utilisateur utilisateur) throws SQLException {
        String req = "insert into utilisateur (nom, prenom, date_naiss, email,mdp, role)" +
                " VALUES (?, ?, ?, ?, ?,?)";

        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setString(1, utilisateur.getNom());
            pre.setString(2, utilisateur.getPrenom());
            pre.setString(3, String.valueOf(utilisateur.getDate_naiss()));
            pre.setString(4, utilisateur.getEmail());
            pre.setString(5, utilisateur.getMdp());
            pre.setString(6, "admin");

            pre.executeUpdate();
        }

        // Send registration confirmation email
        sendRegistrationConfirmationEmail(utilisateur);
    }

    public void changePassword(String email, String newPassword) throws SQLException {
        if (doesEmailExist(email)) {
            String req = "UPDATE utilisateur SET mdp = ? WHERE email = ?";
            try (PreparedStatement pre = connection.prepareStatement(req)) {
                pre.setString(1, newPassword);
                pre.setString(2, email);

                pre.executeUpdate();
            }
        } else {
            // Handle the case where the email does not exist
            throw new SQLException("Email not found.");
        }

        // Send password change confirmation email
        Utilisateur user = new Utilisateur();
        user.setEmail(email);
        sendPasswordChangeConfirmationEmail(user);
    }

    @Override
    public void Modifier(Utilisateur utilisateur) throws SQLException {
        String req = "update utilisateur set nom =?,prenom=?,date_naiss=?,email=?,mdp=? ,role=? where id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1, utilisateur.getPrenom());
        pre.setString(2, utilisateur.getPrenom());
        pre.setString(3, utilisateur.getDate_naiss().toString());
        pre.setString(4, utilisateur.getEmail());
        pre.setString(5, utilisateur.getMdp());
        pre.setString(6, "admin");
        pre.setInt(7, utilisateur.getId());
        pre.executeUpdate();

        // Send profile modification confirmation email
        sendProfileModificationConfirmationEmail(utilisateur);
    }

    @Override
    public void Supprimer(Utilisateur utilisateur) throws SQLException {
        String req = "delete from utilisateur where id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, utilisateur.getId());
        pre.executeUpdate();

        // Send account deletion confirmation email
        sendAccountDeletionConfirmationEmail(utilisateur);
    }

    @Override
    public List<Utilisateur> Afficher() throws SQLException {
        String req = "select * from utilisateur";
        Statement ste = connection.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<Utilisateur> list = new ArrayList<>();
        while (res.next()) {
            Utilisateur u = new Utilisateur();
            u.setId(res.getInt(1));
            u.setNom(res.getString(2));
            u.setPrenom(res.getString(3));
            u.setDate_naiss(res.getString("date_naiss"));
            u.setEmail(res.getString("email"));
            u.setMdp(res.getString("mdp"));
            u.setRole(res.getString("role"));
            list.add(u);
        }
        return list;
    }

    public boolean doesEmailExist(String email) throws SQLException {
        String req = "SELECT * FROM utilisateur WHERE email=?";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setString(1, email);

            ResultSet res = pre.executeQuery();
            return res.next();
        }
    }

    public Utilisateur login(String email, String mdp) throws SQLException {
        String req = "SELECT * FROM utilisateur WHERE email=? AND mdp=?";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setString(1, email);
            pre.setString(2, mdp);

            ResultSet res = pre.executeQuery();
            if (res.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(res.getInt(1));
                user.setNom(res.getString(2));
                user.setPrenom(res.getString(3));
                user.setDate_naiss(res.getString(4));
                user.setEmail(res.getString(5));
                user.setMdp(res.getString(6));
                user.setRole(res.getString(7));
                return user;
            }
        }
        return null;
    }

    public void sendRegistrationConfirmationEmail(Utilisateur utilisateur) {
        String to = utilisateur.getEmail();
        String subject = "Registration Confirmation";
        String body = "Dear " + utilisateur.getNom() + ",\n\n"
                + "Thank you for registering with us.\n"
                + "Your account has been successfully created.\n\n"
                + "Best regards,\n"
                + "Your Application Team";

        sendEmail(to, subject, body);
    }

    public void sendPasswordChangeConfirmationEmail(Utilisateur utilisateur) {
        String to = utilisateur.getEmail();
        String subject = "Password Change Confirmation";
        String body = "Dear User,\n\n"
                + "Your password has been successfully changed.\n\n"
                + "Best regards,\n"
                + "Your Application Team";

        sendEmail(to, subject, body);
    }

    public void sendProfileModificationConfirmationEmail(Utilisateur utilisateur) {
        String to = utilisateur.getEmail();
        String subject = "Profile Modification Confirmation";
        String body = "Dear " + utilisateur.getNom() + ",\n\n"
                + "Your profile has been successfully updated.\n\n"
                + "Best regards,\n"
                + "Your Application Team";

        sendEmail(to, subject, body);
    }

    public void sendAccountDeletionConfirmationEmail(Utilisateur utilisateur) {
        String to = utilisateur.getEmail();
        String subject = "Account Deletion Confirmation";
        String body = "Dear User,\n\n"
                + "Your account has been successfully deleted.\n\n"
                + "Best regards,\n"
                + "Your Application Team";

        sendEmail(to, subject, body);
    }

    public void sendEmail(String to, String subject, String body) {
        String username = "your_email@gmail.com";
        String password = "your_email_password";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
