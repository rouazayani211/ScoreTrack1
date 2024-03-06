package entities;

public class joueur {
    int id,numero,age;
    String nom,prenom,position;
    int id_equipe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

   public int getId_equipe() {
        return id_equipe;
    }

    public void setId_equipe(int id_equipe) {
        this.id_equipe = id_equipe;
    }

    @Override
    public String toString() {
        return "joueur{" +
                "id=" + id +
                ", numero=" + numero +
                ", age=" + age +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", position='" + position + '\'' +
                ", id_equipe='" +id_equipe + '\''+
                '}';
    }

    public joueur() {
    }

    public joueur(int id, int numero, int age, String nom, String prenom, String position, int id_equipe) {
       /* this.id = id;
        this.numero = numero;
        this.age = age;
        this.nom = nom;
        this.prenom = prenom;
        this.position = position;
        this.id_equipe=id_equipe;*/
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.numero = numero;
        this.position = position;
        this.id_equipe=id_equipe;
    }

    public joueur(int numero, int age, String nom, String prenom, String position, int id_equipe) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.numero = numero;
        this.position = position;
        this.id_equipe=id_equipe;
    }
}
