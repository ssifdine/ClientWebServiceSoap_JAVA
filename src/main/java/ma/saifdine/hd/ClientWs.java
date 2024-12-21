package ma.saifdine.hd;

import proxy.Compte;
import proxy.WebServiceSoapImpl;
import proxy.WebServiceSoapImplService;

import java.util.List;
import java.util.Scanner;

public class ClientWs {

    public static void main(String[] args) {
        try {
            // Initialiser le proxy pour le service Web
            WebServiceSoapImpl proxy = new WebServiceSoapImplService().getWebServiceSoapImplPort();

            // Menu principal
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n=== Menu ===");
                System.out.println("1. Convertir montant en dirhams");
                System.out.println("2. Récupérer un compte par code");
                System.out.println("3. Afficher tous les comptes");
                System.out.println("4. Quitter");
                System.out.print("Choisissez une option : ");

                int choix = scanner.nextInt();

                switch (choix) {
                    case 1:
                        convertirMontant(proxy);
                        break;
                    case 2:
                        afficherCompte(proxy);
                        break;
                    case 3:
                        afficherTousLesComptes(proxy);
                        break;
                    case 4:
                        System.out.println("Au revoir !");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            }
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite : " + e.getMessage());
        }
    }

    // Méthode pour convertir un montant en dirhams
    private static void convertirMontant(WebServiceSoapImpl proxy) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez un montant en euros : ");
        double montantEuro = scanner.nextDouble();
        double montantDH = proxy.conversion(montantEuro);
        System.out.println("Montant en dirhams : " + montantDH);
    }

    // Méthode pour afficher les détails d'un compte par code
    private static void afficherCompte(WebServiceSoapImpl proxy) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le code du compte : ");
        int code = scanner.nextInt();
        try {
            Compte compte = proxy.getCompte(code);
            System.out.println("Code : " + compte.getCode() + ", Solde : " + compte.getSolde());
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération du compte : " + e.getMessage());
        }
    }

    // Méthode pour afficher tous les comptes
    private static void afficherTousLesComptes(WebServiceSoapImpl proxy) {
        try {
            List<Compte> comptes = proxy.getComptes();
            System.out.println("Liste des comptes :");
            for (Compte c : comptes) {
                System.out.println("Code : " + c.getCode() + ", Solde : " + c.getSolde());
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des comptes : " + e.getMessage());
        }
    }
}
