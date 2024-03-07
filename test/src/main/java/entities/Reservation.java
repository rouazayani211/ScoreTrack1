package entities;

public class Reservation {
    int idReservation , idUser;
    String dateDeReservation, statutReservation;
    int nombreTicket;

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDateDeReservation() {
        return dateDeReservation;
    }

    public void setDateDeReservation(String dateDeReservation) {
        this.dateDeReservation = dateDeReservation;
    }

    public int getNombreTicket() {
        return nombreTicket;
    }

    public void setNombreTicket(int nombreTicket) {
        this.nombreTicket = nombreTicket;
    }

    public String getStatutReservation() {
        return statutReservation;
    }

    public void setStatutReservation(String statutReservation) {
        this.statutReservation = statutReservation;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idReservation=" + idReservation +
                ", idUser=" + idUser +
                ", dateDeReservation='" + dateDeReservation + '\'' +
                ", nombreTicket=" + nombreTicket +
                ", statutReservation='" + statutReservation +
                '}';
    }

    public Reservation(){

    }

    public Reservation(int idReservation , int idUser , String dateDeReservation, int nombreTicket , String StatutReservation){

        this.idReservation=idReservation;
        this.idUser=idUser;
        this.dateDeReservation=dateDeReservation;
        this.nombreTicket=nombreTicket;
        this.statutReservation=StatutReservation;
    }

    public Reservation( int idUser , String dateDeReservation, int nombreTicket, String StatutReservation){

        this.idUser=idUser;
        this.dateDeReservation=dateDeReservation;
        this.nombreTicket=nombreTicket;
        this.statutReservation=StatutReservation;
    }
}
