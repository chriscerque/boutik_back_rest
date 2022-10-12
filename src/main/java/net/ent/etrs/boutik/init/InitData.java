package net.ent.etrs.boutik.init;

import com.github.javafaker.Faker;
import net.ent.etrs.boutik.model.entities.*;
import net.ent.etrs.boutik.model.facades.*;
import net.ent.etrs.boutik.utils.Hash;
import org.apache.commons.collections4.IterableUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Startup
@Singleton
public class InitData {

    private Faker faker = Faker.instance(Locale.FRANCE);

    @Inject
    private FacadeCategorie facadeCategorie;

    @Inject
    private FacadeMarque facadeMarque;

    @Inject
    private FacadeProduit facadeProduit;

    @Inject
    private FacadeUser facadeUser;

    @Inject
    private FacadeCommande facadeCommande;

    @PostConstruct
    public void init() {
        try {
            this.initUser();
            this.initCategorie();
            this.initMarque();
            this.initProduit();
            this.initCommandes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUser() throws Exception {
        if (this.facadeUser.count() == 0) {
            User user = new User();
            user.setLogin("USER");
            user.setPassword(Hash.hash("USER"));
            user.setRole(Role.USER);
            this.facadeUser.save(user);

            User admin = new User();
            admin.setLogin("ADMIN");
            admin.setPassword(Hash.hash("ADMIN"));
            admin.setRole(Role.ADMIN);
            this.facadeUser.save(admin);

            for (int i = 0; i < 30; i++) {
                User u = new User();
                u.setLogin("USER" + i);
                u.setPassword(Hash.hash("USER" + i));
                u.setRole(Role.USER);
                this.facadeUser.save(u);
            }
        }
    }

    private void initMarque() {
        try {
            if (this.facadeMarque.count() == 0) {
                Set<Marque> marques = new HashSet<>();
                do {
                    Marque m = new Marque();
                    m.setLibelle(this.faker.company().name());
                    m.setLogo(Base64.getDecoder().decode("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"));
                    marques.add(m);
                } while (marques.size() != 50);

                for (Marque m : marques) {
                    this.facadeMarque.save(m);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCategorie() {
        try {
            if (this.facadeCategorie.count() == 0) {
                Set<Categorie> categories = new HashSet<Categorie>();
                do {
                    Categorie c = new Categorie();
                    c.setLibelle(this.faker.funnyName().name());
                    categories.add(c);
                } while (categories.size() != 50);

                for (Categorie c : categories) {
                    this.facadeCategorie.save(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initProduit() {
        try {
            if (this.facadeProduit.count() == 0) {
                List<Categorie> categorieList = IterableUtils.toList(this.facadeCategorie.findAll());
                List<Marque> marqueList = IterableUtils.toList(this.facadeMarque.findAll());
                Set<Produit> produits = new HashSet<>();
                do {
                    Produit p = new Produit();
                    p.setLibelle(this.faker.commerce().material());
                    p.setDescription(this.faker.lorem().paragraph(2));
                    p.setPrixHT(this.generateRandomFloat(0.99f, 4999.99f));
                    p.setPoidsKg(this.generateRandomFloat(0.1f, 50f));
                    p.setMarque(marqueList.get(this.faker.random().nextInt(0, marqueList.size() - 1)));
                    p.setCategorie(categorieList.get(this.faker.random().nextInt(0, categorieList.size() - 1)));
                    produits.add(p);
                } while (produits.size() != 500);

                for (Produit p : produits) {
                    this.facadeProduit.save(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCommandes() {
        try {
            if (this.facadeCommande.count() == 0) {
                List<Produit> produitList = IterableUtils.toList(this.facadeProduit.findAll());
                List<User> userList = IterableUtils.toList(this.facadeUser.findAllClient());

                Set<Commande> commandes = new HashSet<>();
                do {
                    Commande c = new Commande();
                    c.setCreatedAt(this.generateRandomLocalDateTimePast());
                    c.setUser(userList.get(this.faker.random().nextInt(0, userList.size() - 1)));
                    c.setEtat(EtatCommande.values()[this.faker.random().nextInt(0, EtatCommande.values().length - 1)]);

                    for (int i = 0; i <= this.faker.random().nextInt(1, 15); i++) {
                        c.addProduit(produitList.get(this.faker.random().nextInt(0, produitList.size() - 1)), this.faker.random().nextInt(1, 10));
                    }
                    commandes.add(c);
                } while (commandes.size() != 100);

                for (Commande c : commandes) {
                    this.facadeCommande.save(c);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LocalDateTime generateRandomLocalDateTimePast() {
        LocalDateTime now = LocalDateTime.now();
        int year = 60 * 60 * 24 * 365;
        return now.minusSeconds((long) this.faker.random().nextInt(0, 5 * year));
    }

    private float generateRandomFloat(float min, float max) {
        if (min >= max)
            throw new IllegalArgumentException("max must be greater than min");
        float result = ThreadLocalRandom.current().nextFloat() * (max - min) + min;
        if (result >= max) // correct for rounding
            result = Float.intBitsToFloat(Float.floatToIntBits(max) - 1);

        result = (float) (Math.round(result * 100.0) / 100.0);
        return result;
    }
}
